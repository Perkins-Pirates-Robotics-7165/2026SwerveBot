package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;

public class Intake extends Command {

    private final IntakeSubsystem intakeSubsystem;
    private final double speed;

    /**
     * Intake balls with the intake subsystem
     * 
     * @param intakeSubsystem - The intake subsystem to control intake
     * @param speed - Swivel motor speed [-1.0, 1.0]. 
     */
    public Intake(IntakeSubsystem intakeSubsystem, double speed) {

        // Set the subsystem
        this.intakeSubsystem = intakeSubsystem;

        // Save the speed
        this.speed = speed;

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
        intakeSubsystem.intake(speed);
    }

    // When the command is finished
    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}