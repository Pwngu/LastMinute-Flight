package at.afv.control.main;

import at.afv.control.gui.GUIController;
import de.yadrone.base.ARDrone;

public class DroneController {

    private ARDrone drone;
    private QRCodeScanner qrScanner;

	private boolean manualMode = false;
	private boolean initialized = false;
	private boolean flying = false;

	public DroneController() {

	}
	
	public void init(GUIController guiController) {

		if(!initialized) {

			drone = new ARDrone();
			qrScanner = new QRCodeScanner(this, guiController);
			drone.start();

			drone.getCommandManager().setOwnerMac("00:00:00:00:00:00");
            drone.setVerticalCamera();

			manualMode = true;
            initialized = true;

            System.out.println("Drone Controller initialized");
        }
	}

    public ARDrone getARDrone() {

        return drone;
    }
	
	public void takeOff() {

		drone.takeOff();
		drone.hover();
//		try { Thread.sleep(3000); } catch (InterruptedException ignored) { }
		flying = true;
	}
	
	public void land() {

		manualMode();
		flying = false;
		drone.hover();
		drone.landing();
	}
	
	public void emergencyStop() {

		drone.reset();
		drone.freeze();
	}
	
	public void manualMode() {

        drone.getVideoManager().removeImageListener(qrScanner);
        manualMode = true;
	}
	
	public void followMode() {

        drone.getVideoManager().addImageListener(qrScanner);
        manualMode = false;
	}

	public boolean isFlying() {

		return flying;
	}
	
	public boolean isFollowing() {

		return !manualMode;
	}

    public boolean isInitialized() {

        return initialized;
    }

    public void horizontalCamera() {

        drone.setHorizontalCamera();
    }

    public void verticalCamera() {

        drone.setVerticalCamera();
    }
	
	public void destroy() {

		if(initialized) {

			initialized = false;
			drone.getVideoManager().removeImageListener(qrScanner);
            drone.getCommandManager().setOwnerMac("00:00:00:00:00:00");
            this.emergencyStop();
			drone.stop();
		}
	}
}
