package domain.analyse;

import core.exception.GetDataException;
import core.model.BarChartData;
import core.model.Class;
import core.model.DataCar;
import core.model.ExtremeData;
import data.DataCarRepository;
import javafx.scene.chart.XYChart;

import java.awt.*;
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
    public void getBarChartData() throws GetDataException {

        extremeData = repository.getExtremeData();
        extremeData = extremeData.getCeilValue();

        dataCars = repository.getAllData();
    }

    @Override
    public BarChartData graphWithEchelle(int division){


        BarChartData barChartData = new BarChartData();

        for(int j = 0; j<6; j++)
        {
            int finalJ = j;
            Collections.sort(dataCars, Comparator.comparingDouble(dataCar -> dataCar.getData(finalJ)));

            double echelle = (extremeData.getDataMax(j) - extremeData.getDataMin(j)) / division;

            double echelleTmp = echelle;

            int i = 0;

            XYChart.Series<String, Number> serieSlow = new XYChart.Series<String, Number>();
            serieSlow.setName("Slow");
            XYChart.Series<String, Number> serieNormal = new XYChart.Series<String, Number>();
            serieNormal.setName("Normal");
            XYChart.Series<String, Number> serieAggressive = new XYChart.Series<String, Number>();
            serieAggressive.setName("Aggressive");
            while(i<dataCars.size())
            {
                int compteurSlow = 0;
                int compteurNormal = 0;
                int compteurAggressive = 0;
                while(i < dataCars.size() && dataCars.get(i).getData(j) <= extremeData.getDataMin(j) + echelleTmp)
                {
                    if(dataCars.get(i).getClasse() == Class.SLOW)
                        compteurSlow++;
                    else if(dataCars.get(i).getClasse() == Class.NORMAL)
                        compteurNormal++;
                    else
                        compteurAggressive++;
                    i++;
                }

                String valeurMin = String.format("%.2f", extremeData.getDataMin(j) + echelleTmp - echelle);
                String valeurMax = String.format("%.2f", extremeData.getDataMin(j) + echelleTmp);

                serieSlow.getData().add(new XYChart.Data<>(valeurMin + " à " + valeurMax, compteurSlow));
                serieNormal.getData().add(new XYChart.Data<>(valeurMin + " à " + valeurMax, compteurNormal));
                serieAggressive.getData().add(new XYChart.Data<>(valeurMin + " à " + valeurMax, compteurAggressive));

                echelleTmp = echelleTmp + echelle;
            }

            barChartData.getSerie(j).addData(serieSlow);
            barChartData.getSerie(j).addData(serieNormal);
            barChartData.getSerie(j).addData(serieAggressive);
        }

        return barChartData;
    }
}
