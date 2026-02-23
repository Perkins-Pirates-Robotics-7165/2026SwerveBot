// package frc.robot.commands;

// import edu.wpi.first.wpilibj2.command.Command;
// import frc.robot.subsystems.CommandSwerveDrivetrain;
// import frc.robot.subsystems.LimelightSubsystem;

// public class AlignToFunnel extends Command {

//     private final LimelightSubsystem limelightSubsystem;
//     private final CommandSwerveDrivetrain drivetrain;

//     /**
//      * Swivel the shooter with the swivel subsystem
//      * 
//      * @param drivetrain - Utility subsystem for the limelight
//      * @param drivetrain - The subsystem for the drivetrain
//      */
//     public AlignToFunnel(LimelightSubsystem limelightSubsystem, CommandSwerveDrivetrain drivetrain) {

//         // Set the subsystems
//         this.drivetrain = drivetrain;
//         this.limelightSubsystem = limelightSubsystem;

//         // Adds the requirement of subsystem(s) so two commands can't use it at once
//         addRequirements(limelightSubsystem);
//         addRequirements(drivetrain);
//     }

//     // Runs once when initialized
//     @Override
//     public void initialize() {}

//     // Runs while the command is 'sceduled' (aka. while the button is pressed on with a .whileTrue)
//     @Override
//     public void execute() {

//         double xDrive = limelightSubsystem.getTx() / 20.0;

//         xDrive = Math.min(Math.max(xDrive, SwivelConstants.alignSwivelToFunnel_xDriveMin), SwivelConstants.alignSwivelToFunnel_xDriveMax);

//         // Swivel the shooter with the motor's speed
//         drivetrain.applyRequest(xDrive);
//     }

//     // When the command is finished
//     @Override
//     public void end(boolean interrupted) {}

//     @Override
//     public boolean isFinished() {
//         return false;
//     }
// }