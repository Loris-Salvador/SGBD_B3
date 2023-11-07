import core.constant.GraphConstant;
import domain.GraphUseCase;
import domain.GraphUseCaseImpl;
import presentation.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import data.Ords;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        Ords ords = new Ords();
        GraphUseCaseImpl useCase = new GraphUseCaseImpl(ords);
        GraphConstant.initGraphConstants();


        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("presentation/main-window.fxml"));
        fxmlLoader.setController(new MainController(useCase));

        Scene scene = new Scene(fxmlLoader.load(), 1000, 550);
        scene.getStylesheets().add(getClass().getResource("style/main-window-style.css").toExternalForm());
        stage.setScene(scene);
        stage.setMinWidth(1050);
        stage.setMinHeight(600);
        stage.setTitle("Analyse de comportement routier");
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}