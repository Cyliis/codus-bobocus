package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Config
@TeleOp(name = "buru")
public class Main extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        Gm gm= new Gm(gamepad1 , gamepad2);
        MecanumDrive mecanumDrive= new MecanumDrive(hardwareMap);
        IntakePosition intakepos = new IntakePosition(hardwareMap);
        IntakePixel intakePixel=new IntakePixel(hardwareMap);
        Climber climber = new Climber(hardwareMap);
        Lift lift = new Lift(hardwareMap);
        OuttakeArm outtakearm=new OuttakeArm(hardwareMap);
        Pitch pitch=new Pitch(hardwareMap);
        DropDown dropDown=new DropDown(hardwareMap);
        waitForStart();

        if (isStopRequested()) return;
        while (opModeIsActive()) {
            telemetry.addData("State" , IntakePosition.state);
            telemetry.addData("StatePos" , IntakePosition.servo.getPosition());
            telemetry.addData("error" , PID.error);
            telemetry.addData("currentPosition" , Lift.encoder.getCurrentPosition());
            telemetry.addData("AsymmetricMotionProfile" , Lift.profile.getPosition());
            telemetry.addData("Position" , Lift.position);
            telemetry.addData("State" , Lift.state);
            telemetry.addData("StateArm" , OuttakeArm.state);
            telemetry.addData("StatePitch" , Pitch.state);
            telemetry.addData("POSITIONINTAKE" , IntakePosition.profile.getPosition());
            gm.update();
            outtakearm.update();
            pitch.update();
            mecanumDrive.update();
            intakepos.update();
            intakePixel.update();
            telemetry.update();
            climber.update();
            lift.update();
            dropDown.update();


        }
    }
}

