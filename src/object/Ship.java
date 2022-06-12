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

    }
}
