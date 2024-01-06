package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class IntakePosition {

    public static double posUp=0.6 , posGround=0.22;
    public static Servo servo;

    public static double maxVelocity=15 , acceleration=8 , deceleration=8;
    public static boolean ok=true;
    public static BetterMotionProfile profile=new BetterMotionProfile(maxVelocity ,acceleration , deceleration);
    HardwareMap hm;
    enum State{
        UP(posUp),
        GoingUP(posUp),
        GROUND(posGround),
        GoingGround(posGround);
        double pos;
        State(double pos)
        {
            this.pos=pos;
        }
    }
    public static State state=State.UP;
    public IntakePosition(HardwareMap hm)
    {
        this.hm=hm;
        init();
    }
    private void init()
    {
        servo=hm.get(Servo.class , "sch3");
        servo.setPosition(posUp);
        servo.setDirection(Servo.Direction.FORWARD);
        profile.setMotion(posUp , posUp , 0);
    }
    private void updateState()
    {
        switch(state)
        {
            case GoingGround:
            case GROUND:
                if(posGround==Gm.intakePos) state=State.GROUND;
                ok=false;
                break;
            case GoingUP:
            case UP:
                if(posUp==Gm.intakePos)state=State.UP;
                ok=true;
                break;

        }
    }
    private void updatePos()
    {
        switch(state)
        {
            case GoingUP:
            case UP:
                if(posUp!=profile.finalPosition) profile.setMotion(profile.getPosition() , posUp , profile.getVelocity());
                servo.setPosition(profile.getPosition());
                break;
            case GROUND:
            case GoingGround:
                if(posGround!=profile.finalPosition) profile.setMotion(profile.getPosition() , posGround , profile.getVelocity());
                servo.setPosition(profile.getPosition());
                break;
        }
    }


    public void update()
    {
        updatePos();
        updateState();
        profile.update();
    }
}
