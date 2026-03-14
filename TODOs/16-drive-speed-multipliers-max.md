# TODO: Reduce Drive Speed Multipliers

**Severity:** Low  
**File:** `src/main/java/frc/robot/Constants.java`

## Problem

Both drive speed multipliers are set to their maximum possible value:

```java
// Constants.java — DriveConstants
public static final double driveSpeedMultiplier = 1.0;   // 100% of max speed
public static final double rotationSpeedMultiplier = 1.0; // 100% of max angular rate
```

The swerve drivetrain uses 8 motors (4 drive + 4 steer). At full speed, these motors represent the single largest sustained current draw on the robot. Running at 70–80% of maximum speed reduces average current draw meaningfully while having little practical effect on match performance for most driving scenarios.

Max speed is `TunerConstants.kSpeedAt12Volts` = 3.79 m/s. At `1.0` multiplier the robot is commanded up to 3.79 m/s; at `0.75` it caps at ~2.84 m/s, which is still very fast for field navigation.

## Fix

Reduce both multipliers to a value that keeps gameplay effective but limits peak current demand. The rotation multiplier is often the most impactful to reduce since hard rotation spins all 4 drive motors against each other:

```java
public static final double driveSpeedMultiplier = 0.75;    // ~2.84 m/s top speed
public static final double rotationSpeedMultiplier = 0.65; // reduces counter-rotating current spike
```

## Steps

1. Reduce `driveSpeedMultiplier` to `0.75` and `rotationSpeedMultiplier` to `0.65` as a starting point.
2. Run a practice match and ask drivers whether the reduced speed feels manageable.
3. Monitor battery voltage — if sag is improved, keep the values; if drivers need more speed, increase `driveSpeedMultiplier` in 0.05 increments.
4. Keep `rotationSpeedMultiplier` lower than `driveSpeedMultiplier` to limit the current spike from rapid rotation.
