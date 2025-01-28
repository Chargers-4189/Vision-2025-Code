// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.AmpMechanism;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class AmpToggle extends Command {

  private AmpMechanism ampmechanism;
  /** Creates a new AmpCommand. */

  private double encoderPosition;
  private double encoderPositionStart;
  private boolean directionUp;

  public AmpToggle(AmpMechanism ampmechanism) {
    this.ampmechanism = ampmechanism;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(ampmechanism);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    encoderPositionStart = ampmechanism.getEncoderData();
    if (encoderPositionStart <= 0.43){
      directionUp = false;
    }
    else if(encoderPositionStart >= 0.67){
      directionUp = true;
  }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    encoderPosition = ampmechanism.getEncoderData();
    if (directionUp == false) {
      System.out.println("hello");
      if (encoderPosition <= 0.67) {
        ampmechanism.ampRotateDown();
      } else {
        ampmechanism.stop();
      }
    } else if (directionUp == true) {
      if (encoderPosition >= 0.43) {
        ampmechanism.ampRotateUp();
      } else {
        ampmechanism.stop();
      }
    }
    else{
      System.out.println(encoderPositionStart);
      System.out.println(encoderPosition);
    }
  }

  // Called once the command ends or is in terrupted.
  @Override
  public void end(boolean interrupted) {
    ampmechanism.stop();
    System.out.println("Done");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (encoderPosition <= 0.43 && directionUp == true || encoderPosition >= 0.67 && directionUp == false) {
      return true;
    }

    return false;
  }
}
