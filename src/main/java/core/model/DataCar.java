package core.model;


import com.fasterxml.jackson.databind.JsonNode;

public class DataCar {
    private double accX;
    private double accY;
    private double accZ;
    private double gyroX;
    private double gyroY;
    private double gyroZ;
    private Class classe;
    private int timeStamp;

    public DataCar(JsonNode node)
    {
        accX = node.get("accx").asDouble();
        accY = node.get("accy").asDouble();
        accZ = node.get("accz").asDouble();

        gyroX = node.get("gyrox").asDouble();
        gyroY = node.get("gyroy").asDouble();
        gyroZ = node.get("gyroz").asDouble();

        String typeSring = node.get("classe").asText();

        if(typeSring.equals("SLOW"))
        {
            classe  = Class.SLOW;
        }
        else if (typeSring.equals("AGGRESSIVE"))
        {
            classe = Class.AGGRESSIVE;
        }
        else if(typeSring.equals("NORMAL"))
        {
            classe = Class.NORMAL;
        }

        timeStamp = node.get("temps").asInt();
    }

    public double getAccX() {
        return accX;
    }

    public double getAccY() {
        return accY;
    }

    public double getAccZ() {
        return accZ;
    }

    public double getGyroX() {
        return gyroX;
    }

    public double getGyroY() {
        return gyroY;
    }

    public double getGyroZ() {
        return gyroZ;
    }

    public Class getClasse() {
        return classe;
    }

    public Class getType() {
        return classe;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    @Override
    public String toString() {
        return "DataCar{" +
                "accX=" + accX +
                ", accY=" + accY +
                ", accZ=" + accZ +
                ", gyroX=" + gyroX +
                ", gyroY=" + gyroY +
                ", gyroZ=" + gyroZ +
                ", type=" + classe +
                ", timeStamp=" + timeStamp +
                '}';
    }

}
