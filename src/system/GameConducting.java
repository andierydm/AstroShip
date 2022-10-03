package system;

import object.GameObject;
import object.Ship;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameConducting {
    private final List<GameObject> gameObjects = new ArrayList<>();

    public void update(long deltaTime) {
        List<GameObject> copyGameObjects = new ArrayList<>(gameObjects);

        for (GameObject gameObject : copyGameObjects) {
            gameObject.update(deltaTime);
        }
    }

    public void render(Graphics graphics) {
        List<GameObject> copyGameObjects = new ArrayList<>(gameObjects);

        for (GameObject gameObject : copyGameObjects) {
            gameObject.render(graphics);
        }
    }

    public void addGameObject(GameObject gameObject) {
        if (gameObject == null) {
            throw new IllegalArgumentException("object.GameObject can't be null");
        }

        gameObjects.add(gameObject);
    }

    public void removeGameObject(GameObject gameObject) {
        if (gameObject == null) {
            throw new IllegalArgumentException("object.GameObject can't be null");
        }

        gameObjects.remove(gameObject);
    }

    public List<GameObject> getList(){return gameObjects;}
}
