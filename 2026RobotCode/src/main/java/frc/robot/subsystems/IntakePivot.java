// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import frc.robot.Constants.MotorConstants;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkBase;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.MotorConstants;

public class IntakePivot extends SubsystemBase {
  private SparkMax motor = new SparkMax(MotorConstants.intakePivotID, MotorType.kBrushless);
  private SparkClosedLoopController m_controller;
  public double kP, kI, kD;

    private double bottomAngle = 13.616;
    private double topAngle = 86.365;
private double angleMotorRatio=20.3;//TODO:made this num up
  /** Creates a new IntakePivot. */


  private static IntakePivot instance = null;

  public static IntakePivot getInstance() {
     if(instance == null) {
       instance = new IntakePivot();
     }

     return instance;
  }
  private IntakePivot() {
     kP = 5e-5; 
    kI = 1e-6;
    kD = 0; 

    SparkMaxConfig config = new SparkMaxConfig();
    config
    .inverted(true)
    .idleMode(IdleMode.kBrake);
config.absoluteEncoder
    .positionConversionFactor(1)
    .velocityConversionFactor(1).inverted(false);
config.closedLoop
    .pid(kP,kI,kD).positionWrappingEnabled(true).positionWrappingInputRange(0, 1);
    motor.configure(config,SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
    
    m_controller= motor.getClosedLoopController();
  }
  

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void up () {
    // Set the setpoint of the PID controller in raw position mode
    m_controller.setSetpoint(topAngle*angleMotorRatio/360.0, ControlType.kPosition);
  }
public void down () {
    // Set the setpoint of the PID controller in raw position mode
    m_controller.setSetpoint(bottomAngle*angleMotorRatio/360.0, ControlType.kPosition);
  }

}
