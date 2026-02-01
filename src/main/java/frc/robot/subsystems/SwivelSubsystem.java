package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.SwivelConstants;

public class SwivelSubsystem extends SubsystemBase {

    private final TalonFX swivelMotor = new TalonFX(SwivelConstants.swivelMotorID);

    // Initializer, use to set configurations and set attributes
    public SwivelSubsystem() {}

    /*
     * Move the swivel
     * 
     * @param speed - Swivel motor drive percentage [-1.0, 1.0]
     */
    public void moveSwivel(double speed) {
        swivelMotor.set(speed);
    }

    // Period function on field, called every 20ms
    @Override
    public void periodic() {}

    // Periodic function during simulation, called every 20ms
    @Override
    public void simulationPeriodic() {}

}