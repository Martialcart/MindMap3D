package Tools;

/**
 * Vector and coordinate-positions in 3D
 */
public class VP3D {
    double x,y,z;
    public VP3D(Double x, Double y, Double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static VP3D zero() {
        return new VP3D(0.0,0.0,0.0);
    }

    public double length() {
        return Math.sqrt(
                Math.pow(this.x, 2) +
                Math.pow(this.y, 2) +
                Math.pow(this.z, 2)
        );
    }

    public VP3D add(VP3D v) {
        return new VP3D(
                this.x + v.x,
                this.y + v.y,
                this.z + v.z);
    }

    public VP3D diff(VP3D p) {
        return new VP3D(
                p.x - this.x,
                p.y - this.y,
                p.z - this.z);
    }

    public boolean equals(VP3D p) {
        return (p.getClass() == this.getClass() &&
                p.x == this.x &&
                p.y == this.y &&
                p.z == this.z);
    }
    public String toString() {
        return String.format("x:%.2f y:%.2f z:%.2f", this.x, this.y, this.x);
    }


    public static VP3D random(double origoDistanceMax) {
        return new VP3D(
                randNat(origoDistanceMax),
                randNat(origoDistanceMax),
                randNat(origoDistanceMax));
    }

    private static Double randNat(double origoDistanceMax) {
        if(0.5 < Math.random()) {
            return Math.random() * origoDistanceMax * -1.0;
        }
        return Math.random() * origoDistanceMax;
    }

    public VP3D div(double d){
        return new VP3D(
        this.x / d,
        this.y / d,
        this.z / d);
    }
    public VP3D mult(double m) {
        return new VP3D(
                this.x * m,
                this.y * m,
                this.z * m);
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getZ() {
        return z;
    }
    public double angle(VP3D v) {
        return this.dot(v) / (toString().length() * v.length());
    }

    private double dot(VP3D v) {
        return
                this.x * v.x +
                this.y * v.y +
                this.z * v.z;
    }

    public double xAngle(VP3D v) {
        return VP3D.angle2D(this.y,this.z,v.y,v.z);
    }

    public double yAngle(VP3D v) {
        return VP3D.angle2D(this.x,this.z,v.x,v.z);
    }
    public static double angle2D(double x1, double y1, double x2, double y2) {
        double charge = 1;
        if(y2 < y1 || x1 < x2){
            charge = -1;
        }
        return
                Math.round(
                    Math.toDegrees(
                                Math.acos(
                                        (x1 * x2 + y1 * y2)
                                                /
                                                (Math.sqrt( Math.pow((x1), 2.0) + Math.pow((y1), 2.0)) *
                                                Math.sqrt( Math.pow((x2), 2.0) + Math.pow((y2), 2.0))
                                                )
                                )
                        )
                ) * charge;
    }
    public VP3D clone() {
        return new VP3D(this.x, this.y, this.z);
    }
}
