// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.pheonix6.hardware.TalonFX;

import com.ctre.pheonix6.CANBus;

public class Drivetrain extends SubsystemBase {
  /** Creates a new Drivetrain. */
  private final CANBus kCANBus = new CANBus("canivore");
  private final TalonFX m_leftLeader = new TalonFX(1);
  private final TalonFX m_rightFollower = new TalonFX(2);
  private final TalonFX m_rightLeader = new TalonFX(3);
  private final TalonFX m_leftFollower = new TalonFX(4);

  public Drivetrain() {
    m_leftFollower.setControl(new Follower(m_leftLeader.getDeviceID(), false));
    m_rightFollower.setControl(new Follower(m_rightLeader.getDeviceID(), false));
  }

  public void arcadeDrive(double speedLeft, double speedRight){
      m_leftLeader.set(speedLeft);
      m_rightLeader.set(speedRight);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
