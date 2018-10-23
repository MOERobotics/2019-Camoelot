package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;

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

        boolean buttonTwoIsPressed = leftJoystick.getRawButton(2);
        boolean buttonThreeIsPressed = leftJoystick.getRawButton(3);
        boolean buttonFourIsPressed = leftJoystick.getRawButton(4);

        if (buttonThreeIsPressed && !buttonFourIsPressed) {
            collector.set(ControlMode.PercentOutput, 0.5);
        } else if (buttonFourIsPressed && !buttonThreeIsPressed) {
            collector.set(ControlMode.PercentOutput, -0.5);
        } else {
            collector.set(ControlMode.PercentOutput, 0.0);
        }

        if (buttonTwoIsPressed) {
            setDriveMotorPower(0.5, -.5);

        } else if (!buttonTwoIsPressed) {
            double leftMotorPower = capMotorPower(joystickY + joystickX);
            double rightMotorPower = capMotorPower(joystickY - joystickX);
            setDriveMotorPower(leftMotorPower, rightMotorPower);
        }

    }

    @Override
    public void testInit() {

    }

    @Override
    public void testPeriodic() {
    }

    public static double capMotorPower(double inputMotorPower) {
        if (inputMotorPower > 1) inputMotorPower = 1;
        if (inputMotorPower < -1) inputMotorPower = -1;
        return inputMotorPower;
    }

    public void setDriveMotorPower(
        double leftMotorPower,
        double rightMotorPower
    ) {
        leftMotorA.set(ControlMode.PercentOutput, leftMotorPower);
        leftMotorB.set(ControlMode.PercentOutput, leftMotorPower);
        leftMotorC.set(ControlMode.PercentOutput, leftMotorPower);

        rightMotorA.set(ControlMode.PercentOutput, rightMotorPower);
        rightMotorB.set(ControlMode.PercentOutput, rightMotorPower);
        rightMotorC.set(ControlMode.PercentOutput, rightMotorPower);
    }
}
