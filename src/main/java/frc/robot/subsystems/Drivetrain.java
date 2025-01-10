// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
  /** Creates a new Drivetrain. */
  private final CANBus kCANBus = new CANBus("canivore");

  // Falcon 500s -> TalonFX library -> CTRE
  private final TalonFX leadLeft = new TalonFX(1, kCANBus);
  private final TalonFX followLeft = new TalonFX(2, kCANBus);
  private final TalonFX leadRight = new TalonFX(3, kCANBus);
  private final TalonFX followRight = new TalonFX(4, kCANBus);

  public Drivetrain() {
    followLeft.setControl(new Follower(leadLeft.getDeviceID(), false));
    followRight.setControl(new Follower(leadRight.getDeviceID(), false));
  }

  public void tankDrive(double speedLeft, double speedRight) {
    leadLeft.set(speedLeft);
    leadRight.set(speedRight);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
