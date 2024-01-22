package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Base64;

@Config
public class Lift {

    enum State{
        Up,
        Adjusting,
        GoingDown,
        Down;
    }
    public static State state=State.Down;
    public static double position=0;
    double eroare;
    double pow;
    public static ElapsedTime timer=new ElapsedTime();
    PID pid=new PID();
    boolean ok=true;
    double lastPosition;
    int nr;
    HardwareMap hm;
    public static DcMotorEx lift1 , lift2 ,encoder;
    public static double maxVelocity=12000 , acc=10000 , dec=3500 ;
    public static boolean jos=true;
    public static BetterMotionProfile profile=new BetterMotionProfile(maxVelocity , acc , dec);
    public Lift(HardwareMap hm)
    {
        this.hm=hm;
        init();
    }
    private void init()
    {
        lift1=hm.get(DcMotorEx.class , "eh0");
        lift2=hm.get(DcMotorEx.class , "eh1");
        lift2.setDirection(DcMotorSimple.Direction.REVERSE);
        encoder=hm.get(DcMotorEx.class , "ch1");
        encoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        encoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        profile.setMotion(0 , 0 , 0);
        timer.startTime();
    }

    public void updatePower()
    {
        switch(state)
        {
            case Adjusting:
                if(200!=profile.finalPosition)profile.setMotion(profile.getPosition() , 200 , profile.getVelocity());
                pow=pid.pidControl(profile.getPosition() , -encoder.getCurrentPosition());
                lift1.setPower(pow);
                lift2.setPower(pow);
                jos=false;
                if(Pitch.profile.getTimeToMotionEnd()==0 && OuttakeArm.profile.getTimeToMotionEnd()==0)
                {state=State.GoingDown;
                    lift2.setPower(-0.5);
                    lift1.setPower(-0.5);
                    nr=0;
                }
                break;
            case GoingDown:
                if(-encoder.getCurrentPosition()==lastPosition)nr++;
                jos=false;
                if(nr==60)
                {state=State.Down;
                    lift1.setPower(0);
                    lift2.setPower(0);
                    jos=true;
                    encoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    encoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    profile.setMotion(0 , 0 , 0);
                }
                lastPosition=-encoder.getCurrentPosition();
                break;
            case Up:
                if(position<200)position=200;
                if(position>700)position=700;
                ok=false;
                if (profile.finalPosition != position) profile.setMotion(profile.getPosition(), position, profile.getVelocity());
                pow=pid.pidControl(profile.getPosition() , -encoder.getCurrentPosition());
                lift1.setPower(pow);
                lift2.setPower(pow);
                nr=0;
                jos=true;
                break;
        }
        profile.update();
    }
    public void update()
    {
        updatePower();
    }
}
