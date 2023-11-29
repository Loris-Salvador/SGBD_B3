package di;

import data.DataCarRepository;
import data.Ords;
import domain.accident.AccidentUseCase;
import domain.accident.AccidentUseCaseImpl;
import domain.analyse.AnalyseUseCase;
import domain.analyse.AnalyseUseCaseImpl;

public class AppModule {
    private final DataCarRepository dataCarRepository;
    private final AccidentUseCase accidentUseCase;
    private final AnalyseUseCase analyseUseCase;


    public AppModule()
    {
        dataCarRepository = new Ords();
        accidentUseCase = new AccidentUseCaseImpl(dataCarRepository);
        analyseUseCase = new AnalyseUseCaseImpl(dataCarRepository);
    }

    public AccidentUseCase getAccidentUseCase()
    {
        return accidentUseCase;
    }
    public AnalyseUseCase getAnalyseUseCase()
    {
        return analyseUseCase;
    }
}
