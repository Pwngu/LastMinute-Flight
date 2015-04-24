package at.afv.control.gui;


import at.afv.control.main.DroneController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener{
	private DroneController droneController;
	private GUIController guiController;
	
	public ButtonListener(DroneController droneController, GUIController guiController){
		this.droneController = droneController;
		this.guiController = guiController;
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		JButton button = (JButton) ev.getSource();

        if(droneController.isInitialized()) {

            switch(ev.getActionCommand()) {

                case GUIController.TAKEOFF:
                    System.out.println("Start!");
                    droneController.takeOff();
                    button.setActionCommand(GUIController.LAND);
                    button.setText("Land");
                    break;

                case GUIController.LAND:
                    droneController.land();
                    button.setActionCommand(GUIController.TAKEOFF);
                    button.setText("Take Off"); // fall through
//                    break;

                case GUIController.STOP_FOLLOW:
                    droneController.manualMode();
                    button.setActionCommand(GUIController.START_FOLLOW);
                    button.setText("Start Following");
                    break;

                case GUIController.EMERGENCY_STOP:
                    droneController.emergencyStop();
                    break;

                case GUIController.HEIGHT_RESET:
                    break;

                case GUIController.START_FOLLOW:
                    droneController.manualMode();
                    button.setActionCommand(GUIController.STOP_FOLLOW);
                    button.setText("Stop Following");
                    break;

//                case GUIController.CONNECT:
//                    droneController.init(guiController);
//                    guiController.init();
//                    button.setActionCommand(GUIController.DISCONNECT);
//                    button.setText("Disconnect");
//                    break;

                case GUIController.DISCONNECT:
                    droneController.destroy();
                    guiController.destroy();
                    guiController.streamPanel.setImage(null);
                    guiController.streamFrame.setImage(null);
                    guiController.dataListener.batteryLevelChanged(-1);
                    button.setActionCommand(GUIController.CONNECT);
                    button.setText("Connect");
            }
		} else if(button.getActionCommand().equals(GUIController.CONNECT)) {

            button.setForeground(Color.BLACK);
            button.setFont(new Font(button.getFont().getName(), Font.PLAIN, button.getFont().getSize()));

            droneController.init(guiController);
            guiController.init();
            button.setActionCommand(GUIController.DISCONNECT);
            button.setText("Disconnect");
        } else {

            guiController.controlFrame.connectionButton.setForeground(Color.RED);
            Font old = guiController.controlFrame.connectionButton.getFont();
            guiController.controlFrame.connectionButton.setFont(new Font(old.getName(), Font.BOLD, old.getSize()));
        }
	}	
}
