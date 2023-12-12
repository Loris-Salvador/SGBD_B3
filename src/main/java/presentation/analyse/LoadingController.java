package presentation.analyse;

import core.exception.GetDataException;
import di.AppModule;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.fxml.FXMLLoader;
import presentation.MainWindowController;

import java.io.IOException;


public class LoadingController {
    private Timeline colorChangeTimeline;
    @FXML
    private Circle loading;
    @FXML
    private Label labelError;
    @FXML
    private Button reessayerButton;
    private final AnalyseViewLoader analyseViewLoader;


    public LoadingController(AnalyseViewLoader analyseViewLoader)
    {
        this.analyseViewLoader = analyseViewLoader;
    }

    @FXML
    private void initialize()
    {
        reessayerButton.setOnAction(actionEvent -> onButtonReessayerClick());

        new Thread(() -> loadAnalyseView()).start();
        initializeLoadingCircle();
        colorChangeTimeline.play();
    }

    private void initializeLoadingCircle() {
        loading.setFill(Color.TRANSPARENT);
        loading.setStroke(Color.TRANSPARENT);

        Color[] colors = {Color.valueOf("#74E0E5"), Color.valueOf("#A8E1E3")};

        colorChangeTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(loading.fillProperty(), colors[0])),
                new KeyFrame(Duration.seconds(0.2), new KeyValue(loading.fillProperty(), colors[1])),
                new KeyFrame(Duration.seconds(0.4), new KeyValue(loading.fillProperty(), colors[0]))
        );
        colorChangeTimeline.setCycleCount(Timeline.INDEFINITE);
    }


    private void error()
    {
        colorChangeTimeline.stop();
        loading.setVisible(false);
        labelError.setVisible(true);
        reessayerButton.setVisible(true);

    }

    private void loadAnalyseView()
    {
        try {
            analyseViewLoader.loadanalyseView();
        } catch (GetDataException e) {
            error();
        }

    }

    private void onButtonReessayerClick(){
        new Thread(() -> loadAnalyseView()).start();

        colorChangeTimeline.play();
        labelError.setVisible(false);
        reessayerButton.setVisible(false);
        loading.setVisible(true);

    }
}
