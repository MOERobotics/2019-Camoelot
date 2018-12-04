package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.*;
import com.kauailabs.navx.frc.AHRS;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class Robot extends TimedRobot {
    int gear = 1; //gear number 1,2,3,4 only

    Joystick rJoyStk = new Joystick(1);

    List<TalonSRX> leftMotors = new ArrayList<>();
    List<TalonSRX> rightMotors = new ArrayList<>();

    Encoder encoderL = new Encoder(0,1,false,CounterBase.EncodingType.k1X);
    Encoder encoderR = new Encoder(2,3,true,CounterBase.EncodingType.k1X);

    AHRS navX;

    boolean doAutoMove = false;

    @Override
    public void robotInit() {
        System.out.println("Robot Start!");
        //initiate motors to motor array
        for(int i=0;i<3;i++){
            leftMotors.add(new TalonSRX(i+1)); //1,2,3
            rightMotors.add(new TalonSRX(i+12)); //12,13,14
            rightMotors.get(i).setInverted(true); //set right inverted
        }
        //need to find Inch / Pulses to put here
        encoderL.setDistancePerPulse(1.0/112); //MAKE SURE THIS IS A DOUBLE!!!!!!!
        encoderR.setDistancePerPulse(1.0/112);
        //initialize navX (used for finding Yaw and position etc.)
        navX = new AHRS(SPI.Port.kMXP, (byte) 50);

        //initialize smart dashboard number to get later
        disp("autoMove dist", 48);
    }
    @Override
    public void robotPeriodic() {

    }

    @Override
    public void autonomousInit() {

    }

    @Override
    public void autonomousPeriodic() {

    }

    public void disabledPeriodic(){

    }

    @Override
    public void teleopPeriodic() {
        //start moving
        if(rJoyStk.getRawButtonPressed(5)){
            encoderL.reset();
            encoderR.reset();
            doAutoMove = true;
        }
        //stop movingg
        if(rJoyStk.getRawButtonPressed(4)){
            doAutoMove = false;
        }

        double JoyX = rJoyStk.getX();
        double JoyY = rJoyStk.getY();
        
        //calculated motor values using "Trusted Formula"!!!
        //left = Y+X   right = Y-X
        double leftMP = JoyY + JoyX;
        double rightMP = JoyY - JoyX;

        //int dToMove = 48;
        double dToMove = SmartDashboard.getNumber("autoMove dist", 48.0);
        double accBuffer = 12.0;

        //NEED TO WORK ON ACCELERATION
        if(doAutoMove){
            double distMoved = Math.abs(encoderL.getDistance());
            double vel = piecewiseAcc(distMoved+6, accBuffer, dToMove, 1);
            leftMP = vel;
            rightMP = vel;
            //auto stop if past distance
            if(distMoved >= dToMove){ doAutoMove = false; }
        }

        //if button pressed and can go up
        if(rJoyStk.getRawButtonPressed(3) && gear < 4) gear++;
        //if button pressed and can go down
        if(rJoyStk.getRawButtonPressed(2) && gear > 1) gear--;

        //Display Encoders
        disp("Left Encoder", encoderL.getDistance() );
        disp("Right Encoder", encoderR.getDistance() );

        //Yaw
        disp("Yaw", navX.getYaw());
        disp("Pitch", navX.getPitch());
        disp("Roll", navX.getRoll());

        setDriveMP(leftMP, rightMP); //apply motor drive
    }
    private static double limit(double min, double num, double max){
        return Math.min(Math.max(num,min),max);
    }
    private static double piecewiseAcc(double x, double buffer, double maxX, double maxY){
        if(0 < x && x < buffer){
            return (maxY/buffer) * x;
        }else if(buffer <= x && x < maxX-buffer){
            return maxY;
        }else if(maxX-buffer <= x && x < maxX){
            return -maxY/buffer * (x-maxX+buffer) + maxY;
        }
        return 0;
    }
    private void setDriveMP(double lmp, double rmp){ //takes left and right motor power
        double gearMult = (double)gear / 4.0; //divide by 4
        SmartDashboard.putNumber("MotorPower: ", gearMult);
        lmp *= gearMult;
        rmp *= gearMult;

        //limit left and right motor, then go though motor array
        lmp = limit(-1,lmp,1);
        rmp = limit(-1,rmp,1);

        for(int i=0;i<leftMotors.size();i++){
            leftMotors.get(i).set(ControlMode.PercentOutput, lmp);
            rightMotors.get(i).set(ControlMode.PercentOutput, rmp);
        }
    }

    //display functions (write to dashboard)
    private void disp(String name, boolean data){ SmartDashboard.putBoolean(name, data); }
    private void disp(String name, int data){ SmartDashboard.putNumber(name, data); }
    private void disp(String name, double data){ SmartDashboard.putNumber(name, data); }

    @Override
    public void testPeriodic() {

    }
}
