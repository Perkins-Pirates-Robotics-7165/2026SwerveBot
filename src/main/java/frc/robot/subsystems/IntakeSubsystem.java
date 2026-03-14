package frc.robot.subsystems;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class IntakeSubsystem extends SubsystemBase {

    // The intake motor used for intaking balls into the bin
    private final SparkFlex intakeMotor = new SparkFlex(IntakeConstants.intakeMotorID, MotorType.kBrushless);

    // Initializer, use to set configurations and set attributes
    public IntakeSubsystem() {
        SparkFlexConfig config = new SparkFlexConfig();
        config.smartCurrentLimit(65, 40);  // stall, free current limits
        intakeMotor.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    }
    
    /*
     * Start the intake
     * 
     * @param speed - Intake motor drive percentage [-1.0, 1.0]
     */
    public void intake(double speed) {
        intakeMotor.set(speed);
    }

    // Period function on field, called every 20ms
    @Override
    public void periodic() {}

    // Periodic function during simulation, called every 20ms
    @Override
    public void simulationPeriodic() {}

}