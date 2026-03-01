package frc.robot;

/* Eventually, if I get fed up enough, a function will end up in here
 * Subject for growth
 */
public class Utilities {
    public static class MathUtilities {
        public static double clamp(double numberToClamp, double clampMin, double clampMax) {
            return Math.min(Math.max(numberToClamp, clampMin), clampMax);
        }

        public static int clamp(int numberToClamp, int clampMin, int clampMax) {
            return Math.min(Math.max(numberToClamp, clampMin), clampMax);
        }
    }
}
