// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Kicker;
import frc.robot.subsystems.SwerveSubsystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  SwerveSubsystem drive = new SwerveSubsystem();
  

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

    private final CommandXboxController m_opController =
      new CommandXboxController(OperatorConstants.kOpControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    

    
    
    m_opController.a().whileTrue(new Intaking());//Operator
    m_opController.b().onTrue(new InTheTrenches());//Operator-When true 
    m_opController.y().onTrue(new DefensiveMode());//Operator-When True---- Operator right trigger on true
    m_opController.leftTrigger().toggleOnTrue( new PrepareToShoot());//Driver


    drive.setDefaultCommand(drive.teleopDriveAngularVelocity(m_driverController::getLeftY, m_driverController::getLeftX, m_driverController::getRightX));
    //CommandScheduler.getInstance().setDefaultCommand(Kicker.getInstance(), Commands.run( () -> Kicker.getInstance().outake()));
    
    m_driverController.rightBumper().whileTrue(new Shooting());//Driver
    m_driverController.rightBumper().whileTrue(new Outake());//Driver
    m_driverController.leftBumper().onTrue(Commands.runOnce(() -> drive.resetPose()));
  /*
  m_driverController.leftBumper().whileTrue(new AbsoluteDriveAdv(
    drive,
    () -> m_driverController.getLeftY(),
    () -> m_driverController.getLeftX(),
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
    return null;
  }
}
