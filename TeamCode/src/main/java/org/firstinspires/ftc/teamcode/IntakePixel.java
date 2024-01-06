package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakePixel {

    public static double reversePower=-1 , pausePower=0 , intakePower=1;
    enum State{
        Reverse(reversePower),
        Pause(pausePower),
        Intake(intakePower);

        double power;
        State(double power)
        {
            this.power=power;
        }
    }
    public static State state=State.Pause;
    DcMotorEx motor;
    HardwareMap hm;
    public IntakePixel(HardwareMap hm)
    {
        this.hm=hm;
        init();
    }
    private void init()
    {
        motor=hm.get(DcMotorEx.class , "eh2");
        motor.setDirection(DcMotorSimple.Direction.FORWARD);

    }
    void updatePower()
    {
        if((IntakePosition.profile.getPosition()==0.6 && IntakePosition.ok==true) || (IntakePosition.profile.getPosition()==0.22  && IntakePosition.ok==false))
        motor.setPower(state.power);
        else motor.setPower(0);
    }

    public void update()
    {
        updatePower();

    }
}
