// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import frc.robot.Constants.MotorConstants;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeMotor extends SubsystemBase {
  SparkMax motor = new SparkMax(MotorConstants.intakeMotorID, MotorType.kBrushless);

  /** Creates a new IntakeMotor. */
  public IntakeMotor() {}

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
