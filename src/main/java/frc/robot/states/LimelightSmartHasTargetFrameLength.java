package frc.robot.states;

import frc.robot.Constants.LimelightConstants;

public enum LimelightSmartHasTargetFrameLength {
    SHORT, MEDIUM, LONG;

    public static LimelightSmartHasTargetFrameLength fromInteger(Integer length) {
        if (length <= LimelightConstants.shortFrameCountTopBound) {
            return LimelightSmartHasTargetFrameLength.SHORT;
        }

        else if (length <= LimelightConstants.mediumFrameCountTopBound) {
            return LimelightSmartHasTargetFrameLength.MEDIUM;
        }

        else {
            return LimelightSmartHasTargetFrameLength.LONG;
        }
    }

    public LimelightSmartHasTargetFrameLength getBelowFrameLength() {
        switch (this) {
            case LONG:
                return MEDIUM;
            case MEDIUM:
                return SHORT;
            default:
                return SHORT;
        }
    }
}
