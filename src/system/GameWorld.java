package system;

import object.Ship;

public class GameWorld {
    private Ship player;
    private GameConducting conducting;

    public GameWorld(GameConducting conducting){
        this.conducting = conducting;
        player = new Ship(Constants.WIDTH/2-26/2, Constants.HEIGHT-150, 5.0f);
    }

    public void createObjects(){
        conducting.addGameObject(player);
    }
}
