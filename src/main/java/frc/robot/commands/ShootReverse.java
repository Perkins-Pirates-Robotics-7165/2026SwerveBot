package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.BumpSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SuckSubsystem;

public class ShootReverse extends Command {

    private final ShooterSubsystem shooterSubsystem;
    private final SuckSubsystem suckSubsystem;
    private final BumpSubsystem bumpSubsystem;

    private final double shooterSpeed;
    private final double bumpSpeed;
    private final double suckSpeed;

    
    /**
     * Start revving the shooter motor, then when ready start the suck motor
     * 
     * @param shooterSubsystem - The subsystem for shooting
     * @param speed - Shooter motor speed [-1.0, 1.0]. 
     */
    public ShootReverse(
        ShooterSubsystem shooterSubsystem, 
        SuckSubsystem suckSubsystem,
        BumpSubsystem bumpSubsystem,
        double shooterSpeed,
        double bumpSpeed,
        double suckSpeed
    ) {

        // Set the subsystems
        this.shooterSubsystem = shooterSubsystem;
        this.suckSubsystem = suckSubsystem;
        this.bumpSubsystem = bumpSubsystem;

        // Save the shooter speed
        this.shooterSpeed = shooterSpeed;

        // Bump speed
        this.bumpSpeed = bumpSpeed;

        // Suck speed
        this.suckSpeed = suckSpeed;
        
        // Adds the requirement of subsystem(s) so two commands can't use it at once
        addRequirements(shooterSubsystem, bumpSubsystem, suckSubsystem);
    }

    // Runs once when initialized
    @Override
    public void initialize() {}

    // Runs while the command is 'sceduled' (aka. while the button is pressed on with a .whileTrue)
    @Override
    public void execute() {

        // Start reving the shooter
        shooterSubsystem.shoot(shooterSpeed);

        // Start the bump
        bumpSubsystem.bump(bumpSpeed);

        // Start to suck
        suckSubsystem.suck(suckSpeed);
    }

    // When the command is finished
    @Override
    public void end(boolean interrupted) {
        shooterSubsystem.shoot(0.0);
        bumpSubsystem.bump(0.0);
        suckSubsystem.suck(0.0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}