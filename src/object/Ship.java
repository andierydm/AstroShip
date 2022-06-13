package object;

import java.awt.*;

public class Ship extends GameObject {
    public Ship() {
        super(GameObjectType.Player);
    }

    public Ship(float x, float y) {
        super(x, y, GameObjectType.Player);
    }

    @Override
    public void update(long deltaTime) {

    }

    @Override
    public void render(Graphics graphics) {
        Graphics2D g2d = (Graphics2D) graphics;

        g2d.drawRect((int) getX(), (int) getY(), 26, 16);
    }
}
