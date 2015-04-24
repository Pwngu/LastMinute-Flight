package at.afv.run;

import at.afv.control.gui.GUIController;
import at.afv.control.main.DroneController;

public class DroneControlGUI {

    public static void main(String[] args) {

        DroneController controller = new DroneController();
        new GUIController(controller);
    }
}
