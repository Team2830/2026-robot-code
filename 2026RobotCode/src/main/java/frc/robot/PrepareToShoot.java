// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.HoodServo;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.IntakeMotor;
import frc.robot.subsystems.IntakePivot;
import frc.robot.subsystems.Kicker;
import frc.robot.subsystems.Shooter;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class PrepareToShoot extends Command {
  double speed, angle;
  /** Creates a new InTheTrenches. */
  public PrepareToShoot(double speed,double angle) {
    this.speed=speed;
    this.angle=angle;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(
   
    Shooter.getInstance(),
    HoodServo.getInstance()
    // IntakePivot.getInstance()
    
    );
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
    // IntakePivot.getInstance().down();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Shooter.getInstance().shootComplex(speed);
    HoodServo.getInstance().setPosition(angle);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Shooter.getInstance().stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
