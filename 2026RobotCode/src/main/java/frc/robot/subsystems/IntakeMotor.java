// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import frc.robot.Constants.MotorConstants;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeMotor extends SubsystemBase {
  private SparkMax motor = new SparkMax(MotorConstants.intakeMotorID, MotorType.kBrushless);
  private static IntakeMotor instance = null;

  public static IntakeMotor getInstance() {
    if(instance == null) {
      instance = new IntakeMotor();
    }
     return instance;

  }

  
  /** Creates a new IntakeMotor. */
  private IntakeMotor() {
    SparkMaxConfig config = new SparkMaxConfig();
    config
        .smartCurrentLimit(30)
        .idleMode(IdleMode.kBrake);

    // Persist parameters to retain configuration in the event of a power cycle
    motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void in() {
    motor.set(1);
  }

  public void out () {
    motor.set(-1);
  }

  public void stop (){
    motor.set(0);
  } 
}
