package at.afv.control.main;

import at.afv.control.gui.GUIController;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import de.yadrone.base.command.CommandManager;
import de.yadrone.base.command.LEDAnimation;
import de.yadrone.base.video.ImageListener;

import java.awt.image.BufferedImage;

public class QRCodeScanner implements ImageListener {

    public static final int NO_TAG_TOLERANCE = 5;

    public static final int SPIN_THRESHOLD = 15;
    public static final int MAX_SPIN_SPEED = 20;

    public static final int MAX_FORWARD_SPEED = 7;

    // Tag fields
    private boolean isHovering;

    private GUIController gui;
    private CommandManager cm;
    private DroneController controller;
    private volatile int noTagCount;

    // Scanner fields
    private long imageCount = 0;

    public QRCodeScanner(DroneController controller, GUIController gui){

        this.isHovering = false;
        this.controller = controller;
        this.gui = gui;
        this.cm = controller.getARDrone().getCommandManager();
    }

    @Override
    public void imageUpdated(BufferedImage image) {

//        gui.getStreamFrame().setImage(image);
//        gui.getStreamPanel().setImage(image);
        if((++imageCount % 2) != 0)
            return;

        // try to detect QR code
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        // decode the barcode (if only QR codes are used, the QRCodeReader might be a better choice)
        QRCodeReader reader = new QRCodeReader();
        ResultPoint[] points = null;
        double theta = Double.NaN;

        Result scanResult;

        try {

            scanResult = reader.decode(bitmap);

            points = scanResult.getResultPoints();
            ResultPoint a = points[1]; // top-left
            ResultPoint b = points[2]; // top-right

            // Find the degree of the rotation (needed e.g. for auto control)

            double z = Math.abs(a.getX() - b.getX());
            double x = Math.abs(a.getY() - b.getY());
            theta = Math.atan(x / z); // degree in rad (+- PI/2)

            theta = theta * (180 / Math.PI); // convert to degree

            if((b.getX() < a.getX()) && (b.getY() > a.getY())) { // code turned more than 90° clockwise
                theta = 180 - theta;
            } else if((b.getX() < a.getX()) && (b.getY() < a.getY())) { // code turned more than 180° clockwise
                theta = 180 + theta;
            } else if((b.getX() > a.getX()) && (b.getY() < a.getY())) {
                theta = 360 - theta;
            }

        } catch(NotFoundException | FormatException | ChecksumException ex) {
            scanResult = null;
        }

        tag((float) theta, points, scanResult == null ? null : image);
    }

    public void tag(float theta, ResultPoint[] points, BufferedImage img) {

        if(!controller.isFlying())
            return;

        if(!controller.isFollowing()) {
            if(!isHovering) {
                cm.hover();
                isHovering = true;
            }
            cm.setLedsAnimation(LEDAnimation.RED, 1F, 1);
            return;
        }

        if(img == null) {

            if(noTagCount < NO_TAG_TOLERANCE) {

                noTagCount++;
                gui.write("!!!! Skipping " + noTagCount + "\n");
                return;
            }

            cm.setLedsAnimation(LEDAnimation.BLINK_ORANGE, 2F, 1);
            gui.write("~~~~ Hover" + " \n");
            if(!isHovering) {

                isHovering = true;
                cm.hover();
            }
            return;
        }
        noTagCount = 0;

        double x = (points[2].getX() + points[1].getX()) / 2;
        double y = points[0].getY() + points[1].getY() / 2;
        double length = points[2].getX() - points[1].getX();

        gui.write("Tag " + "l: " + length + " x:" + x + " y: " + y + "\n");
        double xDiff = (img.getWidth() / 2) - x;

        int spin;
        ResultPoint a = points[1]; // top-left
        ResultPoint b = points[2]; // top-right
        if((b.getX() - 10 < a.getX()) && (b.getY() - 10 < a.getY()) || (b.getX() + 10 < a.getX()) && (b.getY() + 10 < a.getY())) {
            gui.write("Spin Right");
        } else {
            spin = 0;
        }

        if((b.getX() - 10 > a.getX()) && (b.getY() - 10 > a.getY()) || (b.getX() + 10 > a.getX()) && (b.getY() + 10 > a.getY())) {
            gui.write("Spin Left");
        } else {
            spin = 0;
        }


        int sDiff = (int) (90 - length);
        int speed = (int) (Math.signum(sDiff) * Math.min(MAX_FORWARD_SPEED, Math.abs(sDiff)));
        gui.write((length < 90 ? ">->-> Forward " : "<-<-< Backward ") + speed + "\n" + (xDiff > 0 ? " \\\\\\\\ Left " : " //// Right ") + spin + "\n");

        cm.setLedsAnimation(LEDAnimation.GREEN, 1F, 1);
        isHovering = false;
        cm.move(speed, 0, 0, spin);
    }
}
