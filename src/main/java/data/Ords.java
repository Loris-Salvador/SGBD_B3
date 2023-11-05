package data;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.exception.DataBaseException;
import core.exception.SauvegardeException;
import core.model.DataCar;
import core.model.Instantane;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static core.constant.GraphConstant.FROM_TIME;

public class Ords implements DataCarSource{
    public Ords()
    {

    }

    @Override
    public ArrayList<DataCar> getDataFromTimeStamp(int timeStamp) throws DataBaseException {

        int timestamp2 = timeStamp - FROM_TIME;
        String reponse = executeRequest("http://192.168.203.141:8080/ords/sgbd_b3/getbetween/" + timestamp2 + "/" + timeStamp);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNodeResponse = null;
        try {
            jsonNodeResponse = objectMapper.readTree(reponse);
        } catch (JsonProcessingException e) {
            throw new DataBaseException("Erreur recuperation donnees");
        }
        JsonNode itemNode = jsonNodeResponse.get("items");

        ArrayList<DataCar> list = new ArrayList<>();

        for(JsonNode node : itemNode)
        {
            DataCar dataCar = new DataCar(node);
            list.add(dataCar);
        }

        if(list.isEmpty() || list.getLast().getTimeStamp() != timeStamp)
        {
            throw new DataBaseException("TimeStamp inexistant");
        }
        else if(list.getFirst().getTimeStamp() != timestamp2)
        {
            throw new DataBaseException("Pas assez de donnees");
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
            String urlParameters = json.toString();
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8 );
            int postDataLength = postData.length;

            URL url = new URL("http://192.168.203.141:8080/ords/sgbd_b3/save_instantane/test");
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



    private String executeRequest(String urlRequest) throws DataBaseException {

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
            throw new DataBaseException("Erreur recuperation donnees");
        }

    }
}