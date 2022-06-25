package object;

import system.input.KeyboardInput;
import math.Vector;

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


    public Ship() {
        super(GameObjectType.Player);
        aceleration = new Vector();
        velocity = new Vector(1,1);
        direction = new Vector(0,1);
        loadTexture();
        defaultMagnitude = 0.08;
    }

    public Ship(float x, float y, float maxVel) {
        super(x, y, maxVel, GameObjectType.Player);
        aceleration = new Vector();
        velocity = new Vector(0,-1);
        direction = new Vector(0,1);
        loadTexture();
        defaultMagnitude = 0.08;
    }

    private void loadTexture(){
        try {
            texture = ImageIO.read(Ship.class.getResource("../resource/nave.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(long deltaTime) {
        if (KeyboardInput.up){
            aceleration = direction.scale(defaultMagnitude);
            System.out.println(velocity.getX()+" "+ velocity.getY());
        }

        if (KeyboardInput.right) angle += Math.PI/16;
        if (KeyboardInput.left) angle -= Math.PI/16;

        velocity = velocity.sum(aceleration);

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
