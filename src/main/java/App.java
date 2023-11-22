import core.constant.GraphConstant;
import domain.AccidentUseCase;
import domain.AccidentUseCaseImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import data.Ords;
import presentation.AccidentController;
import presentation.MainWindowController;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {


        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("presentation/main-window.fxml"));
        fxmlLoader.setController(new MainWindowController());

        Scene scene = new Scene(fxmlLoader.load());
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