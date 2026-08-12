// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;


/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends TimedRobot {
  private SparkMax motor;
  private SparkMax followermotor;
  private XboxController controller;
  private SparkClosedLoopController pid;
  private SparkClosedLoopController followerpid;
  private static final double MAX_RPM =5676.0;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    motor = new SparkMax(18, MotorType.kBrushless);
    followermotor = new SparkMax(17, MotorType.kBrushless);
    controller = new XboxController(0);

    SparkMaxConfig config = new SparkMaxConfig();
    config.inverted(true).closedLoop.pid(0.0002, 0.0000002, 0.02);
    motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    SparkMaxConfig followerconfig = new SparkMaxConfig();
    followerconfig.follow(motor, true);
    config.inverted(true);

    pid = motor.getClosedLoopController();
    followerpid =  followermotor.getClosedLoopController();
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    if (controller.getXButton()) {
      pid.setSetpoint(MAX_RPM, ControlType.kVelocity);
      followerpid.setSetpoint(MAX_RPM, ControlType.kVelocity);
    } else {
      motor.set(0.0);
      followermotor.set(0.0);
    }
  }
}
