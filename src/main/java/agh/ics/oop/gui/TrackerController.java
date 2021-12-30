package agh.ics.oop.gui;

import agh.ics.oop.Animal;
import agh.ics.oop.ITrackingObserver;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.HashSet;

public class TrackerController implements ITrackingObserver {

    @FXML
    private Label total_children, total_offspring, death_epoch;

    @FXML
    private Label ageLabel, energyLabel, genomLabel;

    private int total_children_count=0, total_offspring_count=0;
    private Animal trackedAnimal;

    private final HashSet<Animal> offspringAlive = new HashSet<>();

    public void track(Animal animal) {
        this.trackedAnimal = animal;
        this.genomLabel.setText("Genom: \n " + animal.getGenom().toString());

        animal.addTrackingObserver(this);
    }

    public void shutdown() {
        for (Animal animal : offspringAlive) {
            animal.removeTrackingObserver();
        }
        this.trackedAnimal.removeTrackingObserver();
    }

    public void updateOnReproduce(Animal animal, Animal child) {
        Platform.runLater(() -> {
            if (!this.offspringAlive.contains(child)) {
                this.offspringAlive.add(child);
                this.total_offspring_count++;
                this.total_offspring.setText("Offspring: " + this.total_offspring_count);

                child.addTrackingObserver(this);
            }

            if (animal == trackedAnimal) {
                this.total_children_count++;
                this.total_children.setText("Children: " + this.total_children_count);
            }
        });
    }

    public void updateOnDeath(Animal animal, int epoch) {
        Platform.runLater(() -> {
            if (animal != trackedAnimal) {
                animal.removeTrackingObserver();
            }
            offspringAlive.remove(animal);
            if (animal == trackedAnimal) {
                this.death_epoch.setText("Died at epoch " + epoch);
            }
        });
    }

    public void updateNormal(Animal animal) {
        if (animal == trackedAnimal) {
            Platform.runLater(() -> {
                this.ageLabel.setText("Age: " + animal.getLifespan());
                this.energyLabel.setText("Energy: " + animal.getEnergy());
            });
        }
    }

    public boolean isTracked(Animal animal) {
        return animal == trackedAnimal;
    }

}
