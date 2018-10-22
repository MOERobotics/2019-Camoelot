/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {


  Joystick leftJoystick = new Joystick(0);


  TalonSRX leftMotorA = new TalonSRX(1);
  TalonSRX leftMotorB = new TalonSRX(2);
  TalonSRX leftMotorC = new TalonSRX(3);

  TalonSRX rightMotorA = new TalonSRX(12);
  TalonSRX rightMotorB = new TalonSRX(13);
  TalonSRX rightMotorC = new TalonSRX(14);


  TalonSRX shooterMotorA = new TalonSRX(10);
  TalonSRX shooterMotorB = new TalonSRX(11);



  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    System.out.printf("I am a robot\n");
    System.err.printf("Beep boop\n");
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    double joystickX =  leftJoystick.getX();
    double joystickY = -leftJoystick.getY();
    double joystickZ =  leftJoystick.getZ();

    boolean buttonTwoIsPressed = leftJoystick.getRawButton(2);

    if (buttonTwoIsPressed) {
      setDriveMotorPower(0.5,-.5);

    } else if (!buttonTwoIsPressed) {
      double  leftMotorPower = capMotorPower(joystickY + joystickX);
      double rightMotorPower = capMotorPower(joystickY - joystickX);

      setDriveMotorPower(leftMotorPower, rightMotorPower);

    }




  }

  public static double capMotorPower(double inputMotorPower) {
    if (inputMotorPower >  1) inputMotorPower =  1;
    if (inputMotorPower < -1) inputMotorPower = -1;
    return inputMotorPower;
  }

  public void setDriveMotorPower(
        double leftMotorPower,
        double rightMotorPower
  ) {

    leftMotors.forEach(motor -> motor.set(ControlMode.PercentOutput,leftMotorPower));

    rightMotorA.set(ControlMode.PercentOutput, rightMotorPower);
    rightMotorB.set(ControlMode.PercentOutput, rightMotorPower);
    rightMotorC.set(ControlMode.PercentOutput, rightMotorPower);

  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
