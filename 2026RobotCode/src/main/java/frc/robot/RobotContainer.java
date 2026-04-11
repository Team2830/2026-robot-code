// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Kicker;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveSubsystem;
import swervelib.SwerveInputStream;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {
  SwerveSubsystem drive = new SwerveSubsystem();

  private final SendableChooser<Command> autoChooser;
  
  private final CommandXboxController m_driverController =
    new CommandXboxController(OperatorConstants.kDriverControllerPort);

  private final CommandXboxController m_opController =
    new CommandXboxController(OperatorConstants.kOpControllerPort);

  SwerveInputStream driveAngularVelocity = SwerveInputStream.of(
    drive.getSwerveDrive(),
    () -> m_driverController.getLeftY() * -1,
    () -> m_driverController.getLeftX() * -1)
    .withControllerRotationAxis(() -> m_driverController.getRightX()*-1)
    .deadband(0.1)
    .scaleTranslation(0.8)
    .allianceRelativeControl(true);

    SwerveInputStream driveMiddleAutoVelocity = SwerveInputStream.of(
    drive.getSwerveDrive(),
    () -> 0,
    () -> -0.1)
    .withControllerRotationAxis(() -> 0)
    .deadband(0)
    .scaleTranslation(0.8)
    .allianceRelativeControl(true);

  public RobotContainer() {
    NamedCommands.registerCommand("Shooting", new Shooting());
    NamedCommands.registerCommand("PrepareToShoot", new PrepareToShoot(3500, 50, false));
    NamedCommands.registerCommand("PrepareToShootFar", new PrepareToShoot(5500,50,false));
    NamedCommands.registerCommand("Intaking", new Intaking());
    NamedCommands.registerCommand("DefensiveMode", new DefensiveMode());
    NamedCommands.registerCommand("LowerIntake", new LowerIntake());
    NamedCommands.registerCommand("LowerHood", new LowerHood());
  
    autoChooser = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("Auto Chooser", autoChooser);

    configureBindings();
  }

  private void configureBindings() {
    /* Operator Controller Mapping */
    // Intake Balls/"Fuel" -> Press and hold A.
    m_opController.a().whileTrue(new Intaking());

    // Lower Intake -> Press and hold b
    //    Note: Use when you are going under the trench
    // m_opController.b().whileTrue(new InTheTrenches());

    // Raise Intake & Stop All Intaking/Indexing/Shooting Motors -> Press and hold y
    //    Note: Use when defending
    m_opController.y().toggleOnTrue(new RaiseIntake().andThen(new WaitCommand(0.5)).andThen(new DefensiveMode()));

    // Spin Shooter Motor -> Press left trigger once to turn on. Press again to turn off
    m_opController.leftTrigger().toggleOnTrue( new PrepareToShoot(5500, 50, true).withName("PrepareToShootLong"));
    m_opController.rightTrigger().toggleOnTrue( new PrepareToShoot(3500, 50,true).withName("PrepareToShootLong"));
    //   Shooter.getInstance().setDefaultCommand(new AntiJam(2000, 50, false));

    m_opController.x().whileTrue(new UnJam());
    //Runs shooter full speed to unjam fuel
    
    m_opController.povUp().toggleOnTrue(new RaiseHood());

    m_opController.povDown().toggleOnTrue(new LowerHood());

    m_opController.rightBumper().onTrue(new BlockerIn());
    m_opController.leftBumper().onTrue (new BlockerOut());
    m_opController.b().whileTrue(new BlockerManual(m_opController::getLeftY));

    /* Driver Controller Mapping */
    // Default Drive Mode is Field Oriented
    Command driveFieldOrientedAnglularVelocity = drive.driveFieldOriented(driveAngularVelocity);
    drive.setDefaultCommand(driveFieldOrientedAnglularVelocity);
    
    // Shoot the Balls/"Fuel" -> Press and holy right bumper "RB"
    m_driverController.rightBumper().whileTrue(new Shooting().withName("Shooting"));

    // Outake Balls/"Fuel" -> Press and hold right trigger "RT"
    m_driverController.rightTrigger().whileTrue(new Outake());

    // Reset Field Orientation -> Press start button
    m_driverController.start().onTrue(Commands.runOnce(() -> drive.resetPose()));
  /*
  m_driverController.leftBumper().whileTrue(new AbsoluteDriveAdv(
    drive,
    () -> m_driverController.getLeftY(),
    () -> m_driverController.getLeftX),
    () -> 0.0, 
    () -> true,
    () -> false,
    () -> false,
    () -> false
  ));
  
   m_driverController.rightBumper().whileTrue(new AbsoluteDriveAdv(
    drive,
   () -> m_driverController.getLeftY(),
   () -> m_driverController.getLeftX(),
   () ->0.0, 
   () ->false,
   () -> true,
   () -> false,
   () -> false
  ));
*/
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return autoChooser.getSelected();
  }
}
