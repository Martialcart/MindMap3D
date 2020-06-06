package Core;

import Tools.VP;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.LinkedList;

public class Thought extends Text {
    private static int nr = 0;
    private int dimensions = 3;
    private final double THOUGHT_DISTANCE = 90;
    private final double THRESHOLD = 50;
    private static final double LENGTH_INCREASE_FACTOR = Math.PI;
    private double repelDistance = THOUGHT_DISTANCE;
    private double connectionDistance = Math.sqrt(Math.pow(THOUGHT_DISTANCE,2) * dimensions);
    private ArrayList<Thought> allThoughts;
    private ArrayList<Thought> connections = new ArrayList<>();
    private int id;
    private VP pos;
    private VP vector;
    private int counted;
    private boolean zoom = false;
    private static Thought cam = new Thought(new VP(0.0,0.0,-199.0));
    private LinkedList<VP> positions = new LinkedList<>();
    private ArrayList<Thought> possibleSelections;
    private Text input;
    private String inputShortcut;


    public Thought(String text, Text input, ArrayList<Thought> possibleSelections) {
        super(text);
        this.input = input;
        this.pos = new VP(xProperty().getValue(),yProperty().getValue(),0.0);
        this.id = nr++;
        this.possibleSelections = possibleSelections;
        //this.setText(String.valueOf(id));

    }

    public Thought(VP p) {
        this.pos = p;
    }
    public void update() {
        inputShortcut = input.getText();
        updateLineLength();
        if(containsInput()) {
            possibleSelections.add(this);
            grow();
            
        } else {
            shrink();
            if(possibleSelections.contains(this) && inputShortcut.length() > 0) {
                possibleSelections.remove(this);
            }
        }
        if (zoom) {
            if(toFarAway(cam)){
                pos = pos.add(vectorToCamera().mult(1));
            }
        } else {
            pos = pos.add(vectorToConnections().mult(0.4));
            pos = pos.add(vectorFromCloseThoughts().mult(1.2));
            if (behindCamera()) {
                pos = pos.add(vectorToView().mult(1));
            }
        }


        //this.setText(String.valueOf(this.zoom));
        updatePosGraphics();
    }

    public ArrayList<Thought> getConnections() {
        return connections;
    }

    public static void resetId() {
        nr = 0;
    }        //setText(String.valueOf(id));

    public void addMind(ArrayList<Thought> t) {
        this.allThoughts = t;
    }

    public void zoom() {
        zoom = true;
    }

    public void grow() {
        this.setStyle("-fx-font: 24 arial;");
    }

    public void shrink() {
        this.setStyle("-fx-font: 12 arial;");
    }
    public void unZoom() {
        zoom = false;
    }


    private boolean containsInput() {
        return this.getText().toLowerCase().contains(inputShortcut.toLowerCase()) && 0 < inputShortcut.length();
    }

    private boolean toFar() {
        return this.pos.get(2) < -50;
    }

    private boolean behindCamera() {
        return 50 < this.pos.get(2);
    }

    private VP vectorToView() {
        return new VP (0.0,0.0,-50.0);
    }

    private boolean noRepeat() {
        positions.addFirst(pos);
        if(11 < positions.size()) positions.removeLast();
        if(positions.getFirst().equals(positions.getLast())) return false;
        return true;
    }

    private VP vectorToCamera() {
        return getVectorToConnect(cam);
    }

    public void parentGlow() {
        this.setFill(Color.RED);
    }

    private void updateLineLength() {
        repelDistance = mostConnections() * LENGTH_INCREASE_FACTOR + THOUGHT_DISTANCE - THRESHOLD;
        connectionDistance = connections.size() * LENGTH_INCREASE_FACTOR + THOUGHT_DISTANCE + THRESHOLD;
    }

    private double mostConnections() {
        double max = connections.size();
        for (Thought t: connections) {
            if (max < t.connections.size()) {
                max = t.connections.size();
            }
        }
        return max;
    }

    private VP vectorToConnections() {
        if(noConnections()) {
            return VP.zero(dimensions);
        }
        vector = VP.zero(dimensions);
        counted = 0;
        for(Thought t: connections) {
            if(toFarAway(t)) {
                //System.out.println(pos.toString() + " -" + distance(t) + "> " + t.pos.toString() + " travel: " + getVectorToThoughtDistance(t) );
                vector = vector.add(getVectorToConnect(t));
                counted++;
            }
        }
        if(counted == 0 ) return VP.zero(dimensions);
        return vector.div(counted);
    }

    private boolean toFarAway(Thought t) {
        return connectionDistance < distance(t);
    }

    private boolean toClose(Thought t) {
        return distance(t) < repelDistance;
    }

    private boolean noConnections() {
        return connections.size() < 1;
    }

    private VP getVectorToRepel(Thought t) {
        return t.vectorTo(this).mult(THOUGHT_DISTANCE / distance(t));
    }
    private VP getVectorToConnect(Thought t) {
        return this.vectorTo(t).mult(THOUGHT_DISTANCE / distance(t));
    }

    private VP getVectorToRepel(VP t) {
        return t.vectorTo(this.pos).mult(THOUGHT_DISTANCE / distance(t));
    }
    private VP getVectorToConnect(VP t) {
        return this.pos.vectorTo(t).mult(THOUGHT_DISTANCE / distance(t));
    }

    private void updatePosGraphics() {
        this.setTranslateX(pos.get(0));
        this.setTranslateY(pos.get(1));
        this.setTranslateZ(pos.get(2));
    }
    private VP vectorFromCloseThoughts() {
        vector = VP.zero(dimensions);
        for (Thought t : allThoughts) {
            counted = 0;
            if (notSelf(t) && toClose(t)) {
                if (onTopOff(t)) {
                    vector = vector.add(VP.random(3,THOUGHT_DISTANCE));
                } else {
                    vector = vector.add(getVectorToRepel(t));
                }
                counted++;
            }
        }
        if(counted < 1) {
            return VP.zero(dimensions);
        }
        return vector.div(counted);
    }

    private boolean onTopOff(Thought t) {
        return this.pos.equals(t.pos);
    }

    private VP vectorTo(Thought t) {
        return this.pos.diff(t.pos);
    }
    private VP vectorTo(VP t) {
        return this.pos.diff(t);
    }

    private double distance(Thought t) {
        return vectorTo(t).length();
    }

    private double distance(VP t) {
        return vectorTo(t).length();
    }

    private boolean notSelf(Thought t) {
        return this != t;
    }

    public VP getPos() {
        return this.pos;
    }

    public void connect(Thought t) {
        connections.add(t);
    }

    public int id() {
        return this.id;
    }

    public void unGlow() {
        this.setFill(Color.BLACK);
    }

    public void selectGlow() {
        this.setFill(Color.BLUE);
    }
}