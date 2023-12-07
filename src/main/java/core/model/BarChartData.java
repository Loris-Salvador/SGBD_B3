package core.model;

import javafx.scene.chart.XYChart;
import java.util.ArrayList;

public class BarChartData {
    private Serie serieAccX;
    private Serie serieAccY;
    private Serie serieAccZ;
    private Serie serieGyroX;
    private Serie serieGyroY;
    private Serie serieGyroZ;

    public BarChartData() {
        serieAccX = new Serie();
        serieAccY = new Serie();
        serieAccZ = new Serie();
        serieGyroX = new Serie();
        serieGyroY = new Serie();
        serieGyroZ = new Serie();
    }
    private class Serie {
        private ArrayList<XYChart<String, Number>> dataSerie;
        public Serie(){
            dataSerie = new ArrayList<>();
        }
    }


    public Serie getSerieAccX() {
        return serieAccX;
    }

    public Serie getSerieAccY() {
        return serieAccY;
    }

    public Serie getSerieAccZ() {
        return serieAccZ;
    }

    public Serie getSerieGyroX() {
        return serieGyroX;
    }

    public Serie getSerieGyroY() {
        return serieGyroY;
    }

    public Serie getSerieGyroZ() {
        return serieGyroZ;
    }




}
