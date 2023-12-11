package domain.analyse;


import core.exception.GetDataException;
import core.model.BarChartData;

public interface AnalyseUseCase {
    void getBarChartData() throws GetDataException;
    BarChartData graphWithEchelle(int division);

}
