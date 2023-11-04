package repository;

import core.exception.DataBaseException;
import core.exception.SauvegardeException;
import core.model.DataCar;
import core.model.Instantane;
import data.DataCarSource;

import java.util.ArrayList;

public class Repositoryimpl implements DataCarRepository{
    private DataCarSource dataCarSource;

    public Repositoryimpl(DataCarSource dataCarSource)
    {
        this.dataCarSource = dataCarSource;
    }

    @Override
    public ArrayList<DataCar> getDataFromTimeStamp(int timeStamp)throws DataBaseException {
        return dataCarSource.getDataFromTimeStamp(timeStamp);
    }

    @Override
    public void saveInstantane(Instantane instantane) throws SauvegardeException {
        dataCarSource.saveInstantane(instantane);
    }
}
