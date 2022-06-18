package object;

import input.KeyBoard;

import java.awt.*;

public class Ship extends GameObject {
    private final float aceleration;
    private Point acelerationV;
    private Point velocity;


    public Ship() {
        super(GameObjectType.Player);
        aceleration = -0.008f;
        acelerationV = new Point();
        velocity = new Point();
    }

    public Ship(float x, float y, float maxVel) {
        super(x, y, maxVel, GameObjectType.Player);
        aceleration = -0.008f;
        acelerationV = new Point();
        velocity = new Point();
    }

    private Point addVel(float x, float y){
       return new Point((int) (velocity.x+x), (int) (velocity.y+y));
    }

    private Point scaleaceleration(float value){
        return new Point((int) (getX()*value), (int) (getY()*value));
    }

    private void limitVel(float value){
        if(getX() > value)
            setX(value);
        if(getX() < -value)
            setX(-value);
        if(getY() > value)
            setY(value);
        if(getX() > value)
            setY(-value);
    }

    @Override
    public void update(long deltaTime) {
        if (KeyBoard.up){
            acelerationV = scaleaceleration(aceleration);
        }
        velocity = addVel(acelerationV.x, acelerationV.y);
        setX(getX()+velocity.x);
        setY(getY()+velocity.y);
    }

    @Override
    public void render(Graphics graphics) {
        Graphics2D g2d = (Graphics2D) graphics;

        g2d.drawRect((int) getX(), (int) getY(), 26, 16);
    }
}
