package frc.robot;

import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.PigeonConstants;

public class PigeonSubsystem extends SubsystemBase {

    // Save the new pigeon
    private final Pigeon2 pigeon = new Pigeon2(PigeonConstants.pigeonID);


    /* Full Rotation Structures */

    /*
     * Get the Rotation 2d, which is mostly just a wrapper for the yaw.
     * 
     * Rotation2d.getSin() -> How much left is the robot facing (represented on the unit circle)
     * Rotation2d.getCos() -> How much forward is the robot facing (represented on the unit circle)
     * Rotation2d.getTan() -> Can return the slope, but not recommended to use due to deviding by 0
     * 
     */
    public Rotation2d getRotation2d() {
        return pigeon.getRotation2d();
    }

    
    /*
     * Get the Rotation 3d
     * 
     * Rotation3d.getX() -> Get robot roll / left + right tilt
     * Rotation3d.getY() -> Get robot pitch / forward + backward tilt
     * Rotation3d.getZ() -> Get robot yaw / left + right deviation
     * 
     */
    public Rotation3d getRotation3d() {
        return pigeon.getRotation3d();
    }


    /* Get Pitch, Yaw, and Roll */

    // Get forward / backward tilt (Pitch)
    public double getForwardBackTilt() {
        return pigeon.getPitch().getValueAsDouble();
    }

    // Get left / right deviation (Yaw)
    public double getLeftRightDeviation() {
        return pigeon.getYaw().getValueAsDouble();
    }

    // Get left / right tilt (Roll)
    public double getLeftRightTilt() {
        return pigeon.getRoll().getValueAsDouble();
    }


    /* Set Yaw */

    // Set left / right deviation (Yaw)
    public void setLeftRightDeviation(double rotationAngle) {
        pigeon.setYaw(rotationAngle);
    }

    // Set left / right deviation (Yaw)
    public void setLeftRightDeviation(Angle rotationAngle) {
        pigeon.setYaw(rotationAngle);
    }

}