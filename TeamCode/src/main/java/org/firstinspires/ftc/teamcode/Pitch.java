package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Pitch {

    Servo servo;
    public static double posD=0.54, posO=0.22;
    public static double maxVelocity=15 , acc=8 , dec=8 ;
    public static BetterMotionProfile profile=new BetterMotionProfile(maxVelocity , acc , dec);
    enum State{
        Default(posD),
        Outtake(posO);
        double position;
        State(double position)
        {
            this.position=position;
        }
    }
    public static State state=State.Default;
    HardwareMap hm;
    public Pitch(HardwareMap hm)
    {
        this.hm=hm;
        init();
    }
    private void init()
    {
        servo=hm.get(Servo.class , "seh0");
        servo.setDirection(Servo.Direction.FORWARD);
        profile.setMotion(posD , posD , 0);
    }
    private void updatePosition()
    {
        switch(state)
        {
            case Outtake:
                if(profile.finalPosition!=posO)profile.setMotion(profile.getPosition() , posO , profile.getVelocity());
                servo.setPosition(profile.getPosition());
            break;
            case Default:
                if(profile.finalPosition!=posD)profile.setMotion(profile.getPosition() , posD , profile.velocity);
                servo.setPosition(profile.getPosition());
            break;
        }
    }
    public void update()
    {
        updatePosition();
        profile.update();
    }
}