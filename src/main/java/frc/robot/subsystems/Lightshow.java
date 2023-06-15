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

//import java.util.concurrent.BlockingQueue;

import edu.wpi.first.networktables.NetworkTable;
//import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
//import frc.robot.commands.*;
//import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

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
    private int light_count = 54;
    private double time_offset = 0.0;

    public static final int kModeBalance = 0;
    public static final int kModeCone = 1;
    public static final int kModeCube = 2;
    public static final int kModeCaution = 3;
    public static final int kModeError = 4;
    public static final int kModeBlue = 5;
    public static final int kModeUSA = 6;
    public static final int kModeUSA2 = 7;
    public static final int kModeHoliday = 8;
    public static final int kModeHolloween = 9;
    public static final int kModeAdmirals = 10;
    public static final int kModeSparkleWhite = 11;
    public static final int kModeSparkleUSA = 12;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    NetworkTable DataNAVX;
    /**
    *
    */
    public Lightshow() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        Lightshow_table = inst.getTable("SmartDashboard").getSubTable("Lightshow");

        time_offset = System.currentTimeMillis();

        led_strip.setLength(light_count);
        led_data = new AddressableLEDBuffer(light_count);
        led_strip.setData(led_data);
        led_strip.start();

        DataNAVX= inst.getTable("SmartDashboard").getSubTable("DataNAVX");

    }

    public void setDriveTrain(DriveTrain tmp) {
        drivetrain = tmp;
    }

        /**
     * This method rotates through the colors of the array.
     * @param colors The array of colors to rotate through.
     * @param width The number of leds to assign to each color.
     * @param d The speed at which to rotate through the colors.
     */
    private void rotateColors(Color[] colors, int width, double speed) {
        // The number of colors is determined.
        int N = colors.length;

        // the timestamp is converted to seconds
        double timestamp = (System.currentTimeMillis() - time_offset) / 1000.0;

        int offset = (int)(timestamp*speed);

        int pattern_length = N * width;

        for (int i = 0; i < light_count; i++) {
            int idx = (i + offset) % pattern_length; // index within pattern
            int color_index = idx/width;//idx / N; // the color index
            if(color_index < 0) color_index = 0; // Check for out of bounds
            Color color = colors[color_index%N];

            // The color is assigned to the led.
            led_data.setLED(i, color);
        }
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
        if (mode == kModeBlue) {
            flashBlue();
        }
        if (mode == kModeUSA) {
            rotateUSA();
        }
        if (mode == kModeUSA2) {
            rotateUSA2();
        }
        if (mode == kModeHoliday) {
            rotateHoliday();
        }
        if (mode == kModeHolloween) {
            rotateHolloween();
        }
        if (mode == kModeAdmirals) {
            rotateAdmirals();
        }
        if (mode == kModeSparkleWhite) {
            sparkleWhite();
        }
        if (mode == kModeSparkleUSA) {
            sparkleUSA();
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
        for (int i = 0; i < light_count; i++) {
            double wave = Math.cos(5.0 * timestamp + 1.0 * i);
            double val = Math.max(0, wave);
            led_data.setRGB(i, (int) (255*val), (int) (100*val), 0);

        }
    }

    private void rotateUSA() {
        double timestamp = System.currentTimeMillis() / 1000.0;
        for (int i = 0; i < light_count; i++) {
            double red_wave = Math.cos(5.0 * timestamp + 1.0 * i);
            double red_val = Math.max(0, red_wave);
            double blue_wave = Math.cos(5.0 * (timestamp+2.09) + 1.0 * i);
            double blue_val = Math.max(0, blue_wave);
            double white_wave = Math.cos(5.0 * (timestamp+2*2.09) + 1.0 * i);
            double white_val = Math.max(0, white_wave);
            blue_val = Math.max(blue_val,white_val);
            red_val = Math.max(red_val,white_val);
            led_data.setRGB(i, (int)(255*red_val), (int) (255*white_val), (int)(255*blue_val));

        }
    }

    private void rotateUSA2() {
        Color[] colors =  new Color[]{ new Color(1.0, 0.0, 0.0), new Color(1.0, 1.0, 1.0), new Color(0.0, 0.0, 1.0) };
        rotateColors(colors, 6, 20.0);
    }

    private void rotateHoliday() {
        Color[] colors =  new Color[]{ new Color(1.0, 0.0, 0.0), new Color(1.0, 1.0, 1.0), new Color(0.0, 1.0, 0.0) };
        rotateColors(colors, 3, 20.0);
    }

    private void rotateHolloween() {
        Color[] colors =  new Color[]{ new Color(1.0, 0.2, 0.0), new Color(0.4, 0.0, 0.5) };
        rotateColors(colors, 6, 20.0);
    }

    private void rotateAdmirals() {
        Color[] colors =  new Color[]{ new Color(0.0, 0.0, 0.7), new Color(0.4, 0.3, 0.3) };
        rotateColors(colors, 3, 8.0);
    }

    private void sparkleWhite() {
        for (int i = 0; i < light_count; i++) {
            Color color = new Color(0.0,0.0,0.0);
            if(Math.random() < 0.1){
                color = new Color(1.0,1.0,1.0);
            }

            // The color is assigned to the led.
            led_data.setLED(i, color);
        }
    }

    private void sparkleUSA() {
        
        for (int i = 0; i < light_count; i++) {
            Color color = new Color(0.0,0.0,0.0);
            if(Math.random() < 0.1){
                double num = Math.random();
                if(num < .33){
                    color = new Color(1.0,0.0,0.0); // red
                } else if (num < .67){
                    color = new Color(1.0,1.0,1.0); // white
                } else {
                    color = new Color(0.0,0.0,1.0); // blue
                }
            }

            // The color is assigned to the led.
            led_data.setLED(i, color);
        }
    }


    private void error() {
        double timestamp = System.currentTimeMillis() / 1000.0;
        for (int i = 0; i < light_count; i++) {
            double wave = Math.cos(20.0 * timestamp + 1.0 * i);
            double val = Math.max(0, wave);
            led_data.setRGB(i, (int) (255*val), (int) 0, 0);

        }
    }

    private void flashBlue() {
        double timestamp = System.currentTimeMillis() / 1000.0;
        boolean flash = timestamp - Math.floor(timestamp) > 0.5;

        for (int i = 0; i < light_count; i++) {
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

        for (int i = 0; i < light_count; i++) {
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

        for (int i = 0; i < light_count; i++) {
            if (flash) {
                led_data.setRGB(i, 170, 110, 0);
            } else {
                led_data.setRGB(i, 0, 0, 0);
            }
        }
    }

    private void balanceLight() {

        //double angle_filtered = drivetrain.getAccelFiltered();
        double angle_filtered = -DataNAVX.getEntry("navx_filtered_pitch").getDouble(0.0);

        // System.out.printf("%f Balance Lights/n", accel_filtered);

        int light_number = (int) (7 + Math.round(angle_filtered * 7.0 / 15.0));

        if(light_number < 0){light_number = 0;}
        if(light_number > 14){light_number = 14;}

        Lightshow_table.getEntry("light_number").setNumber(light_number);

        // AddressableLEDBuffer led_data = new AddressableLEDBuffer(light_count);
        for (int i = 0; i < light_count; i++) {
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
        //time_offset = System.currentTimeMillis();
        Lightshow_table.getEntry("mode").setNumber(mode);
    }

    public int getMode() {
        return mode;
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

}
