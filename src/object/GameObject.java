package object;

import java.awt.*;

public abstract class GameObject {
    private float x;
    private float y;
    private final GameObjectType gameObjectType;

    public GameObject(GameObjectType gameObjectType) {
        if (gameObjectType == null) {
            throw new IllegalStateException("type of object.GameObject must not be null");
        }

        this.x = 0;
        this.y = 0;
        this.gameObjectType = gameObjectType;
    }

    public GameObject(float x, float y, GameObjectType gameObjectType) {
        if (gameObjectType == null) {
            throw new IllegalStateException("type of object.GameObject must not be null");
        }

        this.x = x;
        this.y = y;
        this.gameObjectType = gameObjectType;
    }

    public abstract void update(long deltaTime);

    public abstract void render(Graphics graphics);

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public GameObjectType getGameObjectType() {
        return gameObjectType;
    }
}
