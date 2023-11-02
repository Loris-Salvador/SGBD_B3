package controller;

import javafx.scene.chart.NumberAxis;

public class GraphThread extends Thread {

    private MainController controller;
    public GraphThread(MainController controller)
    {
        this.controller = controller;
    }

    @Override
    public void run()
    {
        NumberAxis xAxis = (NumberAxis) controller.getLinearGraph().getXAxis();

        while(controller.getCurrentStamp() <= controller.getTimeStamp()-10) {
            try {
                Thread.sleep(500);
                xAxis.setLowerBound(controller.getCurrentStamp());
                xAxis.setUpperBound(controller.getCurrentStamp() + 10);
                controller.setCurrentStamp(controller.getCurrentStamp() + 1);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

}
