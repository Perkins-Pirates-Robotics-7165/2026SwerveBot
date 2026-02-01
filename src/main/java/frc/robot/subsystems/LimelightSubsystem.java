package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LimelightConstants;

public class LimelightSubsystem extends SubsystemBase {

    // Main Network table for limelight
    private final NetworkTable limelightTable;

    // 't' entries
    private final NetworkTableEntry txEntry;
    private final NetworkTableEntry tyEntry;
    private final NetworkTableEntry taEntry;
    private final NetworkTableEntry tvEntry;

    // Bot position entry
    private final NetworkTableEntry botposeEntry;
    
    public LimelightSubsystem() {
        
        // Settup the network table
        limelightTable = NetworkTableInstance.getDefault().getTable(LimelightConstants.limelightName);

        // Set the offset values
        txEntry = limelightTable.getEntry("tx");
        tyEntry = limelightTable.getEntry("ty");

        // Set the other values (angle & hasTarget)
        taEntry = limelightTable.getEntry("ta");
        tvEntry = limelightTable.getEntry("tv");

        // Set the bot position entry
        botposeEntry = limelightTable.getEntry("botpose_targetspace");
    }

    // Returns the offset of the camera from the april tag on the x axes
    public double getTx() {
        return txEntry.getDouble(0.0);
    }
    
    // Returns the offset of the camera from the april tag on the y axes
    public double getTy() {
        return tyEntry.getDouble(0.0);
    }

    /*
     * Returns the percentage of the camera stream that the april tag is apart of
     * 
     * 100% - April Tag is the only thing that the camera can see
     * 0% - There is no April Tag in the camera's view
     * 
     * 100% = 100.0
     * 0% = 0.0
    */
    public double getTa() {
        return taEntry.getDouble(0.0);
    }

    // Returns true if there is an april tag in focus, returns false if there is not an april tag in focus
    public boolean hasTarget() {
        return tvEntry.getDouble(0.0) == 1.0;
    }

    // Returns the distance to the april tag (in relation to the camera) in meters
    public double getDistance() {

        // Get the bot position
        double[] pose = getBotPose();

        // Get the offset values from the bot pose
        double x = pose[0];
        double y = pose[1];
        double z = pose[2];

        // Pythagoream Theorum but in 3d
        double distance = Math.sqrt(x * x + y * y + z * z); 
        
        return distance;
    }

    /*
     * Returns positioning data of the camera in relation to the april tag
     * 
     * array[0] - x offset (meters)
     * array[1] - y offset (meters)
     * array[2] - z offset (meters)
     * array[3] - roll offset (degrees)
     * array[4] - pitch offset (degrees)
     * array[5] - yaw offset (degrees)
     * 
     */
    public double[] getBotPose() {
        return botposeEntry.getDoubleArray(new double[6]);
    }

    /*
     * Prints all information smart dashboard 
     */
    public void printInformationToSmartdashboard() {

        // TX, TY, and TA
        SmartDashboard.putNumber("Limelight TX", this.getTx());
        SmartDashboard.putNumber("Limelight TY", this.getTy());
        SmartDashboard.putNumber("Limelight TA", this.getTa());

        // If Limelight has target
        SmartDashboard.putBoolean("Limelight Has Target", this.hasTarget());

        // TZ or Limelight Distance
        SmartDashboard.putNumber("April Tag Distance", this.getDistance());

        // Botposes
        SmartDashboard.putNumberArray("BotPose", this.getBotPose());

    }
}