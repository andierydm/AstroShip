package object;

import math.Vector;
import system.GameConducting;
import system.ResourceManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ShipsObject extends GameObject {
    protected Vector direction;
    protected Vector velocity;
    private BufferedImage texture, turbo;
    protected AffineTransform at;
    protected AffineTransform att;
    protected double defaultMagnitude;
    protected Vector aceleration;
    protected float maxvel;
    protected double angle;
    protected GameConducting conduting;

    public ShipsObject(GameObjectType gameObjectType) {
        super(gameObjectType);
        maxvel = 5.0f;
        defaultMagnitude = 0.08;
        aceleration = new Vector();
        velocity = new Vector();
        direction = new Vector(0, 1);
        this.angle = 0;
    }

    public ShipsObject(float x, float y, GameObjectType gameObjectType, GameConducting conducting) {
        super(x, y, gameObjectType);
        maxvel = 5.0f;
        defaultMagnitude = 0.08;
        aceleration = new Vector();
        velocity = new Vector();
        direction = new Vector(0, 1);
        this.angle = 0;
        this.conduting = conducting;
    }

    protected void loadTexture() {
        texture = ResourceManager.toBufferedImage(ResourceManager.findImageResource("ship"));
    }

    protected void loadTexture(String path) {
        try {
            turbo = ImageIO.read(ShipsObject.class.getClassLoader().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Vector getCenter(){
        return new Vector(getX()+texture.getWidth()/2-27/2, getY()+texture.getHeight()/2);
    }

    @Override
    public void update(long deltaTime) {
    }

    @Override
    public void render(Graphics graphics) {
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public BufferedImage getTurbo() {
        return turbo;
    }

}
