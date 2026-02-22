package frc.robot;

public class Constants {

    // Mostly controller constants
    public static class ComputerConstants {

        // Controller port numbers
        public static final int primaryPort = 0;
    }

    // Extra control constants for the drivetrain
    public static class DriveConstants {

        // Speed multipliers
        public static final double driveSpeedMultiplier = 1.0;
        public static final double rotationSpeedMultiplier = 1.0;
    }

    // Constants for the shooter subsystem
    public static class ShooterConstants {

        // Motor ids
        public static final int shooterMotorID = 1;
        public static final int bumpMotorID = 2;

        // Motor base speeds
        public static final double shooterSpeed = 0.2;
        public static final double bumpSpeed = 0.35;
    }

    // Constants for the intake subsystem
    public static class IntakeConstants {

        // Motor ids
        public static final int intakeMotorID = 5;

        // Motor base speeds
        public static final double intakeSpeed = 0.3;
    }

    // Constants for the wall bed subsystem
    public static class WallBedConstants {

        // Motor ids
        public static final int wallBedMainMotorID = 6;
        public static final int wallBedFollowerMotorID = 7;
    }

    // Constants for the limelight constants
    public static class LimelightConstants {

        // Limelight server name
        public static final String limelightName = "";
    }

}
