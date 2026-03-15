# TODO: Consistent Shooter Motor Stop Behavior

**Severity:** Low  
**Files:** `src/main/java/frc/robot/commands/Shoot.java`, `src/main/java/frc/robot/commands/RevShoot.java`, `src/main/java/frc/robot/commands/ShooterFullRun.java`

## Problem

The shooter motor is stopped differently across commands:

| Command | `end()` behavior |
|---|---|
| `Shoot` | `shooterSubsystem.shoot(0)` — PID targets 0 RPM (active braking) |
| `RevShoot` | `shooterSubsystem.shoot(500)` — idles at 500 RPM |
| `ShooterFullRun` | `shooterSubsystem.shoot(0)` — PID targets 0 RPM |

Commanding a flywheel PID controller to 0 RPM causes the controller to actively resist rotation, which can stress the motor and gearbox. Using 500 RPM in one command but 0 in others is also inconsistent and confusing.

## Fix

Add a dedicated `stop()` method to `ShooterSubsystem` that calls `shooterMotor.stopMotor()` (which disables the controller output rather than commanding 0 RPM). Use it in all `end()` methods:

**In `ShooterSubsystem.java`:**
```java
public void stop() {
    shooterMotor.stopMotor();
    currentRPM = 0;
}
```

**In each command's `end()`:**
```java
@Override
public void end(boolean interrupted) {
    shooterSubsystem.stop();
    // ... stop other motors
}
```

## Steps

1. Add `stop()` to `ShooterSubsystem`.
2. Update `end()` in `Shoot`, `RevShoot`, and `ShooterFullRun` to call `shooterSubsystem.stop()`.
3. Decide on idle behavior: if there's a reason to keep the flywheel spinning at 500 RPM between shots (faster spin-up), add a separate `idle()` method and use it intentionally rather than as the default stop.
4. Test to confirm the motor spins down cleanly and doesn't jerk to a stop.
