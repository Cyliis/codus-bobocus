package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


@Config
public class MecanumDrive {

    enum State{
        FIELD,
        ROBOT
    }
    public static State state=State.ROBOT;
    HardwareMap hm;
    public static boolean swap , resetImu;
    public static DcMotorEx leftTop , leftDown , rightTop , rightDown;
    double x,y,rotate;
    double botHeading;


    public static IMU imu;
    public MecanumDrive(HardwareMap hm)
    {
        this.hm=hm;
        init();
    }
    private void init()
    {

        leftTop=hm.get(DcMotorEx.class , "ch1");
        rightTop=hm.get(DcMotorEx.class , "ch0");
        leftDown=hm.get(DcMotorEx.class , "ch2");
        rightDown=hm.get(DcMotorEx.class , "ch3");
        imu=hm.get(IMU.class , "imu");
        leftTop.setDirection(DcMotorSimple.Direction.REVERSE);
        rightDown.setDirection(DcMotorSimple.Direction.REVERSE);
        leftTop.setPower(0);
        rightTop.setPower(0);
        leftDown.setPower(0);
        rightDown.setPower(0);
        leftTop.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
        ));
         botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);




    }

    public void updateState()
    {
        if(swap)
        {
            swap=false;
            switch(state)
            {
                case FIELD: state=State.ROBOT;
                break;
                case ROBOT:state=State.FIELD;
                break;
            }

        }

    }

    public void updateVar()
    {
        switch(state)
        {
            case ROBOT :
            {
                x=Gm.x;
                y=Gm.y;
                rotate=Gm.rotate;
                break;
            }
            case FIELD :
            {
                if (resetImu)imu.resetYaw();
                botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

                x=Gm.x*Math.cos(-botHeading) - Gm.y *Math.sin(-botHeading);
                y=Gm.x*Math.sin(-botHeading) + Gm.y * Math.cos(-botHeading);
                rotate=Gm.rotate;
                break;
            }
        }
    }

    private void updatePow()
    {
        double det=Math.max(1 , Math.abs(x)+Math.abs(y)+Math.abs(rotate));
        leftTop.setPower((y+x-rotate)/det);
        rightTop.setPower((y-x+rotate)/det);
        leftDown.setPower((y-x-rotate)/det);
        rightDown.setPower((y+x+rotate)/det);
    }
    public void update()
    {
        if (resetImu)imu.resetYaw();
        updateState();
        updateVar();
        updatePow();
    }
}
