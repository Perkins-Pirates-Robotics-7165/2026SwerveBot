package frc.robot.subsystems;

import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.WallBedConstants;

public class WallBedSubsystem extends SubsystemBase {

    // The 2 motors on either side of the robot, controlling entire intake frame (wall bed)
    private final TalonFX wallBedMainMotor = new TalonFX(WallBedConstants.wallBedMainMotorID);
    private final TalonFX wallBedFollowerMotor = new TalonFX(WallBedConstants.wallBedFollowerMotorID);

    // Two limit switches to make sure the wall bed doesn't move too far
    private final DigitalInput wallBedLeftLimitSwitch = new DigitalInput(WallBedConstants.wallBedLeftLimitSwitchPort);
    private final DigitalInput wallBedRightLimitSwitch = new DigitalInput(WallBedConstants.wallBedRightLimitSwitchPort);

    // Initializer, use to set configurations and set attributes
    public WallBedSubsystem() {

        // Make an inverted configurator for the main motor
        MotorOutputConfigs wallBedMainMotorConfig = new MotorOutputConfigs();
        wallBedMainMotorConfig.Inverted = InvertedValue.CounterClockwise_Positive;

        // Invert the main motor
        wallBedMainMotor.getConfigurator().apply(wallBedMainMotorConfig);

        // Set the follower motor for the main motor, with a reversed direction
        wallBedFollowerMotor.setControl(new Follower(WallBedConstants.wallBedMainMotorID, MotorAlignmentValue.Opposed));
    }

    /*
     * Move the wall bed, if either of the limit switches are pressed, no movement will occour
     * 
     * @param speed - Wall Bed motor drive speed [-1.0, 1.0]
     */
    public void moveWallBedMotor(double speed) {

        // If both of the limit switches are not pressed, set speed as normal (this logic is using De Morgan's laws)
        if (!(wallBedLeftLimitSwitch.get() || wallBedRightLimitSwitch.get())) {
            wallBedMainMotor.set(speed);
        } 
        
        // If one or two of the limit switches are pressed, only move if the speed is negitive (lowering the wall bed)
        else if (speed < 0.0) {
            wallBedMainMotor.set(speed);
        }

        // If neither, do not move the wall bed
        else {
            wallBedMainMotor.set(0.0);
        }
    }

    /*
     * Returns the status of the left limit switch
     * 
     * @returns boolean - True if pressed, false if not pressed
     */
    public boolean getLeftWallBedLimitSwitch() {
        return wallBedLeftLimitSwitch.get();
    }

    /*
     * Returns the status of the right limit switch
     * 
     * @returns boolean - True if pressed, false if not pressed
     */
    public boolean getRightWallBedLimitSwitch() {
        return wallBedRightLimitSwitch.get();
    }

    // Period function on field, called every 20ms
    @Override
    public void periodic() {}

    // Periodic function during simulation, called every 20ms
    @Override
    public void simulationPeriodic() {}

}