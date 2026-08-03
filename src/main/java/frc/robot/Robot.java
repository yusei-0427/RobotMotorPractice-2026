// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;


/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends TimedRobot {
  private SparkMax lShooter;
  private SparkMax rShooter;
  private XboxController controller;
  private PIDController pid;
  private RelativeEncoder encoder;
  private double MaxRPM = 3000;
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    lShooter = new SparkMax(18, MotorType.kBrushless);
    rShooter = new SparkMax(17, MotorType.kBrushless);
    controller = new XboxController(0);
    pid = new PIDController(0.0001, 0, 0);
    SparkMaxConfig Config = new SparkMaxConfig();
    Config.inverted(true);
    lShooter.configure(Config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    if (controller.getXButton()) {
      double output = pid.calculate(encoder.getVelocity(), MaxRPM);
      lShooter.set(output);
      rShooter.set(output);
    } else {
      lShooter.set(0.0);
      rShooter.set(0.0);
      pid.reset();
    }
  }
}
