package core.model;

import javafx.scene.chart.XYChart;

public class AreaChartData {

    private XYChart.Series<Number, Number> aggressive;
    private XYChart.Series<Number, Number> normal;
    private XYChart.Series<Number, Number> slow;


    public AreaChartData()
    {
        aggressive = new XYChart.Series<>();
        normal = new XYChart.Series<>();
        slow = new XYChart.Series<>();


        aggressive.setName("Aggressive");
        normal.setName("Normal");
        slow.setName("Slow");

    }

    public XYChart.Series<Number, Number> getAggressive() {
        return aggressive;
    }

    public XYChart.Series<Number, Number> getNormal() {
        return normal;
    }

    public XYChart.Series<Number, Number> getLent() {
        return slow;
    }
}
