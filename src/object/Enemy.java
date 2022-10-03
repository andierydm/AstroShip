package object;

import math.Vector;
import system.Constants;
import system.GameConducting;
import system.input.AnotherKeyboardInput;
import system.input.KeyboardInput;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

public class Enemy extends ShipsObject{

    public Enemy(float x, float y, GameObjectType gameObjectType, float maxVel, double defaultMagnitude, GameConducting conducting) {
        super(x, y, gameObjectType, conducting);
        this.maxvel = maxVel;
        this.defaultMagnitude = defaultMagnitude;
        loadTexture();
        playerPosition = new Point();
        polarAngle = new Point();
        shootTime = 0;
    }

    private void searchUser(){
        GameConducting c = conduting;
        c.getList().forEach(co -> {
            if (co.getGameObjectType().equals(GameObjectType.Player)){
                Ship s = (Ship) co;
                playerPosition.setLocation(s.getX(), s.getY());
                //angle = Math.abs(Math.toDegrees(Math.atan(division)));
            }
        });
    }

    private double getAngle(){
        double preAngle = Math.abs(Math.toDegrees(Math.atan((playerPosition.getY()-getY())/(playerPosition.getX() - getX()))));

        if(playerPosition.getX() > getX()) {
            //right side
            if (playerPosition.getY() < getY()) {
                //Superior derecha
                preAngle = Math.abs(360 - preAngle);
            }

        } else {
            //left size
            if (playerPosition.getY() < getY()) {
                //Superior izquierda
                preAngle = Math.abs(180 + preAngle);
            } else {
                //Inferior izquierda
                preAngle = Math.abs(180 - preAngle);
            }
        }

        return preAngle;
    }

    private void destroy(){
        /*if(getX() > Constants.WIDTH || getX() < 0){
            conduting.removeGameObject(this);
        }*/

        if(getY() > Constants.HEIGHT){
            conduting.removeGameObject(this);
        }
    }

    @Override
    public void update(long deltaTime) {
        searchUser();

        angle = getAngle();
        polarAngle.setLocation(((getTexture().getWidth() / 2f) * Math.cos(Math.toRadians(angle))) + getX() + (getTexture().getWidth() / 2),
                               ((getTexture().getWidth() / 2f) * Math.sin(Math.toRadians(angle))) + getY() + getTexture().getWidth() / 2);

        if ((deltaTime - shootTime) >= 5 && getY()+getTexture().getHeight()-90 >= 0){
            conduting.addGameObject(new Bullet(new Vector(polarAngle.getX(), polarAngle.getY()),
                    GameObjectType.Bullet, angle, 6, direction, conduting));

            shootTime = deltaTime;
        }

        if((deltaTime - shootTime) >= 2){
            //setX(getX()+1);
            setY(getY()+1);
        }

        direction = direction.setDirection(Math.toRadians(angle));
        destroy();
    }

    @Override
    public void render(Graphics graphics) {
        Graphics2D g2d = (Graphics2D) graphics;

        at = AffineTransform.getTranslateInstance(getX(), getY());
        at.rotate(Math.toRadians(angle) + Math.PI/2, getTexture().getWidth()/2, getTexture().getHeight()/2);
        g2d.drawImage(getTexture(), at, null);
        graphics.drawOval((int) polarAngle.getX() -5, (int) polarAngle.getY() -5, 15, 15);
    }

    private Point playerPosition;
    private Point polarAngle;
    private long shootTime;
}
