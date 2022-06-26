package object;

import math.Vector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ShipsObject extends GameObject{
    protected Vector direction;
    protected Vector velocity;
    private BufferedImage texture;
    protected AffineTransform at;
    protected double defaultMagnitude;
    protected Vector aceleration;
    protected float maxvel;
    protected double angle;

    public ShipsObject(GameObjectType gameObjectType) {
        super(gameObjectType);
        maxvel= 5.0f;
        defaultMagnitude = 0.08;
        aceleration = new Vector();
        velocity = new Vector();
        direction = new Vector(0,1);
        this.angle = 0;
    }

    public ShipsObject(float x, float y, GameObjectType gameObjectType) {
        super(x, y, gameObjectType);
        maxvel= 5.0f;
        defaultMagnitude = 0.08;
        aceleration = new Vector();
        velocity = new Vector();
        direction = new Vector(0,1);
        this.angle = 0;
    }

    protected void loadTexture(String path){
        try {
            texture = ImageIO.read(ShipsObject.class.getClassLoader().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(long deltaTime) {
    }

    @Override
    public void render(Graphics graphics) {
    }

    public BufferedImage getTexture(){return texture;}

}
