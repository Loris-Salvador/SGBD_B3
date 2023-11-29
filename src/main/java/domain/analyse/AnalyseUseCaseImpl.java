package domain.analyse;

import core.exception.GetDataException;
import core.model.AreaChartData;
import core.model.Class;
import core.model.DataCar;
import data.DataCarRepository;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;

public class AnalyseUseCaseImpl implements AnalyseUseCase {

    private final DataCarRepository repository;
    public AnalyseUseCaseImpl(DataCarRepository repository)
    {
        this.repository = repository;
    }


    @Override
    public AreaChartData getGraphData() throws GetDataException {

        ArrayList<DataCar> dataCarList = repository.getAllData();
        AreaChartData areaChartData = new AreaChartData();

        int i = 0;


        for (DataCar dataCar : dataCarList)
        {
            if(dataCar.getClasse()== Class.AGGRESSIVE)
            {
                areaChartData.getAggressive().getData().add(new XYChart.Data<>(dataCar.getTimeStamp(), dataCar.getAccX()));
            }
            else if(dataCar.getClasse()== Class.NORMAL)
            {
                areaChartData.getNormal().getData().add(new XYChart.Data<>(dataCar.getTimeStamp(), dataCar.getAccX()));
            }
            else if(dataCar.getClasse()== Class.SLOW)
            {
                areaChartData.getLent().getData().add(new XYChart.Data<>(dataCar.getTimeStamp(), dataCar.getAccX()));
            }

            i++;

//            if(i==20)
//                return areaChartData;
        }



        return areaChartData;

    }
}
