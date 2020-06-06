package Core;

import Tools.VP;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;

public class Line3D extends Line {
    private Thought start;
    private Thought stop;
    private VP pos = new VP(0.0,0.0,0.0);
    private VP line;
    private DoubleProperty y = new SimpleDoubleProperty(100);
    private DoubleProperty x = new SimpleDoubleProperty(100);
    private VP travel;

    public Line3D(Thought start, Thought stop) {
        startXProperty().setValue(0.0);
        startYProperty().setValue(0.0);
        endXProperty().bind(x);
        endYProperty().bind(y);

        this.start = start;
        this.stop = stop;
        line = new VP(100.0,0.0,0.0);
        update();
    }
    public void update() {
        updatePos();
        adjustLine();
        //adjustAngle();
    }

    private void updatePos() {
        travel = this.pos.diff(start.getPos());
        this.setTranslateX(travel.get(0));
        this.setTranslateY(travel.get(1));
        this.setTranslateZ(travel.get(2));
    }

    private VP lineGoal(){
        return start.getPos().diff(stop.getPos());
    }

    private void adjustLine() {
        x.setValue(lineGoal().get(0));
        y.setValue(lineGoal().get(1));
    }

    private double pointDistance() {
        return lineGoal().length();
    }

    private void adjustAngle() {
        if(!line.equals(lineGoal())) {
            System.out.println("rx " + getRotation() + " ry " + getRotationY() + " line: " + line.toString() + " goal: " + lineGoal());
            getTransforms().add(new Rotate(getRotation(), Rotate.Y_AXIS));
            line = lineGoal();
        }
    }

    private double getRotation() {
        return line.angle(lineGoal());
    }

    private double getRotationY() {
        return line.yAngle(lineGoal());
    }
    public String toString() {
        return start.id() + " " + stop.id();
    }

    public Thought getStart() {
        return start;
    }

    public Thought getStop() {
        return stop;
    }
}
