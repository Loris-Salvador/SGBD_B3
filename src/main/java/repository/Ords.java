package repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import exception.DataBaseException;
import model.DataCar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Ords {
    public Ords()
    {

    }

    public ArrayList<DataCar> getDataFromTimeStamp(int timeStamp) throws IOException, DataBaseException {

        int timestamp2 = timeStamp - 60;
        String reponse = executeRequest("http://192.168.203.140:8080/ords/sgbd_b3/getbetween/" + timestamp2 + "/" + timeStamp);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNodeResponse = objectMapper.readTree(reponse);
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

        return list;
    }

    private String executeRequest(String urlRequest) throws IOException {

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
}
