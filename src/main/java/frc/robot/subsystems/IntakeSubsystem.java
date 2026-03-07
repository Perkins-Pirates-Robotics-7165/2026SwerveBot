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

        // // Define the flex config
        // SparkFlexConfig sparkFlexConfig = new SparkFlexConfig();

        // // Set the config to inverted
        // sparkFlexConfig.encoder.inverted(true);

        // // Set the configurator to our motor
        // intakeMotor.configure(sparkFlexConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

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