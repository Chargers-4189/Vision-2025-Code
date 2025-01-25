package frc.robot.subsystems;

import java.util.List;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructArrayPublisher;
import edu.wpi.first.networktables.StructArraySubscriber;
import edu.wpi.first.networktables.StructPublisher;

public class AprilTagCamera {

  AprilTagFieldLayout tagLayout;

  private boolean estimateAvailable = false;
  public Pose2d estimatedPose;

  protected PhotonCamera camera;
  private PhotonPoseEstimator poseEstimator;
  private NetworkTableInstance networkTableInstance = NetworkTableInstance.getDefault();
  private StructPublisher<Pose3d> photonRobotPosition = networkTableInstance.getStructTopic("PhotonRobotPosition", Pose3d.struct).publish();

  public AprilTagCamera(String cameraName, Transform3d cameraTranslation) {
    camera = new PhotonCamera(cameraName);
    try {
      tagLayout = AprilTagFieldLayout.loadFromResource(
          AprilTagFields.k2025Reefscape.m_resourceFile);
    } catch (Exception e) {
      System.err.println(e);
    }
    poseEstimator = new PhotonPoseEstimator(
        tagLayout,
        PoseStrategy.CLOSEST_TO_CAMERA_HEIGHT,
        cameraTranslation);
    poseEstimator.setMultiTagFallbackStrategy(
        PhotonPoseEstimator.PoseStrategy.LOWEST_AMBIGUITY);
  }

  public Pose2d getEstimatedRobotPose() {
    estimateAvailable = false;
    return estimatedPose;
  }

  public boolean isEstimateReady() {
    return estimateAvailable;
  }

  public void update() {
    var results = camera.getAllUnreadResults();
    var estimatedResult = targetFilter(results);
    /// ystem.out.println("EstimatedResult"+estimatedResult);
    if (estimatedResult != null) {
      estimateAvailable = true;
      estimatedPose = estimatedResult.estimatedPose.toPose2d();
    }
  }

  public void getAprilTagX() {
    var result = camera.getLatestResult();
    boolean hasTargets = result.hasTargets();
    if (hasTargets) {
      List<PhotonTrackedTarget> targets = result.getTargets();
      PhotonTrackedTarget target = result.getBestTarget();
      var estimatedResult = poseEstimator.update(result);
      System.out.println("Estimated Result:" + estimatedResult.get().estimatedPose);
      photonRobotPosition.set(estimatedResult.get().estimatedPose);
      // System.out.println(target.yaw);
    }
    // result.getBestTarget();

    // PhotonUtils.estimateFieldToRobotAprilTag(null, null, null)
    // PhotonTrackedTarget testerer = result.targets.get(0);
    // Pose2d robotPose =
    // PhotonUtils.estimateFieldToRobotAprilTag(testerer.getBestCameraToTarget(),tagLayout.getTagPose(testerer.getFiducialId()),);

  }

  private EstimatedRobotPose targetFilter(List<PhotonPipelineResult> results) {
    if (!results.isEmpty()) {
      var result = results.get(results.size() - 1);
      
      // System.out.println("Result: "+ result);
      result.targets.removeIf(tag -> {
        double maxDistance = 6.0;
        Transform3d transform = tag.getBestCameraToTarget();
        return (transform.getX() > maxDistance || transform.getY() > maxDistance);
      });
      if (result.hasTargets() &&
          result.getTargets().size() < 16 &&
          result.getTargets().size() > 0) {
        // for (var i : result.getTargets()) {
        // System.out.println(i.getFiducialId());
        // }
        // System.out.println("Result: "+ result);
        // var estimatedResult = poseEstimator.update(result);
        // System.out.println("Estimated Result:" + estimatedResult);
        // System.out.println(estimatedResult);
        var estimatedResult = poseEstimator.update(result);
      System.out.println("Estimated Result:" + estimatedResult.get().estimatedPose);
      photonRobotPosition.set(estimatedResult.get().estimatedPose);
        if (estimatedResult.isPresent()) {
          return estimatedResult.get();
        }
      }
    }
    return null;
  }
}
