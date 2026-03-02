package frc.robot.subsystems;

import java.util.Collections;
import java.util.Map;
import java.util.Queue;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightUtilities;
import frc.robot.Constants.LimelightConstants;
import frc.robot.states.LimelightSmartHasTargetFrameLength;
import frc.robot.states.LimelightSmartHasTargetFramePattern;
import frc.robot.states.LimelightSmartHasTargetSafety;

public class LimelightSubsystem extends SubsystemBase {


    /* Network Tables */

    // Main Network table for limelight
    private final NetworkTable limelightTable;

    // 't' entries
    private final NetworkTableEntry txEntry;
    private final NetworkTableEntry tyEntry;
    private final NetworkTableEntry taEntry;
    private final NetworkTableEntry tvEntry;

    // Bot position entry
    private final NetworkTableEntry botposeEntry;


    /* Telemetry Data */

    // Telemetry data for smartHasTarget();

    // Frames that a limelight target has been seen
    private int currentAprilTagFramesInSight = 0;
    private Queue<Integer> framesQueue;

    public LimelightSubsystem() {
        
        // Setup the network table
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

    /*
     * Periodic functions run about every 20ms
     * 
     * Used for things like telemetry or logging
     */
    @Override
    public void periodic() {

        // Start taking telemetry data for LimelightSubsystem.smartHasTarget()
        this.smartHasTargetTelemetry();

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

    // Telemetry logging for the smartHasTarget
    public void smartHasTargetTelemetry() {

        // Check to see if the limelight has sight of an april tag
        // Also check to make sure the april tag count is less than the long counter
        if (this.hasTarget() && this.currentAprilTagFramesInSight < LimelightConstants.maxCountedFrames) {

            // If there is sight, add one to the frame counter
            this.currentAprilTagFramesInSight += 1;
        } 
        
        // If there is no sight of the april tag
        else {
            
            // Check to make sure that we aren't adding more than wanted to framesQueue
            if (this.framesQueue.size() >= LimelightConstants.savedAprilTagSightFrames) {
                
                // If there is a greater (or equal) number of frames saved to the number wanted to save, pop one off
                this.framesQueue.remove();
            }

            // Add the current frame time to the queue
            this.framesQueue.add(this.currentAprilTagFramesInSight);

            // Reset the current frame time 
            this.currentAprilTagFramesInSight = 0;
        }
    }

    /*
     * A little bit smarter hasTarget(), used for deciding how much sight the limelight actually has.
     * 
     * @returns LimelightSmartHasTargetState - The state that the current april tag sight is in
     */
    public LimelightSmartHasTargetSafety smartHasTarget() {
        
        /* Find the frameLength of framesQueue */

        // Use mode function to get table of frame lengths
        Map<LimelightSmartHasTargetFrameLength, Integer> modeMapFramesQueue = LimelightUtilities.mode(framesQueue);

        // Get the final frame length
        LimelightSmartHasTargetFrameLength frameLength = this.findFrameLengthOfFramesQueue(modeMapFramesQueue);

        /* Find the framePattern of framesQueue */
        LimelightSmartHasTargetFramePattern framePattern = this.findFramePatternOfFramesQueue(modeMapFramesQueue, frameLength);

        return framePattern.getSafety(frameLength);

    }

    private LimelightSmartHasTargetFrameLength findFrameLengthOfFramesQueue(Map<LimelightSmartHasTargetFrameLength, Integer> modeMapFramesQueue) {
        
        Map.Entry<LimelightSmartHasTargetFrameLength, Integer> trueMode = Collections.max(modeMapFramesQueue.entrySet(), Map.Entry.comparingByValue());

        // If the highest is short, return that
        if (trueMode.getKey() == LimelightSmartHasTargetFrameLength.SHORT) {
            return trueMode.getKey();
        } 

        // Take the highest and the one below it (if the highest is short, return that)
        LimelightSmartHasTargetFrameLength belowTrueMode = trueMode.getKey().getBelowFrameLength();


        // If the one below it is between 0 and a deviation, go with that
        LimelightSmartHasTargetFrameLength newCurrentFrameLengthWithDeviation = ((trueMode.getValue() - modeMapFramesQueue.get(belowTrueMode)) > LimelightConstants.takeShorterDeviationTopBound) ? trueMode.getKey() : belowTrueMode;
        
        // Make sure that we're not already at the bottom. This will only run if the frame length is LONG
        if (belowTrueMode != LimelightSmartHasTargetFrameLength.SHORT) {
            // Compare the bottom (short) with the newCurrentFrameLengthWithDeviation
            newCurrentFrameLengthWithDeviation = ((modeMapFramesQueue.get(newCurrentFrameLengthWithDeviation) - modeMapFramesQueue.get(LimelightSmartHasTargetFrameLength.SHORT)) > LimelightConstants.takeShorterDeviationTopBound) ? newCurrentFrameLengthWithDeviation : LimelightSmartHasTargetFrameLength.SHORT;
        }

        // Return newCurrentFrameLengthWithDeviation
        return newCurrentFrameLengthWithDeviation;
    }

    private LimelightSmartHasTargetFramePattern findFramePatternOfFramesQueue(Map<LimelightSmartHasTargetFrameLength, Integer> modeMapFramesQueue, LimelightSmartHasTargetFrameLength frameLength) {
        // Use mean function against framesQueue
        int meanOfFramesQueue = LimelightUtilities.mean(framesQueue);

        // If that value is within the found frame length, return consistent that
        if (LimelightSmartHasTargetFrameLength.fromInteger(meanOfFramesQueue) == frameLength) {
            return LimelightSmartHasTargetFramePattern.getConsistantFromLength(frameLength);
        }

        // If it not
    
        // Clone framesQueue
        Queue<Integer> clonedFramesQueue = framesQueue;

        // Take the shortest frame length's appearence count, remove that amount from the cloned framesQueue
        int minimumValue = Collections.min(modeMapFramesQueue.entrySet(), Map.Entry.comparingByValue()).getValue();

        for (int i = 0; i < minimumValue; i++) {

            if (clonedFramesQueue.size() <= 1) break;

            clonedFramesQueue.remove();
        }

        // Do the mean function again
        int meanOfModifiedFramesQueue = LimelightUtilities.mean(framesQueue);

        // If that value is within the found frame length from before, return consistent that
        LimelightSmartHasTargetFrameLength meanOfModifiedFrameQueueFrameLength = LimelightSmartHasTargetFrameLength.fromInteger(meanOfModifiedFramesQueue);

        if (meanOfModifiedFrameQueueFrameLength == frameLength) {
            return LimelightSmartHasTargetFramePattern.getConsistantFromLength(frameLength);
        }

        // If not, call the pattern interupted with that
        return LimelightSmartHasTargetFramePattern.getInterruptedFromLength(meanOfModifiedFrameQueueFrameLength);
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