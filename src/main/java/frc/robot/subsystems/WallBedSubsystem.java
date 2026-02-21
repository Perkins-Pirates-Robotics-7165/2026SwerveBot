package frc.robot.subsystems;

import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.WallBedConstants;
import frc.robot.states.WallBedState;

public class WallBedSubsystem extends SubsystemBase {

    private final TalonFX wallBedMainMotor = new TalonFX(WallBedConstants.wallBedMainMotorID);
    private final TalonFX wallBedFollowerMotor = new TalonFX(WallBedConstants.wallBedFollowerMotorID);

    private final DigitalInput wallBedLimitSwitch = new DigitalInput(WallBedConstants.wallBedLimitSwitchPort);


    // Initializer, use to set configurations and set attributes
    public WallBedSubsystem() {

        MotorOutputConfigs wallBedMainMotorConfig = new MotorOutputConfigs();
        wallBedMainMotorConfig.Inverted = InvertedValue.CounterClockwise_Positive;

        wallBedMainMotor.getConfigurator().apply(wallBedMainMotorConfig);
        wallBedFollowerMotor.setControl(new Follower(WallBedConstants.wallBedMainMotorID, MotorAlignmentValue.Opposed));
    }

    /*
     * Move the wallbed motor
     * 
     * @param speed - Wall Bed motor drive speed [-1.0, 1.0]
     */
    public void moveWallBedMotor(double speed) {
        wallBedMainMotor.set(speed);
    }

    /*
     * Set the wallbed state to a position
     * 
     * @param wallBedState - WallBedState enum, moves the wall bed to a position
     */
    public void setWallbedState(WallBedState wallBedState) {
        // TODO: Finish
    }

    /*
     * Returns the position that the limit switch is in for the wall bed
     * 
     * @return boolean - True: Pressed, False: Not Pressed
     */
    public boolean getWallBedLimitSwitchState() {
        return wallBedLimitSwitch.get();
    }

    // Period function on field, called every 20ms
    @Override
    public void periodic() {}

    // Periodic function during simulation, called every 20ms
    @Override
    public void simulationPeriodic() {}

}