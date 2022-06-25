package system;

import object.GameObjectType;
import object.Ship;

public class GameWorld {
    private final GameConducting conducting;

    public GameWorld(GameConducting conducting) {
        this.conducting = conducting;
    }

    public void createObjects() {
        Ship player = new Ship(Constants.WIDTH / 2 - 26 / 2, Constants.HEIGHT - 150, GameObjectType.Player, 5.0f, 0.08);
        conducting.addGameObject(player);
    }
}
