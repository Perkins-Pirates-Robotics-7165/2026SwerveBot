package frc.robot;

public class Constants {

    public static class ComputerConstants {
        public static final int primaryPort = 0;
    }

    public static class DriveConstants {
        public static final double driveSpeedMultiplier = 1.0;
        public static final double rotationSpeedMultiplier = 1.0;
    }

    public static class ShooterConstants {
        public static final int shooterMotorID = 1;
        public static final int bumpMotorID = 2;

        public static final double shooterSpeed = 0.2;
        public static final double bumpSpeed = 0.35;
    }

    public static class IntakeConstants {
        public static final int intakeMotorID = 5;
        public static final double intakeSpeed = 0.3;
    }

    public static class WallBedConstants {
        public static final int wallBedMainMotorID = 6;
        public static final int wallBedFollowerMotorID = 7;
        public static final int wallBedLimitSwitchPort = 0;
    }

    public static class LimelightConstants {
        public static final String limelightName = "";
    }

}
