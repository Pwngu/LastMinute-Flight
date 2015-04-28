package at.afv.run;

import at.afv.createlib.*;

/**
 * Created by klaus on 01.04.15.
 */
public class DroneControl {


    static {
        System.load("/home/pi/LastMinute-Flight/src/main/java/at/afv/createlib/libcreate.so"); //loads create-library
    }


    static int[] openings = new int[3];//0,1 oder 2
    static int zone = 0;
    public static void main(String[] args) throws InterruptedException {
        create.create_connect();

        int lichtsensor = create.getAnalogOutput(0); //detect starting light
        while(lichtsensor > 399) {
            lichtsensor = create.getAnalogOutput(0);
        }

        int distanzsensor = create.getAnalogOutput(1); //drive till proximity sensor detects a wall
        while(distanzsensor > 100){
            create.create_drive_direct((short) 100, (short) 100);
            Thread.sleep(200);
            if(create.get_create_right_front_cliff() < 100){
                openings[zone] = 0;
                zone = 1;
            }
        }

        if(zone != 1) {
            getToNext();
        }
        getToNext();
    }

    static void findNext(boolean inverted) throws InterruptedException {
        if(inverted) {
            turnLeft();
        }else{
            turnRight();
        }

        create.create_drive_direct((short) 100, (short) 100);
        Thread.sleep(5000);

        if(inverted) {
            turnLeft();
        }else{
            turnRight();
        }
    }

    static void getToNext() throws InterruptedException {
        if(openings[zone] == 0) {
            findNext(false);//drives to next potential opening
        }else{
            findNext(true);
        }
        openings[zone] = 1;
        if(create.getAnalogOutput(1) < 100){
            if(openings[zone] == 0){
                findNext(false);
            }else if(openings[zone] == 2){
                findNext(true);
            }else if(openings[zone] == 1){
                turnRight();
                create.create_drive_direct((short) 100, (short) 100);
                Thread.sleep(10000); //double the normal to get to the other side
                turnRight();
            }
            zone++;
            openings[zone] = 2;
        }

        int distanzsensor = create.getAnalogOutput(1);
        while(distanzsensor > 100){ //drives further
            distanzsensor = create.getAnalogOutput(1);
            create.create_drive_direct((short) 100, (short) 100);
            Thread.sleep(200);
        }
    }

    static void turnRight() throws InterruptedException {
        create.create_drive_direct((short) -50, (short) 50);
        Thread.sleep(5000);
    }

    static void turnLeft() throws InterruptedException {
        create.create_drive_direct((short) 50, (short) -50);
        Thread.sleep(5000);
    }
}
