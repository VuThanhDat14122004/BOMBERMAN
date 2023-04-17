package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Grass extends Entity {
    private static double timeAnimation = 0;
    private static int timeRunAnimation = 3;
    private double timeToDead = 2;

    public Grass(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        setAnimate();
    }

    public void setAnimate() {
        if (this.DeadAnimation && this.timeDeadAnimation > 0) {
            timeAnimation = (timeAnimation > timeToDead) ? 0 : timeAnimation + 0.15;
            this.setImg((Sprite.movingSprite(Sprite.bomb_exploded,Sprite.bomb_exploded1,Sprite.bomb_exploded2,(int)timeAnimation,timeRunAnimation).getFxImage()));
            timeDeadAnimation -= 0.2;
        }  if (timeDeadAnimation <= 0) {
            this.setImg(Sprite.grass.getFxImage());
            timeDeadAnimation = 4;
            this.setDeadAnimation(false);
        }
    }
}
