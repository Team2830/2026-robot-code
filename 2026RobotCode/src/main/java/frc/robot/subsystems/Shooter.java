// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Constants.MotorConstants;

import static edu.wpi.first.units.Units.RPM;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkBase;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  private SparkMax motorLeft = new SparkMax(MotorConstants.leftShooterID, MotorType.kBrushless);
  private SparkMax motorCenter = new SparkMax(MotorConstants.centerShooterID, MotorType.kBrushless);
  private SparkMax motorRight = new SparkMax(MotorConstants.rightShooterID, MotorType.kBrushless);
  private SparkClosedLoopController m_controllerLeft;
  private SparkClosedLoopController m_controllerCenter;
  private SparkClosedLoopController m_controllerRight;
  public double kP, kI, kD, kV;

  private static Shooter instance = null;

  public static Shooter getInstance() {
    if (instance == null) {
      instance = new Shooter();
    }

    return instance;
  }

  /** Creates a new Shooter. */
  private Shooter() {
    kP = 0.0005; // was 0.001
    kI = 0;// was 4e-6;
    kD = 0.001;
    kV = 0.0002;// was 0.0006541

    SparkMaxConfig configLeft = new SparkMaxConfig();
    configLeft
        .inverted(false)
        .idleMode(IdleMode.kCoast).smartCurrentLimit(30);

    configLeft.closedLoop.pid(kP, kI, kD).outputRange(-8500, 8500).feedForward.kV(kV);

    motorLeft.configure(configLeft, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    SparkMaxConfig configCenter = new SparkMaxConfig();
    configCenter
        .inverted(false)
        .idleMode(IdleMode.kCoast).smartCurrentLimit(30);

    configCenter.closedLoop.pid(kP, kI, kD).outputRange(-8500, 8500).feedForward.kV(kV);

    motorCenter.configure(configCenter, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    SparkMaxConfig configRight = new SparkMaxConfig();
    configRight
        .inverted(false)
        .idleMode(IdleMode.kCoast).smartCurrentLimit(30);

    configRight.closedLoop.pid(kP, kI, kD).outputRange(-8500, 8500).feedForward.kV(kV);

    motorRight.configure(configRight, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    m_controllerLeft = motorLeft.getClosedLoopController();

    m_controllerCenter = motorCenter.getClosedLoopController();

    m_controllerRight = motorRight.getClosedLoopController();

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putData("Shooter", this);
    SmartDashboard.putNumber("Left Shooter PV", -motorLeft.getEncoder().getVelocity());
  }

  public void shootSimple(double speed) {
    motorLeft.set(-speed);
    motorCenter.set(-speed);
    motorRight.set(speed);

  }

  public void shootComplex(double rpm) {
    m_controllerLeft.setSetpoint(-rpm, ControlType.kVelocity);

    m_controllerCenter.setSetpoint(-rpm, ControlType.kVelocity);
    m_controllerRight.setSetpoint(rpm, ControlType.kVelocity);

  }

  public double getRPM() {
    return motorLeft.getEncoder().getVelocity();
  }

  public void stop() {
    motorLeft.stopMotor();
    motorCenter.stopMotor();
    motorRight.stopMotor();

  }

}
