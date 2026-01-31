package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {

    TalonFX shooterMotor = new TalonFX(ShooterConstants.shooterMotorID);

    public ShooterSubsystem() {}

    /*
     * Start the shooter
     * 
     * @param speed - Switch Motor drive percentage [-1.0, 1.0]
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