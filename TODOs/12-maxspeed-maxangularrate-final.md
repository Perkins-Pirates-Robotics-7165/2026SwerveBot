# TODO: Declare MaxSpeed and MaxAngularRate as final in RobotContainer

**Severity:** Low (code quality)  
**File:** `src/main/java/frc/robot/RobotContainer.java`

## Problem

`MaxSpeed` and `MaxAngularRate` are declared as `private double` but are never reassigned after the class is constructed. Missing `final` means the compiler won't catch accidental reassignment, and the intent — that these are fixed constants — isn't communicated clearly.

```java
private double MaxSpeed = 1.0 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond);
private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond);
```

## Fix

Add `final`:

```java
private final double MaxSpeed = 1.0 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond);
private final double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond);
```

Also note: by Java convention, `final` instance fields that are effectively constants should use `camelCase` (since they are not `static`). The current `PascalCase` names (`MaxSpeed`, `MaxAngularRate`) follow a CTRE template style. This is a minor style note — changing the names would require updating all usages, so only do it if the team wants to enforce consistent naming.

## Steps

1. Add `final` to both field declarations.
2. Build to confirm there are no accidental reassignment sites that would now fail to compile.
