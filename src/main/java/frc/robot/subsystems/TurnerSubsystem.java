package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.TurnerConstants;

public class TurnerSubsystem extends SubsystemBase {

    // The motor seperating the bin from the shooter
    private final VictorSPX turnMotor = new VictorSPX(TurnerConstants.turnMotorID);


    // Initializer, use to set configurations and set attributes
    public TurnerSubsystem() {

    }

    /*
     * Start the suck motor
     * 
     * @param speed - Suck Motor drive percentage [-1.0, 1.0]
     */
    public void turn(double speed) {
        turnMotor.set(VictorSPXControlMode.PercentOutput, speed);
    }

    // Period function on field, called every 20ms
    @Override
    public void periodic() {}

    // Periodic function during simulation, called every 20ms
    @Override
    public void simulationPeriodic() {}

}