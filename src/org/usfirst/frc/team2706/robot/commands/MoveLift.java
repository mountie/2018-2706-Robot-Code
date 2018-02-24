package org.usfirst.frc.team2706.robot.commands;

import java.util.function.Supplier;

import org.usfirst.frc.team2706.robot.Robot;
import org.usfirst.frc.team2706.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class MoveLift extends Command {

    private Lift move;
    
    private final Supplier<Double> liftspeed;

    public MoveLift(Joystick stick, int axis) {
        this(() -> stick.getRawAxis(axis));
    }
    
    /**
     * Allows us to use the methods in 'Intake'
     * 
     * @param speed The the speed
     */
    public MoveLift(double speed) {
        this(() -> speed);
    }
    
    /**
     * Allows us to use the methods in 'Intake'
     * 
     * @param speed The supplier for the speed
     */
    public MoveLift(Supplier<Double> speed) {
        move = Robot.lift;
        this.liftspeed = speed;
        this.requires(Robot.lift);
    }

    /**
     * I don't believe initialization is required 
     */
    public void initialize() {}
    
    /**
     * Turns the motors on to suck in the cube
     */
    public void execute() {
        System.out.println(liftspeed.get());
            move.move(liftspeed.get());
    }
    
    /**
     * Sets both Intake motors to 0, stopping them
     */
    public void end() {
        System.out.println("Ended IntakeCube command");
        move.stop();
    }

    protected boolean isFinished() {
        // TODO Auto-generated method stub
        return false;
    }

    
}