package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;

public class Intake extends Command {

    private final IntakeSubsystem intakeSubsystem;
    private final double intakeSpeed;

    /**
     * Intake balls with the intake subsystem
     * 
     * @param intakeSubsystem - The subsystem to control the intake
     * @param intakeSpeed - Intake motor speed [-1.0, 1.0]. 
     */
    public Intake(IntakeSubsystem intakeSubsystem, double intakeSpeed) {

        // Set the subsystem
        this.intakeSubsystem = intakeSubsystem;

        // Save the intake speed
        this.intakeSpeed = intakeSpeed;

        // Adds the requirement of subsystem(s) so two commands can't use it at once
        addRequirements(intakeSubsystem);
    }

    // Runs once when initialized
    @Override
    public void initialize() {}

    // Runs while the command is 'sceduled' (aka. while the button is pressed on with a .whileTrue)
    @Override
    public void execute() {
        // Start the intake with the givin speed
        intakeSubsystem.intake(intakeSpeed);
    }

    // When the command is finished
    @Override
    public void end(boolean interrupted) {
        intakeSubsystem.intake(0.0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}