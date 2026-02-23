package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;

public class TemplateCommand extends Command {

    private final TemplateSubsystem templateSubsystem;

    /**
     * Shoots balls with the shooter
     * 
     * @param templateSubsystem - 
     */
    public Shoot(TemplateSubsystem templateSubsystem) {

        // Set the subsystem
        this.templateSubsystem = templateSubsystem;

        // Adds the requirement of subsystem(s) so two commands can't use it at once
        addRequirements(templateSubsystem);
    }

    // Runs once when initialized
    @Override
    public void initialize() {}

    // Runs while the command is 'sceduled' (aka. while the button is pressed on with a .whileTrue)
    @Override
    public void execute() {
        
    }

    // When the command is finished
    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}