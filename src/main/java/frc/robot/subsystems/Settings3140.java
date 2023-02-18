package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/*
 * Manage persistant network table settings.
 */
public class Settings3140 extends SubsystemBase {
    
    public static Settings3140 global_instance = null;

    NetworkTable settings_table = NetworkTableInstance.getDefault().getTable("Settings3140");

    /**
     * Get a global instace of settings
     */
    public static Settings3140 getInstance(){

        if(global_instance == null){
            global_instance = new Settings3140();
        }

        assert global_instance != null;
        return global_instance;
    }

    private Settings3140(){
        settings_table = NetworkTableInstance.getDefault().getTable("Settings3140");
    }

    public double setDouble(String name, double default_value){return 0.0;}
    public double getDouble(){ return 0.0; } 
    public double getDouble(double default_value){ return 0.0; } 
    
}
