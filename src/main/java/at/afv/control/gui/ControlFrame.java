package at.afv.control.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class ControlFrame extends JFrame {
	
	private static final long serialVersionUID = 3103918082908681589L;

    JPanel contentPane;

    JPanel mainPanel;
    JTextArea output;
    JScrollPane scrollOutput;

    JTextField batteryLevel;

    JPanel controlPanel;
    JButton flyStateButton;
    JButton followButton;
    JButton heightResetButton;
    JButton emergencyButton;
    JButton connectionButton;
    JButton switchCameraButton;


    boolean focus;

	public ControlFrame(GUIController guiController) {

		this.contentPane = new JPanel();

        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        mainPanel = new JPanel(new BorderLayout());
        output = new JTextArea();
        output.setColumns(25);
        output.setEditable(false);
        scrollOutput = new JScrollPane(output);
        scrollOutput.getVerticalScrollBar().addAdjustmentListener(e -> {
            if(!focus)
                e.getAdjustable().setValue(e.getAdjustable().getMaximum());
        });

        output.addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent e) {
                focus = false;

            }

            @Override
            public void focusGained(FocusEvent e) {
                focus = true;

            }
        });

        //init buttons
        flyStateButton = new JButton("Start");
        followButton = new JButton("Start Following");
        heightResetButton = new JButton("Height Reset");
        emergencyButton = new JButton("Emergency Stop");
        connectionButton = new JButton("Connect");
        switchCameraButton = new JButton("Switch Camera");

        flyStateButton.addActionListener(guiController.getButtonListener());
        flyStateButton.setActionCommand(GUIController.TAKEOFF);
        followButton.addActionListener(guiController.getButtonListener());
        followButton.setActionCommand(GUIController.START_FOLLOW);
        heightResetButton.addActionListener(guiController.getButtonListener());
        heightResetButton.setActionCommand(GUIController.HEIGHT_RESET);
        emergencyButton.addActionListener(guiController.getButtonListener());
        emergencyButton.setActionCommand(GUIController.EMERGENCY_STOP);
        connectionButton.addActionListener(guiController.getButtonListener());
        connectionButton.setActionCommand(GUIController.CONNECT);
        followButton.addActionListener(guiController.getButtonListener());
        followButton.setActionCommand(GUIController.START_FOLLOW);
        switchCameraButton.addActionListener(guiController.getButtonListener());
        switchCameraButton.setActionCommand(GUIController.VERT_CAMERA);

        batteryLevel = new JTextField("--", 4);
        batteryLevel.setEditable(false);

        controlPanel = new JPanel();
        controlPanel.add(flyStateButton);
        controlPanel.add(followButton);
        controlPanel.add(emergencyButton);
        controlPanel.add(switchCameraButton);
        controlPanel.add(connectionButton);
        controlPanel.add(batteryLevel);
        controlPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, controlPanel.getMinimumSize().height));

        mainPanel.add(scrollOutput, BorderLayout.EAST);
        mainPanel.add(guiController.getStreamPanel(), BorderLayout.CENTER);
        contentPane.add(mainPanel);
        contentPane.add(controlPanel);


        this.setContentPane(contentPane);
        this.pack();
        this.setSize(800, 600);
        this.setVisible(true);
        this.addKeyListener(guiController.getKeyListener());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
	
	public void write(String s){
		output.append(s);
	}
}
