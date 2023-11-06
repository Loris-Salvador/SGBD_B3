package presentation;

import javafx.application.Platform;
import javafx.scene.chart.NumberAxis;

import static core.constant.GraphConstant.*;

public class DefilementAvant extends Thread {

    private MainController controller;
    public DefilementAvant(MainController controller)
    {
        this.controller = controller;
    }

    @Override
    public void run()
    {
        NumberAxis xAxis = (NumberAxis) controller.getLinearGraph().getXAxis();

        try {
            Thread.sleep(350);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        while(controller.getCurrentStamp() <= controller.getTimeStamp() - TAILLE_AXE_X) {
            try {
                controller.setCurrentStamp(controller.getCurrentStamp() + ECHELLE);
                xAxis.setLowerBound(controller.getCurrentStamp());
                xAxis.setUpperBound(controller.getCurrentStamp() + TAILLE_AXE_X);
                Thread.sleep(controller.getRafraichissement());
            } catch (InterruptedException e) {
                return;
            }
        }
        Platform.runLater(() -> {
            controller.finGraph();
        });

    }

}
