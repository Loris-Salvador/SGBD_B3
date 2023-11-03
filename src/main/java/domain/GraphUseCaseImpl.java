package domain;

import core.exception.DataBaseException;
import javafx.scene.chart.XYChart;
import core.model.DataCar;
import core.model.DataSet;
import repository.DataCarRepository;

import java.io.IOException;
import java.util.ArrayList;

public class GraphUseCaseImpl implements GraphUseCase{

    private DataCarRepository repo;
    private DataSet dataSet;

    public GraphUseCaseImpl(DataCarRepository repo)
    {
        this.repo = repo;
    }

    public DataSet getDataSet(int timeStamp) throws DataBaseException {

        ArrayList<DataCar> dataCar = repo.getDataFromTimeStamp(timeStamp);

        dataSet = new DataSet();

        for(DataCar data : dataCar)
        {
            int tmpTimeStamp = data.getTimeStamp();
            dataSet.getAccX().getData().add(new XYChart.Data<>(tmpTimeStamp, data.getAccX()));
            dataSet.getAccY().getData().add(new XYChart.Data<>(tmpTimeStamp, data.getAccY()));
            dataSet.getAccZ().getData().add(new XYChart.Data<>(tmpTimeStamp, data.getAccZ()));
            dataSet.getGyroX().getData().add(new XYChart.Data<>(tmpTimeStamp, data.getGyroX()));
            dataSet.getGyroY().getData().add(new XYChart.Data<>(tmpTimeStamp, data.getGyroY()));
            dataSet.getGyroZ().getData().add(new XYChart.Data<>(tmpTimeStamp, data.getGyroZ()));
        }

        return dataSet;


    }
}
