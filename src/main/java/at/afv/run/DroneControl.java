package at.afv.run;

import static at.afv.createlib.Create.*;

/**
 * Created by klaus on 01.04.15.
 */
public class DroneControl {


    static {
        System.load("/home/pi/LastMinute-Flight/lib/createlib/libcreate.so"); //loads create-library
    }


    public static void main(String[] args) throws InterruptedException {

        create_drive_direct(50, 50);
        Thread.sleep(1000);
        create_drive_direct(0, 0);
        System.out.println("juchuuu");
    }
}
