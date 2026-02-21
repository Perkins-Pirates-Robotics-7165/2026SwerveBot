# Robot

This is just a planning markdown file to list subsystems / commands / whatever else I want to put down.

## Table of Contents

<!--toc:start-->
- [Robot](#robot)
  - [Subsystems](#subsystems)
  - [Commands (Top Level)](#commands-top-level)
  - [Motor IDs](#motor-ids)
<!--toc:end-->

## Subsystems

- Drivetrain
  - Motors:
    - FLeft swerve & drive
    - FRight swerve & drive
    - BLeft swerve & drive
    - BRight swerve & drive
  - Parts:
    - FLeft CanCoder
    - FRight CanCoder
    - BLeft CanCoder
    - BRight CanCoder
- Shooter Subsystem
  - Motors:
    - Shooter Motor
  - Methods:
    - shoot(speed)
<!-- deprived -->
<!-- - Swivel Subsystem
  - Motors:
    - Swivel Motor
  - Methods:
    - moveSwivel(speed) -->
- Intake Subsystem
  - Motors:
    - Intake Motor
  - Methods:
    - intake(speed)
- WallBed Subsystem
  - Motors:
    - Wallbed Motor
  - Parts:
    - Limit Switch
  - Methods:

| Name | Params | Return Value |
| --------------- | --------------- | --------------- |
| setWallbedState | WallBedState.State | void |
| moveWallBedMotor | speed | void |
| getWallBedLimitSwitchState | void | boolean |

- Limelight Subsystem
  - Parts:
    - Limelight
  - Methods:

| Name | Params | Return Value |
| --------------- | --------------- | --------------- |
| getTx | void | double |
| getTy | void | double |
| getTa | void | double |
| hasTarget | void | boolean |
| getDistance | void | double |
| getBotPose | void | ```double[6]``` |
| printTelemetry | void | void |

- *Pigeon Subsystem?* - Maybe??
  - Parts:
    - Pigeon 2

## Commands (Top Level)

- Shoot
  - Subsystem Requirements:
    - Shooter Subsystem
- Intake
  - Subsystem Requirements:
    - Intake Subsystem

## Motor IDs

### Drivetrain

| Motor Use | CAN ID | Motor Type |
|----------|----------|------------|
| FLeft    | 10       | Kraken Swerve |
| FLeft    | 11       | Kraken Drive |
| FLeft    | 12       |  CanCoder |
| FRight   | 20       | Kraken Swerve |
| FRight   | 21       | Kraken Drive |
| FRight   | 22       |  CanCoder |
| BLeft    | 30       | Kraken Swerve |
| BLeft    | 31       | Kraken Drive |
| BLeft    | 32       |  CanCoder |
| BRight   | 40       | Kraken Swerve |
| BRight   | 41       | Kraken Drive |
| BRight   | 42       |  CanCoder |

### Shooter

| Motor Use      | CAN ID | Motor Type |
|---------------|----------|------------|
| Shooter Motor | 0        | TalonFX    |
| Shooter Bump motor | 5        | TalonFX    |

### Intake

| Motor Use     | CAN ID | Motor Type |
|--------------|----------|------------|
| Intake Motor | 9        | TalonFX    |

### WallBed

| Motor Use | CAN ID | Motor Type |
| --------------- | --------------- | --------------- |
| WallBed Motor Main | 6 | TalonFX |
| WallBed Motor Main | 7 | TalonFX |