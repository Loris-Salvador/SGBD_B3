package repository;

import core.exception.DataBaseException;
import core.model.DataCar;
import data.DataCarSource;

import java.util.ArrayList;

public class Repositoryimpl implements DataCarRepository{
    private DataCarSource dataCarSource;

    public Repositoryimpl(DataCarSource dataCarSource)
    {
        this.dataCarSource = dataCarSource;
    }

    public ArrayList<DataCar> getDataFromTimeStamp(int timeStamp)throws DataBaseException {
        return dataCarSource.getDataFromTimeStamp(timeStamp);
    }
}
