package data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.exception.DataBaseException;
import core.model.DataCar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Ords implements DataCarSource{
    public Ords()
    {

    }

    public ArrayList<DataCar> getDataFromTimeStamp(int timeStamp) throws DataBaseException {

        int timestamp2 = timeStamp - 60;
        String reponse = executeRequest("http://192.168.203.140:8080/ords/sgbd_b3/getbetween/" + timestamp2 + "/" + timeStamp);

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