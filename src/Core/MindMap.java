package Core;


import Tools.Space;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.text.Text;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MindMap {
    public static Space positions = new Space(3);
    private ArrayList<Thought> thought = new ArrayList<>();
    private ArrayList<Line3D> lines = new ArrayList<>();
    private ArrayList<Thought> possibleSelections = new ArrayList<>();
    private int selectionIndex = 0;
    private Thought parent = null;
    private Thought target = null;
    private Thought selected = null;
    private Group gThought;
    private Group gLines;
    private Text input;
    private Thought thoughtMemory;
    private Line3D lineMemory;
    private PerspectiveCamera camera;

    public MindMap(Group gThought, Group gLines, Text input, PerspectiveCamera camera) {
        this.gThought = gThought;
        this.gLines = gLines;
        this.input = input;
        this.camera = camera;
    }

    public void delete(Thought t) {
        for(Line3D l: lines) {
            if (l.getStart() == t || l.getStop() == t) {
                lines.remove(l);
                gLines.getChildren().remove(l);
            }
        }
        for (Thought c: t.getConnections()) {
            c.getConnections().remove(t);
        }
        thought.remove(t);
        gThought.getChildren().remove(t);
        parent = null;
        selected = null;
    }

    public void setSelected(Thought selected) {
        if (this.selected != null) {
            this.selected.unGlow();
        }
        this.selected = selected;
        selected.selectGlow();
    }

    public void addThought(Thought t){
        t.addMind(thought);
        thought.add(t);
        gThought.getChildren().add(t);
    }
    public void update() {
        gThought.getChildren().forEach(t-> {
            thoughtMemory = (Thought) t;
            thoughtMemory.update();
        });
        gLines.getChildren().forEach(l-> {
            lineMemory = (Line3D) l;
            lineMemory.update();
        });
    }
    public void setParent(Thought t) {
        if(parent != null) {
            parent.unGlow();
            parent.unZoom();
            for(Thought c: parent.getConnections()) {
                if(possibleSelections.contains(c)) {
                    possibleSelections.remove(c);
                }
            }
        }
        parent = t;
        parent.parentGlow();
        parent.zoom();
        possibleSelections.addAll(parent.getConnections());
    }

    public void nextThought() {
        if (!possibleSelections.isEmpty()){
            setSelected(possibleSelections.get(selectionIndex++ % possibleSelections.size()));
        }
    }
    public void previousThought() {
        System.out.println(selectionIndex);
        if (!possibleSelections.isEmpty())
            --selectionIndex;
            if(selectionIndex < 0) {
                selectionIndex = possibleSelections.size() - 1;
            }
            setSelected(possibleSelections.get(selectionIndex));
    }

    public void insertThought() {
        target = createThought();
        if(parent == null) {
            setParent(target);
        } else {
            bind(parent,target);
            setParent(target);
        }
    }

    private Thought createThought() {
        Thought t = new Thought(input.getText(),input, possibleSelections);
        addThought(t);
        return t;
    }

    public void bindSelected() {
        if(selected != null && parent != null) {
            bind(parent, selected);
        }
    }
    private void bind(Thought selected, Thought thought) {
        Line3D temp = new Line3D(selected, thought);
        selected.connect(thought);
        thought.connect(selected);
        gLines.getChildren().add(temp);
        lines.add(temp);
    }
    public void save(String path) throws FileNotFoundException {
        PrintStream writer = new PrintStream(path);
        for (Thought t :thought) {
            writer.println(t.getText());
        }
        writer.println("§");
        for (Line3D l : lines) {
            writer.println(l.toString());
        }

    }
    public void load(String path) throws FileNotFoundException {
        deleteEverything();
        Scanner read = new Scanner(new File(path));
        while(!read.hasNext("§")){
            addThought(new Thought(read.nextLine(),input, possibleSelections));
        }
        read.nextLine();
        while(read.hasNext()) {
            bind(thought.get(read.nextInt()),thought.get(read.nextInt()));
        }
        if (0 < thought.size()) {
            setParent(thought.get(0));
        }
    }

    private void deleteEverything() {
        thought.clear();
        lines.clear();
        gLines.getChildren().clear();
        gThought.getChildren().clear();
        Thought.resetId();
        parent = null;
    }

    public void setSelectedAsParent() {
        if (selected != null) {
            setParent(selected);
        }
    }

    public void inputMode() {
        possibleSelections.clear();
        possibleSelections.addAll(parent.getConnections());
    }

    public void deleteParent() {
        delete(parent);
    }
}
