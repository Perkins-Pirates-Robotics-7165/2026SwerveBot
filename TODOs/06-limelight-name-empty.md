# TODO: Set Limelight NetworkTable Name in Constants

**Severity:** High (deferred — limelight not yet active)  
**File:** `src/main/java/frc/robot/Constants.java`

## Context

The limelight subsystem is not currently in active use. Set this constant before activating the limelight.

## Problem

The limelight NetworkTable name is an empty string:

```java
public static final String limelightName = "";
```

All limelight data (`tx`, `ty`, `tv`, `ta`, `botpose_targetspace`) is read from this table. An empty string means the code is reading from the root NetworkTable instead of the limelight's actual table. Every read will silently return the default value (0.0 / false), making the limelight appear non-functional with no error messages.

## Fix

Set the name to match the limelight's configured hostname. The default Limelight hostname is `"limelight"`. If this robot uses a custom name (configured via the limelight web UI), use that instead.

```java
public static final String limelightName = "limelight"; // or "limelight-front", etc.
```

## Steps

1. Connect to the limelight web interface (default: `http://limelight.local:5801`).
2. Check the **Settings** tab for the configured hostname.
3. Update `LimelightConstants.limelightName` in `Constants.java` to match.
4. Verify in the FRC Driver Station NetworkTables viewer that limelight entries appear under the correct table name.
