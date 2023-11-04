package presentation;

import core.exception.DataBaseException;
import core.exception.SauvegardeException;
import domain.GraphUseCase;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import core.model.DataSet;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.util.Arrays;
import java.util.List;

import static core.constant.GraphConstant.*;


public class MainController {
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
        if(graphThread != null && graphThread.isAlive())
            graphThread.interrupt();
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
            currentStamp = timeStamp - FROM_TIME;
            NumberAxis xAxis = (NumberAxis) linearGraph.getXAxis();
            xAxis.setAutoRanging(false);
            xAxis.setTickUnit(ECHELLE);
            xAxis.setLowerBound(currentStamp-ECHELLE);
            xAxis.setUpperBound(currentStamp+TAILLE_AXE_X);
            setCheckBoxesVisibility(true);

            if(problemLabel.isVisible())
            {
                problemLabel.setVisible(false);
            }
            avancerButton.setVisible(true);
            avancerButton.setDisable(false);
            reculerButton.setVisible(true);
            reculerButton.setDisable(true);
            pauseButton.setVisible(true);
            pauseButton.setDisable(true);
            jugementComboBox.setVisible(true);
            sauvegarderButton.setVisible(true);

            accXCB.setSelected(true);
            accYCB.setSelected(true);
            accZCB.setSelected(true);
            gyroXCB.setSelected(true);
            gyroYCB.setSelected(true);
            gyroZCB.setSelected(true);
            nodeCB.setSelected(true);

        }
        catch (DataBaseException e)
        {
            afficherInfoLabel(e.getMessage(), false);
        }
        catch (NumberFormatException e)
        {
            afficherInfoLabel("Veuillez entrer un timeStamp valide", false);
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

        if(jugementComboBox.getValue() == null)
        {
            afficherInfoLabel("Veuillez entrer un jugement", false);
            return;
        }

        try {
            graphUseCase.saveSnapShot(linearGraph, timeStamp, jugementComboBox.getValue().toString());
        }
        catch (SauvegardeException e)
        {
            afficherInfoLabel(e.getMessage(), false);
        }

        afficherInfoLabel("Sauvegarde réussie !", true);
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

    private void afficherInfoLabel(String text, boolean info)
    {
        javafx.scene.paint.Color color;

        if(info)
            color = javafx.scene.paint.Color.GREEN;
        else
            color = Color.RED;


        problemLabel.setText(text);
        problemLabel.setTextFill(color);
        problemLabel.setVisible(true);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), problemLabel);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        fadeOut.setOnFinished(event -> {
            problemLabel.setVisible(false);
            problemLabel.setOpacity(1.0);
        });

        fadeOut.play();
    }

}
