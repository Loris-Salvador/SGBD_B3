package presentation;

import core.exception.DataBaseException;
import domain.GraphUseCase;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import core.model.DataSet;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class MainController {
    private Button lastButtonclicked;
    @FXML
    private ComboBox jugementComboBox;
    @FXML
    private Button sauvegarderButton;

    @FXML
    private Button avancerButton;
    @FXML
    private Button reculerButton;
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
    private Thread graphThread;
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
        pauseButton.setOnAction(event -> pauseButtonClick());
        avancerButton.setOnAction(event -> avancerButtonClick());
        reculerButton.setOnAction(event -> reculerButtonClick());
        sauvegarderButton.setOnAction(event -> sauvegarderButtonClick());
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
            avancerButton.setVisible(true);
            reculerButton.setVisible(true);
            reculerButton.setDisable(true);
            pauseButton.setVisible(true);
            pauseButton.setDisable(true);
            jugementComboBox.setVisible(true);
            sauvegarderButton.setVisible(true);
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

    private void pauseButtonClick()
    {
        graphThread.interrupt();
        pauseButton.setDisable(true);
        avancerButton.setDisable(false);
        reculerButton.setDisable(false);
        sauvegarderButton.setDisable(false);
    }

    private void avancerButtonClick()
    {
        if(graphThread != null && graphThread.isAlive())
            graphThread.interrupt();

        pauseButton.setDisable(false);
        graphThread = new DefilementAvant(this);
        graphThread.start();
        avancerButton.setDisable(true);
        reculerButton.setDisable(false);
        sauvegarderButton.setDisable(true);
    }

    private void reculerButtonClick()
    {
        if(graphThread != null && graphThread.isAlive())
            graphThread.interrupt();

        pauseButton.setDisable(false);
        graphThread = new DefilementArriere(this);
        graphThread.start();
        reculerButton.setDisable(true);
        avancerButton.setDisable(false);
        sauvegarderButton.setDisable(true);

    }

    private void sauvegarderButtonClick() {
        problemLabel.setText("Veuillez entrer un jugement");
        problemLabel.setVisible(true);

        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> {
                FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), problemLabel);
                fadeOut.setFromValue(1.0);
                fadeOut.setToValue(0.0);

                fadeOut.setOnFinished(event -> {
                    problemLabel.setVisible(false);
                    problemLabel.setOpacity(1.0);
                });

                fadeOut.play();
            });
        });

        thread.setDaemon(true);
        thread.start();
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

    public void finGraph()
    {
        pauseButton.setDisable(true);
        sauvegarderButton.setDisable(false);
    }

}
