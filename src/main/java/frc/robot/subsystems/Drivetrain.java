// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

//import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {

  /** Creates a new Drivetrain. */
  private final TalonFX m_leftLeader = new TalonFX(1);
  private final TalonFX m_rightFollower = new TalonFX(4);
  private final TalonFX m_rightLeader = new TalonFX(3);
  private final TalonFX m_leftFollower = new TalonFX(2);

  private DifferentialDrive drive;
  public Drivetrain() {
    m_leftFollower.setControl(new Follower(m_leftLeader.getDeviceID(), false));
    m_rightFollower.setControl(
      new Follower(m_rightLeader.getDeviceID(), false)
    );
      drive = new DifferentialDrive(m_leftLeader::set, m_rightLeader::set);
  }

  public void arcadeDrive(double y, double x){
    drive.arcadeDrive(x, y);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
