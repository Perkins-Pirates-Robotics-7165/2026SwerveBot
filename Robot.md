# Robot

This is just a planning markdown file to list subsystems / commands / whatever else I want to put down.

## Table of Contents

<!--toc:start-->
- [Robot](#robot)
  - [Subsystems](#subsystems)
  - [Commands (Top Level)](#commands-top-level)
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
- Swivel Subsystem
  - Motors:
    - Swivel Motor
- Intake Subsystem
  - Motors:
    - Intake Motor
    - WallBed motor
- Limelight Subsystem
  - Parts:
    - Limelight
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
