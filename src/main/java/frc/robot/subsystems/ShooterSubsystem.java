package frc.robot.subsystems;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {

    // The motor that launches the balls out to the feild
    private final SparkFlex shooterMotor = new SparkFlex(ShooterConstants.shooterMotorID, MotorType.kBrushless);

    // Initializer, use to set configurations and set attributes
    public ShooterSubsystem() {}

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