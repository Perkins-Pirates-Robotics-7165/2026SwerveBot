package frc.robot;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import frc.robot.states.LimelightSmartHasTargetFrameLength;

public class LimelightUtilities {
    
    public static Map<LimelightSmartHasTargetFrameLength, Integer> mode(Queue<Integer> framesQueue) {

        // Initialize a hasmap
        Map<LimelightSmartHasTargetFrameLength, Integer> frameLengthMap = new HashMap<LimelightSmartHasTargetFrameLength, Integer>();

        // Set the values in the hashmap
        frameLengthMap.put(LimelightSmartHasTargetFrameLength.SHORT, 0);
        frameLengthMap.put(LimelightSmartHasTargetFrameLength.MEDIUM, 0);
        frameLengthMap.put(LimelightSmartHasTargetFrameLength.LONG, 0);

        // Loop each frame number
        for (Integer frameNumber : framesQueue) {
            
            // Get what frame length the current frame number is counted as
            LimelightSmartHasTargetFrameLength currentFrameLength = LimelightSmartHasTargetFrameLength.fromInteger(frameNumber);

            // Key into that frame length, and add one to its value
            frameLengthMap.put(currentFrameLength, frameLengthMap.get(currentFrameLength) + 1);
        }

        // Return the final map
        return frameLengthMap;
    }

    public static int mean(Queue<Integer> framesQueue) {

        int totalAdded = 0;

        // Loop every int and add it to a total
        for (Integer frameNumber : framesQueue) {
            totalAdded += frameNumber;
        }

        // Average the numbers and return it
        return totalAdded / framesQueue.size();
    }

}