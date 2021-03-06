package org.usfirst.frc.team2706.robot.commands.autonomous.core;

import org.usfirst.frc.team2706.robot.Log;
import org.usfirst.frc.team2706.robot.Robot;
import org.usfirst.frc.team2706.robot.RobotConfig;
import org.usfirst.frc.team2706.robot.controls.talon.TalonPID;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Have the robot drive certain distance
 */
public class TalonStraightDriveWithEncoders extends Command {

    private final double speed;

    private final double distance;

    private final double error;

    private TalonPID PID;

    private final int minDoneCycles;

    private final double P = 7.5, I = 2.0, D = 25, F = 0;

    /**
     * Drive at a specific speed for a certain amount of time
     * 
     * @param speed Speed in range [-1,1]
     * @param distance The encoder distance to travel
     * @param error The range that the robot is happy ending the command in
     * @param name The name of the of the configuration properties to look for
     */
    public TalonStraightDriveWithEncoders(double speed, double distance, double error,
                    int minDoneCycles, String name) {
        super(name);
        requires(Robot.driveTrain);

        this.speed = RobotConfig.get(name + ".speed", speed);

        this.distance = RobotConfig.get(name + ".distance", distance);

        this.error = RobotConfig.get(name + ".error", error) / 12;

        this.minDoneCycles = RobotConfig.get(name + ".minDoneCycles", minDoneCycles);

        PID = Robot.driveTrain.getTalonPID();
        PID.setPID(P, I, D, F);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Log.d(this, "Talon driving " + distance + " feet at a speed of " + speed);

        Robot.driveTrain.reset();

        Robot.driveTrain.brakeMode(true);

        // Set output speed range
        if (speed > 0) {
            PID.setOutputRange(-speed, speed);
        } else {
            PID.setOutputRange(speed, -speed);
        }

        Robot.driveTrain.initGyro = Robot.driveTrain.getHeading();

        PID.setSetpoint(distance);

        // Will accept within 5 inch of target
        PID.setError(error);

        // Start going to location
        PID.enable();

        this.doneTicks = 0;
    }

    private int doneTicks;

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

        if (PID.isOnTarget())
            doneTicks++;
        else
            doneTicks = 0;

        return doneTicks >= minDoneCycles;
    }

    // Called once after isFinished returns true
    protected void end() {
        // Disable PID output and stop robot to be safe
        PID.disable();
        Robot.driveTrain.drive(0, 0);

        Log.d(this, "Done driving");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
