package presentation;

import core.constant.GraphConstant;
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
    private Thread graphThread;
    private DataSet dataSet;
    private int timeStamp;
    private int currentStamp;
    private final GraphUseCase graphUseCase;
    @FXML
    private ComboBox jugementComboBox;
    @FXML
    private Button sauvegarderButton;
    @FXML
    private Button avancerButton;
    @FXML
    private Button reculerButton;
    @FXML
    private Label infoLabel;
    @FXML
    private LineChart linearGraph;
    @FXML
    private Button pauseButton;
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

    public MainController(GraphUseCase graphUseCase)
    {
        this.graphUseCase = graphUseCase;
    }
    @FXML
    public void initialize() {
        timeStampTextField.setOnAction(event -> afficherGraphique());
        pauseButton.setOnAction(event -> pauseButtonClick());
        avancerButton.setOnAction(event -> avancerButtonClick());
        reculerButton.setOnAction(event -> reculerButtonClick());
        sauvegarderButton.setOnAction(event -> sauvegarderButtonClick());
        accXCB.selectedProperty().addListener((observable, oldValue, newValue) -> checkBoxesChange(accXCB, dataSet.getAccX()));
        accYCB.selectedProperty().addListener((observable, oldValue, newValue) -> checkBoxesChange(accYCB, dataSet.getAccY()));
        accZCB.selectedProperty().addListener((observable, oldValue, newValue) -> checkBoxesChange(accZCB, dataSet.getAccZ()));
        gyroXCB.selectedProperty().addListener((observable, oldValue, newValue) -> checkBoxesChange(gyroXCB, dataSet.getGyroX()));
        gyroYCB.selectedProperty().addListener((observable, oldValue, newValue) -> checkBoxesChange(gyroYCB, dataSet.getGyroY()));
        gyroZCB.selectedProperty().addListener((observable, oldValue, newValue) -> checkBoxesChange(gyroZCB, dataSet.getGyroZ()));
        nodeCB.selectedProperty().addListener((observable, oldValue, newValue) ->  NodeVisibility(newValue));
    }

    private void afficherGraphique() {
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

            if(infoLabel.isVisible())
            {
                infoLabel.setVisible(false);
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

    private void pauseButtonClick() {
        graphThread.interrupt();
        pauseButton.setDisable(true);
        avancerButton.setDisable(false);
        reculerButton.setDisable(false);
        sauvegarderButton.setDisable(false);
    }

    private void avancerButtonClick() {
        if(graphThread != null && graphThread.isAlive() && graphThread instanceof DefilementArriere)
            graphThread.interrupt();

        pauseButton.setDisable(false);
        graphThread = new DefilementAvant(this);
        graphThread.start();
        avancerButton.setDisable(true);
        reculerButton.setDisable(false);
        sauvegarderButton.setDisable(true);
    }

    private void reculerButtonClick() {
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

    private void setCheckBoxesVisibility(boolean choix) {
        accXCB.setVisible(choix);
        accYCB.setVisible(choix);
        accZCB.setVisible(choix);
        gyroXCB.setVisible(choix);
        gyroYCB.setVisible(choix);
        gyroZCB.setVisible(choix);
        nodeCB.setVisible(choix);
    }

    private void checkBoxesChange(CheckBox checkBox, XYChart.Series<Number, Double> series) {
        boolean newValue = checkBox.isSelected();
        series.getNode().setVisible(newValue);
        if(newValue && !nodeCB.isSelected()) {
            return;
        }
        for (XYChart.Data<Number, Double> data : series.getData()) {
            data.getNode().setVisible(newValue);
        }

        updateAxisScale();
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

    public void finGraph() {
        pauseButton.setDisable(true);
        sauvegarderButton.setDisable(false);
    }

    private void afficherInfoLabel(String text, boolean info) {
        javafx.scene.paint.Color color;

        if(info)
            color = javafx.scene.paint.Color.GREEN;
        else
            color = Color.RED;


        infoLabel.setText(text);
        infoLabel.setTextFill(color);
        infoLabel.setVisible(true);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), infoLabel);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);



        fadeOut.setOnFinished(event -> {
            infoLabel.setVisible(false);
            infoLabel.setOpacity(1.0);
        });

        fadeOut.play();
    }

    private void updateAxisScale() {
        CheckBox checkBoxes[] = new CheckBox[6];
        checkBoxes[0] = accXCB;
        checkBoxes[1] = accYCB;
        checkBoxes[2] = accZCB;
        checkBoxes[3] = gyroXCB;
        checkBoxes[4] = gyroYCB;
        checkBoxes[5] = gyroZCB;
        NumberAxis yAxis = (NumberAxis) linearGraph.getYAxis();

        double minYValue = Double.MAX_VALUE;
        double maxYValue = Double.MIN_VALUE;

        List<XYChart.Series<Number, Double>> seriesList = Arrays.asList(
                dataSet.getAccX(),
                dataSet.getAccY(),
                dataSet.getAccZ(),
                dataSet.getGyroX(),
                dataSet.getGyroY(),
                dataSet.getGyroZ()
        );

        for (int i = 0; i < seriesList.size(); i++) {
            CheckBox checkBox = checkBoxes[i];
            if (checkBox.isSelected()) {
                XYChart.Series<Number, Double> series = seriesList.get(i);
                for (XYChart.Data<Number, Double> data : series.getData()) {
                    double yValue = data.getYValue();
                    if (yValue < minYValue) {
                        minYValue = yValue;
                    }
                    if (yValue > maxYValue) {
                        maxYValue = yValue;
                    }
                }
            }
        }


        linearGraph.getYAxis().setAutoRanging(false);
        yAxis.setUpperBound(maxYValue);
        yAxis.setLowerBound(minYValue);
    }


}
