package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.kauailabs.navx.frc.AHRS;

public class Robot extends TimedRobot {


    Joystick leftJoystick = new Joystick(0);


    TalonSRX leftMotorA = new TalonSRX(12);
    TalonSRX leftMotorB = new TalonSRX(13);
    TalonSRX leftMotorC = new TalonSRX(14);

    TalonSRX rightMotorA = new TalonSRX(1);
    TalonSRX rightMotorB = new TalonSRX(2);
    TalonSRX rightMotorC = new TalonSRX(3);

    TalonSRX collector = new TalonSRX(0);

    TalonSRX shootMotorA = new TalonSRX(10);
    TalonSRX shootMotorB = new TalonSRX(11);

    AHRS navX = new AHRS(SPI.Port.kMXP, (byte) 50);

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
        boolean buttonThreeIsPressed = leftJoystick.getRawButton(3);
        if (buttonThreeIsPressed) {
            System.err.println("RESET");
            encoderL.reset();
            encoderR.reset();
            SmartDashboard.putNumber("Left Encoder: ", encoderL.getRaw());
            SmartDashboard.putNumber("Right Encoder: ", encoderR.getRaw());
            float yaw = navX.getYaw();
            navX.reset();
            SmartDashboard.putNumber("neelyaw", yaw);
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
    private int  x = 0;

    @Override
    public void teleopPeriodic() {

        boolean buttonTwoIsPressed = leftJoystick.getRawButton(2);
        boolean buttonFiveIsPressed = leftJoystick.getRawButtonPressed(5);
        boolean buttonSixIsPressed = leftJoystick.getRawButtonPressed(6);

        SmartDashboard.putNumber("Left Encoder", -1 * encoderL.getRaw());
        SmartDashboard.putNumber("Right Encoder", encoderR.getRaw());

        double leftMotorPower = 0;
        double rightMotorPower = 0;

        float yaw = navX.getYaw();
        double tolerance = 0.06;
        double left = 0;
        double right = 0;



        if (leftJoystick.getRawButton(2)) {
            encoderL.reset();
            encoderR.reset();


            if (encoderL.getDistance() <= (48 * 112)) {
                if (yaw > tolerance) {
                    leftMotorPower = 0.3;
                    rightMotorPower = 0.4;
                }
                if (yaw < -tolerance) {
                    leftMotorPower = 0.4;
                    rightMotorPower = 0.3;
                }
                setDriveMotorPower(leftMotorPower, rightMotorPower);
                SmartDashboard.putNumber("YawRun", yaw);


            }
        }
        if (leftJoystick.getRawButton(3)) {
            if (x < 5) {
                //
                if (encoderL.getDistance() <= (48 * 112)) {

                    if (yaw > tolerance) {
                        leftMotorPower = 0.3;
                        rightMotorPower = 0.4;
                    }
                    if (yaw < -tolerance) {
                        leftMotorPower = 0.4;
                        rightMotorPower = 0.3;
                    }
                    setDriveMotorPower(leftMotorPower, rightMotorPower);
                }
                navX.reset();

                if (yaw < 90) {
                    encoderL.reset();
                    setDriveMotorPower(0.0, 0.4);
                    x++;
                }

            }


        }
    }


    double JoyX = leftJoystick.getX();
    double JoyY = leftJoystick.getY();


    double leftMP = JoyY + JoyX;
    double rightMP = JoyY - JoyX;


    double distMoved = encoderL.getDistance();


    /**
     * if(buttonTwoIsPressed) {
     * <p>
     * if(yaw > tolerance){
     * left = 0.3;
     * right = 0.5;
     * }
     * else if(yaw < -tolerance){
     * left = 0.5;
     * right = 0.3;
     * }
     * else{
     * left = 0.4;
     * right = 0.4;
     * }
     * setDriveMotorPower(left, right);
     * SmartDashboard.putNumber("neelyaw", yaw);
     * <p>
     * }
     * <p>
     * <p>
     * <p>
     * /*        if (buttonFiveIsPressed) {
     * gearNumber++;
     * } else if (buttonSixIsPressed) {
     * gearNumber = gearNumber - 1;
     * } else {
     * System.out.println("Hello world");
     * }
     * <p>
     * <p>
     * <p>
     * double joystickX = leftJoystick.getX();
     * double joystickY = -leftJoystick.getY();
     * <p>
     * <p>
     * leftMotorPower = capMotorPower(joystickY + joystickX, gearNumber);
     * rightMotorPower = capMotorPower(joystickY - joystickX, gearNumber);
     * setDriveMotorPower(leftMotorPower, rightMotorPower);
     * <p>
     * <p>
     * }
     * <p>
     * <p>
     * <p>
     * boolean buttonFiveIsPressed = leftJoystick.getRawButtonPressed(5);
     * boolean buttonSixIsPressed = leftJoystick.getRawButtonPressed(6);
     * <p>
     * double gearOneMotorPower;
     * double gearTwoMotorPower;
     * double gearThreeMotorPower;
     * double gearFourMotorPower;
     * <p>
     * if(buttonFiveIsPressed && !buttonSixIsPressed){
     * gearOneMotorPower = capMotorPower(.25);
     * gearTwoMotorPower = capMotorPower(.50);
     * gearThreeMotorPower = capMotorPower(.75);
     * gearFourMotorPower = capMotorPower(1.0);
     * <p>
     * <p>
     * <p>
     * } else if(!buttonFiveIsPressed && buttonSixIsPressed){
     * gearOneMotorPower = capMotorPower(-.25);
     * gearTwoMotorPower = capMotorPower(-.50);
     * gearThreeMotorPower = capMotorPower(-.75);
     * gearFourMotorPower = capMotorPower(-1.0);
     * }
     */


    @Override
    public void testInit() {

    }

    @Override
    public void testPeriodic() {
    }

    public void resetEncoders() {
        encoderL.reset();
        encoderR.reset();
    }

    public static double capMotorPower(double inputMotorPower, int gearNumber) {
        if (gearNumber == 1) {
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
