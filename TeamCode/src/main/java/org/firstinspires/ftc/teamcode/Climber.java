package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Climber {

    enum State
    {
        GoingUp,
        Staying,
        GoingDown;
    }
    public static State state=State.Staying;
    DcMotorEx motor;
    HardwareMap hm;
    public Climber(HardwareMap hm)
    {
        this.hm=hm;
        init();
    }
    private void init()
    {
        motor=hm.get(DcMotorEx.class , "eh3");
        motor.setPower(0);
    }

    private void updatePower()
    {
        switch(state)
        {
            case GoingUp: motor.setPower(1);
            break;
            case Staying: motor.setPower(0);
            break;
            case GoingDown: motor.setPower(-1);
        }
    }
    public void update()
    {
        updatePower();
    }
}
