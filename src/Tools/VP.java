package Tools;

/**
 * Vector and coordinate-positions
 */
public class VP {
    double[] axis;
    private double[] tempP;
    private double tempD;
    private StringBuilder sb = new StringBuilder();

    public VP(double... args) {
        axis = args;
        tempP = new double[axis.length];
    }

    public VP vectorTo(VP t) {
        return this.diff(t);
    }

    public static VP zero(int dimensions) {
        return new VP(0.0,0.0,0.0);
    }

    public static VP NEGATIVE_INFINITY() {
        double n = Double.NEGATIVE_INFINITY;
        return new VP(n,n,n);
    }
    public static VP INFINITY() {
        double n = Double.POSITIVE_INFINITY;
        return new VP(n,n,n);
    }

    public double length() {
        this.tempD = 0;
        for(int i = 0; i < axis.length; i++) {
            tempD += Math.pow(this.axis[i], 2);
        }
        return Math.sqrt(this.tempD);
    }

    public VP add(VP v) {
        for (int i = 0; i < axis.length; i++) {
           this.tempP[i] = this.axis[i] + v.axis[i];
        }
        return new VP(tempP.clone());
    }

    public VP diff(VP p) {
        for (int i = 0; i < axis.length; i++) {
            this.tempP[i] =  p.axis[i] - this.axis[i];
        }
        return new VP(this.tempP.clone());
    }

    public boolean equals(VP p) {
        for (int i = 0; i < axis.length; i++) {
            if (this.axis[i] !=  p.axis[i]) return false;
        }
        return true;
    }

    public String toString() {
        sb.delete(0,sb.length());
        for (int i = 0; i < axis.length;i++) {
            sb.append(" " + axis[i]);
        }
        return sb.toString();
    }

    public static VP random(int dimensions, double origoDistanceMax) {
        double[] randV = new double[dimensions];
        for (int i = 0; i < dimensions; i++) {
            randV[i] = randNat(origoDistanceMax);
        }
        return new VP(randV);
    }

    private static Double randNat(double origoDistanceMax) {
        if(0.5 < Math.random()) {
            return Math.random() * origoDistanceMax * -1.0;
        }
        return Math.random() * origoDistanceMax;
    }

    public VP div(double d){
        for (int i = 0; i < axis.length; i++) {
            this.tempP[i] = this.axis[i] / d;
        }
        return new VP(this.tempP.clone());
    }
    public VP mult(double m) {
        for (int i = 0; i < axis.length; i++) {
            this.tempP[i] = this.axis[i] * m;
        }
        return new VP(this.tempP.clone());
    }

    public double get(int i) {
        return axis[i];
    }
    public double angle(VP v) {
        return this.dot(v) / (toString().length() * v.length());
    }

    private double dot(VP v) {
        this.tempD = 0;
        for(int i = 0; i < axis.length; i++) {
            tempD += this.axis[i] * v.axis[i];
        }
        return tempD;
    }

    public double xAngle(VP v) {
        //todo
        return 0;
    }

    public double yAngle(VP v) {
        //todo
        return 0;
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
    public VP clone() {
        return new VP(this.axis.clone());
    }
}
