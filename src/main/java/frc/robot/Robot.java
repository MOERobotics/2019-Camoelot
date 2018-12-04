package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Encoder;

public class Robot extends TimedRobot {


    Joystick leftJoystick = new Joystick(0);


    TalonSRX leftMotorA  = new TalonSRX(12);
    TalonSRX leftMotorB  = new TalonSRX(13);
    TalonSRX leftMotorC  = new TalonSRX(14);

    TalonSRX rightMotorA = new TalonSRX( 1);
    TalonSRX rightMotorB = new TalonSRX( 2);
    TalonSRX rightMotorC = new TalonSRX( 3);

    TalonSRX collector   = new TalonSRX( 0);

    TalonSRX shootMotorA = new TalonSRX(10);
    TalonSRX shootMotorB = new TalonSRX(11);

    Encoder encoderL = new Encoder(0, 1, true, CounterBase.EncodingType.k1X);
    Encoder encoderR = new Encoder(2, 3, true, CounterBase.EncodingType.k1X);

    @Override
    public void robotInit() {
        rightMotorA.setInverted(true);
        rightMotorB.setInverted(true);
        rightMotorC.setInverted(true);
        System.out.printf("I am a robot\n");
        System.err.printf("Beep boop\n");



    }

    @Override
    public void robotPeriodic() {

    }

    @Override
    public void disabledInit() {

    }

    @Override
    public void disabledPeriodic() {
        boolean buttonTwoIsPressed = leftJoystick.getRawButtonPressed(2);
        if(buttonTwoIsPressed){
            resetEncoders();
        }
    }

    @Override
    public void autonomousInit() {

    }

    @Override
    public void autonomousPeriodic() {

    }

    @Override
    public void teleopInit() {
    }

    private int gearNumber = 0;

    @Override
    public void teleopPeriodic() {

        boolean buttonThreeIsPressed = leftJoystick.getRawButton(3);
        boolean buttonFiveIsPressed = leftJoystick.getRawButtonPressed(5);
        boolean buttonSixIsPressed = leftJoystick.getRawButtonPressed(6);

        SmartDashboard.putNumber("Left Encoder", -1 * encoderL.getRaw());
        SmartDashboard.putNumber("Right Encoder", encoderR.getRaw());

        double leftMotorPower;
        double rightMotorPower;



        if(java.lang.Math.abs(encoderL.getRaw()) <= 1320) {
            if(buttonThreeIsPressed) {
                setDriveMotorPower(.5, .5);
            }
            else{
                setDriveMotorPower(0,0);
            }
        }


/*        if (buttonFiveIsPressed) {
            gearNumber++;
        } else if (buttonSixIsPressed) {
            gearNumber = gearNumber - 1;
        } else {
            System.out.println("Hello world");
        }
        */


        double joystickX = leftJoystick.getX();
        double joystickY = -leftJoystick.getY();

/*
        leftMotorPower = capMotorPower(joystickY + joystickX, gearNumber);
        rightMotorPower = capMotorPower(joystickY - joystickX, gearNumber);
        setDriveMotorPower(leftMotorPower, rightMotorPower);
        */
        }
         /**


         boolean buttonFiveIsPressed = leftJoystick.getRawButtonPressed(5);
        boolean buttonSixIsPressed = leftJoystick.getRawButtonPressed(6);

        double gearOneMotorPower;
        double gearTwoMotorPower;
        double gearThreeMotorPower;
        double gearFourMotorPower;

        if(buttonFiveIsPressed && !buttonSixIsPressed){
            gearOneMotorPower = capMotorPower(.25);
            gearTwoMotorPower = capMotorPower(.50);
            gearThreeMotorPower = capMotorPower(.75);
            gearFourMotorPower = capMotorPower(1.0);



        } else if(!buttonFiveIsPressed && buttonSixIsPressed){
            gearOneMotorPower = capMotorPower(-.25);
            gearTwoMotorPower = capMotorPower(-.50);
            gearThreeMotorPower = capMotorPower(-.75);
            gearFourMotorPower = capMotorPower(-1.0);
        }
*/


    @Override
    public void testInit() {

    }

    @Override
    public void testPeriodic() {
    }

    public void resetEncoders(){
        encoderL.reset();
        encoderR.reset();
    }

    public static double capMotorPower(double inputMotorPower, int gearNumber) {
        if(gearNumber == 1) {
            inputMotorPower = inputMotorPower * 0.25;

        } else if (gearNumber == 2) {
            inputMotorPower = inputMotorPower * 0.50;

        } else if (gearNumber == 3) {
            inputMotorPower = inputMotorPower * 0.75;

        } else if (gearNumber == 4) {
            inputMotorPower = inputMotorPower;

        }
        return inputMotorPower;

    }

    public void setDriveMotorPower(
        double leftMotorPower,
        double rightMotorPower

    ) {
        SmartDashboard.putNumber("Power of the left motor:", leftMotorPower);
        SmartDashboard.putNumber("Power of the right motor:", rightMotorPower);

        leftMotorA.set(ControlMode.PercentOutput, leftMotorPower);
        leftMotorB.set(ControlMode.PercentOutput, leftMotorPower);
        leftMotorC.set(ControlMode.PercentOutput, leftMotorPower);

        rightMotorA.set(ControlMode.PercentOutput, rightMotorPower);
        rightMotorB.set(ControlMode.PercentOutput, rightMotorPower);
        rightMotorC.set(ControlMode.PercentOutput, rightMotorPower);
    }
}
