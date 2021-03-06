package org.usfirst.frc.team2706.robot.controls;

import java.util.function.Supplier;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.buttons.Trigger;

public class LimitSwitch extends Trigger {

    private final Supplier<Boolean> input;
    
    public LimitSwitch(TalonSRX talon, boolean forward) {
        if(forward) {
            input = talon.getSensorCollection()::isFwdLimitSwitchClosed;
        }
        else {
            input = talon.getSensorCollection()::isRevLimitSwitchClosed;
        }
    }
    
    public LimitSwitch(int channel) {
        input = new DigitalInput(channel)::get;
    }
    
    @Override
    public boolean get() {
        return !input.get();
    }

}
