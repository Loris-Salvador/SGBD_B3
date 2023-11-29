package domain.accident;

import core.exception.GetDataException;
import core.exception.SauvegardeException;
import core.model.LineGraphData;
import javafx.scene.chart.LineChart;

public interface AccidentUseCase {
    public LineGraphData getDataSet(int timeStamp) throws GetDataException;
    public void saveSnapShot(LineChart chart, int timeStamp, String jugement) throws SauvegardeException;
}
