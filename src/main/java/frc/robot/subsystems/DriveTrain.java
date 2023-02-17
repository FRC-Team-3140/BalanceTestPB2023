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

import frc.robot.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import com.ctre.phoenix.motorcontrol.ControlMode;

/**
 *
 */
public class DriveTrain extends SubsystemBase {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private WPI_TalonSRX talonSRX1;
    private WPI_TalonSRX talonSRX2;
    private WPI_TalonSRX talonSRX3;
    private MotorControllerGroup leftMotorGroup;
    private WPI_TalonSRX talonSRX4;
    private WPI_TalonSRX talonSRX5;
    private WPI_TalonSRX talonSRX6;
    private MotorControllerGroup rightMotorGroup;
    private DifferentialDrive differentialDrive1;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    // private AddressableLED led_strip = new AddressableLED(5);

    double accel_angle = 0.0;
    double accel_filtered = 0.0;
    LinearFilter angle_filter = LinearFilter.singlePoleIIR(0.2, 0.02);

    private Accelerometer accelerometer;

    NetworkTable drivetrain_table;

    /**
    *
    */
    public DriveTrain() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        talonSRX1 = new WPI_TalonSRX(2);

        talonSRX2 = new WPI_TalonSRX(4);

        talonSRX3 = new WPI_TalonSRX(6);

        leftMotorGroup = new MotorControllerGroup(talonSRX1, talonSRX2, talonSRX3);
        addChild("Left Motor Group", leftMotorGroup);

        talonSRX4 = new WPI_TalonSRX(3);

        talonSRX5 = new WPI_TalonSRX(5);

        talonSRX6 = new WPI_TalonSRX(7);

        rightMotorGroup = new MotorControllerGroup(talonSRX4, talonSRX5, talonSRX6);
        addChild("Right Motor Group", rightMotorGroup);

        differentialDrive1 = new DifferentialDrive(leftMotorGroup, rightMotorGroup);
        addChild("Differential Drive 1", differentialDrive1);
        differentialDrive1.setSafetyEnabled(true);
        differentialDrive1.setExpiration(0.1);
        differentialDrive1.setMaxOutput(1.0);

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        // talonSRX1.setSafetyEnabled(false);
        talonSRX1.set(ControlMode.PercentOutput, 0.0);
        talonSRX1.configOpenloopRamp(0.0);
        talonSRX1.setInverted(false);

        // talonSRX2.setSafetyEnabled(false);
        talonSRX2.set(ControlMode.PercentOutput, 0.0);
        talonSRX2.configOpenloopRamp(0.0);
        talonSRX2.setInverted(true);

        // talonSRX3.setSafetyEnabled(false);
        talonSRX3.set(ControlMode.PercentOutput, 0.0);
        talonSRX3.configOpenloopRamp(0.0);
        talonSRX3.setInverted(false);

        // talonSRX4.setSafetyEnabled(false);
        talonSRX4.set(ControlMode.PercentOutput, 0.0);
        talonSRX4.configOpenloopRamp(0.0);
        talonSRX4.setInverted(true);

        // talonSRX5.setSafetyEnabled(false);
        talonSRX5.set(ControlMode.PercentOutput, 0.0);
        talonSRX5.configOpenloopRamp(0.0);
        talonSRX5.setInverted(false);

        // talonSRX6.setSafetyEnabled(false);
        talonSRX6.set(ControlMode.PercentOutput, 0.0);
        talonSRX6.configOpenloopRamp(0.0);
        talonSRX6.setInverted(true);

        // talonSRX2.setInverted(true);
        // talonSRX3.setInverted(true);

        accelerometer = new BuiltInAccelerometer();

        NetworkTableInstance inst = NetworkTableInstance.getDefault();

        drivetrain_table = inst.getTable("SmartDashboard").getSubTable("DriveTrain");
        // led_strip.setLength(15);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        drivetrain_table.getEntry("accel_x").setNumber(accelerometer.getX());
        drivetrain_table.getEntry("accel_y").setNumber(accelerometer.getY());
        drivetrain_table.getEntry("accel_z").setNumber(accelerometer.getZ());

        accel_angle = -Math.atan2(getAccelX(), getAccelZ()) * 180 / Math.PI;

        drivetrain_table.getEntry("accel_angle").setNumber(accel_angle);

        accel_filtered = angle_filter.calculate(accel_angle);

        if (accel_filtered > 15)
            accel_filtered = 15;
        if (accel_filtered < -15)
            accel_filtered = -15;

        drivetrain_table.getEntry("lt").setNumber(accel_filtered * 7.0 / 15.0);

        int light_number = (int) (7 + Math.round(accel_filtered * 7.0 / 15.0));
        drivetrain_table.getEntry("light_number").setNumber(light_number);

        /*
         * AddressableLEDBuffer led_data = new AddressableLEDBuffer(15);
         * for (int i = 0; i < 15; i++) {
         * led_data.setRGB(i, 0, 0, 0);
         * }
         * if (light_number == 7) {
         * led_data.setRGB(light_number, 0, 255, 0);
         * } else {
         * led_data.setRGB(light_number, 70, 0, 0);
         * }
         * 
         * led_strip.setData(led_data);
         * led_strip.start();
         */
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }

    public void arcadeDrive(double xSpeed, double zRotation) {
        drivetrain_table.getEntry("arcade_xspeed").setNumber(xSpeed);
        drivetrain_table.getEntry("arcade_zrotation").setNumber(xSpeed);

        differentialDrive1.arcadeDrive(xSpeed, zRotation);
    }

    public double getAccelX() {
        return accelerometer.getX();
    }

    public double getAccelY() {
        return accelerometer.getY();
    }

    public double getAccelZ() {
        return accelerometer.getZ();
    }

    public double getAccelAngle() {
        return accel_angle;
    }

    public double getAccelFiltered() {
        return accel_filtered;
    }
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

}
