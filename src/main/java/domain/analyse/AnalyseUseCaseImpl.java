package domain.analyse;

import core.exception.GetDataException;
import core.model.BarChartData;
import core.model.DataCar;
import core.model.ExtremeData;
import data.DataCarRepository;
import javafx.scene.chart.XYChart;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class AnalyseUseCaseImpl implements AnalyseUseCase {
    private ExtremeData extremeData;
    private ArrayList<DataCar> dataCars;
    private final DataCarRepository repository;
    public AnalyseUseCaseImpl(DataCarRepository repository)
    {
        this.repository = repository;
    }

    @Override
    public BarChartData getBarChartData() throws GetDataException {

        extremeData = repository.getExtremeData();
        extremeData = extremeData.getCeilValue();

        dataCars = repository.getAllData();
        Collections.sort(dataCars, Comparator.comparingDouble(DataCar::getAccX));

        BarChartData barChartData = new BarChartData();

        double echelle = (extremeData.getDataMax().getAccX() - extremeData.getDataMin().getAccX()) / 5;

        double echelleTmp = echelle;

        int i = 0;
        while(i<dataCars.size())
        {
            XYChart.Series<String, Number> serie = new XYChart.Series<String, Number>();
            int compteur = 0;
            while(i < dataCars.size() && dataCars.get(i).getAccX() <= extremeData.getDataMin().getAccX() + echelleTmp)
            {
                compteur ++;
                i++;
            }

            String valeurMin = String.format("%.2f", extremeData.getDataMin().getAccX() + echelleTmp - echelle);
            String valeurMax = String.format("%.2f", extremeData.getDataMin().getAccX() + echelleTmp);

            serie.getData().add(new XYChart.Data<>(valeurMin + " à " + valeurMax, compteur));


            echelleTmp = echelleTmp + echelle;
            barChartData.getSerieAccX().addData(serie);
        }

        return barChartData;
    }
}
