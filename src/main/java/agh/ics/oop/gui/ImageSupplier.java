package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageSupplier {
    private final Image grass_img;
    private final Image animal_img;
    private final Image animal_dominant_img;
    private final Image animal_tracked_img;
    private final int size;
    private final int starting_energy;
    private final Stats stats;

    public ImageSupplier(int size, int starting_energy, Stats stats) throws FileNotFoundException {
        this.size = size;
        this.starting_energy = starting_energy;
        this.stats = stats;

        grass_img = new Image(new FileInputStream("src/main/resources/img/grass.png"));

        animal_img = new Image(new FileInputStream("src/main/resources/img/ladybug.png"));

        animal_dominant_img = new Image(new FileInputStream("src/main/resources/img/ladybugBlue.png"));

        animal_tracked_img = new Image(new FileInputStream("src/main/resources/img/ladybugTracked.png"));

    }

    public Node getImageView(IMapElement element) {

        if (element instanceof Grass) {
            ImageView view = new ImageView(grass_img);
            view.setFitWidth(size);
            view.setFitHeight(size);
            return view;
        }
        if (element instanceof Animal) {
            Animal animal = (Animal) element;

            ImageView view = new ImageView(animal_img);
            if (animal.isTracked()) {
                view = new ImageView(animal_tracked_img);
            } else if (animal.getGenom()==stats.current_dominant_genom) {
                view = new ImageView(animal_dominant_img);
            }

            Direction direction = animal.getDirection();
            view.setRotate(direction.getAngle());
            view.setFitWidth(size);
            view.setFitHeight(size);

            int energy = animal.getEnergy();
            ColorAdjust colorAdjust = new ColorAdjust();

            colorAdjust.setBrightness(0.2);
            colorAdjust.setSaturation(energy/(double)starting_energy-1);
            view.setEffect(colorAdjust);

            double scale = (getScale(energy)*100);
            view.setScaleX(getScale(energy));
            view.setScaleY(getScale(energy));

            return view;
        }
        return null;
    }

    private double getScale(int energy) {
        return 1.5/(1+Math.exp(-energy/(double)(starting_energy*2)));
    }
}
