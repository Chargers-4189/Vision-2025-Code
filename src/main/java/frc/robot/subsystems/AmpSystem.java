// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class AmpSystem extends SubsystemBase {

  /** Creates a new AmpMech. */
  //Motors
  private final WPI_TalonSRX AMP_SHOOTER_MOTOR = new WPI_TalonSRX(11); //Clockwise = Intake
  private final WPI_VictorSPX AMP_AXIS_MOTOR = new WPI_VictorSPX(14);
  private boolean ampAxisDirection = false; //true = up. false = down

  //encoder + sensor
  private DutyCycleEncoder encoder = new DutyCycleEncoder(0);

  //private DigitalInput ampSensor = new DigitalInput(0);

  public AmpSystem() {}

  //encoder
  public double getEncoderData() {
    return encoder.get();
  }

  //motor speed

  public void ampAxis(double ampSpeed) {
    AMP_AXIS_MOTOR.set(ampSpeed);
  }

  public void AmpShooter(double speed) {
    AMP_SHOOTER_MOTOR.set(speed);
  }

  //this is for the amp bc I needed something to keep track of which direction its going
  public void changeAmpDirection() {
    if (ampAxisDirection == true) {
      ampAxisDirection = false;
    } else if (ampAxisDirection == false) {
      ampAxisDirection = true;
    }
  }

  public boolean getAmpDirection() {
    return ampAxisDirection;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
