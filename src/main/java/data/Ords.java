package data;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.exception.GetDataException;
import core.exception.SauvegardeException;
import core.model.DataCar;
import core.model.Instantane;
import org.json.JSONObject;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Properties;

import static core.constant.GraphConstant.FROM_TIME;

public class Ords implements DataCarRepository{
    private final String ip;
    private final String port;
    public Ords() {
        Properties prop = new Properties();
        try (FileInputStream fis = new FileInputStream("data_base.properties")) {
            prop.load(fis);
        }
        catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement de l'application", "Erreur", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        port = prop.getProperty("port");
        ip = prop.getProperty("ip");
    }

    @Override
    public ArrayList<DataCar> getDataFromTimeStamp(int timeStamp) throws GetDataException {

        ArrayList<DataCar> list = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNodeResponse;

        int timestamp2 = timeStamp - FROM_TIME;
        int verifyData = timestamp2;

        do
        {
            String reponse = executeGetRequest("http://" + ip + ":" + port + "/ords/sgbd_b3/getbetween/" + timestamp2 + "/" + timeStamp);

            try {
                jsonNodeResponse = objectMapper.readTree(reponse);
            } catch (JsonProcessingException e) {
                throw new GetDataException("Erreur recuperation donnees");
            }
            JsonNode itemNode = jsonNodeResponse.get("items");

            if(!list.isEmpty())//pagination eviter doublon
            {
                JsonNode node = itemNode.get(0);
                DataCar dataCar = new DataCar(node);
                while(list.getLast().getTimeStamp() == dataCar.getTimeStamp())
                {
                    list.removeLast();
                    node = itemNode.get(0);
                    dataCar = new DataCar(node);
                }
            }

            for(JsonNode node : itemNode)
            {
                DataCar dataCar = new DataCar(node);
                list.add(dataCar);
            }

            if(jsonNodeResponse.get("hasMore").asBoolean())
            {
                timestamp2 = list.getLast().getTimeStamp();
            }
        }
        while (jsonNodeResponse.get("hasMore").asBoolean());

        if(list.isEmpty() || list.getLast().getTimeStamp() != timeStamp)
        {
            throw new GetDataException("TimeStamp inexistant");
        }
        else if(list.getFirst().getTimeStamp() != verifyData)
        {
            throw new GetDataException("Pas assez de donnees");
        }

        return list;
    }

    @Override
    public void saveInstantane(Instantane instantane) throws SauvegardeException {

        try
        {
            JSONObject json = new JSONObject();
            json.put("image", instantane.getImage());
            json.put("timestamp", instantane.getTimeStamp());
            json.put("jugement", instantane.getJugement());
            json.put("dateExpertise", instantane.getDateExpertise());
            String urlParameters = json.toString();
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8 );
            int postDataLength = postData.length;

            URL url = new URL("http://" + ip + ":" + port + "/ords/sgbd_b3/save_instantane/test");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setUseCaches(false);

            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(postData);


            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null)
            {
                System.out.println(line);
            }
            rd.close();

        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new SauvegardeException("Erreur Sauvegarde");
        }
    }

    @Override
    public ArrayList<DataCar> getAllData() throws GetDataException {

        ArrayList<DataCar> list = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNodeResponse;

        String request = "http://" + ip + ":" + port + "/ords/sgbd_b3/get/all";

        do{

            String reponse = executeGetRequest(request);

            try {
                jsonNodeResponse = objectMapper.readTree(reponse);
            } catch (JsonProcessingException e) {
                throw new GetDataException("Erreur recuperation donnees");
            }
            JsonNode itemNode = jsonNodeResponse.get("items");

            for(JsonNode node : itemNode)
            {
                DataCar dataCar = new DataCar(node);
                list.add(dataCar);
            }

            if(jsonNodeResponse.get("hasMore").asBoolean())
            {
                JsonNode linkNode = jsonNodeResponse.get("links");

                for(JsonNode node : linkNode)
                {
                    try{
                        if(node.get("rel").asText().equals("next"))
                        {
                            request = node.get("href").asText();
                        }
                    }
                    catch (Exception e)
                    {
                        throw new GetDataException("erreur recuperation hasMore");
                    }
                }
            }
        }
        while(jsonNodeResponse.get("hasMore").asBoolean());

        return list;


    }


    private String executeGetRequest(String urlRequest) throws GetDataException {

        try {
        URL url = new URL(urlRequest);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");


            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;


            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            String responseString = response.toString();

            return responseString;
        }
        catch (IOException e)
        {
            throw new GetDataException("Erreur recuperation donnees");
        }

    }
}