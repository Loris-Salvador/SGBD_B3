package repository;

import core.exception.DataBaseException;
import core.model.DataCar;

import java.util.ArrayList;

public interface DataCarRepository {
    public ArrayList<DataCar> getDataFromTimeStamp(int timeStamp) throws DataBaseException;

}
