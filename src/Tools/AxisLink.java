package Tools;

public class AxisLink<T> implements Comparable<AxisLink<T>> {
    private double axisValue;
    private T link;

    public AxisLink(double newAxisValue, T link){
        this.axisValue = newAxisValue;
        this.link = link;
    }

    public AxisLink(T link){
        this.link = link;
    }

    public void setAxisValue(double newAxisValue) {
        this.axisValue = newAxisValue;
    }

    public double getAxisValue() {
        return this.axisValue;
    }

    public T getLink() {
        return this.link;
    }

    @Override
    public int compareTo(AxisLink<T> other) {
        return (int) (this.axisValue - other.axisValue);
    }
}