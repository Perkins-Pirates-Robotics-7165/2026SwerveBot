package frc.robot.subsystems;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class BumpSubsystem extends SubsystemBase {

    // The motor right below the shooter motor, used to bump the balls up to it
    private final SparkFlex bumpMotor = new SparkFlex(ShooterConstants.bumpMotorID, MotorType.kBrushless);


    // Initializer, use to set configurations and set attributes
    public BumpSubsystem() {}

    /*
     * Start the bump motor
     * 
     * @param speed - Bump Motor drive percentage [-1.0, 1.0]
     */
    public void bump(double speed) {
        bumpMotor.set(speed);
    }

    // Period function on field, called every 20ms
    @Override
    public void periodic() {}

    // Periodic function during simulation, called every 20ms
    @Override
    public void simulationPeriodic() {}

}