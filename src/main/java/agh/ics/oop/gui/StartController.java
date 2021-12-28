package agh.ics.oop.gui;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.HashMap;

public class StartController {

    @FXML
    private TextField map_width_field, map_height_field, jungle_ratio_field;

    @FXML
    private TextField starting_animals_field, starting_plants_field;

    @FXML
    private TextField starting_energy_field, move_energy_field, plant_energy_field;


    @FXML
    protected void onStartClick() {
        System.out.println("Start clicked");

        if(validate()) {
            initializeParams();
        } else {
            System.out.println("Sth went wrong wit the params");
            //TODO show error
        }

    }

    private boolean validate() {
        try {
            Integer.parseInt(map_width_field.getText());
            Integer.parseInt(map_height_field.getText());
            Double.parseDouble(jungle_ratio_field.getText());

            Integer.parseInt(starting_animals_field.getText());
            Integer.parseInt(starting_plants_field.getText());
            Integer.parseInt(starting_energy_field.getText());
            Integer.parseInt(move_energy_field.getText());
            Integer.parseInt(plant_energy_field.getText());
        }
        catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
    private final ReadOnlyObjectWrapper<HashMap<String, Integer>> params = new ReadOnlyObjectWrapper<>();

    public ReadOnlyObjectProperty<HashMap<String, Integer>> simulationParams() {
        return params.getReadOnlyProperty();
    }

    public void initializeParams() {
        HashMap<String, Integer> params = new HashMap<>();

        params.put("map_width", Integer.parseInt(map_width_field.getText()));
        params.put("map_height", Integer.parseInt(map_height_field.getText()));
        params.put("jungle_width", (int) (Double.parseDouble(jungle_ratio_field.getText()) * params.get("map_width")));
        params.put("jungle_height", (int) (Double.parseDouble(jungle_ratio_field.getText()) * params.get("map_height")));

        params.put("starting_animals", Integer.parseInt(starting_animals_field.getText()));
        params.put("starting_plants", Integer.parseInt(starting_plants_field.getText()));
        params.put("starting_energy", Integer.parseInt(starting_energy_field.getText()));
        params.put("move_energy", Integer.parseInt(move_energy_field.getText()));
        params.put("plant_energy", Integer.parseInt(plant_energy_field.getText()));

        this.params.set(params);
    }

}