package math;

public class Vector {
    private double x, y;
    private Vector direction;

    public Vector(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vector(){
        this.x = 0.0;
        this.y = 0.0;
    }

    public Vector sum(Vector vector){
        return new Vector(x+vector.x, y+vector.y);
    }

    public Vector scale(double value){
        return new Vector(x*value, y*value);
    }

    public Vector maxVelLimit(double limit){
        if (getMagnitude() > limit) return this.normalize().scale(limit);
        return this;
    }

    public Vector normalize(){
        return new Vector((float) (x/getMagnitude()), (float) (y/getMagnitude()));
    }

    public double getMagnitude(){
        return Math.sqrt(x*x + y*y);
    }

    public double getAngle(){
        return Math.asin(y/getMagnitude());
    }

    public double crossProduct(Vector vector) {
        return x * vector.y - y * vector.x;
    }

    public double dotProduct(Vector vector) {
        return x * vector.x + y * vector.y;
    }

    public double angleBetweenDot(Vector vector) {
        return 180.0 - Math.toDegrees(Math.asin((Math.sqrt(Math.pow(crossProduct(vector), 2))) / (getMagnitude() * vector.getMagnitude())));
    }

    public double angleBetweenCross(Vector vector) {
        return Math.toDegrees(Math.acos(dotProduct(vector) / (getMagnitude() * vector.getMagnitude())));
    }

    public Vector setDirection(double angle){
        return direction = new Vector(Math.cos(angle)*getMagnitude(), Math.sin(angle)*getMagnitude());
    }

    public Vector getDirection(){
        return direction;
    }


    //Getter and Setter-------------------------------------------------------------------------------------------------

    public double getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
