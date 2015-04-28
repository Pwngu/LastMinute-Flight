package at.afv.control.gui;

import at.afv.control.main.DroneController;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DroneKeyListener implements KeyListener {
	
	private DroneController droneController;

    public int moveX, moveY, moveZ, spin;

	public DroneKeyListener(DroneController droneController) {
		
		this.droneController = droneController;
	}
	
	@Override
	public void keyTyped(KeyEvent ev) { }

	@Override
	public void keyPressed(KeyEvent ev) {

		switch(ev.getKeyCode()) {

			case KeyEvent.VK_SHIFT:

                System.out.println("landing");
                if(droneController.isFlying()) {

                    droneController.land();
                } else {

                    droneController.takeOff();
                }
                break;

			case KeyEvent.VK_SPACE:

                System.out.println("####################");
                System.out.println("###EMERGENCY STOP###");
                System.out.println("####################");

                droneController.emergencyStop();
                break;

            case KeyEvent.VK_W:

                if(droneController.isFollowing()) return;

                System.out.println("forward");
                break;

            case KeyEvent.VK_A:

                if(droneController.isFollowing()) return;


                System.out.println("left");
                break;

            case KeyEvent.VK_S:

                if(droneController.isFollowing()) return;

                System.out.println("backward");
                break;

            case KeyEvent.VK_D:

                if(droneController.isFollowing()) return;

                System.out.println("right");
                break;

            case KeyEvent.VK_Q:

                if(droneController.isFollowing()) return;

                System.out.println("turn left");
                break;

            case KeyEvent.VK_E:

                if(droneController.isFollowing()) return;

                System.out.println("turn right");
                break;

            case KeyEvent.VK_F:

                if(droneController.isFollowing()) return;

                System.out.println("down");
                break;

            case KeyEvent.VK_T:

                if(droneController.isFollowing()) return;

                System.out.println("up");
                break;
		}
	}

	@Override
	public void keyReleased(KeyEvent ev) { }
}
