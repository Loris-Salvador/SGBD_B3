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
import javafx.scene.input.ScrollEvent;
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
    private int multiplicateur;
    private int rafraichissement;
    @FXML
    private ComboBox jugementComboBox;
    @FXML
    private Button multiplicateurButton;
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

    public MainController(GraphUseCase graphUseCase) {
        this.graphUseCase = graphUseCase;
        multiplicateur = 1;
        rafraichissement = TAUX_RAFRAISHISSEMENT;
    }
    @FXML
    public void initialize() {
        timeStampTextField.setOnAction(event -> afficherGraphique());
        pauseButton.setOnAction(event -> pauseButtonClick());
        avancerButton.setOnAction(event -> avancerButtonClick());
        reculerButton.setOnAction(event -> reculerButtonClick());
        sauvegarderButton.setOnAction(event -> sauvegarderButtonClick());
        multiplicateurButton.setOnAction(event -> multiplicateurButtonClick());
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
            NumberAxis yAxis = (NumberAxis) linearGraph.getYAxis();
            yAxis.setAutoRanging(false);
            yAxis.setLowerBound(-3);
            yAxis.setUpperBound(3);
            yAxis.setTickUnit(1);
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
            multiplicateurButton.setVisible(true);
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

            linearGraph.setOnScroll(event -> zoomOnGraph(event));

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

    private void multiplicateurButtonClick(){

        if(multiplicateur < MULTIPLICATEUR_MAX)
        {
            multiplicateur = multiplicateur * 2;
        }
        else
        {
            multiplicateur = 1;
        }

        multiplicateurButton.setText("X" + multiplicateur);
        rafraichissement = TAUX_RAFRAISHISSEMENT / multiplicateur;
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
    }

    private void zoomOnGraph(ScrollEvent event) {
        NumberAxis yAxis = (NumberAxis) linearGraph.getYAxis();
        yAxis.setAutoRanging(false);

        double zoomFactor = 0.03; // Ajustez ce facteur selon vos besoins

        double axisRange = yAxis.getUpperBound() - yAxis.getLowerBound();

        if (event.getDeltaY() > 0) {
            // Zoom in
            double zoomAmount = axisRange * zoomFactor;
            yAxis.setLowerBound(yAxis.getLowerBound() + zoomAmount);
            yAxis.setUpperBound(yAxis.getUpperBound() - zoomAmount);
        } else {
            // Zoom out
            double zoomAmount = axisRange * zoomFactor;
            yAxis.setLowerBound(yAxis.getLowerBound() - zoomAmount);
            yAxis.setUpperBound(yAxis.getUpperBound() + zoomAmount);
        }

        yAxis.setTickUnit(axisRange / 10);
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

    public int getRafraichissement(){
        return rafraichissement;
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

}
