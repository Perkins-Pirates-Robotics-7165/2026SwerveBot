package frc.robot;

import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import frc.robot.generated.TunerConstants;

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
        public static final double driveSpeedMultiplier = 0.48;
        public static final double rotationSpeedMultiplier = 0.5;
        public static final double strafeSpeed = 0.3;

        // Max speeds
        public static final double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond);
        public static final double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond);

        // Auto align distance (meters)
        public static final double funnelTargetDistance = 0.8;

        // Auto aim & align timeouts (seconds)
        public static final double approachFunnelTeleopTimeout = 0.6;
        public static final double alignFunnelTeleopTimeout = 0.6;
    }

    // Constants for the shooter subsystem
    public static class ShooterConstants {

        // Motor ids
        public static final int shooterMotorID = 1;

        // Motor base speeds
        public static final int shooterForwardSpeed = 3800;
        public static final int shooterReverseSpeed = -4500;

        // Rev Shoot trigger threshold
        public static final double triggerThreshold = 0.1;
    }

    // Constants for the bump subsystem
    public static class BumpConstants {

        // Motor ids
        public static final int bumpMotorID = 2;

        // Motor base speeds
        public static final double bumpForwardSpeed = -0.6;
        public static final double bumpReverseSpeed = 1.0;
    }

    // Constants for the suck thing on the robot
    public static class SuckConstants {
    
        // Motor ids
        public static final int suckMotorID = 3;

        // Motor base speeds
        public static final double suckForwardSpeed = 0.6;    
        public static final double suckReverseSpeed = -1.0;
    }

    // Constants for the intake subsystem
    public static class IntakeConstants {

        // Motor ids
        public static final int intakeMotorID = 5;

        // Motor base speeds
        public static final double intakeForwardSpeed = 0.7;
        public static final double intakeReverseSpeed = -0.7;

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
        public static final double wallBedRaiseSpeed = 0.2;
        public static final double wallBedLowerSpeed = -0.2;

    }

    public static class TurnerConstants {

        // Motor ID
        public static final int turnMotorID = 9;

        // Base speed
        public static final double turnMotorSpeed = .6;

    }

    // Constants for the limelight subsystem
    public static class LimelightConstants {

        // Limelight server name
        public static final String limelightName = "limelight";
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

        /* ------------------------ AimAndEmptyBin ------------------------ */
        public static class AimAndEmptyBinConstants {

            // Target distance from funnel (meters)
            public static final double targetDistance = 0.8;

            // Max seconds that the wall bed will run before gravity takes over
            public static final double wallBedLowerTimeout = 1.0;

            // Time that the shooter / bumper motor will spend reving before the suck motor starts (seconds)
            public static final double shooterRevSeconds = 3.0;

        }
    }

    // Constants for the pigeon subsystem
    public static class PigeonConstants {

        // Pigeon device id
        public static final int pigeonID = 0;

    }

}
