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

  private final TalonFX MOTOR_FRONT_LEFT = new TalonFX(1, kCANBus);
  private final TalonFX MOTOR_BACK_LEFT = new TalonFX(2, kCANBus);
  private final TalonFX MOTOR_FRONT_RIGHT = new TalonFX(3, kCANBus);
  private final TalonFX MOTOR_BACK_RIGHT = new TalonFX(4, kCANBus);

  public Drivetrain() {
    MOTOR_BACK_LEFT.setControl(new Follower(MOTOR_FRONT_LEFT.getDeviceID(), false));
    MOTOR_BACK_RIGHT.setControl(new Follower(MOTOR_FRONT_RIGHT.getDeviceID(), false));
  }

  public void tankDrive(double SpeedLeft, double SpeedRight) {
    MOTOR_FRONT_LEFT.set(SpeedLeft);
    MOTOR_FRONT_RIGHT.set(SpeedRight);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
