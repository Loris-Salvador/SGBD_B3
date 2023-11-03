package presentation;

import javafx.application.Platform;
import javafx.scene.chart.NumberAxis;

public class DefilementArriere extends Thread {

    private MainController controller;
    public DefilementArriere(MainController controller)
    {
        this.controller = controller;
    }

    @Override
    public void run()
    {
        NumberAxis xAxis = (NumberAxis) controller.getLinearGraph().getXAxis();

        while(controller.getCurrentStamp() >= controller.getTimeStamp()-60) {
            try {
                Thread.sleep(500);
                xAxis.setLowerBound(controller.getCurrentStamp());
                xAxis.setUpperBound(controller.getCurrentStamp() + 10);
                controller.setCurrentStamp(controller.getCurrentStamp() - 1);
            } catch (InterruptedException e) {
                controller.setCurrentStamp(controller.getCurrentStamp() + 1);
                return;
            }
        }
        controller.setCurrentStamp(controller.getCurrentStamp() + 1);
        Platform.runLater(() -> {
            controller.finGraph();
        });

    }

}
