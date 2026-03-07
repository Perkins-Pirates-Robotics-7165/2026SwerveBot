package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {

    // Top shooter motor
    private final SparkFlex shooterMotor = new SparkFlex(ShooterConstants.shooterMotorID, MotorType.kBrushless);

    // Initializer, use to set configurations and set attributes
    public ShooterSubsystem() {

        // // Define the flex config
        // SparkFlexConfig sparkFlexConfig = new SparkFlexConfig();

        // // Set the config to inverted
        // sparkFlexConfig.encoder.inverted(true);

        // // Set the configurator to our motor
        // shooterMotor.configure(sparkFlexConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    }

    /*
     * Start the shooter
     * 
     * @param speed - Shooter Motor drive percentage [-1.0, 1.0]
     */
    public void shoot(double speed) {
        shooterMotor.set(speed);
    }

    // Period function on field, called every 20ms
    @Override
    public void periodic() {}

    // Periodic function during simulation, called every 20ms
    @Override
    public void simulationPeriodic() {}

}