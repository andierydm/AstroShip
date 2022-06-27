package object;

import system.GameConducting;
import system.input.AnotherKeyboardInput;
import system.input.KeyboardInput;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Ship extends ShipsObject {
    private long shootTime = 0;
    private boolean acelerating;

    public Ship() {
        super(GameObjectType.Player);
        //loadTexture();
    }

    public Ship(float x, float y, GameObjectType type, float maxVel, double defaultMagnitude, GameConducting conducting) {
        super(x, y, type, conducting);
        loadTexture();
        loadTexture("resource/fire02.png");
        this.defaultMagnitude = defaultMagnitude;
        this.maxvel= maxVel;
        acelerating = false;
    }

    @Override
    public void update(long deltaTime) {
        if (KeyboardInput.right) angle += Math.PI/36;
        if (KeyboardInput.left) angle -= Math.PI/36;

        if (KeyboardInput.up){
            aceleration = direction.scale(defaultMagnitude);
            acelerating = true;
        }else {
            if (velocity.getMagnitude() != 0)   aceleration = velocity.scale(-1).normalize().scale(defaultMagnitude);
            acelerating = false;
        }

        if(AnotherKeyboardInput.getInstance().keyDown(KeyEvent.VK_SHIFT)){
            if (shootTime == 0 || System.nanoTime() - shootTime >= 42000000){
                conduting.addGameObject(new Bullet(getCenter().sum(direction.scale(getTexture().getWidth()/2)),
                        GameObjectType.Bullet, angle, 8, direction, conduting));
                shootTime = System.nanoTime();
            }
        }else shootTime = 0;

        velocity = velocity.sum(aceleration);
        velocity = velocity.maxVelLimit(maxvel);

        direction = direction.setDirection(angle - Math.PI/2);

        setX((float) (getX()+velocity.getX()));
        setY((float) (getY()+velocity.getY()));
    }

    @Override
    public void render(Graphics graphics) {
        Graphics2D g2d = (Graphics2D) graphics;

        att = AffineTransform.getTranslateInstance(getX()+getTurbo().getWidth()+11,
                                                   getY()+getTexture().getHeight()/2+getTurbo().getHeight()+2);
        att.rotate(angle, getTexture().getWidth()/2, 0);


        at = AffineTransform.getTranslateInstance(getX(), getY());
        at.rotate(angle, getTexture().getWidth()/2, getTexture().getHeight()/2+12);

        g2d.drawImage(getTexture(), at, null);
        if (acelerating)
            g2d.drawImage(getTurbo().getScaledInstance(26, 56, BufferedImage.SCALE_SMOOTH), att, null);
    }
}
