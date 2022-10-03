package system;

import object.Enemy;
import object.GameObjectType;
import object.Ship;

public class GameWorld {
    private final GameConducting conducting;

    public GameWorld(GameConducting conducting) {
        this.conducting = conducting;
        enemyTime = 0;
    }

    public void createObjects() {
        Ship player = new Ship(Constants.WIDTH / 2 - 26 / 2, Constants.HEIGHT - 150, GameObjectType.Player, 9.1f, 0.08, conducting);

        //Enemy enemy = new Enemy((float) (Math.random()*Constants.WIDTH), 30, GameObjectType.Enemy, 10.2f, 0.09, conducting);
        conducting.addGameObject(player);
    }

    public void createEnemy(int time){
        if((time - enemyTime) == 3){
            conducting.addGameObject(new Enemy((float) (Math.random()*Constants.WIDTH-50), -150, GameObjectType.Enemy, 10.2f, 0.09, conducting));
            conducting.addGameObject(new Enemy((float) (Math.random()*Constants.WIDTH-50), -150, GameObjectType.Enemy, 10.2f, 0.09, conducting));
            conducting.addGameObject(new Enemy((float) (Math.random()*Constants.WIDTH-50), -150, GameObjectType.Enemy, 10.2f, 0.09, conducting));
            conducting.addGameObject(new Enemy((float) (Math.random()*Constants.WIDTH-50), -150, GameObjectType.Enemy, 10.2f, 0.09, conducting));
            conducting.addGameObject(new Enemy((float) (Math.random()*Constants.WIDTH-50), -150, GameObjectType.Enemy, 10.2f, 0.09, conducting));
            enemyTime = time;
        }
    }

    private int enemyTime;
}
