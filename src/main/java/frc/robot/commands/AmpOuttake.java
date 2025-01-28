// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.AmpMechanism;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class AmpOuttake extends Command {

  private AmpMechanism ampmechanism;
  private double encoderPosition;
  private int rounds;

  /** Creates a new AmpOuttake. */
  public AmpOuttake(AmpMechanism ampmechanism) {
    this.ampmechanism = ampmechanism;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(ampmechanism);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    rounds = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    ampmechanism.diskOuttake();
    rounds++;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    ampmechanism.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
      if(rounds >= 50){
        return true;
      }
    

    return false;
  }
}
