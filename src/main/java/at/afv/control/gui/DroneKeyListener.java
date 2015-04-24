package at.afv.control.gui;

import at.afv.control.main.DroneController;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DroneKeyListener implements KeyListener {
	
	private DroneController droneController;

	public DroneKeyListener(DroneController droneController) {
		
		this.droneController = droneController;
	}
	
	@Override
	public void keyTyped(KeyEvent ev) { }

	@Override
	public void keyPressed(KeyEvent ev) {

		switch(ev.getKeyCode()) {

			case KeyEvent.VK_SHIFT:

				System.out.println("shift");
				droneController.emergencyStop();
				break;

			case KeyEvent.VK_SPACE:

				System.out.println("space");
				if(droneController.isFlying()) {

					droneController.land();
				} else {

					droneController.takeOff();
				}
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent ev) { }
}
