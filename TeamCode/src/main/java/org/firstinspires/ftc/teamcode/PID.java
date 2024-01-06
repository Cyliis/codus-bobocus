package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
public class PID {

    double integralSum=0;
    public static double kp=0.055;
    public static double error=0;
    public static double ki=0.0000052;
    public static double kd=0.00052;
    double lasterror=0;

    ElapsedTime timer= new ElapsedTime();
    public double pidControl(double reference , double state)
    {
         error=reference-state;
        integralSum+=error*timer.seconds();
        double derivative=(error-lasterror) / timer.seconds();
        lasterror=error;
        timer.reset();
        double output=error * kp + derivative * kd + integralSum * ki;
        return output;
    }
}
