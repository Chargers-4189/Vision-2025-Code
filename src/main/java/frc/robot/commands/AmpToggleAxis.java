// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.AmpSystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class AmpToggleAxis extends Command {

  /** Creates a new AmpToggleDown. */
  private AmpSystem ampsystem;

  private double encoderPos;

  public AmpToggleAxis(AmpSystem ampsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.ampsystem = ampsystem;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println(encoderPos);
    ampsystem.changeAmpDirection();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //go down - starts at 37/0.0 - end at 65
    if (encoderPos >= 0.0 && ampsystem.getAmpDirection() == false) {
      encoderPos = ampsystem.getEncoderData();
      ampsystem.ampAxis(1);
      System.out.println(encoderPos);
    }
    // go up - starts at 68 - end at 35
    if (encoderPos <= 68 && ampsystem.getAmpDirection() == true) {
      encoderPos = ampsystem.getEncoderData();
      ampsystem.ampAxis(-1);
      System.out.println(encoderPos);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    ampsystem.ampAxis(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (encoderPos >= 0.65 && ampsystem.getAmpDirection() == false) {
      return true;
    } else if (encoderPos <= 0.35 && ampsystem.getAmpDirection() == true) {
      return true;
    } else {
      return false;
    }
  }
}
