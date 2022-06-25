package math;

public class Vector {
    private float x, y;

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector() {
        this.x = 0.0f;
        this.y = 0.0f;
    }

    public Vector sum(Vector vector) {
        return new Vector(x + vector.x, y + vector.y);
    }

    public Vector scale(double value) {
        return new Vector((float) (x * value), (float) (y * value));
    }

    public double getMagnitude() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(x, 2));
    }

    public Vector setDirection(double angle) {
        return new Vector((float) ((float) Math.cos(angle) * getMagnitude()), (float) ((float) Math.sin(angle) * getMagnitude()));
    }


    //Getter and Setter-------------------------------------------------------------------------------------------------

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
