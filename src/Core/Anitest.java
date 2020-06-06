package Core;


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import java.io.FileNotFoundException;

/**
 * @author Jan Olav Berg
 */
public class Anitest extends Application {
    private Pane root = new Pane();
    private Text input = new Text();
    private Scene scene;
    private Group gLine = new Group();
    private Group gThought = new Group();
    private MindMap mindmap;

    private PerspectiveCamera camera;

    private Parent createContent() {
        root.setPrefSize(600, 400);
        root.getChildren().addAll(input, gLine, gThought);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update(now);
            }
        };
        timer.start();
        return root;
    }

    private void createCamera() {
        camera = new PerspectiveCamera();
        camera.setTranslateZ(-200);
        camera.setTranslateX(-600);
        camera.setTranslateY(-350);
        scene.setCamera(camera);
    }

    private void update(long now) {
        mindmap.update();
    }

    @Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(createContent());
        createCamera();
        mindmap = new MindMap(gThought, gLine, input, camera);
        setupInput();
        input();
        stage.setScene(scene);
        stage.show();
    }

    private void setupInput() {
        input.setTranslateZ(-150);
        input.setTranslateX(-600);
        input.setTranslateY(-350);
    }

    private void input() {
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT: mindmap.previousThought(); break;
                case RIGHT: mindmap.nextThought(); break;
                case UP: mindmap.setSelectedAsParent(); clearInput(); break;
                case PAGE_UP: rotateLeft(); break;
                case PAGE_DOWN: rotateRight(); break;
                case ENTER: addThought(); clearInput(); break;
                case BACK_SPACE: backspaceinput(); break;
                case INSERT : loadMind(); clearInput(); break;
                case CONTROL: saveMind(); clearInput(); break;
                case DOWN: mindmap.bindSelected(); clearInput(); break;
                case DELETE: mindmap.deleteParent();
                default: insertInput(e);
            }
        });
    }

    private void addThought() {
        mindmap.insertThought();
    }

    private void clearInput() {
        input.setText("");
    }

    private void insertInput(KeyEvent e) {
        input.setText(input.getText() + e.getText());
    }

    private void saveMind() {
        try {
            mindmap.save(input.getText());
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private void loadMind() {
        try {
            mindmap.load(input.getText());
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private void backspaceinput() {
        input.setText(input.getText().substring(0, input.getText().length() - 1));
        if(input.getText().length() < 1) mindmap.inputMode();
    }

    private void rotateRight() {
        camera.getTransforms().add(new Rotate(-10, 600, 350, 200, Rotate.Y_AXIS));
        input.getTransforms().add(new Rotate(-10, Rotate.Y_AXIS));
        gThought.getChildren().forEach(c-> c.getTransforms().add(new Rotate(-10, Rotate.Y_AXIS)));
    }

    private void rotateLeft() {
        camera.getTransforms().add(new Rotate(10, 600, 350, 200, Rotate.Y_AXIS));
        input.getTransforms().add(new Rotate(10, Rotate.Y_AXIS));
        gThought.getChildren().forEach(c-> c.getTransforms().add(new Rotate(10, Rotate.Y_AXIS)));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
