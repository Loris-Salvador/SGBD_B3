package presentation;

import core.exception.DataBaseException;
import domain.GraphUseCase;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import core.model.DataSet;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class MainController {
    @FXML
    private Label problemLabel;
    @FXML
    private LineChart linearGraph;
    @FXML
    private Button pauseButton;
    @FXML
    private Button afficherButton;
    @FXML
    private CheckBox accXCB;
    @FXML
    private CheckBox accYCB;
    @FXML
    private CheckBox accZCB;
    @FXML
    private CheckBox gyroXCB;
    @FXML
    private CheckBox gyroYCB;
    @FXML
    private CheckBox gyroZCB;
    @FXML
    private CheckBox nodeCB;
    @FXML
    private TextField timeStampTextField;
    @FXML
    private Button demarrerButton;
    private GraphThread graphThread;
    private DataSet dataSet;
    private int timeStamp;
    private int currentStamp;
    private GraphUseCase graphUseCase;

    public MainController(GraphUseCase graphUseCase)
    {
        this.graphUseCase = graphUseCase;
    }
    @FXML
    public void initialize() {
        afficherButton.setOnAction(event -> afficherButtonClick());
        demarrerButton.setOnAction(event -> demarrerButtonClick());
        pauseButton.setOnAction(event -> pauseButtonClick());
        accXCB.selectedProperty().addListener((observable, oldValue, newValue) -> handleCheckBoxChanged(accXCB, dataSet.getAccX()));
        accYCB.selectedProperty().addListener((observable, oldValue, newValue) -> handleCheckBoxChanged(accYCB, dataSet.getAccY()));
        accZCB.selectedProperty().addListener((observable, oldValue, newValue) -> handleCheckBoxChanged(accZCB, dataSet.getAccZ()));
        gyroXCB.selectedProperty().addListener((observable, oldValue, newValue) -> handleCheckBoxChanged(gyroXCB, dataSet.getGyroX()));
        gyroYCB.selectedProperty().addListener((observable, oldValue, newValue) -> handleCheckBoxChanged(gyroYCB, dataSet.getGyroY()));
        gyroZCB.selectedProperty().addListener((observable, oldValue, newValue) -> handleCheckBoxChanged(gyroZCB, dataSet.getGyroZ()));
        nodeCB.selectedProperty().addListener((observable, oldValue, newValue) ->  NodeVisibility(newValue));
    }


    private void afficherButtonClick()
    {
        try
        {
            dataSet = graphUseCase.getDataSet(Integer.parseInt(timeStampTextField.getText()));

            linearGraph.getData().removeAll();
            linearGraph.getData().clear();


            linearGraph.getData().addAll(
                    dataSet.getAccX(),
                    dataSet.getAccY(),
                    dataSet.getAccZ(),
                    dataSet.getGyroX(),
                    dataSet.getGyroY(),
                    dataSet.getGyroZ()
            );

            timeStamp = Integer.parseInt(timeStampTextField.getText());
            currentStamp = timeStamp -60;
            NumberAxis xAxis = (NumberAxis) linearGraph.getXAxis();
            xAxis.setAutoRanging(false);
            xAxis.setTickUnit(1);
            xAxis.setLowerBound(currentStamp-1);
            xAxis.setUpperBound(currentStamp+10);
            setCheckBoxesVisibility(true);


            if(problemLabel.isVisible())
            {
                problemLabel.setVisible(false);
            }
            demarrerButton.setText("Demarrer");
            demarrerButton.setVisible(true);
            pauseButton.setVisible(false);
        }
        catch (DataBaseException e)
        {
            problemLabel.setText(e.getMessage());
            problemLabel.setVisible(true);
        }
        catch (NumberFormatException e)
        {
            problemLabel.setText("Veuillez entrer un TimeStamp Valide");
            problemLabel.setVisible(true);
        }
    }

    private void demarrerButtonClick()
    {
        if(demarrerButton.getText().equals("Redemarrer"))
        {
            currentStamp = timeStamp-60;
            demarrerButton.setText("Demarrer");
        }

        graphThread = new GraphThread(this);
        graphThread.start();
        pauseButton.setVisible(true);
        pauseButton.setDisable(false);
        demarrerButton.setDisable(true);
    }

    private void pauseButtonClick()
    {
        graphThread.interrupt();
        demarrerButton.setDisable(false);
        demarrerButton.setText("Reprendre");
        pauseButton.setDisable(true);
    }

    private void setCheckBoxesVisibility(boolean choix)
    {
        accXCB.setVisible(choix);
        accYCB.setVisible(choix);
        accZCB.setVisible(choix);
        gyroXCB.setVisible(choix);
        gyroYCB.setVisible(choix);
        gyroZCB.setVisible(choix);
        nodeCB.setVisible(choix);
    }

    private void handleCheckBoxChanged(CheckBox checkBox, XYChart.Series<Number, Double> series) {
        boolean newValue = checkBox.isSelected();
        series.getNode().setVisible(newValue);
        if(newValue && !nodeCB.isSelected()) {
            return;
        }
        for (XYChart.Data<Number, Double> data : series.getData()) {
            data.getNode().setVisible(newValue);
        }
    }

    private void NodeVisibility(boolean choix) {
        CheckBox[] checkBoxes = {accXCB, accYCB, accZCB, gyroXCB, gyroYCB, gyroZCB};
        List<XYChart.Series<Number, Double>> seriesList = Arrays.asList(dataSet.getAccX(), dataSet.getAccY(), dataSet.getAccZ(), dataSet.getGyroX(), dataSet.getGyroY(), dataSet.getGyroZ());

        for (int i = 0; i < checkBoxes.length; i++) {
            if (checkBoxes[i].isSelected()) {
                XYChart.Series<Number, Double> currentSeries = seriesList.get(i);
                for (XYChart.Data<Number, Double> data : currentSeries.getData()) {
                    data.getNode().setVisible(choix);
                }
            }
        }
    }


    public LineChart getLinearGraph() {
        return linearGraph;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public int getCurrentStamp() {
        return currentStamp;
    }

    public void setCurrentStamp(int newValue) {
        currentStamp = newValue;
    }

    public void defilementTermine()
    {
        demarrerButton.setText("Redemarrer");
        demarrerButton.setDisable(false);
        pauseButton.setVisible(false);
    }
}
