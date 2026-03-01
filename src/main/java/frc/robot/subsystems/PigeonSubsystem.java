package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.PigeonConstants;

public class PigeonSubsystem extends SubsystemBase {

    // Save the new pigeon
    private static final Pigeon2 pigeon = new Pigeon2(PigeonConstants.pigeonID);


    /* Full Rotation Structures */

    // TODO: Find out more about the returned values:
    // Rotation 2d
    public static Rotation2d getRotation2d() {
        return pigeon.getRotation2d();
    }

    // TODO: Find out more about the returned values
    // Rotation 3d
    public static Rotation3d getRotation3d() {
        return pigeon.getRotation3d();
    }


    /* Get Pitch, Yaw, and Roll */

    // Get forward / back tilt (Pitch)
    public static double getForwardBackTilt() {
        return pigeon.getPitch().getValueAsDouble();
    }

    // Get left / right deviation (Yaw)
    public static double getLeftRightDeviation() {
        return pigeon.getYaw().getValueAsDouble();
    }

    // Get left / right tilt (Roll)
    public static double getLeftRightTilt() {
        return pigeon.getRoll().getValueAsDouble();
    }


    /* Set Yaw */

    // Set left / right deviation (Yaw)
    public static void setLeftRightDeviation(double rotationAngle) {
        pigeon.setYaw(rotationAngle);
    }

    // Set left / right deviation (Yaw)
    public static void setLeftRightDeviation(Angle rotationAngle) {
        pigeon.setYaw(rotationAngle);
    }

}