package at.afv.control.gui;

import de.yadrone.base.navdata.BatteryListener;
import de.yadrone.base.video.ImageListener;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GUIDataListener implements BatteryListener, ImageListener {

    private GUIController controller;

    public GUIDataListener(GUIController controller) {

        this.controller = controller;
    }

    @Override
    public void batteryLevelChanged(int i) {
        if(i == -1) {
            controller.controlFrame.batteryLevel.setForeground(Color.BLACK);
            controller.controlFrame.batteryLevel.setText("--");
            return;
        }

        if(i < 30)
            controller.controlFrame.batteryLevel.setForeground(Color.RED);
        else
            controller.controlFrame.batteryLevel.setForeground(Color.GREEN);

        controller.controlFrame.batteryLevel.setText(i + "%");

    }

    @Override
    public void voltageChanged(int i) { }


    @Override
    public void imageUpdated(BufferedImage bufferedImage) {

        controller.streamFrame.setImage(bufferedImage);
        controller.streamPanel.setImage(bufferedImage);
    }
}
