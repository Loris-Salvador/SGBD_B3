package domain.analyse;

import core.exception.GetDataException;
import core.model.AreaChartData;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;

public interface AnalyseUseCase {
    AreaChartData getGraphData() throws GetDataException;
}
