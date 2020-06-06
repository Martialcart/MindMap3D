package Tests;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Testing extends Application {
    @Override
    public void start(Stage primaryStage) {
        //circle1
        Polyline obj = new Polyline();
        obj.getPoints().addAll(new Double[]{
                0.0, 0.0,
                50.0, 50.0});
        //new Point3D(0.0,0.0,0.0), new Point3D(50.0,50.0,50.0)
        System.out.println(4^2);
        obj.setTranslateX(200);
        obj.setTranslateY(200);
        obj.setTranslateZ(200);

        //grouping
        Group root = new Group();
        root.getChildren().add(obj);
        Scene scene = new Scene(root, 800, 600, true, SceneAntialiasing.BALANCED);
        primaryStage.setScene(scene);


        //set camera
        PerspectiveCamera camera = new PerspectiveCamera();
        camera.setTranslateZ(0);
        camera.setTranslateX(0);
        camera.setTranslateY(0);
        camera.setRotationAxis(new Point3D(0, 1, 0));
        scene.setCamera(camera);

        primaryStage.show();



        //Timeline
        Timeline timeline = new Timeline();
        KeyFrame startFrame = new KeyFrame(Duration.ZERO,
                new KeyValue(obj.translateZProperty(), 0));

        KeyFrame endFrame = new KeyFrame( new Duration(10000),
                new KeyValue(obj.translateZProperty(), 4000));


        timeline.getKeyFrames().add(startFrame);
        timeline.getKeyFrames().add(endFrame);

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
