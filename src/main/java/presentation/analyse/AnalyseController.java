package presentation.analyse;

import core.model.BarChartData;
import domain.analyse.AnalyseUseCase;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;


public class AnalyseController {
    @FXML
    private BarChart barChart;
    private final AnalyseUseCase useCase;
    public AnalyseController(AnalyseUseCase useCase)
    {
        this.useCase = useCase;
    }

    @FXML
    private void initialize() {
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("2003");
        series1.getData().add(new XYChart.Data("austria", 25601.34));
        series1.getData().add(new XYChart.Data("brazil", 20148.82));
        series1.getData().add(new XYChart.Data("france", 10000));
        series1.getData().add(new XYChart.Data("italy", 35407.15));
        series1.getData().add(new XYChart.Data("usa", 12000));

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("2004");
        series2.getData().add(new XYChart.Data("austria", 57401.85));
        series2.getData().add(new XYChart.Data("brazil", 41941.19));
        series2.getData().add(new XYChart.Data("france", 45263.37));
        series2.getData().add(new XYChart.Data("italy", 117320.16));
        series2.getData().add(new XYChart.Data("usa", 14845.27));

        XYChart.Series series3 = new XYChart.Series();
        series3.setName("2005");
        series3.getData().add(new XYChart.Data("austria", 45000.65));
        series3.getData().add(new XYChart.Data("brazil", 44835.76));
        series3.getData().add(new XYChart.Data("france", 18722.18));
        series3.getData().add(new XYChart.Data("italy", 17557.31));
        series3.getData().add(new XYChart.Data("usa", 92633.68));

        barChart.getData().addAll(series1, series2, series3);

    }


}
