// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.List;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructPublisher;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {
  public Pose2d estimatedPose;

  protected PhotonCamera camera;
  private PhotonPoseEstimator poseEstimator;
  private NetworkTableInstance networkTableInstance = NetworkTableInstance.getDefault();
  private StructPublisher<Pose3d> photonRobotPosition = networkTableInstance
      .getStructTopic("PhotonRobotPosition", Pose3d.struct).publish();

  /** Creates a new Vision. */
  public Vision() {
    Rotation3d robotToCam1Rot = new Rotation3d(0, Math.toRadians(-15), 0);
    Transform3d robotToCam1 = new Transform3d(
        new Translation3d(0.25, -0.25, 0),
        robotToCam1Rot);
    try {
      camera = new PhotonCamera("USB_Camera");
      AprilTagFieldLayout tagLayout = AprilTagFieldLayout.loadFromResource(
          AprilTagFields.k2025Reefscape.m_resourceFile);
      poseEstimator = new PhotonPoseEstimator(
          tagLayout,
          PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR,
          robotToCam1);

      poseEstimator.setMultiTagFallbackStrategy(
          PhotonPoseEstimator.PoseStrategy.LOWEST_AMBIGUITY);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  private EstimatedRobotPose targetFilter(List<PhotonPipelineResult> results) {
    if (!results.isEmpty()) {
      var result = results.get(results.size() - 1);
      result.targets.removeIf(tag -> {
        double maxDistance = 6.0;
        Transform3d transform = tag.getBestCameraToTarget();
        return (
          transform.getX() > maxDistance || transform.getY() > maxDistance
        );
      });

      if (
        result.hasTargets() &&
        result.getTargets().size() < 16 &&
        result.getTargets().size() > 0
      ) {
        var estimatedResult = poseEstimator.update(result);
        if (estimatedResult.isPresent()) {
          return estimatedResult.get();
        }
      }
    }
    return null;
  }

  @Override
  public void periodic() {
    var results = camera.getAllUnreadResults();
    var estimatedResult = targetFilter(results);
    if (estimatedResult != null) {
      System.out.println("Estimated 2D Pose:" + estimatedResult.estimatedPose.toPose2d());
      System.out.println("Estimated 3D Pose:" + estimatedResult.estimatedPose);
      photonRobotPosition.set(estimatedResult.estimatedPose);
    }
  }
}
