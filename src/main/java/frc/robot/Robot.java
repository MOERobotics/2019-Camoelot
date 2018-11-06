package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.*;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class Robot extends TimedRobot {
    int gear; //gear number 1,2,3,4 only

    List<Boolean> prevButton = new ArrayList<>();

    Joystick rJoyStk = new Joystick(0);

    List<TalonSRX> leftMotors = new ArrayList<>();
    List<TalonSRX> rightMotors = new ArrayList<>();
    @Override
    public void robotInit() {
        System.out.println("Robot Start!");
        //initiate motors to motor array
        for(int i=0;i<3;i++){
            leftMotors.add(new TalonSRX(i+1)); //1,2,3
            rightMotors.add(new TalonSRX(i+12)); //12,13,14
            rightMotors.get(i).setInverted(true); //set right inverted
        }
        for(int i=0;i<10;i++){
            prevButton.add(false);
        }
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

    @Override
    public void teleopPeriodic() {
        double JoyX = rJoyStk.getX();
        double JoyY = rJoyStk.getY();

        //calculated motor values using "Trusted Formula"!!!
        //left = Y+X   right = Y-X
        double leftMP = JoyY + JoyX;
        double rightMP = JoyY - JoyX;

        //get button triggers
        boolean doGearUp = didButtonTrigger(3);
        boolean doGearDown = didButtonTrigger(2);

        //if button pressed and can go up
        if(doGearUp && gear < 4) gear++;
        //if button pressed and can go down
        if(doGearDown && gear > 1) gear--;

        SmartDashboard.putNumber("GearNum", gear);

        setDriveMP(leftMP, rightMP); //apply motor drive
        updateButtonsArr(); //get all button values from joystick
    }
    private static double limit(double min, double num, double max){
        return Math.min(Math.max(num,min),max);
    }
    private void setDriveMP(double lmp, double rmp){ //takes left and right motor power
        //limit left and right motor, then go though motor array
        lmp = limit(-1,lmp,1);
        rmp = limit(-1,rmp,1);

        double gearMult = (double)gear / 4.0; //divide by 4
        SmartDashboard.putNumber("GearMult", gearMult);
        lmp *= gearMult;
        rmp *= gearMult;

        for(int i=0;i<leftMotors.size();i++){
            leftMotors.get(i).set(ControlMode.PercentOutput, lmp);
            rightMotors.get(i).set(ControlMode.PercentOutput, rmp);
        }
    }
    private void updateButtonsArr(){
        for(int i=0;i<10;i++){
            prevButton.set(i, rJoyStk.getRawButton(i));
        }
    }
    private boolean didButtonTrigger(int button) {
        boolean curButtonState = rJoyStk.getRawButton(button);
        return (prevButton.get(button) != curButtonState && curButtonState )
    }

    @Override
    public void testPeriodic() {

    }
}
