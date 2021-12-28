package agh.ics.oop.gui;

import agh.ics.oop.Animal;
import agh.ics.oop.Direction;
import agh.ics.oop.Grass;
import agh.ics.oop.IMapElement;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageSupplier {
    private Image grass_img;
    private Image animal_img;
    private int size, starting_energy;

    public ImageSupplier(int size, int starting_energy) throws FileNotFoundException {
        this.size = size;
        this.starting_energy = starting_energy;

        grass_img = new Image(new FileInputStream("src/main/resources/img/grass.png"));

        animal_img = new Image(new FileInputStream("src/main/resources/img/ant.png"));
    }

    public ImageView getImageView(IMapElement element) {


        if (element instanceof Grass) {
            ImageView view = new ImageView(grass_img);
            view.setFitWidth(size);
            view.setFitHeight(size);
            return view;
        }
        if (element instanceof Animal) {
            Animal animal = (Animal) element;
            Direction direction = animal.getDirection();
            ImageView view = new ImageView(animal_img);
            view.setRotate(direction.getAngle());
            view.setFitWidth(size);
            view.setFitHeight(size);

            int energy = animal.getEnergy();
            ColorAdjust colorAdjust = new ColorAdjust();

            colorAdjust.setBrightness(0.4);
            colorAdjust.setSaturation(energy/(double)starting_energy-1);
            view.setEffect(colorAdjust);

            double scale = (getScale(energy)*100);
            view.setScaleX(getScale(energy));
            view.setScaleY(getScale(energy));

            return view;
        }
        return null;
    }

    double getScale(int energy) {
        return 1.5/(1+Math.exp(-energy/(double)(starting_energy*2)));
    }
}
