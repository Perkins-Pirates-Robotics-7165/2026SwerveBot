package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.WallBedConstants;
import frc.robot.states.WallBedState;

public class WallBedSubsystem extends SubsystemBase {

    private final TalonFX wallBedMotor = new TalonFX(WallBedConstants.wallBedMotorID);

    private final DigitalInput wallBedLimitSwitch = new DigitalInput(WallBedConstants.wallBedLimitSwitchPort);


    // Initializer, use to set configurations and set attributes
    public WallBedSubsystem() {}

    /*
     * Move the wallbed motor
     * 
     * @param speed - Wall Bed motor drive speed [-1.0, 1.0]
     */
    public void moveWallBedMotor(double speed) {
        wallBedMotor.set(speed);
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