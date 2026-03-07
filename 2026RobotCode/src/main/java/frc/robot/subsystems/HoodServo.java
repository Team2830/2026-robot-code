// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import frc.robot.LinearServo;
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

public class HoodServo extends SubsystemBase {

  private static HoodServo instance = null;

  private LinearServo rightServo;
  private LinearServo leftServo;


  public static HoodServo getInstance() {
     if(instance == null) {
        instance = new HoodServo(); 
     }

     return instance;
  }


  
  /** Creates a new Indexer. */
  public HoodServo() {
    rightServo = new LinearServo(0,168,15);
    leftServo = new LinearServo(1,168,15);
  }
  

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    rightServo.updateCurPos();
    leftServo.updateCurPos();
  }

  public void setPosition(double pos_mm){
    rightServo.setPosition(pos_mm);
    leftServo.setPosition(pos_mm);
  }

  public double getPosition(){
  return rightServo.getPosition();
  }
  
}
