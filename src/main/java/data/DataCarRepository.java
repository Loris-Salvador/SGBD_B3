package data;

import core.exception.DataBaseException;
import core.exception.SauvegardeException;
import core.model.DataCar;
import core.model.Instantane;

import java.util.ArrayList;

public interface DataCarRepository {
    public ArrayList<DataCar> getDataFromTimeStamp(int timeStamp) throws DataBaseException;
    public void saveInstantane(Instantane instantane) throws SauvegardeException;

}
