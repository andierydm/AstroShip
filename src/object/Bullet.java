package object;

import math.Vector;
import system.Constants;
import system.GameConducting;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Bullet extends ShipsObject{
    //private String to;

    public Bullet(GameObjectType gameObjectType) {
        super(gameObjectType);
    }

    public Bullet(Vector position, GameObjectType gameObjectType, double angle, double maxVel, Vector velocity, GameConducting conduting) {
        super((float) position.getX(), (float) position.getY(), gameObjectType, conduting);
        this.angle = angle;
        this.velocity = velocity.scale(maxVel);
    }

    //public String getTo(){return to;}

    @Override
    public void update(long deltaTime) {
        setX((float) (getX()+velocity.getX()));
        setY((float) (getY()+velocity.getY()));

        if(getX() > Constants.WIDTH || getX() < 0){
                conduting.removeGameObject(this);
        }

        if(getY() > Constants.HEIGHT || getY() < 0){
                conduting.removeGameObject(this);
        }
    }

    @Override
    public void render(Graphics graphics) {
        Graphics2D g2d = (Graphics2D) graphics;
        at = AffineTransform.getTranslateInstance(getX(), getY());
        at.rotate(angle);
        g2d.setColor(new Color((int) (Math.random()*255), (int) (Math.random()*255), (int) (Math.random()*255)));
        g2d.drawOval((int) getX(), (int) getY(), 26, 26);
    }
}
