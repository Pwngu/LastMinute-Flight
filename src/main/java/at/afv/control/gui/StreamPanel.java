package at.afv.control.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class StreamPanel extends JPanel {

	private BufferedImage image;
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		if(image != null) {
			g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
		}
	}
	
	public void setImage(BufferedImage image){
		this.image = image;
		this.repaint();
	}
}
