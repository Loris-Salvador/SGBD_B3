package core.model;

import javafx.scene.chart.CategoryAxis;
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
    public class Serie {
        private ArrayList<XYChart.Series<String, Number>> dataSerie;
        public Serie(){
            dataSerie = new ArrayList<>();
        }
        public void addData(XYChart.Series<String, Number> data){
            dataSerie.add(data);
        }

        public ArrayList<XYChart.Series<String, Number>> getDataSerie() {
            return dataSerie;
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
