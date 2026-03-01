package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.WallBedSubsystem;

public class MoveWallBed extends Command {

    private final WallBedSubsystem wallBedSubsystem;
    private final double wallBedSpeed;

    /**
     * Move the intake frame (wall bed)
     * 
     * @param wallBedSubsystem - The subsystem to control the wall bed
     * @param wallBedSpeed - Wall bed motor speed [-1.0, 1.0]. 
     */
    public MoveWallBed(WallBedSubsystem wallBedSubsystem, double wallBedSpeed) {

        // Set the subsystem
        this.wallBedSubsystem = wallBedSubsystem;

        // Save the wall bed speed
        this.wallBedSpeed = wallBedSpeed;

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
        wallBedSubsystem.moveWallBedMotor(wallBedSpeed);
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