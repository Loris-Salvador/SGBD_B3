package presentation;

import core.constant.GraphConstant;
import data.Ords;
import domain.AccidentUseCase;
import domain.AccidentUseCaseImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class MainWindowController {

    @FXML
    private BorderPane mainPane;

    public MainWindowController() {


    }

    @FXML
    public void initialize() throws IOException {
        Ords ords = new Ords();
        AccidentUseCase useCase = new AccidentUseCaseImpl(ords);
        GraphConstant.initGraphConstants();


        FXMLLoader loader = new FXMLLoader(getClass().getResource("accident-view.fxml"));
        loader.setController(new AccidentController(useCase));
        GridPane accidentView = loader.load();


        String cssFile = "style/accident-view-style.css";
        accidentView.getStylesheets().add(cssFile);

        mainPane.setCenter(accidentView);



    }
}
