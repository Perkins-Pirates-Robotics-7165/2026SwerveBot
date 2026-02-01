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

## Motor IDs

### Drivetrain

- FLeft  
10 - Kraken Swerve  
11 - Kraken Drive  
12 - Kraken CanCoder  

- FRight  
20 - Kraken Swerve  
21 - Kraken Drive  
22 - Kraken Cancoder  

- BLeft  
30 - Kraken Swerve  
31 - Kraken Drive  
32 - Kraken Cancoder  

- BRight  
40 - Kraken Swerve  
41 - Kraken Drive  
42 - Kraken Cancoder  

### Shooter

- Shooter Motor  
0 - TalonFX  

### Swivel

- Swivel Motor  
1 - TalonFX  

### Intake

- Intake Motor  
5 - TalonFX  

- WallBed Motor  
6 - TalonFX  
