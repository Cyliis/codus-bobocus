package org.firstinspires.ftc.teamcode;

import android.widget.Switch;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class DropDown {

    enum State{
        Close,
        Open
    }
    public static State state=State.Close;
    public static double posC=0 , posO=0.7;
    Servo servoLeft , servoRight;
    HardwareMap hm;
    public DropDown(HardwareMap hm)
    {
        this.hm=hm;
        init();
    }
    private void init()
    {
        servoLeft=hm.get(Servo.class , "sch4");
        servoRight=hm.get(Servo.class , "sch5");
        servoRight.setDirection(Servo.Direction.REVERSE);
    }
    private void updatePos()
    {
        switch(state)
        {
            case Close:
                servoLeft.setPosition(posC);
                servoRight.setPosition(posC);
                break;
            case Open:
                servoLeft.setPosition(posO);
                servoRight.setPosition(posO);
                break;
        }
    }
    public void update()
    {
        updatePos();
    }
}
