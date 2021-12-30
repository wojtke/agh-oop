package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.HashMap;

public class SimulationController implements IObserver {

    @FXML
    protected ScrollPane mapPane;

    @FXML
    protected Slider slider;
    @FXML
    protected Label simulationSpeedLabel, simulationLabel;
    @FXML
    protected Button simulationPauseButton;

    @FXML
    protected AnchorPane countChartPane, energyChartPane, lifespanChartPane, childrenChartPane, genesChartPane;
    private ChartView countChart, energyChart, lifespanChart, childrenChart, genesChart;

    @FXML
    protected Label epochLabel, animalCountLabel, grassCountLabel, energyLabel, lifespanLabel, childrenLabel, dominantGenomLabel;

    private MapView mapView;
    private Stats stats;
    private SimulationEngine engine;
    private Thread engineThread;
    private long lastUpdate=0;

    public void startSimulation(HashMap<String, Integer> params, boolean isWrappy, boolean isMagic) {
        this.engine = new SimulationEngine(
                params.get("map_width"),
                params.get("map_height"),
                params.get("jungle_width"),
                params.get("jungle_height"),
                params.get("starting_animals"),
                params.get("starting_plants"),
                params.get("starting_energy"),
                params.get("move_energy"),
                params.get("plant_energy"),
                isWrappy,
                isMagic
        );
        this.engine.addObserver(this);
        this.stats = engine.getStats();

        mapView = new MapView(engine, (550/params.get("map_width")), params.get("starting_energy"));
        mapPane.setContent(mapView.getView());

        this.engineThread = new Thread(engine);
        this.engineThread.start();

        initLabels(isWrappy, isMagic);
        initCharts();
    }

    @Override
    public void update(int epoch) {
        if (System.currentTimeMillis()-lastUpdate>=50) {
            this.lastUpdate = System.currentTimeMillis();
            Platform.runLater(() -> {
                mapView.drawFill();

                countChart.update(epoch);
                energyChart.update(epoch);
                lifespanChart.update(epoch);
                childrenChart.update(epoch);
                genesChart.update(epoch);

                updateCurrentStats(epoch);

            });
        }
    }

    private void updateCurrentStats(int epoch) {
        this.epochLabel.setText("Epoch: " + epoch);
        this.animalCountLabel.setText("Animals: " + stats.current_animal_count);
        this.grassCountLabel.setText("Grass: " + stats.current_grass_count);
        this.energyLabel.setText("Avg energy: " + String.format("%.2f", stats.current_animal_avg_energy.doubleValue()));
        this.lifespanLabel.setText("Avg lifespan: " + String.format("%.2f", stats.current_animal_avg_lifespan.doubleValue()));
        this.childrenLabel.setText("Avg children: " + String.format("%,.2f", stats.current_animal_avg_children.doubleValue()));
        this.dominantGenomLabel.setText("Dominant Genom: \n" + stats.current_dominant_genom);
    }

    public void setSimulationSpeed() {
        int epochs_per_second = (int) Math.pow(2, slider.getValue());
        int speed = Math.max(1000/epochs_per_second, 1);
        this.engine.setSimulationSpeed(speed);

        this.simulationSpeedLabel.setText("Speed: " + epochs_per_second + " epochs per second");
    }

    public void pauseSimulationToggle() {
        pauseSimulation(!engine.isPaused());
    }

    private void pauseSimulation(boolean pause) {
        engine.pauseSimulation(pause);
        this.simulationPauseButton.setText(pause ? "Resume" : "Pause");
    }

    private void initLabels(boolean isWrappy, boolean isMagic) {
        this.simulationLabel.setText("Map: " + (isWrappy ? "Wrappy, " : "Not wrappy, ") + (isMagic ? " magic" : "not magic"));
        setSimulationSpeed();
        pauseSimulation(false);
    }

    private void initCharts() {
        this.countChart = new ChartView(stats.getAnimalCount(), stats.getGrassCount(), "Animals", "Grass");
        this.countChartPane.getChildren().add(countChart.getView());

        this.energyChart = new ChartView(stats.getAnimalAvgEnergy(), "Energy");
        this.energyChartPane.getChildren().add(energyChart.getView());

        this.lifespanChart = new ChartView(stats.getAnimalAvgLifespan(), "Lifespan");
        this.lifespanChartPane.getChildren().add(lifespanChart.getView());

        this.childrenChart = new ChartView(stats.getAnimalAvgChildren(), "Children");
        this.childrenChartPane.getChildren().add(childrenChart.getView());

        this.genesChart = new ChartView(stats.getGenesDistribution(), Genom.getNames());
        this.genesChartPane.getChildren().add(genesChart.getView());
    }

    public void save() {
        stats.saveCsv("stats" +System.currentTimeMillis() + ".csv");
    }

}
