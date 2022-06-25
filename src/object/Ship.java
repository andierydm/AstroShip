package object;

import math.Vector;
import system.input.KeyboardInput;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Ship extends GameObject {
    private Vector direction;
    private Vector velocity;
    private BufferedImage texture;
    private AffineTransform at;
    private final double defaultMagnitude;
    private Vector aceleration;
    private final float maxvel;
    protected double angle;


    public Ship() {
        super(GameObjectType.Player);
        aceleration = new Vector();
        velocity = new Vector(1,1);
        direction = new Vector(0,1);
        loadTexture();
        defaultMagnitude = 0.08;
        maxvel = 5.0f;
        this.angle = 0;
    }

    public Ship(float x, float y, GameObjectType type, float maxVel) {
        super(x, y, type);
        loadTexture();
        aceleration = new Vector();
        velocity = new Vector(0,-1);
        direction = new Vector(0,1);
        defaultMagnitude = 0.08;
        this.maxvel= maxVel;
        this.angle = 0;
    }

    private void loadTexture(){
        try {
            texture = ImageIO.read(Ship.class.getResource("../resource/nave.png"));
            System.out.println(texture);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        at.rotate(angle, texture.getWidth()/2, texture.getHeight()/2+12);

        g2d.drawImage(texture, at, null);
    }
}
