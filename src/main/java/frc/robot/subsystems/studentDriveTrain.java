// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;


import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class studentDriveTrain extends SubsystemBase {


  private final TalonFX leftLeader = new TalonFX(1 );
  private final TalonFX leftFollower = new TalonFX(2 );
  private final TalonFX rightLeader = new TalonFX(3 );
  private final TalonFX rightFollower = new TalonFX(4 );
  /** Creates a new studentDriveTrain. */
  public studentDriveTrain() {
    leftFollower.setControl(new Follower(leftLeader.getDeviceID(), false));
    rightFollower.setControl(new Follower(rightLeader.getDeviceID(), false));
  }

  public void arcadeDrive(double leftSpeed , double rightSpeed){
    leftLeader.set(leftSpeed);
    rightLeader.set(-rightSpeed);
  } 

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
