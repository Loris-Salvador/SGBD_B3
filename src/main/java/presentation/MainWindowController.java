package presentation;

import core.constant.GraphConstant;
import di.AppModule;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import presentation.accident.AccidentController;
import presentation.analyse.AnalyseController;

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
    private GridPane analyseView;
    private final AppModule module;

    public MainWindowController(AppModule module)
    {
        this.module = module;
    }

    @FXML
    private void initialize() {
        accidentButton.setOnAction(event -> clickOnAccidentButton());
        analyseButton.setOnAction(event -> clickOnAnalyseButton());

        initializeAccidentView();
        initializeAnalyseView();

        mainPane.setCenter(accidentView);
    }

    private void clickOnAccidentButton() {
        mainPane.setCenter(accidentView);
        analyseButton.setDisable(false);
        accidentButton.setDisable(true);
    }

    private void clickOnAnalyseButton() {
        mainPane.setCenter(analyseView);
        accidentButton.setDisable(false);
        analyseButton.setDisable(true);
    }

    private void initializeAccidentView() {
        try {
            GraphConstant.initGraphConstants();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("accident-view.fxml"));
            loader.setController(new AccidentController(module.getAccidentUseCase()));
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

    private void initializeAnalyseView(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("analyse-view.fxml"));
            loader.setController(new AnalyseController(module.getAnalyseUseCase()));
            analyseView = loader.load();

            analyseView.getStylesheets().add("style/analyse-view-style.css");

        }
        catch (IOException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement de l'application", "Erreur", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
}
