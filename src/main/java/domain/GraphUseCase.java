package domain;

import core.exception.DataBaseException;
import core.exception.SauvegardeException;
import core.model.DataSet;
import core.model.Jugement;
import javafx.scene.chart.LineChart;

import java.io.IOException;

public interface GraphUseCase {
    public DataSet getDataSet(int timeStamp) throws DataBaseException;
    public void saveSnapShot(LineChart chart, int timeStamp, String jugement) throws SauvegardeException;
}
