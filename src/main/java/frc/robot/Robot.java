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
  private SparkMax lShooter;
  private SparkMax rShooter;
  private XboxController controller;
  private SparkClosedLoopController leftShooterController;
  private SparkClosedLoopController rightShooterController;
  private static final double MAX_RPM = 3000.0;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    lShooter = new SparkMax(18, MotorType.kBrushless);
    rShooter = new SparkMax(17, MotorType.kBrushless);
    controller = new XboxController(0);

    // Configure the PID loop that runs on the Spark MAX, using its built-in encoder for feedback.
    SparkMaxConfig leftConfig = new SparkMaxConfig();
    leftConfig
        .inverted(true)
        .closedLoop
        .pid(0.0001, 0.0, 0.0);
    lShooter.configure(leftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // Only the left shooter is inverted. The right shooter uses the same PID gains normally.
    SparkMaxConfig rightConfig = new SparkMaxConfig();
    rightConfig.closedLoop.pid(0.0001, 0.0, 0.0);
    rShooter.configure(rightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    leftShooterController = lShooter.getClosedLoopController();
    rightShooterController = rShooter.getClosedLoopController();
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    if (controller.getXButton()) {
      leftShooterController.setSetpoint(MAX_RPM, ControlType.kVelocity);
      rightShooterController.setSetpoint(MAX_RPM, ControlType.kVelocity);
    } else {
      lShooter.set(0.0);
      rShooter.set(0.0);
    }
  }
}
