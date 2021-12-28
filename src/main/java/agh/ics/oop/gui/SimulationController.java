package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class SimulationController implements IObserver {

    @FXML
    protected ScrollPane map_pane;

    @FXML
    protected VBox count_chart, energy_chart;

    @FXML
    protected Slider slider;

    @FXML
    protected Label simulationSpeedLabel;

    private MapView mapViewLeft, mapViewRight;
    private Stats statsLeft, statsRight;
    private ChartView chart1, chart2;
    private SimulationEngine engine;

    public void startSimulation(HashMap<String, Integer> params) {
        this.engine = new SimulationEngine(
                params.get("map_width"),
                params.get("map_height"),
                params.get("jungle_width"),
                params.get("jungle_height"),
                params.get("starting_animals"),
                params.get("starting_plants"),
                params.get("starting_energy"),
                params.get("move_energy"),
                params.get("plant_energy")
        );
        this.engine.addObserver(this);
        statsLeft = engine.getStats();

        mapViewLeft = new MapView(engine, 30, params.get("starting_energy"));
        map_pane.setContent(mapViewLeft.getView());

        Thread engineThread = new Thread(engine);
        engineThread.start();

        //setCharts();
    }

    public void setCharts() {
        chart1 = new ChartView(statsLeft.getAnimalCountSeries(), statsLeft.getGrassCountSeries());
        count_chart.getChildren().add(chart1.getView());

        chart2 = new ChartView(statsLeft.getAnimalAvgEnergySeries());
        energy_chart.getChildren().add(chart2.getView());
    }

    @Override
    public void update() {
        Platform.runLater(() -> {
            mapViewLeft.drawFill();
            //chart1.updateAxis(100);
            //chart2.updateAxis(100);
        });
    }

    public void setSimulationSpeed() {
        int epochs_per_second = (int) Math.pow(2, slider.getValue());
        int speed = Math.max(1000/epochs_per_second, 1);
        this.engine.setSimulationSpeed(speed);

        this.simulationSpeedLabel.setText("Speed: " + epochs_per_second + " epochs per second");
    }
}
