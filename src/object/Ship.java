package object;

import system.input.KeyboardInput;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Ship extends ShipsObject {

    public Ship() {
        super(GameObjectType.Player);
        loadTexture("../resource/nave.png");
    }

    public Ship(float x, float y, GameObjectType type, float maxVel, double defaultMagnitude) {
        super(x, y, type);
        loadTexture("../resource/nave.png");
        this.defaultMagnitude = defaultMagnitude;
        this.maxvel= maxVel;
    }

    @Override
    public void update(long deltaTime) {
        if (KeyboardInput.up){
            aceleration = direction.scale(defaultMagnitude);
        }else {
            if (velocity.getMagnitude() != 0)   aceleration = velocity.scale(-1).normalize().scale(defaultMagnitude);
        }

        if (KeyboardInput.right) angle += Math.PI/36;
        if (KeyboardInput.left) angle -= Math.PI/36;

        velocity = velocity.sum(aceleration);
        velocity = velocity.maxVelLimit(maxvel);

        direction = direction.setDirection(angle - Math.PI/2);

        setX(getX()+velocity.getX());
        setY(getY()+velocity.getY());
    }

    @Override
    public void render(Graphics graphics) {
        Graphics2D g2d = (Graphics2D) graphics;

        at = AffineTransform.getTranslateInstance(getX(), getY());
        at.rotate(angle, getTexture().getWidth()/2, getTexture().getHeight()/2+12);

        g2d.drawImage(getTexture(), at, null);
    }
}
