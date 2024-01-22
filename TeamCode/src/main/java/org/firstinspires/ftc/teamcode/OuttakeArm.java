package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class OuttakeArm {


    public static double posD=0.94, posO=.28;
    public static double maxVelocity=16 , acc=12 , dec=10 ;
    public static BetterMotionProfile profile=new BetterMotionProfile(maxVelocity , acc , dec);

    enum State
    {
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
    Servo servoLeft , servoRight;
    public OuttakeArm(HardwareMap hm)
    {
        this.hm=hm;
        init();
    }
    private void init()
    {
        servoLeft=hm.get(Servo.class , "seh2");
        servoLeft.setDirection(Servo.Direction.FORWARD);
        servoRight=hm.get(Servo.class , "seh4");
        servoRight.setDirection(Servo.Direction.REVERSE);
        profile.setMotion(posD , posD , 0);

    }
    private void updatePosition()
    {
        switch (state)
        {
            case Outtake:
                if(profile.finalPosition!=posO)profile.setMotion(profile.getPosition() , posO , profile.getVelocity());
                servoLeft.setPosition(profile.getPosition());
                servoRight.setPosition(profile.getPosition());
               break;
            case Default:
                if(profile.finalPosition!=posD)profile.setMotion(profile.getPosition() , posD , profile.getVelocity());
                servoLeft.setPosition(profile.getPosition());
                servoRight.setPosition(profile.getPosition());
                break;
        }

    }
    public void update()
    {
        updatePosition();
        profile.update();
    }
}
