package agh.ics.oop.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class Main extends Application {
    private Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("start-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Simulation starting");
        stage.setScene(scene);
        stage.show();

        StartController controller = fxmlLoader.getController();
        controller.simulationParams().addListener((obs, oldParams, newParams) -> {
            try {
                simulate(newParams);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void simulate(HashMap<String, Integer> params) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("simulation-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Simulation!");
        stage.setScene(scene);
        stage.show();

        SimulationController controller = fxmlLoader.getController();

        controller.startSimulation(params);
    }

    public static void main(String[] args) {
        launch();
    }
}