package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.kauailabs.navx.frc.AHRS;


public class Robot extends TimedRobot {

    final double TICKS_TO_INCHES = 112.08;
    final double TICKS_TO_FEET = TICKS_TO_INCHES * 12;

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

    AHRS navX = new AHRS(SPI.Port.kMXP, (byte) 50);

    boolean buttonTwoIsPressed = leftJoystick.getRawButton(2);
    boolean buttonThreeIsPressed = leftJoystick.getRawButton(3);
    boolean buttonFourIsPressed = leftJoystick.getRawButton(4);
    boolean buttonFiveIsPressed = leftJoystick.getRawButton(5);
    boolean buttonSixIsPressed = leftJoystick.getRawButton(6);

    boolean button2HasBeenPressed = false;
    boolean button3HasBeenPressed = false;

    int counter = 0;
    boolean turning = false;

    @Override
    public void robotInit() {
        rightMotorA.setInverted(true);
        rightMotorB.setInverted(true);
        rightMotorC.setInverted(true);

        encoderL.reset();
        encoderR.reset();


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

        buttonTwoIsPressed = leftJoystick.getRawButton(2);
        buttonThreeIsPressed = leftJoystick.getRawButton(3);

        if (buttonThreeIsPressed) {
            encoderL.reset();
            encoderR.reset();
            navX.reset();
            SmartDashboard.putNumber("Left ENCODER: ", encoderL.getRaw());
            SmartDashboard.putNumber("Right ENCODER: ", encoderR.getRaw());
            SmartDashboard.putNumber("YeehAW: ", navX.getYaw());
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

    @Override
    public void teleopPeriodic() {

        double joystickX = leftJoystick.getX();
        double joystickY = -leftJoystick.getY();

        double leftMotorPower = capMotorPower(joystickY + joystickX);
        double rightMotorPower = capMotorPower(joystickY - joystickX);

        double yaw = navX.getYaw();
        double tolerance = 0.5;

        buttonTwoIsPressed = leftJoystick.getRawButton(2);
        buttonThreeIsPressed = leftJoystick.getRawButton(3);

        if (buttonTwoIsPressed) {
            button2HasBeenPressed = true;
        }
        else if (buttonThreeIsPressed) {
            button3HasBeenPressed = true;
        }


        //press button 2 -> go forward 48 inches, turn left 90º
        if (button2HasBeenPressed) {
            if (encoderL.getRaw() > -(48 * TICKS_TO_INCHES)) {
                leftMotorPower = 0.3;
                rightMotorPower = 0.3;

                driveStraight(yaw, leftMotorPower, rightMotorPower, tolerance);

                SmartDashboard.putNumber("Left ENCODER: ", encoderL.getRaw());
                SmartDashboard.putNumber("YeehAW: ", navX.getYaw());
            }
            else {
                button2HasBeenPressed = false;
            }
            /*else if (encoderL.getRaw() <= -(48 * TICKS_TO_INCHES)) {
                setDriveMotorPower(-0.6, 0.6);
                if (navX.getYaw() <= -90) {
                    setDriveMotorPower(0, 0);
                    button2HasBeenPressed = false;
                }
            }*/

        }

        if (button3HasBeenPressed) {

            if (counter < 4) {
                encoderL.reset();
                navX.reset();
                if (encoderL.getRaw() > -(48 * TICKS_TO_INCHES) || turning) {
                    leftMotorPower = 0.3;
                    rightMotorPower = 0.3;

                    driveStraight(yaw, leftMotorPower, rightMotorPower, tolerance);

                    SmartDashboard.putNumber("Left ENCODER: ", encoderL.getRaw());
                    SmartDashboard.putNumber("YeehAW: ", navX.getYaw());
                    SmartDashboard.putBoolean("Turning: ", false);
                }
                else {
                    turning = true;
                    if (navX.getYaw() >= -90) {
                        setDriveMotorPower(-0.6, 0.6);

                        SmartDashboard.putNumber("Left ENCODER: ", encoderL.getRaw());
                        SmartDashboard.putNumber("YeehAW: ", navX.getYaw());
                        SmartDashboard.putBoolean("Turning: ", true);
                        SmartDashboard.putNumber("Counter: ", counter);

                    }
                    else {
                        turning = false;
                        setDriveMotorPower(0, 0);
                        navX.reset();
                        counter++;
                        encoderL.reset();
                        SmartDashboard.putBoolean("Turning: ", false);
                    }

                }

            }
            else if (counter == 4)
                button3HasBeenPressed = false;

        }

        //joystick-controlled driving
        if (!button2HasBeenPressed && !button3HasBeenPressed) {
            setDriveMotorPower(rightMotorPower, leftMotorPower);

            SmartDashboard.putNumber("YeehAW: ", navX.getYaw());

            SmartDashboard.putNumber("Left ENCODER: ", encoderL.getRaw());
            SmartDashboard.putNumber("Right ENCODER: ", encoderR.getRaw());
        }


    }

    @Override
    public void testInit() {

    }

    @Override
    public void testPeriodic() {
    }


    //cap motor power at 1 and -1
    public static double capMotorPower(double inputMotorPower) {
        if (inputMotorPower > 1) inputMotorPower = 1;
        if (inputMotorPower < -1) inputMotorPower = -1;
        return inputMotorPower;
    }

    //drive
    public void setDriveMotorPower(double leftMotorPower, double rightMotorPower) {
        leftMotorA.set(ControlMode.PercentOutput, leftMotorPower);
        leftMotorB.set(ControlMode.PercentOutput, leftMotorPower);
        leftMotorC.set(ControlMode.PercentOutput, leftMotorPower);

        rightMotorA.set(ControlMode.PercentOutput, rightMotorPower);
        rightMotorB.set(ControlMode.PercentOutput, rightMotorPower);
        rightMotorC.set(ControlMode.PercentOutput, rightMotorPower);
    }

    public void driveStraight(double yaw, double leftMotorPower, double rightMotorPower, double tolerance) {
        if (yaw > tolerance) {
            leftMotorPower = 0.2;
        }
        else if (yaw < -tolerance) {
            rightMotorPower = 0.2;
        }

        setDriveMotorPower(leftMotorPower, rightMotorPower);
    }



}
