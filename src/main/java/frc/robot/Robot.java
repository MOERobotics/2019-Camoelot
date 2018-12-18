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


@SuppressWarnings("ALL")
public class Robot extends TimedRobot {


    Joystick rJoyStk = new Joystick(1);

    TalonSRX leftMotorA = new TalonSRX(12);
    TalonSRX leftMotorB = new TalonSRX(13);
    TalonSRX leftMotorC = new TalonSRX(14);

    TalonSRX rightMotorA = new TalonSRX(1);
    TalonSRX rightMotorB = new TalonSRX(2);
    TalonSRX rightMotorC = new TalonSRX(3);

    Encoder encoderL = new Encoder(0, 1, false, CounterBase.EncodingType.k1X);
    Encoder encoderR = new Encoder(2, 3, true, CounterBase.EncodingType.k1X);

    AHRS navX = new AHRS(SPI.Port.kMXP, (byte) 50);


    //boolean doAutoMove = false;

    @Override
    public void robotInit() {
        System.out.println("Robot Start!");
        //initiate motors to motor array

        rightMotorA.setInverted(true);
        rightMotorB.setInverted(true);
        rightMotorC.setInverted(true);
    }

    //need to find Inch / Pulses to put here
    double ticksconversion = 112;


    @Override
    public void robotPeriodic() {

    }

    @Override
    public void autonomousInit() {

    }

    @Override
    public void autonomousPeriodic() {

    }

    public void disabledPeriodic() {
        if (rJoyStk.getRawButton(3)) {
            encoderL.reset();
            encoderR.reset();
            navX.reset();
        }
    }

    @Override
    public void teleopPeriodic() {

        double yaw = navX.getYaw();
        double tolerance = .3;
        double leftPower = 0;
        double rightPower = 0;
        double rightTurn = 90;
        int x = 0;

        if (rJoyStk.getRawButton(2)) {
            SmartDashboard.putString("The button 2 works", "yes");
            encoderL.reset();
            encoderR.reset();


            if (encoderL.getDistance() <= (4*ticksconversion)) {
                if (yaw > tolerance) {
                    leftPower = 0.3;
                    rightPower = 0.4;
                }
                if (yaw < -tolerance) {
                    leftPower = 0.4;
                    rightPower = 0.3;
                }
                setDriveMP(leftPower, rightPower);
                SmartDashboard.putNumber("YawRun", yaw);


            }
            if (rJoyStk.getRawButton(3)) {
                 while (x<5) {
                     if (encoderL.getDistance() <= (4 * ticksconversion)) {

                         if (yaw > tolerance) {
                             leftPower = 0.3;
                             rightPower = 0.4;
                         }
                         if (yaw < -tolerance) {
                             leftPower = 0.4;
                             rightPower = 0.3;
                         }
                         setDriveMP(leftPower, rightPower);
                     }


                     if (yaw <= rightTurn) {
                         setDriveMP(0.3, 0.4);
                     }
                     x++;
                 }


            }
            }
        } else {
            setDriveMP(0.1, 0.1);
            //power is at 0.1 just to see if the if loop is even running
        }


        double JoyX = rJoyStk.getX();
        double JoyY = rJoyStk.getY();


        double leftMP = JoyY + JoyX;
        double rightMP = JoyY - JoyX;


        double distMoved = encoderL.getDistance();


    }

    public void setDriveMP(double leftMotorPower, double rightMotorPower) {
        leftMotorA.set(ControlMode.PercentOutput, leftMotorPower);
        leftMotorB.set(ControlMode.PercentOutput, leftMotorPower);
        leftMotorC.set(ControlMode.PercentOutput, leftMotorPower);

        rightMotorA.set(ControlMode.PercentOutput, rightMotorPower);
        rightMotorB.set(ControlMode.PercentOutput, rightMotorPower);
        rightMotorC.set(ControlMode.PercentOutput, rightMotorPower);
    }



    @Override
    public void testPeriodic() {

    }}

