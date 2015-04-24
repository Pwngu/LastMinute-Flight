package at.afv.control.gui;


import at.afv.control.main.DroneController;

public class GUIController {

	public static final String TAKEOFF = "takeOff";
	public static final String LAND = "land";
	public static final String EMERGENCY_STOP = "emgy_stop";

    public static final String VERT_CAMERA = "vert_camera";
    public static final String HORZ_CAMERA = "horz_camera";

	public static final String START_FOLLOW = "start_follow";
	public static final String STOP_FOLLOW = "stop_follow";
    public static final String HEIGHT_RESET = "height_reset";

	public static final String CONNECT = "connect";
	public static final String DISCONNECT = "disconnect";

   private DroneController droneController;

	ControlFrame controlFrame;
	ButtonListener buttonListener;
	DroneKeyListener keyListener;
	StreamFrame streamFrame;
	StreamPanel streamPanel;
    GUIDataListener dataListener;

    public GUIController(DroneController controller) {

        this.droneController = controller;

        dataListener = new GUIDataListener(this);
        keyListener = new DroneKeyListener(droneController);
        buttonListener = new ButtonListener(droneController, this);
        streamPanel = new StreamPanel();
        streamFrame = new StreamFrame(new StreamPanel());
        controlFrame = new ControlFrame(this);
    }

    public void init() {

        droneController.getARDrone().getVideoManager().addImageListener(dataListener);
        droneController.getARDrone().getNavDataManager().addBatteryListener(dataListener);
    }

    public void destroy() {

//      droneVideoManager.getVideoManager().addImageListener(dataListener);
        droneController.getARDrone().getNavDataManager().removeBatteryListener(dataListener);
    }

	public void write(String str) {

		controlFrame.write(str);
	}

	public ControlFrame getControlFrame() {

		return this.controlFrame;
	}
	
	public StreamFrame getStreamFrame() {

		return streamFrame;
	}
	
	public StreamPanel getStreamPanel() {

		return streamPanel;
	}
	
	public ButtonListener getButtonListener() {

		return buttonListener;
	}


	public DroneKeyListener getKeyListener() {

		return keyListener;
	}
}