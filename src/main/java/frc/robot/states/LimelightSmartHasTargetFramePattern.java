package frc.robot.states;

public enum LimelightSmartHasTargetFramePattern {
    CONSISTENT_SHORT,
    CONSISTENT_MEDIUM,
    CONSISTENT_LONG,
    INTERUPTED_SHORT,
    INTERUPTED_MEDIUM,
    INTERUPTED_LONG;

    public static LimelightSmartHasTargetFramePattern getConsistantFromLength(LimelightSmartHasTargetFrameLength frameLength) {
        switch (frameLength) {
            case LONG:
                return LimelightSmartHasTargetFramePattern.CONSISTENT_LONG;
            case MEDIUM:
                return LimelightSmartHasTargetFramePattern.CONSISTENT_MEDIUM;
            default:
                return LimelightSmartHasTargetFramePattern.CONSISTENT_SHORT;
        }
    }

    public static LimelightSmartHasTargetFramePattern getInterruptedFromLength(LimelightSmartHasTargetFrameLength frameLength) {
        switch (frameLength) {
            case LONG:
                return LimelightSmartHasTargetFramePattern.INTERUPTED_LONG;
            case MEDIUM:
                return LimelightSmartHasTargetFramePattern.INTERUPTED_MEDIUM;
            default:
                return LimelightSmartHasTargetFramePattern.INTERUPTED_SHORT;
        }
    }

    public LimelightSmartHasTargetSafety getSafety(LimelightSmartHasTargetFrameLength frameLength) {

        /*
         * This is based upon this mermaid diagram:
         *
         * """
         * flowchart TD
         * A(framesQueue) -->|Pattern of Frames| B(Count)
         * B --> C(Short Frame Counts
         *    Interupted OR Consistent)
         * B --> D(Medium Consistent
         *    Frame Counts)
         * B --> E(Medium Frame Counts
         *    Interuped by Short Counts)
         * B --> F(Medium Frame Counts
         *    Interuped by Long Counts)
         * B --> G(Long Frame Counts
         *    Interupted OR Consistent)
         * C --> H(Partial Target
         *    Unsafe)
         * D --> I(Partial Target)t each competition that we go to,
         * E --> H(Partial Target
         *    Unsafe)
         * F --> J(Partial Target
         *    Safe)
         * G --> K(Strong Target)
         *
         */

        switch (frameLength) {
            case SHORT:
                return LimelightSmartHasTargetSafety.PARTIAL_TARGET_UNSAFE;
            case MEDIUM:
                switch (this) {
                    case INTERUPTED_SHORT:
                        return LimelightSmartHasTargetSafety.PARTIAL_TARGET_UNSAFE;
                    
                    case INTERUPTED_LONG:
                        return LimelightSmartHasTargetSafety.PARTIAL_TARGET_SAFE;

                    case CONSISTENT_MEDIUM:
                        return LimelightSmartHasTargetSafety.PARTIAL_TARGET;
                
                    default:
                        return LimelightSmartHasTargetSafety.NO_TARGET;
                }
            case LONG:
                return LimelightSmartHasTargetSafety.STRONG_TARGET;
            default:
                return LimelightSmartHasTargetSafety.NO_TARGET;
        }
    }
}
