// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.AmpMechanism;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Test extends SequentialCommandGroup {
    private final AmpMechanism ampMechanism = new AmpMechanism();

  /** Creates a new Test. */
  public Test() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    try {
      addCommands(new AmpIntake(ampMechanism).andThen(new AmpToggle(ampMechanism)).andThen(new AmpOuttake(ampMechanism)));
    } catch (Exception e) {
      // TODO: handle exception
    }
    
  }
}
