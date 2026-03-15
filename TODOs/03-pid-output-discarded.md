# TODO: PID Output Discarded in AlignToFunnel and ApproachFunnel

**Severity:** Critical (deferred — these commands are not yet active)  
**Files:** `src/main/java/frc/robot/commands/AlignToFunnel.java`, `src/main/java/frc/robot/commands/ApproachFunnel.java`

## Context

`AlignToFunnel` and `ApproachFunnel` are not currently in active use. Fix this alongside TODO #02 when these commands are activated.

## Problem

In both commands, the P-controller output is computed and stored in a variable, then immediately **overwritten** by passing the raw (un-divided) input into `MathUtilities.clamp()`. The division by the P constant is wasted.

**AlignToFunnel:**
```java
double rotationalDrive = leftRightOffset / AlignToFunnelConstants.leftRightOffsetP; // ← computed
rotationalDrive = MathUtilities.clamp(leftRightOffset, ...); // ← rotationalDrive overwritten with raw input!
```

**ApproachFunnel:**
```java
double distanceOffsetDrive = targetDistanceOffset / ApproachFunnelConstants.targetDistanceOffsetP; // ← computed
distanceOffsetDrive = MathUtilities.clamp(targetDistanceOffset, ...); // ← overwritten with raw input!
```

## Fix

Pass the P-scaled variable (not the raw offset) into `clamp()`:

**AlignToFunnel:**
```java
double rotationalDrive = leftRightOffset / AlignToFunnelConstants.leftRightOffsetP;
rotationalDrive = MathUtilities.clamp(rotationalDrive, AlignToFunnelConstants.rotationalSpeedMin, AlignToFunnelConstants.rotationalSpeedMax);
```

**ApproachFunnel:**
```java
double distanceOffsetDrive = targetDistanceOffset / ApproachFunnelConstants.targetDistanceOffsetP;
distanceOffsetDrive = MathUtilities.clamp(distanceOffsetDrive, ApproachFunnelConstants.targetDistanceSpeedMin, ApproachFunnelConstants.targetDistanceSpeedMax);
```

## Steps

1. Apply the two-line fix in each file (fix the `clamp()` argument).
2. Re-tune the P constants in `Constants.java` after the fix — behavior will change significantly since the raw offset was previously being used as the speed.
3. Test on robot with limelight locked onto a target.
