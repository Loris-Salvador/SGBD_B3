package data;

import core.exception.GetDataException;
import core.exception.SauvegardeException;
import core.model.DataCar;
import core.model.ExtremeData;
import core.model.Instantane;

import java.util.ArrayList;

public interface DataCarRepository {
    ArrayList<DataCar> getDataFromTimeStamp(int timeStamp) throws GetDataException;
    void saveInstantane(Instantane instantane) throws SauvegardeException;
    ArrayList<DataCar> getAllData() throws GetDataException;
    ExtremeData getExtremeData() throws GetDataException;

}
