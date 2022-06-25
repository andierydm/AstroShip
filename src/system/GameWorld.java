package system;

import object.Ship;

public class GameWorld {
    private final GameConducting conducting;

    public GameWorld(GameConducting conducting) {
        this.conducting = conducting;
    }

    public void createObjects() {
        Ship player = new Ship(Constants.WIDTH / 2 - 26 / 2, Constants.HEIGHT - 150, 5.0f);
        conducting.addGameObject(player);
    }
}
