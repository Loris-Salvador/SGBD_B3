package presentation;

import core.constant.GraphConstant;
import data.Ords;
import domain.AccidentUseCase;
import domain.AccidentUseCaseImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import presentation.accident.AccidentController;

import javax.swing.*;
import java.io.IOException;

public class MainWindowController {

    @FXML
    private BorderPane mainPane;
    @FXML
    private Button accidentButton;
    @FXML
    private Button analyseButton;
    private GridPane accidentView;

    @FXML
    public void initialize() {
        accidentButton.setOnAction(event -> clickOnAccidentButton());
        analyseButton.setOnAction(event -> clickOnAnalyseButton());

        initializeAccidentView();

        mainPane.setCenter(accidentView);
    }

    private void clickOnAccidentButton() {
        mainPane.setCenter(accidentView);
        analyseButton.setDisable(false);
        accidentButton.setDisable(true);
    }

    private void clickOnAnalyseButton() {
        accidentButton.setDisable(false);
        analyseButton.setDisable(true);
    }

    private void initializeAccidentView() {
        try {

            Ords ords = new Ords();
            AccidentUseCase useCase = new AccidentUseCaseImpl(ords);
            GraphConstant.initGraphConstants();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("accident-view.fxml"));
            loader.setController(new AccidentController(useCase));
            accidentView = loader.load();

            accidentView.getStylesheets().add("style/accident-view-style.css");
        }
        catch (IOException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement de l'application", "Erreur", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
}
