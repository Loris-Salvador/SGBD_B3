package controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import model.DataSet;
import use_case.GraphUseCase;

import java.io.IOException;


public class MainController {
    @FXML
    LineChart linearGraph;
    @FXML
    Button pauseButton;
    @FXML
    Button afficherButton;
    @FXML
    CheckBox accXCB;
    @FXML
    CheckBox accYCB;
    @FXML
    CheckBox accZCB;
    @FXML
    CheckBox gyroXCB;
    @FXML
    CheckBox gyroYCB;
    @FXML
    CheckBox gyroZCB;
    @FXML
    CheckBox nodeCB;
    @FXML
    TextField timeStampTextField;
    @FXML
    Button demarrerButton;
    private Thread graphThread;
    private DataSet dataSet;
    private int timeStamp;
    private int currentStamp;
    private GraphUseCase graphUseCase;

    public MainController(GraphUseCase graphUseCase)
    {
        this.graphUseCase = graphUseCase;
        graphThread = new Thread(() -> {
            NumberAxis xAxis = (NumberAxis) linearGraph.getXAxis();

            while(currentStamp <= timeStamp-10) {
                try {
                    Thread.sleep(500);
                    xAxis.setLowerBound(currentStamp);
                    xAxis.setUpperBound(currentStamp + 10);
                    currentStamp = currentStamp + 1;
                } catch (InterruptedException e) {
                    return;
                }
            }
        });
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
        }
        catch (IOException e)
        {
            System.out.println("Erreur DB");
        }
    }

    private void demarrerButtonClick()
    {
        graphThread.start();
        pauseButton.setVisible(true);
    }

    private void pauseButtonClick()
    {
        graphThread.interrupt();
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

    private void NodeVisibility(boolean choix)
    {
        for (XYChart.Data<Number, Double> data : dataSet.getAccX().getData()) {
            data.getNode().setVisible(choix);
        }
        for (XYChart.Data<Number, Double> data : dataSet.getAccY().getData()) {
            data.getNode().setVisible(choix);
        }
        for (XYChart.Data<Number, Double> data : dataSet.getAccZ().getData()) {
            data.getNode().setVisible(choix);
        }
        for (XYChart.Data<Number, Double> data : dataSet.getGyroX().getData()) {
            data.getNode().setVisible(choix);
        }
        for (XYChart.Data<Number, Double> data : dataSet.getGyroZ().getData()) {
            data.getNode().setVisible(choix);
        }
        for (XYChart.Data<Number, Double> data : dataSet.getGyroY().getData()) {
            data.getNode().setVisible(choix);
        }
    }


}
