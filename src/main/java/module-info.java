module agh.ics.oop {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;

    opens agh.ics.oop.gui to javafx.fxml;
    exports agh.ics.oop.gui to javafx.graphics;
    exports agh.ics.oop.test to junit;
}