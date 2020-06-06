package Tools;

import java.util.*;

public class Space implements Iterable<Point>{
    private Point top, bottom, reader, temp;
    private Set<Point> chosen;
    private int dimensions, size;

    public Space(int dimensions) {
        this.dimensions = dimensions;
        chosen = new LinkedHashSet<>();
        size = 0;
        top = new Point(VP.INFINITY());
        bottom = new Point(VP.NEGATIVE_INFINITY());
        for (int axis = 0; axis < dimensions; axis++) {
            connect(axis, bottom, top);
        }
    }

    public void add(Point point) {
            addFrom(point, bottom);
    }

    public void updatePosition(Point p, VP v) {
        replaceWithTemp(p);
        addFrom(p, temp);
        detach(temp);

    }

    private void replaceWithTemp(Point p) {
        for (int axis = 0; axis < dimensions; axis++) {
            putBetween(axis, p.getPrevious(axis), temp, p.getNext(axis));
        }
    }

    public void addFrom(Point newPoint, Point origin) {
        for (int axis = 0; axis < dimensions; axis++) {
            reader = origin;
            if(readerIsLowerThanNewPoint(axis, newPoint)) {
                while (readerIsLowerThanNewPoint(axis, newPoint)) {
                    reader = reader.getNext(axis);
                }
            } else {
                while(readerIsHigherThanNewPoint(axis, newPoint)) {
                    reader = reader.getPrevious(axis);
                }
            }
            putBetween(axis, reader.getPrevious(axis), newPoint, reader);
        }
        size++;
    }

    private boolean readerIsLowerThanNewPoint(int axis, Point newPoint) {
        return reader.under(axis, newPoint);
    }

    private void detach(Point point) {
        for(int axis = 0; axis < dimensions;axis++) {
            connect(axis, point.getPrevious(axis), point.getNext(axis));
        }
    }

    private boolean readerIsHigherThanNewPoint(int axis, Point newPoint) {
        return reader.under(axis, newPoint);
    }

    private void connect(int axis, Point previous, Point next) {
        previous.setNext(axis, next);
        next.setPrevious(axis, previous);
    }

    private void putBetween(int axis, Point previous, Point middle, Point next) {
        connect(axis, previous, middle);
        connect(axis, middle, next);
    }

    @java.lang.Override
    public Iterator<Point> iterator() {
        return new Iterator<Point>() {
            private Point point = bottom;
            @Override
            public boolean hasNext() {
                return point.getNext(0) != null;
            }

            @Override
            public Point next() {
                point = point.getNext(0);
                return point;
            }
        };
    }

    public LinkedHashSet<Point> getAllWithin(Point origin, double v) {
         chosen.clear();
         for ( int axis = 0; axis < dimensions; axis++) {
             reader = origin;
             while(reader.getAxis(axis) < origin.getAxis(axis) + v) {
                 reader = reader.getNext(axis);
                 if(!chosen.contains(reader)) {
                     chosen.add(reader);
                 }
             }
             reader = origin;
             while(reader.getAxis(axis) > origin.getAxis(axis) - v) {
                 reader = reader.getPrevious(axis);
                 if(!chosen.contains(reader)) {
                     chosen.add(reader);
                 }
             }
         }
         return new LinkedHashSet<Point>(chosen);
    }
}
