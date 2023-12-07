package domain.analyse;

import core.exception.GetDataException;
import core.model.BarChartData;
import core.model.DataCar;
import core.model.ExtremeData;
import data.DataCarRepository;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class AnalyseUseCaseImpl implements AnalyseUseCase {
    private  ExtremeData extremeData;
    private ArrayList<DataCar> dataCarList;
    private final DataCarRepository repository;
    public AnalyseUseCaseImpl(DataCarRepository repository)
    {
        this.repository = repository;
    }

    @Override
    public BarChartData getBarChartData() throws GetDataException {

        extremeData = repository.getExtremeData();
        dataCarList = repository.getAllData();

        BarChartData barChartData = new BarChartData();
        return null;
    }
}
