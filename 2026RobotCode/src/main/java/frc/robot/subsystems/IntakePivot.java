// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakePivot extends SubsystemBase {
    private SparkMax motor = new SparkMax(2, MotorType.kBrushless);

    private double bottomAngle= 13.616;
    private double topAngle = 86.365;

  /** Creates a new IntakePivot. */
  public IntakePivot() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void up () {

  }
}
