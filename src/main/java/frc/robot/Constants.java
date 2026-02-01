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
        public static final int shooterMotorID = 0;
        public static final double shooterSpeed = 0.7;
    }

    public static class SwivelConstants {
        public static final int swivelMotorID = 1;

        // Command AlignSwivelToFunnel
        public static final double alignSwivelToFunnel_xDriveMax = 0.4;
        public static final double alignSwivelToFunnel_xDriveMin = -0.4;
    }

    public static class IntakeConstants {
        public static final int intakeMotorID = 5;
    }

    public static class WallBedConstants {
        public static final int wallBedMotorID = 6;
        public static final int wallBedLimitSwitchPort = 0;
    }

    public static class LimelightConstants {
        public static final String limelightName = "";
    }

}
