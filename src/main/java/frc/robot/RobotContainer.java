// RobotBuilder Version: 5.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

// ROBOTBUILDER TYPE: RobotContainer.

package frc.robot;

import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command.InterruptionBehavior;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
//import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
//import frc.robot.subsystems.*;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.I2C.Port;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot
 * (including subsystems, commands, and button mappings) should be declared
 * here.
 */
public class RobotContainer {

    private static RobotContainer m_robotContainer = new RobotContainer();

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    // The robot's subsystems
    public final Lightshow m_lightshow = new Lightshow();
    public final DriveTrain m_driveTrain = new DriveTrain();

    // Joysticks
    private final XboxController driverController = new XboxController(0);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    //private ADS ads = new ADS();

    private final AHRS NAVX = new AHRS(Port.kMXP);
    NetworkTable DataNAVX;
    LinearFilter angle_filter = LinearFilter.singlePoleIIR(0.1, 0.02);;
    double m_last_pitch = 0.0;

    private final Comms3140 comms = Comms3140.getInstance();

    // A chooser for autonomous commands
    SendableChooser<Command> m_chooser = new SendableChooser<>();

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    private RobotContainer() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SMARTDASHBOARD
        // Smartdashboard Subsystems
        SmartDashboard.putData(m_lightshow);
        SmartDashboard.putData(m_driveTrain);

        // SmartDashboard Buttons
        SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
        SmartDashboard.putData("Test Forward", new TestForward(m_driveTrain));
        SmartDashboard.putData("Test Reverse", new TestReverse(m_driveTrain));
        SmartDashboard.putData("Test Turn", new TestTurn(m_driveTrain));
        SmartDashboard.putData("TeleOp Command", new TeleOpCommand(m_driveTrain));
        SmartDashboard.putData("Test Balance", new TestBalance(m_driveTrain));
        SmartDashboard.putData("Light Balance", new LightBalance(m_lightshow));
        SmartDashboard.putData("Light Cone", new LightCone(m_lightshow));
        SmartDashboard.putData("Light Cube", new LightCube(m_lightshow));

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SMARTDASHBOARD
        // Configure the button bindings
        configureButtonBindings();

        // Configure default commands
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SUBSYSTEM_DEFAULT_COMMAND
        m_lightshow.setDefaultCommand(new LightBalance(m_lightshow));
        m_driveTrain.setDefaultCommand(new TeleOpCommand(m_driveTrain));

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SUBSYSTEM_DEFAULT_COMMAND
        XboxController controller = getDriverController();
        m_driveTrain.setDefaultCommand(new BoostDrive(m_driveTrain,
                () -> -controller.getRawAxis(1),
                () -> -controller.getRawAxis(4),
                () -> controller.getRawAxis(3),
                () -> controller.getRawAxis(2)));

        // Configure autonomous sendable chooser
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

        Command auto_balance = new SequentialCommandGroup(
                new MoveToRamp(m_driveTrain).withInterruptBehavior(InterruptionBehavior.kCancelSelf),
                new ClimbRamp(m_driveTrain).withInterruptBehavior(InterruptionBehavior.kCancelSelf),
                new BalanceAndEngage(m_driveTrain).withInterruptBehavior(InterruptionBehavior.kCancelSelf));

        m_chooser.setDefaultOption("Autonomous Command", auto_balance);

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        DataNAVX = inst.getTable("SmartDashboard").getSubTable("DataNAVX");

        m_lightshow.setDriveTrain(m_driveTrain);

        SmartDashboard.putData("Auto Mode", m_chooser);
    }

    public static RobotContainer getInstance() {
        return m_robotContainer;
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
     * it to a
     * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=BUTTONS
        // Create some buttons
        final JoystickButton rBButton = new JoystickButton(driverController, XboxController.Button.kRightBumper.value);
        rBButton.whileTrue(new LightCube(m_lightshow).withInterruptBehavior(InterruptionBehavior.kCancelSelf));

        final JoystickButton lBButton = new JoystickButton(driverController, XboxController.Button.kLeftBumper.value);
        lBButton.whileTrue(new LightCone(m_lightshow).withInterruptBehavior(InterruptionBehavior.kCancelSelf));

        Command easy_balance = new SequentialCommandGroup(
                // new
                // MoveToRamp(m_driveTrain).withInterruptBehavior(InterruptionBehavior.kCancelSelf),
                new ClimbRamp(m_driveTrain).withInterruptBehavior(InterruptionBehavior.kCancelSelf),
                new BalanceAndEngage(m_driveTrain).withInterruptBehavior(InterruptionBehavior.kCancelSelf));

        final JoystickButton xButton = new JoystickButton(driverController, XboxController.Button.kX.value);
        xButton.whileTrue(easy_balance);

        final JoystickButton yButton = new JoystickButton(driverController, XboxController.Button.kY.value);
        yButton.whileTrue(new PongDrive(m_driveTrain, 90.0, () -> {return -driverController.getRawAxis(1);}).withInterruptBehavior(InterruptionBehavior.kCancelSelf));

        final JoystickButton aButton = new JoystickButton(driverController, XboxController.Button.kA.value);
        aButton.whileTrue(new PongDrive(m_driveTrain, -90.0, () -> {return -driverController.getRawAxis(1);}).withInterruptBehavior(InterruptionBehavior.kCancelSelf));

        Command auto_balance = new SequentialCommandGroup(
                new MoveToRamp(m_driveTrain).withInterruptBehavior(InterruptionBehavior.kCancelSelf),
                new ClimbRamp(m_driveTrain).withInterruptBehavior(InterruptionBehavior.kCancelSelf),
                new BalanceAndEngage(m_driveTrain).withInterruptBehavior(InterruptionBehavior.kCancelSelf));

        final JoystickButton startButton = new JoystickButton(driverController, XboxController.Button.kStart.value);
        startButton.whileTrue(auto_balance);

        final JoystickButton backButton = new JoystickButton(driverController, XboxController.Button.kBack.value);
        backButton.whileTrue(new ZeroNavX());

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=BUTTONS
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
    public XboxController getDriverController() {
        return driverController;
    }

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // The selected command will be run in autonomous
        return m_chooser.getSelected();
    }

    public void runCaution() {
        m_lightshow.setMode(Lightshow.kModeCaution);
    }

    public void runError() {
        m_lightshow.setMode(Lightshow.kModeError);
    }

    public void updateNavX() {
        // System.out.println("Update NAVX");
        DataNAVX.getEntry("navx_yaw").setNumber(NAVX.getYaw());
        DataNAVX.getEntry("navx_pitch").setNumber(NAVX.getPitch());
        DataNAVX.getEntry("navx_roll").setNumber(NAVX.getRoll());
        DataNAVX.getEntry("navx_compass").setNumber(NAVX.getCompassHeading());

        DataNAVX.getEntry("navx_gyrox").setNumber(NAVX.getRawGyroX());
        DataNAVX.getEntry("navx_gyroy").setNumber(NAVX.getRawGyroY());
        DataNAVX.getEntry("navx_gyroz").setNumber(NAVX.getRawGyroZ());

        double filtered_pitch = angle_filter.calculate(NAVX.getPitch());
        DataNAVX.getEntry("navx_filtered_pitch").setNumber(filtered_pitch);

        double pitch_change = Math.abs(50.0 * (filtered_pitch - m_last_pitch)); // Estimate the pitch change per second
        m_last_pitch = filtered_pitch;

        DataNAVX.getEntry("navx_pitch_change").setNumber(pitch_change);

    }

    public void zeroNavX() {
        NAVX.zeroYaw();
    }

}
