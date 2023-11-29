package presentation.analyse;

import core.exception.GetDataException;
import core.model.AreaChartData;
import domain.analyse.AnalyseUseCase;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;

public class AnalyseController {
    @FXML
    private AreaChart areaGraph;
    private final AnalyseUseCase useCase;
    public AnalyseController(AnalyseUseCase useCase)
    {
        this.useCase = useCase;
    }

    @FXML
    private void initialize() {

        try {

            AreaChartData areaChartData = useCase.getGraphData();


            areaGraph.getData().addAll(
                    areaChartData.getAggressive(),
                    areaChartData.getLent(),
                    areaChartData.getNormal()
            );

            NumberAxis xAxis = (NumberAxis) areaGraph.getXAxis();

            xAxis.setLowerBound(819977);
            xAxis.setUpperBound(820023);
            xAxis.setTickUnit(1);

        }
        catch (GetDataException e)
        {
            e.printStackTrace();
        }

    }


}
