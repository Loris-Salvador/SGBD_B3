package presentation;

import core.exception.GetDataException;
import di.AppModule;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import presentation.accident.AccidentController;
import presentation.analyse.AnalyseController;
import presentation.analyse.AnalyseViewLoader;
import presentation.analyse.LoadingController;

import javax.swing.*;
import java.io.IOException;

public class MainWindowController implements AnalyseViewLoader {

    @FXML
    private BorderPane mainPane;
    @FXML
    private Button accidentButton;
    @FXML
    private Button analyseButton;
    private Pane accidentView;
    private Pane analyseView;
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
        loadLoadingView();

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

    private void loadLoadingView(){

        LoadingController loadingController = new LoadingController(this);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("loading-view.fxml"));
            loader.setController(loadingController);
            analyseView = loader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement de l'application", "Erreur", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    @Override
    public void loadanalyseView() throws GetDataException {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("analyse-view.fxml"));
            module.getAnalyseUseCase().getBarChartData();
            loader.setController(new AnalyseController(module.getAnalyseUseCase()));
            analyseView = loader.load();

            analyseView.getStylesheets().add("style/analyse-view-style.css");

            Platform.runLater(() -> {
                if (analyseButton.isDisable() == true)
                    mainPane.setCenter(analyseView);
            });
        }
        catch (IOException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement analyse view", "Erreur", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

}
