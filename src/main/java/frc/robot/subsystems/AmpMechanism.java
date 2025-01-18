// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
//import com.ctre.phoenix.CANifier.PWMChannel;
//import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import frc.robot.Constants;

public class AmpMechanism extends SubsystemBase {
  private DutyCycleEncoder encoder = new DutyCycleEncoder(0);

  private final WPI_VictorSPX m_ampMover = new WPI_VictorSPX(14);
  private final WPI_TalonSRX m_diskPlacer = new WPI_TalonSRX(11); 
  
     /** Creates a new AmpMechanism. */
  public AmpMechanism() {

   }
   public double getEncoderData(){
    return encoder.get();
   }

  public void ampRotateDown(){

       m_ampMover.set( 0.5);
  }
public void stop(){
  m_ampMover.set(0);
}
public void ampRotateUp(){

    m_ampMover.set(-0.5);

}
public void diskOuttake(){
  m_diskPlacer.set(1);
}


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
