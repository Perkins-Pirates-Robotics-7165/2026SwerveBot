package frc.robot.subsystems;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.SuckConstants;

public class SuckSubsystem extends SubsystemBase {

    // The motor separating the bin from the shooter
    private final SparkFlex suckMotor = new SparkFlex(SuckConstants.suckMotorID, MotorType.kBrushless);


    // Initializer, use to set configurations and set attributes
    public SuckSubsystem() {
        SparkFlexConfig config = new SparkFlexConfig();
        config.smartCurrentLimit(65, 35);  // stall, free current limits
        suckMotor.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    }

    /*
     * Start the suck motor
     * 
     * @param speed - Suck Motor drive percentage [-1.0, 1.0]
     */
    public void suck(double speed) {
        suckMotor.set(speed);
    }

    // Period function on field, called every 20ms
    @Override
    public void periodic() {}

    // Periodic function during simulation, called every 20ms
    @Override
    public void simulationPeriodic() {}

}