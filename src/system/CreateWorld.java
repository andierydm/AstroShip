package system;

import main.Constantes;
import object.Ship;

public class CreateWorld {
    private Ship player;
    private GameConducting conducting;

    public CreateWorld(GameConducting conducting){
        this.conducting = conducting;
        player = new Ship(Constantes.WIDTH/2-26/2, Constantes.HEIGHT-76);
    }

    public void createObjects(){
        conducting.addGameObject(player);
    }
}
