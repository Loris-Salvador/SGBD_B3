package data;

import core.exception.DataBaseException;
import core.model.DataCar;

import java.util.ArrayList;

public interface DataCarSource {
    public ArrayList<DataCar> getDataFromTimeStamp(int timeStamp) throws DataBaseException;
}
