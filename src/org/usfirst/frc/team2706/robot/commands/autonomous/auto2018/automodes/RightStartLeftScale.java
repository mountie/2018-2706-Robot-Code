package org.usfirst.frc.team2706.robot.commands.autonomous.auto2018.automodes;

import org.usfirst.frc.team2706.robot.commands.autonomous.core.RotateDriveWithGyro;
import org.usfirst.frc.team2706.robot.commands.autonomous.core.StraightDriveWithEncoders;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RightStartLeftScale extends CommandGroup {
    public RightStartLeftScale() {
        this.addSequential(new StraightDriveWithEncoders(0.5, 20, 1, 5, "ForwardToSwitch"));
        this.addSequential(new RotateDriveWithGyro(0.5,-90,3,"RotateToSwitch"));
        this.addSequential(new StraightDriveWithEncoders(0.5,20,1,5, "PressSwitch"));
        this.addSequential(new RotateDriveWithGyro(0.5,90,3,"RotateToSwitch"));
        this.addSequential(new StraightDriveWithEncoders(0.5,5,1,5, "PressSwitch"));
    }
}