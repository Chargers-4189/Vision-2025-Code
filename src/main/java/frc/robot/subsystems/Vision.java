// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.List;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.simulation.VisionSystemSim;
import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {

  //public VisionSystemSim visionSimField = new VisionSystemSim("main");
  public PhotonCamera camera1 = new PhotonCamera("FrontCamera");
  public PhotonPoseEstimator poseEstimator1;
  public Pose2d estimatedAvgPose;
  public Pose3d robotPose = new Pose3d();
  public Pose2d robotPosition = new Pose2d();
  public boolean estimateAvailable = false;

  /** Creates a new Vision. */
  public Vision() {
    Rotation3d robotToCam1Rot = new Rotation3d(0, Math.toRadians(-15), 0);
    Transform3d robotToCam1 = new Transform3d(
      new Translation3d(0.25, -0.25, 0),
      robotToCam1Rot
    );
    try {
      AprilTagFieldLayout tagLayout = AprilTagFieldLayout.loadFromResource(
        AprilTagFields.k2025Reefscape.m_resourceFile
      );
      poseEstimator1 =
        new PhotonPoseEstimator(
          tagLayout,
          PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR,
          robotToCam1
        );

      poseEstimator1.setMultiTagFallbackStrategy(
        PhotonPoseEstimator.PoseStrategy.LOWEST_AMBIGUITY
      );
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void update() {
    var results1 = camera1.getAllUnreadResults();
    var estimatedResult1 = targetFilter(results1);

    if (estimatedResult1 != null) {
      estimatedAvgPose = estimatedResult1.estimatedPose.toPose2d();
      estimateAvailable = true;
      System.out.println(estimatedAvgPose);
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
        // TODO helper class for different camera's
        var estimatedResult = poseEstimator1.update(result);
        if (estimatedResult.isPresent()) {
          return estimatedResult.get();
        }
      }
    }
    return null;
  }

  public Pose2d getEstimatedRobotPose() {
    estimateAvailable = false;
    return estimatedAvgPose;
  }

  public void updateRobotPosition(Pose2d position) {
    robotPosition = position;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    this.update();
  }
}
