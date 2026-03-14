package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {

    // Top shooter motor
    private final SparkFlex shooterMotor = new SparkFlex(ShooterConstants.shooterMotorID, MotorType.kBrushless);
    private final SparkClosedLoopController closedLoopController = shooterMotor.getClosedLoopController();
    private int currentRPM = 500;
    private int lastSetRPM = Integer.MIN_VALUE;

    // Initializer, use to set configurations and set attributes
    public ShooterSubsystem() {
        SparkFlexConfig config = new SparkFlexConfig();
        config.closedLoop.pid(0.001, 0, 0);

        shooterMotor.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    }

    /*
     * Start the shooter
     * 
     * @param rpm - Shooter Motor rpm amount
     */
    public void shoot(int rpm) {
        currentRPM = rpm;
    }

    // Period function on field, called every 20ms
    @Override
    public void periodic() {
        if (currentRPM != lastSetRPM) {
            closedLoopController.setSetpoint(currentRPM, ControlType.kVelocity);
            lastSetRPM = currentRPM;
        }

        SmartDashboard.putNumber("Shooter RPM", shooterMotor.getEncoder().getVelocity());
    }

    // Periodic function during simulation, called every 20ms
    @Override
    public void simulationPeriodic() {}

}