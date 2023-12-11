package presentation.analyse;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class LoadingController {
    private Timeline colorChangeTimeline;
    @FXML
    private Circle loading;

    @FXML
    private void initialize()
    {
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
}
