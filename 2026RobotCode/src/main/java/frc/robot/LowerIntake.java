// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.IntakeMotor;
import frc.robot.subsystems.IntakePivot;
import frc.robot.subsystems.Kicker;

public class LowerIntake extends Command {
  public LowerIntake() {
    addRequirements(
        IntakePivot.getInstance()  
    );
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  @Override
  public void execute() { IntakePivot.getInstance().down();}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    IntakePivot.getInstance().stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
