package frc.robot.subsystems;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.WallBedConstants;

public class WallBedSubsystem extends SubsystemBase {

    // The 2 motors on either side of the robot, controlling entire intake frame (wall bed)
    private final TalonFX wallBedMainMotor = new TalonFX(WallBedConstants.wallBedMainMotorID);
    private final TalonFX wallBedFollowerMotor = new TalonFX(WallBedConstants.wallBedFollowerMotorID);

    // Initializer, use to set configurations and set attributes
    public WallBedSubsystem() {

        // Make an inverted configurator for the main motor
        MotorOutputConfigs wallBedMainMotorConfig = new MotorOutputConfigs();
        wallBedMainMotorConfig.Inverted = InvertedValue.CounterClockwise_Positive;

        // Invert the main motor
        wallBedMainMotor.getConfigurator().apply(wallBedMainMotorConfig);

        // Apply current limits to both wall bed motors
        CurrentLimitsConfigs currentLimits = new CurrentLimitsConfigs();
        currentLimits.StatorCurrentLimit = 8;
        currentLimits.StatorCurrentLimitEnable = true;
        wallBedMainMotor.getConfigurator().apply(currentLimits);
        wallBedFollowerMotor.getConfigurator().apply(currentLimits);

        // Set the follower motor for the main motor, with a reversed direction
        wallBedFollowerMotor.setControl(new Follower(WallBedConstants.wallBedMainMotorID, MotorAlignmentValue.Opposed));
    }

    /*
     * Move the wall bed, if either of the limit switches are pressed, no movement will occour
     * 
     * @param speed - Wall Bed motor drive speed [-1.0, 1.0]
     */
    public void moveWallBedMotor(double speed) {
        // Move the wall bed with the set speed
        wallBedMainMotor.set(speed);
    }

    // Period function on field, called every 20ms
    @Override
    public void periodic() {}

    // Periodic function during simulation, called every 20ms
    @Override
    public void simulationPeriodic() {}

}