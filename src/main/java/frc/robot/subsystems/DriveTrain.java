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

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.networktables.NetworkTable;
//import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
//import edu.wpi.first.networktables.PubSubOptions;
//import edu.wpi.first.wpilibj.AddressableLED;
//import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;

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
    double angle_filtered = 0.0;
    LinearFilter angle_filter;
    double afpc = 0.02;
    double aftc = 0.2;

    private Accelerometer accelerometer;

    private final Encoder leftEncoder;
    private final Encoder rightEncoder;

    NetworkTable drivetrain_table;
    // private final DoubleEntry max_speed;

    /**
    *
    */
    public DriveTrain() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        DigitalInput l1 = new DigitalInput(9); // yellow
        DigitalInput l2 = new DigitalInput(8); // blue
        DigitalInput r1 = new DigitalInput(7); // yellow
        DigitalInput r2 = new DigitalInput(6); // blue
        leftEncoder = new Encoder(l1,l2,true);
        rightEncoder =  new Encoder(r1,r2,false);

        leftEncoder.setDistancePerPulse(1.0/3000.0);
        rightEncoder.setDistancePerPulse(1.0/3000.0);

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

        accelerometer = new BuiltInAccelerometer();

        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        // Challenges with new networktable entries - Does no show up correctly in table
        // DoubleTopic topic =
        // inst.getDoubleTopic("SmartDashboard/DriveTrain/max_speed");
        // topic.setPersistent(true);
        // topic.publish(PubSubOption.disableLocal(false),PubSubOption.disableRemote(false),PubSubOption.periodic(0.5));
        // max_speed = topic.getEntry(1.0);
        // max_speed.set(max_speed.get());

        drivetrain_table = inst.getTable("SmartDashboard").getSubTable("DriveTrain");

        // Create persistant configuration options in network table.
        aftc = drivetrain_table.getEntry("angle_filter_time_const").getNumber(0.2).doubleValue();
        afpc = drivetrain_table.getEntry("angle_filter_period_const").getNumber(0.02).doubleValue();

        drivetrain_table.getEntry("angle_filter_time_const").setNumber(aftc);
        drivetrain_table.getEntry("angle_filter_period_const").setNumber(afpc);

        drivetrain_table.getEntry("angle_filter_time_const").setPersistent();
        drivetrain_table.getEntry("angle_filter_period_const").setPersistent();

        angle_filter = LinearFilter.singlePoleIIR(aftc, afpc);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

        checkNetworkTableChanges();

        drivetrain_table.getEntry("accel_x").setNumber(accelerometer.getX());
        drivetrain_table.getEntry("accel_y").setNumber(accelerometer.getY());
        drivetrain_table.getEntry("accel_z").setNumber(accelerometer.getZ());

        accel_angle = -Math.atan2(getAccelX(), getAccelZ()) * 180 / Math.PI;

        drivetrain_table.getEntry("accel_angle").setNumber(accel_angle);

        if (angle_filtered > 15)
            angle_filtered = 15;
        if (angle_filtered < -15)
            angle_filtered = -15;

            drivetrain_table.getEntry("enc_dist_left").setDouble(leftEncoder.getDistance());
            //drivetrain_table.getEntry("enc_dist_right").setDouble(rightEncoder.getDistance());
            drivetrain_table.getEntry("enc_rate_left").setDouble(leftEncoder.getRate());
            //drivetrain_table.getEntry("enc_rate_right").setDouble(rightEncoder.getRate());
        
    }

    private void checkNetworkTableChanges() {
        // Check for a change in the angle filter values
        double new_tc = drivetrain_table.getEntry("angle_filter_time_const").getNumber(0.2).doubleValue();
        double new_pc = drivetrain_table.getEntry("angle_filter_period_const").getNumber(0.02).doubleValue();
        if (new_tc != aftc || new_pc != afpc) {
            aftc = new_tc;
            afpc = new_pc;
            angle_filter = LinearFilter.singlePoleIIR(aftc, afpc);
            System.out.printf("Updating angle filter values. time=%.3f period=%.3f \n", aftc, afpc);
        }
        angle_filtered = angle_filter.calculate(accel_angle);

        drivetrain_table.getEntry("angle_filtered").setDouble(angle_filtered);
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
        return angle_filtered;
    }
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

}
