package Tools;

public class Point {
    private VP pos;
    private Point[] next, previous;

    public Point(VP p) {
        this.pos = p;
        this.next = new Point[p.axis.length];
        this.previous = new Point[p.axis.length];
    };
    public double getAxis(int i) {
        return pos.axis[i];
    }

    public Point getNext(int i) {
        return next[i];
    }

    public void setNext(int i, Point next) {
        this.next[i] = next;
    }

    public void setPrevious(int i, Point previous) {
        this.previous[i] = previous;
    }

    public Point getPrevious(int axis) {
        return previous[axis];
    }

    public boolean under(int axis, Point other) {
        return this.pos.axis[axis] < other.pos.axis[axis];
    }
    public Point clone() {
        Point temp = new Point(pos.clone());
        temp.next = this.next.clone();
        temp.previous = this.previous.clone();
        return temp;
    }
}
