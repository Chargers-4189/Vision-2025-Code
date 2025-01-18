// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import 

public class AmpMechanism extends SubsystemBase {
  /** Creates a new AmpMechanism. */
  public AmpMechanism() {
    private final TalonSRX m_diskPlacer = new TalonSRX();//add CAN numbers
    private final TalonSRX m_ampMover = new TalonSRX();

    public void ampRotate(boolean positionUp){

      if(//get angle from encoder <= //whatever angle we want the mechanism to be when placing into amp){
        {m_ampMover.set(0.25);
      }
    }
    public void ampRotate(boolean positionDown){

      if(//get angle from encoder >= //whatever angle we want the mechanism to be when at rest){
        {m_ampMover.set(-0.25);
    }
    public void diskOuttake(){
      m_diskOuttake.set(0.5);
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
