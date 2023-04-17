package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class bombExplore extends Entity{
    private boolean isAlive = true;

    private static double timeAnimationBomb = 0;
    private static int timeRunAnimationBomb = 3;
    private  double timeExplore = 6;
    public bombExplore(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        setExplore();
    }
    public void setExplore(){
        if (timeExplore > 0 && isAlive) {
            timeAnimationBomb = (timeAnimationBomb > timeExplore) ? 0 : timeAnimationBomb + 0.15;
            this.setImg((Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, (int)timeAnimationBomb, timeRunAnimationBomb).getFxImage()));
            timeExplore -=0.15;
        }
        else {
            this.setAlive(false);
            timeExplore = 6;
        }
    }
    public double getTimeExplore(){
        return this.timeExplore;
    }

    public void setTimeExplore(double timeExplore) {
        this.timeExplore = timeExplore;
    }

    public boolean getIsAlive(){
        return this.isAlive;
    }

    @Override
    public void setAlive(boolean alive) {
        this.isAlive =  alive;
    }
}
