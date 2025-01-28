// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.AmpMechanism;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class AmpRotateUp extends Command {
  private AmpMechanism ampmechanism;
  private double encoderPosition;
  /** Creates a new AmpRotateUp. */
  public AmpRotateUp(AmpMechanism ampmechanism) {
    this.ampmechanism = ampmechanism;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    encoderPosition = ampmechanism.getEncoderData();
    if (encoderPosition >= 0.43) {
      ampmechanism.ampRotateUp();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    ampmechanism.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(encoderPosition <= 0.43){
      return true;
    }
    return false;
  }
}
