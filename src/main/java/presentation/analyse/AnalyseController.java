package presentation.analyse;

import core.exception.GetDataException;
import core.model.BarChartData;
import domain.analyse.AnalyseUseCase;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import javax.swing.plaf.basic.BasicButtonUI;


public class AnalyseController {
    @FXML
    private StackedBarChart barChart;
    @FXML
    private Label echelleLabel;
    @FXML
    private Slider slider;
    @FXML
    private Button accXButton;
    @FXML
    private Button accYButton;
    @FXML
    private Button accZButton;
    @FXML
    private Button gyroXButton;
    @FXML
    private Button gyroYButton;
    @FXML
    private Button gyroZButton;

    private Button currentButton;
    private BarChartData data;
    private final AnalyseUseCase useCase;
    public AnalyseController(AnalyseUseCase useCase)
    {
        this.useCase = useCase;
    }

    @FXML
    private void initialize() {

        slider.valueProperty().addListener((observable, oldValue, newValue) -> onSliderChange(newValue));
        accXButton.setOnAction(event -> onButtonGraphClicked(event.getSource()));
        accYButton.setOnAction(event -> onButtonGraphClicked(event.getSource()));
        accZButton.setOnAction(event -> onButtonGraphClicked(event.getSource()));
        gyroXButton.setOnAction(event -> onButtonGraphClicked(event.getSource()));
        gyroYButton.setOnAction(event -> onButtonGraphClicked(event.getSource()));
        gyroZButton.setOnAction(event -> onButtonGraphClicked(event.getSource()));

        initializeGraph();

    }

    private void onSliderChange(Number newValue)
    {
        echelleLabel.setText(String.valueOf(newValue.intValue()));
        barChart.getData().removeAll();
        barChart.getData().clear();

        data = useCase.graphWithEchelle(newValue.intValue());

        changeGraph();
    }

    private void initializeGraph(){
        accXButton.setDisable(true);
        currentButton = accXButton;

        barChart.setAnimated(false);
        barChart.setCategoryGap(0);


        try {
            data = useCase.getBarChartData();
        }
        catch (GetDataException e)
        {
            e.printStackTrace();
        }

        for(int i = 0; i < data.getSerie(0).getDataSerie().size(); i++)
        {
            barChart.getData().add(data.getSerie(0).getDataSerie().get(i));
        }

    }

    private void onButtonGraphClicked(Object source){
        Button button = (Button) source;
        button.setDisable(true);
        currentButton.setDisable(false);
        currentButton = button;

        changeGraph();
    }

    private void changeGraph(){
        if(currentButton.equals(accXButton))
        {
            barChart.getData().removeAll();
            barChart.getData().clear();

            data = useCase.graphWithEchelle((int)slider.getValue());

            for(int i = 0; i < data.getSerie(0).getDataSerie().size(); i++)
            {
                barChart.getData().add(data.getSerie(0).getDataSerie().get(i));
            }
        }
        else if(currentButton.equals(accYButton))
        {
            barChart.getData().removeAll();
            barChart.getData().clear();

            data = useCase.graphWithEchelle((int)slider.getValue());

            for(int i = 0; i < data.getSerie(1).getDataSerie().size(); i++)
            {
                barChart.getData().add(data.getSerie(1).getDataSerie().get(i));
            }
        }
        else if(currentButton.equals(accZButton))
        {
            barChart.getData().removeAll();
            barChart.getData().clear();

            data = useCase.graphWithEchelle((int)slider.getValue());

            for(int i = 0; i < data.getSerie(2).getDataSerie().size(); i++)
            {
                barChart.getData().add(data.getSerie(2).getDataSerie().get(i));
            }
        }
        else if(currentButton.equals(gyroXButton))
        {
            barChart.getData().removeAll();
            barChart.getData().clear();

            data = useCase.graphWithEchelle((int)slider.getValue());

            for(int i = 0; i < data.getSerie(3).getDataSerie().size(); i++)
            {
                barChart.getData().add(data.getSerie(3).getDataSerie().get(i));
            }
        }
        else if(currentButton.equals(gyroYButton))
        {
            barChart.getData().removeAll();
            barChart.getData().clear();

            data = useCase.graphWithEchelle((int)slider.getValue());

            for(int i = 0; i < data.getSerie(4).getDataSerie().size(); i++)
            {
                barChart.getData().add(data.getSerie(4).getDataSerie().get(i));
            }
        }
        else
        {
            barChart.getData().removeAll();
            barChart.getData().clear();

            data = useCase.graphWithEchelle((int)slider.getValue());

            for(int i = 0; i < data.getSerie(5).getDataSerie().size(); i++)
            {
                barChart.getData().add(data.getSerie(5).getDataSerie().get(i));
            }
        }
    }



}
