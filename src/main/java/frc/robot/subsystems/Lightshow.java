// RobotBuilder Version: 5.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

// ROBOTBUILDER TYPE: Subsystem.

package frc.robot.subsystems;

//import frc.robot.commands.*;
//import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//import java.util.concurrent.BlockingQueue;

import edu.wpi.first.networktables.NetworkTable;
//import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */

public class Lightshow extends SubsystemBase {
    private AddressableLED led_strip = new AddressableLED(5);
    NetworkTable Lightshow_table;
    AddressableLEDBuffer led_data;
    int mode = 0;
    DriveTrain drivetrain;

    public static final int kModeBalance = 0;
    public static final int kModeCone = 1;
    public static final int kModeCube = 2;
    public static final int kModeCaution = 3;
    public static final int kModeError = 4;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    /**
    *
    */
    public Lightshow() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        Lightshow_table = inst.getTable("SmartDashboard").getSubTable("Lightshow");

        led_strip.setLength(15);
        led_data = new AddressableLEDBuffer(15);
        led_strip.setData(led_data);
        led_strip.start();
    }

    public void setDriveTrain(DriveTrain tmp) {
        drivetrain = tmp;
    }

    @Override
    public void periodic() {

        if (mode == kModeBalance) {
            balanceLight();
        }
        if (mode == kModeCone) {
            flashYellow();
        }
        if (mode == kModeCube) {
            flashPurple();
        }
        if (mode == kModeCaution) {
            caution();
        }
        if (mode == kModeError) {
            error();
        }
        // if (light_number == 7) {
        // led_data.setRGB(light_number, 0, 255, 0);
        // } else {
        // led_data.setRGB(light_number, 70, 0, 0);
        // }

        led_strip.setData(led_data);
        // This method will be called once per scheduler run

    }

    private void caution() {
        double timestamp = System.currentTimeMillis() / 1000.0;
        for (int i = 0; i < 15; i++) {
            double wave = Math.cos(5.0 * timestamp + 1.0 * i);
            double val = Math.max(0, wave);
            led_data.setRGB(i, (int) (255*val), (int) (100*val), 0);

        }
    }

    private void error() {
        double timestamp = System.currentTimeMillis() / 1000.0;
        for (int i = 0; i < 15; i++) {
            double wave = Math.cos(20.0 * timestamp + 1.0 * i);
            double val = Math.max(0, wave);
            led_data.setRGB(i, (int) (255*val), (int) 0, 0);

        }
    }

    private void flashBlue() {
        double timestamp = System.currentTimeMillis() / 1000.0;
        boolean flash = timestamp - Math.floor(timestamp) > 0.5;

        for (int i = 0; i < 15; i++) {
            if (flash) {
                led_data.setRGB(i, 0, 0, 255);
            } else {
                led_data.setRGB(i, 0, 0, 0);
            }
        }
    }

    private void flashPurple() {
        double flash_speed = 3.0;
        double timestamp = flash_speed * System.currentTimeMillis() / 1000.0;
        boolean flash = timestamp - Math.floor(timestamp) > 0.5;

        for (int i = 0; i < 15; i++) {
            if (flash) {
                led_data.setRGB(i, 55, 25, 170);
            } else {
                led_data.setRGB(i, 0, 0, 0);
            }
        }
    }

    private void flashYellow() {
        double flash_speed = 3.0;
        double timestamp = flash_speed * System.currentTimeMillis() / 1000.0;
        boolean flash = timestamp - Math.floor(timestamp) > 0.5;

        for (int i = 0; i < 15; i++) {
            if (flash) {
                led_data.setRGB(i, 170, 110, 0);
            } else {
                led_data.setRGB(i, 0, 0, 0);
            }
        }
    }

    private void balanceLight() {
        // double flash_speed = 3.0;
        // double timestamp = flash_speed * System.currentTimeMillis() / 1000.0;
        // boolean flash = timestamp - Math.floor(timestamp) > 0.5;

        double accel_filtered = drivetrain.getAccelFiltered();
        // System.out.printf("%f Balance Lights/n", accel_filtered);

        int light_number = (int) (7 + Math.round(accel_filtered * 7.0 / 15.0));
        Lightshow_table.getEntry("light_number").setNumber(light_number);

        // AddressableLEDBuffer led_data = new AddressableLEDBuffer(15);
        for (int i = 0; i < 15; i++) {
            led_data.setRGB(i, 0, 0, 0);
        }
        if (light_number == 7) {
            led_data.setRGB(light_number, 0, 255, 0);
        } else {
            led_data.setRGB(light_number, 70, 0, 0);
        }

        // System.out.printf("Bal complete");
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }

    public void setMode(int m) {
        mode = m;
        Lightshow_table.getEntry("mode").setNumber(mode);
    }

    public int getMode() {
        return mode;
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

}
