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


import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  private SparkMax motorLeft = new SparkMax(MotorConstants.leftShooterID, MotorType.kBrushless);
  private SparkMax motorCenter = new SparkMax(MotorConstants.centerShooterID, MotorType.kBrushless);
  private SparkMax motorRight = new SparkMax(MotorConstants.rightShooterID, MotorType.kBrushless);
    private SparkClosedLoopController m_controllerLeft;
    private SparkClosedLoopController m_controllerCenter;
    private SparkClosedLoopController m_controllerRight;
  public double kP, kI, kD;


   private static Shooter instance = null;

   public static Shooter getInstance() {
      if(instance == null) {
         instance = new Shooter();
      }

      return instance;
   }
  
  /** Creates a new Shooter. */
  private Shooter() 
  {
    kP = 5e-5; 
    kI = 4e-6;
    kD = 0; 



    SparkMaxConfig configLeft = new SparkMaxConfig();
    configLeft
    .inverted(false)
    .idleMode(IdleMode.kBrake);

    configLeft.closedLoop.feedForward.kV(0.0006541);

    motorLeft.configure(configLeft, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);

    SparkMaxConfig configCenter = new SparkMaxConfig();
    configCenter
    .inverted(false)
    .idleMode(IdleMode.kBrake);

    configCenter.closedLoop.feedForward.kV(0.0006541);

    motorCenter.configure(configCenter, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);

    SparkMaxConfig configRight = new SparkMaxConfig();
    configRight
    .inverted(false)
    .idleMode(IdleMode.kBrake);

    configRight.closedLoop.feedForward.kV(0.0006541);

    motorRight.configure(configRight, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
// configLeft.absoluteEncoder
//     .positionConversionFactor(1)
//     .velocityConversionFactor(1).inverted(false);
// configLeft.closedLoop
//     .pid(kP,kI,kD).positionWrappingEnabled(true).positionWrappingInputRange(0, 1);
//     motorLeft.configure(configLeft,SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
    
    m_controllerLeft= motorLeft.getClosedLoopController();

//    SparkMaxConfig configCenter = new SparkMaxConfig();
//     configCenter
//     .inverted(false)
//     .idleMode(IdleMode.kBrake);
// configCenter.absoluteEncoder
//     .positionConversionFactor(1)
//     .velocityConversionFactor(1).inverted(false);
// configCenter.closedLoop
//     .pid(kP,kI,kD).positionWrappingEnabled(true).positionWrappingInputRange(0, 1);
//     motorCenter.configure(configCenter,SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
    
    m_controllerCenter= motorCenter.getClosedLoopController();

//     SparkMaxConfig configRight = new SparkMaxConfig();
//     configRight
//     .inverted(false)
//     .idleMode(IdleMode.kBrake);
// configRight.absoluteEncoder
//     .positionConversionFactor(1)
//     .velocityConversionFactor(1).inverted(false);
// configRight.closedLoop
//     .pid(kP,kI,kD).positionWrappingEnabled(true).positionWrappingInputRange(0, 1);
//     motorRight.configure(configCenter,SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
    
    m_controllerRight= motorRight.getClosedLoopController();


  }
  

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void shootSimple(double speed) {
  motorLeft.set(-speed);
  motorCenter.set(-speed);
  motorRight.set(-speed);
    
  }
public void shootComplex(double rpm) {
  m_controllerLeft.setSetpoint(-rpm, ControlType.kVelocity);
   m_controllerCenter.setSetpoint(-rpm, ControlType.kVelocity);
    m_controllerRight.setSetpoint(rpm, ControlType.kVelocity);
}
public double getRPM(){ 
return motorLeft.getEncoder().getVelocity();
}

  public void stop() {
   motorLeft.stopMotor();
   motorCenter.stopMotor();
   motorRight.stopMotor();
   
 /* 
  m_controllerLeft.setSetpoint(0,ControlType.kVelocity);
  m_controllerCenter.setSetpoint(0,ControlType.kVelocity);
  m_controllerRight.setSetpoint(0,ControlType.kVelocity);
   */
  }

}
