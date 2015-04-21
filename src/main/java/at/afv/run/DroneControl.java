package at.afv.run;

import at.afv.createlib.*;

/**
 * Created by klaus on 01.04.15.
 */
public class DroneControl {


    static {
        System.load("/home/pi/LastMinute-Flight/main/java/at/afv/createlib/libcreate.so"); //loads create-library
    }


    public static void main(String[] args) throws InterruptedException {
        create.create_connect();
        create.create_drive_direct((short) 50, (short) 50);
        Thread.sleep(1000);
        create.create_drive_direct((short) 0, (short) 0);
        System.out.println("juchuuu");
    }
}
