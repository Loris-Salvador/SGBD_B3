package core.model;

import javafx.scene.chart.XYChart;

public class LineGraphData {
    private XYChart.Series<Number, Double> accX;
    private XYChart.Series<Number, Double> accY;
    private XYChart.Series<Number, Double> accZ;
    private XYChart.Series<Number, Double> gyroX;
    private XYChart.Series<Number, Double> gyroY;
    private XYChart.Series<Number, Double> gyroZ;

    public LineGraphData()
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
    public XYChart.Series<Number, Double> getAccY() {
        return accY;
    }
    public XYChart.Series<Number, Double> getAccZ() {
        return accZ;
    }
    public XYChart.Series<Number, Double> getGyroX() {
        return gyroX;
    }
    public XYChart.Series<Number, Double> getGyroY() {
        return gyroY;
    }
    public XYChart.Series<Number, Double> getGyroZ() {
        return gyroZ;
    }

}
