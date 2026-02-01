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
- Swivel Subsystem
  - Motors:
    - Swivel Motor
  - Methods:
    - swivel(speed)
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
    - setWallbedState(WallBedState.State)
    - moveMotor(speed)
    - softLimitSwitchState() -> boolean
- Limelight Subsystem
  - Parts:
    - Limelight
  - Methods:
    - getTx() -> double
    - getTy() -> double
    - getTa() -> double
    - hasTarget() -> boolean
    - getDistance() -> double
    - getBotPose() -> ```double[6]```
    - printTelemetry()
- *Pigeon Subsystem?* - Maybe??
  - Parts:
    - Pigeon 2

## Commands (Top Level)

- Shoot
  - Subsystem Requirements:
    - Shooter Subsystem
- Swivel
  - Subsystem Requirements:
    - Swivel Subsystem
- Intake
  - Subsystem Requirements:
    - Intake Subsystem
- AlignSwivelToFunnel
  - Subsystem Requirements:
    - Limelight Subsystem
    - Swivel Subsystem
- AlignDriveToFunnel
  - Subsystem Requirements:
    - Limelight Subsystem
    - Drivetrain

## Motor IDs

### Drivetrain

| Motor Use | Motor ID | Motor Type |
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

| Motor Use      | Motor ID | Motor Type |
|---------------|----------|------------|
| Shooter Motor | 0        | TalonFX    |

### Swivel

| Motor Use    | Motor ID | Motor Type |
|-------------|----------|------------|
| Swivel Motor | 1        | TalonFX    |

### Intake

| Motor Use     | Motor ID | Motor Type |
|--------------|----------|------------|
| Intake Motor | 5        | TalonFX    |

### WallBed

| Motor Use | Motor ID | Motor Type |
| --------------- | --------------- | --------------- |
| WallBed Motor | 6 | TalonFX |
