// RobotBuilder Version: 5.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

// ROBOTBUILDER TYPE: Command.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
//import java.util.function.DoubleSupplier;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Settings3140;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */
public class TestBalance extends CommandBase {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
    private final DriveTrain m_driveTrain;

    private Settings3140 settings = Settings3140.getInstance();

    //NetworkTable balance_table;
    double max_speed = 0.55;
    double max_angle = 15.0;
    //double pid_p = max_speed / max_angle;
    //double pid_i = 0.0;
    //double pid_d = 0.0;

    PIDController pid = new PIDController(0.10,0.00,0.02);

    //NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable DataNAVX ;


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

    public TestBalance(DriveTrain subsystem) {

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES

        m_driveTrain = subsystem;
        addRequirements(m_driveTrain);
        pid.setIntegratorRange(-1.0, 1.0);

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES

        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        DataNAVX= inst.getTable("SmartDashboard").getSubTable("DataNAVX");
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {

        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        // Challenges with new networktable entries - Does no show up correctly in table
        // DoubleTopic topic =
        // inst.getDoubleTopic("SmartDashboard/DriveTrain/max_speed");
        // topic.setPersistent(true);
        // topic.publish(PubSubOption.disableLocal(false),PubSubOption.disableRemote(false),PubSubOption.periodic(0.5));
        // max_speed = topic.getEntry(1.0);
        // max_speed.set(max_speed.get());

        //balance_table = inst.getTable("SmartDashboard").getSubTable("Test Balance");
        //DataNAVX = inst.getTable("SmartDashboard").getSubTable("DataNAVX");


        //settings.getDouble("balance_speed", max_speed);
        //settings.getDouble("balance_pid_p", pid_p);
        //settings.getDouble("balance_pid_i", pid_i);
        //settings.getDouble("balance_pid_d", pid_d);

        // Create persistant configuration options in network table.
        // max_speed = balance_table.getEntry("max_speed").getDouble(max_speed);
        // max_angle = balance_table.getEntry("max_angle").getDouble(max_angle);
        // pid_p = balance_table.getEntry("pid_p").getDouble(pid_p);
        // pid_i = balance_table.getEntry("pid_i").getDouble(pid_i);
        /// pid_d = balance_table.getEntry("pid_d").getDouble(pid_d);

        // balance_table.getEntry("max_speed").setNumber(max_speed);
        // balance_table.getEntry("max_angle").setNumber(max_angle);
        /// balance_table.getEntry("pid_p").setNumber(pid_p);
        // balance_table.getEntry("pid_i").setNumber(pid_i);
        // balance_table.getEntry("pid_d").setNumber(pid_d);
        // balance_table.getEntry("current_time").setNumber(System.currentTimeMillis()/1000.0);

        /// balance_table.getEntry("max_speed").setPersistent();
        // balance_table.getEntry("max_angle").setPersistent();
        // balance_table.getEntry("pid_p").setPersistent();
        // balance_table.getEntry("pid_i").setPersistent();
        // balance_table.getEntry("pid_d").setPersistent();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        //updateNetworkTableEntries();

        double angle = -DataNAVX.getEntry("navx_filtered_pitch").getDouble(0.0);

        //pid.setP(settings.getDouble("balance_pid_p"));
        //pid.setI(settings.getDouble("balance_pid_i"));
        //pid.setD(settings.getDouble("balance_pid_d"));
        double targ_speed = pid.calculate(-angle);

        // limit max speed
        double speed = Math.min(Math.max(targ_speed, -max_speed), max_speed);

        System.out.printf("angle=%.3f targ_speed=%.3f speed=%.3f max_speed=%.3f p=%.5f i=%.5f d=%.5f\n", angle, targ_speed, speed, max_speed, pid.getP(),pid.getI(),pid.getD());
        m_driveTrain.arcadeDrive(speed, 0);

        //balance_table.getEntry("current_angle").setNumber(angle);
        //balance_table.getEntry("current_speed").setNumber(speed);

        // if (angle > 14) {
        // m_driveTrain.arcadeDrive(0.5, 0);
        // } else if (angle < 12 && angle > 2) {
        // m_driveTrain.arcadeDrive(-0.6, 0);
        // } else if (angle > -12 && angle < -2) {
        // m_driveTrain.arcadeDrive(0.6, 0);
        // } else if (angle < -14) {
        // m_driveTrain.arcadeDrive(-0.5, 0);
        // }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean runsWhenDisabled() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DISABLED
        return false;

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DISABLED
    }
}
