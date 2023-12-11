package presentation.analyse;

import core.model.BarChartData;
import domain.analyse.AnalyseUseCase;
import javafx.fxml.FXML;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;


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

        echelleLabel.setText(String.valueOf((int)slider.getValue()));

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

        data = useCase.graphWithEchelle(12);


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

        barChart.getData().removeAll();
        barChart.getData().clear();

        data = useCase.graphWithEchelle((int)slider.getValue());

        int indice = -1;

        if(currentButton.equals(accXButton))
            indice = 0;
        else if(currentButton.equals(accYButton))
            indice = 1;
        else if(currentButton.equals(accZButton))
            indice = 2;
        else if(currentButton.equals(gyroXButton))
            indice = 3;
        else if(currentButton.equals(gyroYButton))
            indice = 4;
        else
            indice = 5;

        for(int i = 0; i < data.getSerie(indice).getDataSerie().size(); i++)
        {
            barChart.getData().add(data.getSerie(indice).getDataSerie().get(i));
        }
    }



}
