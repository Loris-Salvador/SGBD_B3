package domain;

import core.exception.DataBaseException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import core.model.DataCar;
import core.model.DataSet;
import javafx.scene.image.WritableImage;
import repository.DataCarRepository;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GraphUseCaseImpl implements GraphUseCase{

    private DataCarRepository repo;
    private DataSet dataSet;

    public GraphUseCaseImpl(DataCarRepository repo)
    {
        this.repo = repo;
    }

    public DataSet getDataSet(int stamp) throws DataBaseException {

        ArrayList<DataCar> dataCar = repo.getDataFromTimeStamp(stamp);

        dataSet = new DataSet();
        double tmp = -1;

        for(DataCar data : dataCar)
        {
            double timeStamp = data.getTimeStamp();
            if(timeStamp == tmp)
            {
                timeStamp = timeStamp + 0.5;
            }
            dataSet.getAccX().getData().add(new XYChart.Data<>(timeStamp, data.getAccX()));
            dataSet.getAccY().getData().add(new XYChart.Data<>(timeStamp, data.getAccY()));
            dataSet.getAccZ().getData().add(new XYChart.Data<>(timeStamp, data.getAccZ()));
            dataSet.getGyroX().getData().add(new XYChart.Data<>(timeStamp, data.getGyroX()));
            dataSet.getGyroY().getData().add(new XYChart.Data<>(timeStamp, data.getGyroY()));
            dataSet.getGyroZ().getData().add(new XYChart.Data<>(timeStamp, data.getGyroZ()));

            tmp = timeStamp;
        }

        return dataSet;
    }

    public void saveSnapShot(LineChart chart)
    {
        WritableImage snapshot = chart.snapshot(new SnapshotParameters(), null);
        File file = new File("chart_snapshot.png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
