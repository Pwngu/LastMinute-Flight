package at.afv.control.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class StreamFrame extends JFrame {

	private StreamPanel panel;

	public StreamFrame(StreamPanel panel) {

		this.setSize(800, 600);
		this.panel = panel;
		this.setContentPane(panel);
		GraphicsDevice[] gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		this.setLocation(gd[gd.length-1].getDefaultConfiguration().getBounds().x, this.getY());
		if(gd.length != 1)	
			this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setVisible(true);
	}

	public void setImage(BufferedImage image) {

		panel.setImage(image);
	}
}
