package agh.ics.oop.gui;

import agh.ics.oop.Animal;
import agh.ics.oop.IMapElement;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;

public class GuiElementBox {

        private Image img;
        private String labelText;

        public GuiElementBox(IMapElement element) {
            try {
                this.img = element.getImage();
                this.labelText = element instanceof Animal ? "Z " + element.getPosition() : "Trawa";
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
                this.img = null;
                this.labelText = "";
            }
        }

        public VBox mapElementView() {
            Label elementLabel = new Label(labelText);
            ImageView elementView = new ImageView(img);
            VBox elementVBox = new VBox();

            elementView.setFitWidth(20);
            elementView.setFitHeight(20);

            elementVBox.getChildren().addAll(elementView, elementLabel);
            elementVBox.setAlignment(Pos.CENTER);

            return elementVBox;

        }
}
