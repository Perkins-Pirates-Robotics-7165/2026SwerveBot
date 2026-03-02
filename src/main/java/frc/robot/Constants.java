package frc.robot;

public class Constants {

    // Mostly controller constants
    public static class ComputerConstants {

        // Controller port numbers
        public static final int primaryPort = 0;
        public static final int secondaryPort = 1;
    }

    // Extra control constants for the drivetrain
    public static class DriveConstants {

        // Speed multipliers
        public static final double driveSpeedMultiplier = 1.0;
        public static final double rotationSpeedMultiplier = 1.0;
        public static final double strafeSpeed = 0.3;
    }

    // Constants for the shooter subsystem
    public static class ShooterConstants {

        // Motor ids
        public static final int shooterMotorID = 1;

        // Motor base speeds
        public static final double shooterForwardSpeed = 0.55;
        public static final double shooterReverseSpeed = -0.4;

        // Rev Shoot trigger threshold
        public static final double triggerThreshold = 0.1;
    }

    // Constants for the bump subsystem
    public static class BumpConstants {

        // Motor ids
        public static final int bumpMotorID = 2;

        // Motor base speeds
        public static final double bumpForwardSpeed = 1.0;
        public static final double bumpReverseSpeed = -1.0;
    }

    // Constants for the suck thing on the robot
    public static class SuckConstants {
    
        // Motor ids
        public static final int suckMotorID = 3;

        // Motor base speeds
        public static final double suckForwardSpeed = 0.8;    
        public static final double suckReverseSpeed = -0.8;
    }

    // Constants for the intake subsystem
    public static class IntakeConstants {

        // Motor ids
        public static final int intakeMotorID = 5;

        // Motor base speeds
        public static final double intakeForwardSpeed = 0.5;
        public static final double intakeReverseSpeed = -0.5;

        // Intake trigger threshold
        public static final double triggerThreshold = 0.1;
    }

    // Constants for the wall bed subsystem
    public static class WallBedConstants {

        // Motor ids
        public static final int wallBedMainMotorID = 6;
        public static final int wallBedFollowerMotorID = 7;

        // Limit switch ports
        public static final int wallBedLeftLimitSwitchPort = 0;
        public static final int wallBedRightLimitSwitchPort = 1;

        // Motor base speeds
        public static final double wallBedRaiseSpeed = 0.1;
        public static final double wallBedLowerSpeed = -0.1;

    }

    // Constants for the limelight subsystem
    public static class LimelightConstants {

        // Limelight server name
        public static final String limelightName = "";

        // Limelight configs
        public static final int limelightFrameRate = 30;

        // Smart Has Target telemetry constants
        public static final int savedAprilTagSightFrames = 30;
        public static final int maxCountedFrames = 240;

        // Bounds for what each frame count is counted as
        public static final int shortFrameCountTopBound = 30;
        public static final int mediumFrameCountTopBound = 100;
        public static final int longFrameCountTopBound = 240;

        // Deviation of values for frameLength mode where we will take the shorter one
        public static final int takeShorterDeviationTopBound = 6;

        
    }

    // Constants for the programatic controlled commands (limelight & PID)
    public static class ProgramaticCommandConstants {

        /* ------------------------ AlignToFunnel ------------------------ */
        public static class AlignToFunnelConstants {

            // PID values

            public static final double leftRightOffsetP = 20.0;

            // Speed clamps
            public static final double rotationalSpeedMin = -0.6;
            public static final double rotationalSpeedMax = 0.6;

        }

        /* ------------------------ ApproachFunnel ------------------------ */
        public static class ApproachFunnelConstants {

            // PID values
            public static final double targetDistanceOffsetP = 1.0;

            // Speed Clamps
            public static final double targetDistanceSpeedMin = -0.5;
            public static final double targetDistanceSpeedMax = 0.5;

        }
    }

    // Constants for the pigeon subsystem
    public static class PigeonConstants {

        // Pigeon device id
        public static final int pigeonID = 0;

    }

}
