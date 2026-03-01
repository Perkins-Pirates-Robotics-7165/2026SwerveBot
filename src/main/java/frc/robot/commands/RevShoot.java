package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.BumpSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SuckSubsystem;

public class RevShoot extends Command {

    // Subsystems
    private final ShooterSubsystem shooterSubsystem;
    private final BumpSubsystem bumpSubsystem;
    private final SuckSubsystem suckSubsystem;

    // Shooter & Bump speeds for the rev
    private final double shooterSpeed;
    private final double bumpSpeed;

    // Suck speed & supplier to start the suck or not
    private final Supplier<Boolean> startSuck;
    private final double suckSpeed;

    
    /**
     * Start revving the shooter & bump motor, then when ready start the suck motor
     * 
     * @param shooterSubsystem - The subsystem for shooting
     * @param bumpSubsystem - The subsystem for bumping the balls
     * @param suckSubsystem - The subsystem for sucking balls
     * @param shooterSpeed - Shooter motor speed [-1.0, 1.0].
     * @param bumpSpeed - Bump motor speed [-1.0, 1.0].  
     * @param startSuck - Boolean suplier used for when to start the suck motor (true -> start)
     * @param suckSpeed - Suck motor speed [-1.0, 1.0].  
     */
    public RevShoot(
        ShooterSubsystem shooterSubsystem, 
        BumpSubsystem bumpSubsystem,
        SuckSubsystem suckSubsystem,
        double shooterSpeed,
        double bumpSpeed,
        Supplier<Boolean> startSuck,
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

        // Save the suck speed & supplier
        this.startSuck = startSuck;
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
        // Start reving the shooter & bump
        shooterSubsystem.shoot(shooterSpeed);
        bumpSubsystem.bump(bumpSpeed);

        // If the startSuck supplier is true, run the suck motor, turn it off if supplier is false
        if (startSuck.get()) {
            suckSubsystem.suck(suckSpeed);
        } else {
            suckSubsystem.suck(0.0);
        }
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