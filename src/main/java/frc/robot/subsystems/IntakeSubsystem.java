package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class IntakeSubsystem extends SubsystemBase {

    TalonFX intakeMotor = new TalonFX(IntakeConstants.intakeMotorID);

    // Initializer, use to set configurations and set attributes
    public IntakeSubsystem() {}

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