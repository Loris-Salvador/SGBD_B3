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

    public Serie getSerie(int i) {
        return switch (i) {
            case 0 -> serieAccX;
            case 1 -> serieAccY;
            case 2 -> serieAccZ;
            case 3 -> serieGyroX;
            case 4 -> serieGyroY;
            case 5 -> serieGyroZ;
            default -> serieAccX;
        };
    }



}
