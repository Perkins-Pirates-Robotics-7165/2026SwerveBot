package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.WallBedSubsystem;

public class MoveWallBed extends Command {

    private final WallBedSubsystem wallBedSubsystem;
    private final double speed;

    /**
     * Move the wall bed system
     * 
     * @param wallBedSubsystem - The wall bed subsystem to control intake
     * @param speed - Wall bed movement speed [-1.0, 1.0]. 
     */
    public MoveWallBed(WallBedSubsystem wallBedSubsystem, double speed) {

        // Set the subsystem
        this.wallBedSubsystem = wallBedSubsystem;

        // Save the speed
        this.speed = speed;

        // Adds the requirement of subsystem(s) so two commands can't use it at once
        addRequirements(wallBedSubsystem);
    }

    // Runs once when initialized
    @Override
    public void initialize() {}

    // Runs while the command is 'sceduled' (aka. while the button is pressed on with a .whileTrue)
    @Override
    public void execute() {
        // Start the wall bed with the givin speed
        wallBedSubsystem.moveWallBedMotor(speed);
    }

    // When the command is finished
    @Override
    public void end(boolean interrupted) {
        wallBedSubsystem.moveWallBedMotor(0.0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}