package presentation.analyse;

import core.exception.GetDataException;
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
    private void initialize() throws GetDataException {

        BarChartData data = useCase.getBarChartData();

        System.out.println(data.getSerieAccX().getDataSerie().size());

        for(int i = 0; i < data.getSerieAccX().getDataSerie().size(); i++)
        {
            barChart.getData().add(data.getSerieAccX().getDataSerie().get(i));
        }

    }


}
