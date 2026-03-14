# TODO: Reduce Turner Motor Speed to Limit Battery Drain

**Severity:** High  
**Files:** `src/main/java/frc/robot/Constants.java`, `src/main/java/frc/robot/RobotContainer.java`  
**Related:** TODO #05 (turner uncontrolled startup — fix the scheduler ownership issue there first)

## Problem

The turner motor runs at **100% output for the entire match** from the moment the robot is enabled. The constant is set to maximum:

```java
// Constants.java
public static final double turnMotorSpeed = 1.0;
```

And it is started unconditionally in the constructor with no mechanism to stop it:

```java
// RobotContainer.java constructor
turnerSubsystem.turn(TurnerConstants.turnMotorSpeed);
```

Running a mechanism motor at full power continuously is a significant and unnecessary battery drain throughout all 150 seconds of a match, independent of whether the robot is actively using the turner.

## Fix

### Step 1 — Fix scheduler ownership (see TODO #05 first)

Move the turner to a default command so the scheduler manages it. This is a prerequisite.

### Step 2 — Reduce the speed constant

Lower `turnMotorSpeed` to the minimum speed that still performs reliably. Start around 0.3–0.4 and increase only as needed:

```java
// Constants.java
public static final double turnMotorSpeed = 0.35; // was 1.0
```

### Step 3 (Optional) — Run turner only on demand

If the turner does not need to run continuously, bind it to a button rather than a default command:

```java
// configureBindings()
secondary.someButton().whileTrue(
    new RunCommand(() -> turnerSubsystem.turn(TurnerConstants.turnMotorSpeed), turnerSubsystem)
);
```

## Steps

1. Resolve TODO #05 to move the turner into a scheduler-owned default command.
2. Reduce `TurnerConstants.turnMotorSpeed` to the lowest value that still moves game pieces reliably.
3. If the turner only needs to run during specific actions, convert it to a button-bound command instead of a default command.
4. Monitor battery voltage during practice matches and reduce the speed further if possible.
