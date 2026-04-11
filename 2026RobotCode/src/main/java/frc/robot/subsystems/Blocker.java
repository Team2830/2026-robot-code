// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.DutyCycle;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Blocker extends SubsystemBase {
  private final TalonFX motor= new TalonFX(1, new CANBus("canivore"));
  private final DutyCycleOut dutycylerequest=new DutyCycleOut(0);
  private final PositionVoltage PositionRequest=new PositionVoltage(0).withSlot(0);

  private static Blocker instance = null;

  public static Blocker getInstance(){
    if (instance == null) {
      instance = new Blocker ();
    }

    return instance;
  }

  /** Creates a new Blocker. */
  private Blocker() {
    TalonFXConfiguration configs=new TalonFXConfiguration();
    configs.Slot0.kP=2.4;
    configs.Slot0.kI=0;
    configs.Slot0.kD=0;
    configs.Feedback.SensorToMechanismRatio = 5*5*5*1.5;

    StatusCode status =  StatusCode.StatusCodeNotInitialized;
    for (int i = 0;i < 5; ++i) {
      status = motor.getConfigurator().apply(configs);
      if (status.isOK()) break;
    }
    if (!status.isOK()){
      System.out.println("Could not apply congigs, error code: " + status.toString());
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setSpeed(double speed){
    motor.setControl(dutycylerequest.withOutput(speed));

  }

  public void setPosition(double positionRotations) {
    motor.setControl(PositionRequest.withPosition(positionRotations));
  }
}
