# TODO: Reuse getBotPose Array in LimelightSubsystem

**Severity:** Medium (performance, deferred — limelight not yet active)  
**File:** `src/main/java/frc/robot/subsystems/LimelightSubsystem.java`

## Context

The limelight is not currently in active use. Address this when activating the limelight (alongside TODOs #02, #03, #06).

## Problem

`getBotPose()` passes `new double[6]` as the default value to `getDoubleArray()` on every call. Every call to `getBotPose()` (and indirectly `getDistance()`) allocates a new heap array, contributing to GC pressure during the 20ms robot loop.

```java
public double[] getBotPose() {
    return botposeEntry.getDoubleArray(new double[6]); // ← new allocation every call
}
```

## Fix

Declare a reusable field and pass it as the default instead:

```java
// Class field:
private final double[] m_poseCache = new double[6];

// Updated method:
public double[] getBotPose() {
    return botposeEntry.getDoubleArray(m_poseCache);
}
```

> **Note:** With this change, callers must not hold onto the returned array across loop iterations — the same backing array is reused. Since `getDistance()` and any command execute methods read the array and immediately compute from it, this is safe in the current usage.

## Steps

1. Add `private final double[] m_poseCache = new double[6]` as a class field.
2. Change `getBotPose()` to pass `m_poseCache` instead of `new double[6]`.
3. Review all callers of `getBotPose()` to ensure none store the reference for later use.
