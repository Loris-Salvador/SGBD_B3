package core.model;

import javafx.scene.chart.XYChart;

public class DataSet {
    private XYChart.Series<Number, Double> accX;
    private XYChart.Series<Number, Double> accY;
    private XYChart.Series<Number, Double> accZ;
    private XYChart.Series<Number, Double> gyroX;
    private XYChart.Series<Number, Double> gyroY;
    private XYChart.Series<Number, Double> gyroZ;

    public DataSet()
    {
        accX = new XYChart.Series<>();
        accY = new XYChart.Series<>();
        accZ = new XYChart.Series<>();
        gyroX = new XYChart.Series<>();
        gyroY = new XYChart.Series<>();
        gyroZ = new XYChart.Series<>();

        accX.setName("AccX");
        accY.setName("AccY");
        accZ.setName("AccZ");
        gyroX.setName("GyroX");
        gyroY.setName("GyroY");
        gyroZ.setName("GyroZ");


    }

    public XYChart.Series<Number, Double> getAccX() {
        return accX;
    }

    public void setAccX(XYChart.Series<Number, Double> accX) {
        this.accX = accX;
    }

    public XYChart.Series<Number, Double> getAccY() {
        return accY;
    }

    public void setAccY(XYChart.Series<Number, Double> accY) {
        this.accY = accY;
    }

    public XYChart.Series<Number, Double> getAccZ() {
        return accZ;
    }

    public void setAccZ(XYChart.Series<Number, Double> accZ) {
        this.accZ = accZ;
    }

    public XYChart.Series<Number, Double> getGyroX() {
        return gyroX;
    }

    public void setGyroX(XYChart.Series<Number, Double> gyroX) {
        this.gyroX = gyroX;
    }

    public XYChart.Series<Number, Double> getGyroY() {
        return gyroY;
    }

    public void setGyroY(XYChart.Series<Number, Double> gyroY) {
        this.gyroY = gyroY;
    }

    public XYChart.Series<Number, Double> getGyroZ() {
        return gyroZ;
    }

    public void setGyroZ(XYChart.Series<Number, Double> gyroZ) {
        this.gyroZ = gyroZ;
    }
}
