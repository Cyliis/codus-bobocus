package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

public class Gm {

    Gamepad gamepad , gamepad2;
    static double x,y,rotate;
    boolean lift1;
    public static double intakePos;
    static boolean prevy , prevgamepadX , prevgamepadUp , prevgamepadDown;
    public Gm(Gamepad gamepad , Gamepad gamepad2)
    {
        this.gamepad=gamepad;
        this.gamepad2=gamepad2;
    }
    public void update()
    {
        intakePos=IntakePosition.servo.getPosition();
        x=gamepad.left_stick_x;
        y=-gamepad.left_stick_y;
        rotate=gamepad.left_trigger-gamepad.right_trigger;
        if(gamepad.y && !prevy)
        {
            MecanumDrive.swap=true;
        }
        prevy=gamepad.y;
        if(gamepad.left_bumper && gamepad.right_bumper)MecanumDrive.resetImu=true;
        else MecanumDrive.resetImu=false;

        if(gamepad2.right_trigger>0 && gamepad2.left_trigger==0)IntakePosition.state= IntakePosition.State.GoingGround;
        else IntakePosition.state= IntakePosition.State.GoingUP;

         if(gamepad2.left_trigger>0 && intakePos==IntakePosition.posUp)IntakePixel.state= IntakePixel.State.Reverse;
        else if(gamepad2.right_trigger>0  && intakePos==IntakePosition.posGround)
         {IntakePixel.state= IntakePixel.State.Intake;
         }
        else
         {IntakePixel.state= IntakePixel.State.Pause;
         }

        if(gamepad.right_bumper && !gamepad.left_bumper)Climber.state= Climber.State.GoingUp;
        else if(gamepad.left_bumper && !gamepad.right_bumper)Climber.state=Climber.State.GoingDown;
        else Climber.state=Climber.State.Staying;
        if(gamepad2.x && !prevgamepadX && Lift.jos==true)
        {Lift.timer.reset();
            switch(Lift.state)
            {
                case Up : Lift.state= Lift.State.Adjusting;
                          DropDown.state=DropDown.State.Close;
                          lift1=false;
                    break;
                case Down: Lift.state= Lift.State.Up;
                lift1=true;
                    break;
            }
            switch (OuttakeArm.state)
            {
                case Default:OuttakeArm.state= OuttakeArm.State.Outtake;
                break;
                case Outtake:OuttakeArm.state= OuttakeArm.State.Default;
                break;
            }
            switch(Pitch.state)
            {
                case Default:Pitch.state=Pitch.State.Outtake;
                break;
                case Outtake:Pitch.state=Pitch.State.Default;
                break;
            }
        }
        prevgamepadX=gamepad2.x;
          if(lift1==true && gamepad.b)DropDown.state= DropDown.State.Open;


        if(gamepad2.dpad_up && !prevgamepadUp)Lift.position+=50;
         prevgamepadUp=gamepad2.dpad_up;

        if(gamepad2.dpad_down && !prevgamepadDown)Lift.position-=50;
         prevgamepadDown=gamepad2.dpad_down;
    }

}
