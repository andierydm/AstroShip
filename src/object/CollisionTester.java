package object;

import system.collision.CollisionShape;

import java.awt.*;

public class CollisionTester extends GameObject {
    CollisionShape collisionShape = CollisionShape.createBoxShape(25, 25);

    public CollisionTester(float x, float y) {
        super(x, y, GameObjectType.Player);
    }

    @Override
    public void update(long deltaTime) {

    }

    @Override
    public void render(Graphics graphics) {

    }
}
