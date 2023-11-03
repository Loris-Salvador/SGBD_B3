package domain;

import core.exception.DataBaseException;
import core.model.DataSet;
import javafx.scene.chart.LineChart;

import java.io.IOException;

public interface GraphUseCase {
    public DataSet getDataSet(int timeStamp) throws DataBaseException;
    public void saveSnapShot(LineChart chart);
}
