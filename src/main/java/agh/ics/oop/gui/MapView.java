package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class MapView {
    private GridPane grid;
    private Map map;
    private ImageSupplier imgs;

    private final ArrayList<Node> nodes = new ArrayList<>();

    public MapView(SimulationEngine engine, int grid_size, int starting_energy) {
        try {

            this.imgs = new ImageSupplier(grid_size, starting_energy, engine.getStats());

            this.map = engine.getMap();

            this.grid = new GridPane();

            drawBackground(grid, map, grid_size);
            drawFill();

        } catch (Exception e) {
            Text error_text = new Text(e.getMessage());
            this.grid.getChildren().clear();
            this.grid.add(error_text, 0, 0);
        }
    }

    public GridPane getView() {
        return grid;
    }

    private void drawBackground(GridPane grid, Map map, int grid_size) {
        grid.setAlignment(Pos.CENTER);
        grid.setBackground(getBackground(Color.SEAGREEN, 0.0));

        Vector2d[] bounds = map.getBoundaries();
        for (int i = bounds[0].x; i <= bounds[1].x; i++) {
            for (int j = bounds[0].y; j <= bounds[1].y; j++) {
                Pane pane = new Pane();
                pane.setPrefSize(grid_size,grid_size);
                pane.setMinSize(grid_size,grid_size);
                pane.setBackground(getBackground(Color.GREENYELLOW, 0.5));
                grid.add(pane, i, j);
            }
        }

        bounds = map.getJungleBoundaries();
        for (int i = bounds[0].x; i <= bounds[1].x; i++) {
            for (int j = bounds[0].y; j <= bounds[1].y; j++) {
                Pane pane = new Pane();
                pane.setPrefSize(grid_size,grid_size);
                pane.setMinSize(grid_size,grid_size);
                pane.setBackground(getBackground(Color.GREEN, 0.5));
                grid.add(pane, i, j);
            }
        }
    }

    private Background getBackground(Color color, double randomness) {
        double factor = (Math.random()*(randomness)) + 1-randomness/2;
        color = color.deriveColor(1, 1, factor, 1);

        return new Background(new BackgroundFill(color, null, null));
    }

    public void drawFill() {
        grid.getChildren().removeAll(nodes);
        nodes.clear();

        Vector2d[] bounds = map.getBoundaries();

        for (int i = bounds[0].x; i <= bounds[1].x; i++) {
            for (int j = bounds[0].y; j <= bounds[1].y; j++) {
                IMapElement element = map.objectAt(new Vector2d(i, j));
                if (element != null) {
                    Node view = imgs.getImageView(element);
                    view.setOnMouseClicked((event -> handleTracking(element)));
                    grid.add(view,i - bounds[0].x, bounds[1].y-j);
                    GridPane.setHalignment(view, HPos.CENTER);
                    nodes.add(view);
                }
            }
        }
    }

    public void handleTracking(IMapElement element) {
        if (element instanceof Animal) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("tracker.fxml"));

                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();

                TrackerController controller = fxmlLoader.getController();
                controller.track((Animal) element);
                stage.setOnHidden(e -> {
                    controller.shutdown();
                });

                stage.setTitle("Tracking!");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
