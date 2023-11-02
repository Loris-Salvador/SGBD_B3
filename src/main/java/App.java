import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.Ords;
import use_case.GraphUseCase;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        Ords repository = new Ords();
        GraphUseCase useCase = new GraphUseCase(repository);


        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("view/main-window.fxml"));
        fxmlLoader.setController(new MainController(useCase));

        Scene scene = new Scene(fxmlLoader.load(), 1000, 550);
        scene.getStylesheets().add(getClass().getResource("style/main-window-style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Analyse de comportement routier");
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}