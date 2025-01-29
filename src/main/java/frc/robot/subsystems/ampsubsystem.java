// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ampsubsystem extends SubsystemBase {

  final WPI_TalonSRX actuatureMotor = new WPI_TalonSRX(14);
  final WPI_TalonSRX shootingMotor = new WPI_TalonSRX(11);
  private boolean isAMPRotaiting = false;
  private boolean isAMPRotaitingDown = false;
  private boolean isAMPRotaitingup = false;
  private boolean isshootermoving = false;
  private boolean isshooterRotatitingout = false;
  private boolean isshooterRotatitingin = false;
  final DutyCycleEncoder encodethis = new DutyCycleEncoder(0);

  /** Creates a new ampsubsystem. */
  public ampsubsystem() {}

  public double actualamp() {
    return encodethis.get();
  }

  public void amprotaitdown() {
    if (!isAMPRotaiting || !isAMPRotaitingDown) {
      actuatureMotor.set(-0.5);
      isAMPRotaiting = true;
      isAMPRotaitingDown = true;
    } else {
      actuatureMotor.set(0);
      isAMPRotaiting = false;
      isAMPRotaitingDown = false;
    }
  }

  public void stop() {
    actuatureMotor.set(0);
    shootingMotor.set(0);
    isAMPRotaiting = false;
    isAMPRotaitingDown = false;
  }

  public void ampRotateUp() {
    if (!isAMPRotaiting || !isAMPRotaitingup) {
      actuatureMotor.set(-0.5);
      isAMPRotaitingup = true;
      isAMPRotaiting = true;
    } else {
      isAMPRotaitingup = false;
      isAMPRotaiting = false;
      actuatureMotor.set(0);
    }
  }

  public void diskOuttake() {
    if (!isshootermoving || !isshooterRotatitingout) {
      shootingMotor.set(1);
      isshooterRotatitingout = true;
      isshootermoving = true;
    } else {
      shootingMotor.set(0);
      isshooterRotatitingout = false;
      isshootermoving = false;
    }
  }

  public void diskIntake() {
    if (!isshootermoving || !isshooterRotatitingin) {
      shootingMotor.set(-1);
      isshooterRotatitingin = true;
      isshootermoving = true;
    } else {
      shootingMotor.set(0);
      isshooterRotatitingin = false;
      isshootermoving = false;
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
