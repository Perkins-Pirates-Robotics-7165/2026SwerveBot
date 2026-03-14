# TODO: Add Current Limits to All Motors

**Severity:** Critical  
**Files:** `src/main/java/frc/robot/subsystems/ShooterSubsystem.java`, `src/main/java/frc/robot/subsystems/BumpSubsystem.java`, `src/main/java/frc/robot/subsystems/SuckSubsystem.java`, `src/main/java/frc/robot/subsystems/IntakeSubsystem.java`, `src/main/java/frc/robot/subsystems/WallBedSubsystem.java`

## Problem

No motor in the codebase has a current limit configured. Without limits, a stalled or heavily loaded motor will pull 80–100+ amps until the code tells it to stop. This is the most likely root cause of the voltage sag seen during matches (voltage dropping to 7–8 V on a fresh battery).

Affected motors and their drivers:

| Motor | Subsystem | Driver |
|---|---|---|
| Shooter | `ShooterSubsystem` | SparkFlex |
| Bump | `BumpSubsystem` | SparkFlex |
| Suck | `SuckSubsystem` | SparkFlex |
| Intake | `IntakeSubsystem` | SparkFlex |
| Wall Bed (×2) | `WallBedSubsystem` | TalonFX |

The swerve drive/steer TalonFX motors (managed by `TunerConstants`) should also have current limits set via Tuner X and regenerated — do not edit `TunerConstants.java` manually.

## Fix

### SparkFlex motors (Shooter, Bump, Suck, Intake)

Add `smartCurrentLimit()` to the `SparkFlexConfig` in each subsystem constructor. 40 A is a reasonable starting point; tune down if voltage sag persists.

```java
// Example — repeat for each SparkFlex subsystem
SparkFlexConfig config = new SparkFlexConfig();
config.smartCurrentLimit(40); // amps
shooterMotor.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
```

For subsystems that currently construct no config at all (Bump, Suck, Intake), create one:

```java
SparkFlexConfig config = new SparkFlexConfig();
config.smartCurrentLimit(40);
bumpMotor.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
```

### TalonFX motors (WallBed)

Add `CurrentLimitsConfigs` alongside the existing `MotorOutputConfigs` apply in `WallBedSubsystem`:

```java
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;

CurrentLimitsConfigs currentLimits = new CurrentLimitsConfigs();
currentLimits.StatorCurrentLimit = 40;        // amps — maximum allowed stator current
currentLimits.StatorCurrentLimitEnable = true;
wallBedMainMotor.getConfigurator().apply(currentLimits);
// The follower inherits stator limiting via its own hardware; configure it too:
wallBedFollowerMotor.getConfigurator().apply(currentLimits);
```

## Recommended Limits (Starting Point)

| Subsystem | Suggested Limit |
|---|---|
| Shooter (SparkFlex) | 60 A (flywheel — brief high-current spins are expected) |
| Bump (SparkFlex) | 40 A |
| Suck (SparkFlex) | 40 A |
| Intake (SparkFlex) | 40 A |
| WallBed Main (TalonFX) | 40 A |
| WallBed Follower (TalonFX) | 40 A |

## Steps

1. Add `smartCurrentLimit()` to the SparkFlex config in `ShooterSubsystem`, `BumpSubsystem`, `SuckSubsystem`, and `IntakeSubsystem`.
2. Add `CurrentLimitsConfigs` in `WallBedSubsystem` for both TalonFX motors.
3. Deploy and observe per-motor current draw on SmartDashboard or through Phoenix Tuner X during a simulated match cycle.
4. Lower limits further if voltage sag continues; raise limits if motors are unable to reach target speeds.
5. Set swerve drive/steer current limits via Tuner X and regenerate `TunerConstants.java`.
