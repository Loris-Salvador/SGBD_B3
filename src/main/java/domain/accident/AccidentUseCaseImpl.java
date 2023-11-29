package domain.accident;

import core.exception.GetDataException;
import core.exception.SauvegardeException;
import core.model.Instantane;
import core.model.Jugement;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import core.model.DataCar;
import core.model.LineGraphData;
import javafx.scene.image.WritableImage;
import data.DataCarRepository;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;

public class AccidentUseCaseImpl implements AccidentUseCase{
    private final DataCarRepository repo;
    private LineGraphData lineGraphData;

    public AccidentUseCaseImpl(DataCarRepository repo)
    {
        this.repo = repo;
    }

    @Override
    public LineGraphData getDataSet(int stamp) throws GetDataException {

        ArrayList<DataCar> dataCar = repo.getDataFromTimeStamp(stamp);

        lineGraphData = new LineGraphData();
        double tmp = -1;

        for(DataCar data : dataCar)
        {
            double timeStamp = data.getTimeStamp();
            if(timeStamp == tmp)
            {
                timeStamp = timeStamp + 0.5;
            }
            lineGraphData.getAccX().getData().add(new XYChart.Data<>(timeStamp, data.getAccX()));
            lineGraphData.getAccY().getData().add(new XYChart.Data<>(timeStamp, data.getAccY()));
            lineGraphData.getAccZ().getData().add(new XYChart.Data<>(timeStamp, data.getAccZ()));
            lineGraphData.getGyroX().getData().add(new XYChart.Data<>(timeStamp, data.getGyroX()));
            lineGraphData.getGyroY().getData().add(new XYChart.Data<>(timeStamp, data.getGyroY()));
            lineGraphData.getGyroZ().getData().add(new XYChart.Data<>(timeStamp, data.getGyroZ()));

            tmp = timeStamp;
        }

        return lineGraphData;
    }
    @Override
    public void saveSnapShot(LineChart chart, int timeStamp, String jugementStr) throws SauvegardeException
    {
        Jugement jugement = null;

        if(jugementStr.equals("En Droit"))
            jugement = Jugement.DROIT;
        else if(jugementStr.equals("En Tort"))
            jugement = Jugement.TORT;

        byte[] byteArray;

        WritableImage snapshot = chart.snapshot(new SnapshotParameters(), null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", outputStream);
            byteArray = outputStream.toByteArray();
        } catch (IOException e) {
            throw new SauvegardeException("probleme de sauvegarde");
        }

        String encoded = Base64.getEncoder().encodeToString(byteArray);

        LocalDateTime currentDateTime = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = currentDateTime.format(formatter);

        Instantane instantane = new Instantane(encoded, jugement, timeStamp, formattedDateTime);

        repo.saveInstantane(instantane);
    }
}
