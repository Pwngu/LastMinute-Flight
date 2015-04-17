package at.afv.run;

import at.afv.createlib.SWIGTYPE_p_int16_t;

import static at.afv.createlib.create.*;

/**
 * Created by klaus on 01.04.15.
 */
public class DroneControl {


    static {
        System.load("/home/pi/LastMinute-Flight/lib/createlib/libcreate.so"); //loads create-library
    }


    public static void main(String[] args) throws InterruptedException {

        create_connect();
        create_drive_direct(new SWIGTYPE_p_int16_t(50, true), new SWIGTYPE_p_int16_t(50, true));
        Thread.sleep(1000);
        create_drive_direct(new SWIGTYPE_p_int16_t(0, true), new SWIGTYPE_p_int16_t(0, true));
        System.out.println("juchuuu");
    }
}
