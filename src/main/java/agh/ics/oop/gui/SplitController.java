package agh.ics.oop.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.HashMap;

public class SplitController {

    @FXML
    private Pane left, right;

    public void startSimulations(HashMap<String, Integer> params) throws IOException {
        FXMLLoader fxmlLoader;
        SimulationController controller;

        //left
        fxmlLoader = new FXMLLoader(Main.class.getResource("simulation-view.fxml"));
        fxmlLoader.load();

        controller = fxmlLoader.getController();
        controller.startSimulation(params, true, params.get("left_magic")==1);
        AnchorPane leftRoot = fxmlLoader.getRoot();

        left.getChildren().addAll(leftRoot.getChildren());

        //right
        fxmlLoader = new FXMLLoader(Main.class.getResource("simulation-view.fxml"));
        fxmlLoader.load();

        controller = fxmlLoader.getController();
        controller.startSimulation(params, false, params.get("right_magic")==1);
        AnchorPane rightRoot = fxmlLoader.getRoot();

        right.getChildren().addAll(rightRoot.getChildren());
    }

}
