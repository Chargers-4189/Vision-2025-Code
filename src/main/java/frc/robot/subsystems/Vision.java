// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import org.photonvision.EstimatedRobotPose;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {

  private Pose2d avgEstimatedRobotPosition = new Pose2d();
  private boolean avgEstimateAvailable = false;

  /** Creates a new vision. */
  private AprilTagCamera[] cameras;

  public Vision() {

    Rotation3d robotToCamRot = new Rotation3d(0, Math.toRadians(-15), 0);
    Transform3d robotToCam = new Transform3d(
      new Translation3d(0.25, -0.25, 0),
      robotToCamRot
    );
    Rotation3d robotToCam2Rot = new Rotation3d(0, Math.toRadians(-15), 0);
    Transform3d robotToCam2 = new Transform3d(
      new Translation3d(0.25, 0.25, 0),
      robotToCam2Rot
    );

    cameras =
      new AprilTagCamera[] {
        new AprilTagCamera("USB_Camera", robotToCam),
      };
  }

  public Pose2d getEstimatedRobotPosition() {
    if (avgEstimateAvailable) {
      return avgEstimatedRobotPosition;
    }
    return null;
  }

  public void AvgEstimatedRobotPosition() {
    int count = 0;
    double x = 0;
    double y = 0;
    double degrees = 0;
    for (AprilTagCamera camera : cameras) {
      if (camera.isEstimateReady()) {
        x += camera.getEstimatedRobotPose().getX();
        y += camera.getEstimatedRobotPose().getY();
        degrees += camera.getEstimatedRobotPose().getRotation().getDegrees();
        count++;
      }
    }
    x /= count;
    y /= count;
    degrees = ((degrees / count) * Math.PI) / 180;
    avgEstimatedRobotPosition = new Pose2d(x, y, new Rotation2d(degrees));
    avgEstimateAvailable = true;
    if (count == 0) {
      avgEstimatedRobotPosition = null;
      avgEstimateAvailable = false;
    }
  }

  @Override
  public void periodic() {
    for (AprilTagCamera camera : cameras) {
      camera.update();
      //camera.getAprilTagX();
    }
    //AvgEstimatedRobotPosition();
    //System.out.println(avgEstimatedRobotPosition);
  }
}
