// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;

public class AmpSystem extends SubsystemBase {
  /** Creates a new AmpMech. */
  private boolean ampPos = true; //true: up. false: down. Set to starting position
  private final WPI_TalonSRX AMP_SHOOTER_MOTOR = new WPI_TalonSRX(11); //Clockwise = Intake
  private final WPI_VictorSPX AMP_AXIS_MOTOR = new WPI_VictorSPX(14);

  //private DutyCycleEncoder encoder = new DutyCycleEncoder();


  

  public AmpSystem() {
    
  }

  public void togglePos() {
    if(ampPos == true) {
      //code to toggle
      //code to check to make sure it didnt get stuck
      ampPos = false;
    }else {
      //code to toggle
      //code to check to make sure it didnt get stuck
      ampPos = true;
    }
  }

  public void AmpAxis(double speed) {
    AMP_AXIS_MOTOR.set(speed);
  }

  public void AmpShooter(double speed) {
    AMP_SHOOTER_MOTOR.set(speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
