package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends Entity{
    private static double timeAnimation = 0;
    private static int timeRunAnimation = 3;
    private double timeToDead = 4;
    public Brick(int x, int y, Image image){
        super(x,y,image);
    }
    @Override
    public void update() {
            setAnimate();
    }
    public void setAnimate(){
        if(this.DeadAnimation && this.timeDeadAnimation > 0){
            timeAnimation = (timeAnimation > timeToDead) ? 0 : timeAnimation + 0.15;
            this.setImg((Sprite.movingSprite(Sprite.brick_exploded,Sprite.brick_exploded1,Sprite.brick_exploded2,(int)timeAnimation,timeRunAnimation).getFxImage()));
            timeDeadAnimation -= 0.15;
        } else if (timeDeadAnimation <= 0) {
            this.setImg(Sprite.grass.getFxImage());
            this.setAlive(false);
            timeDeadAnimation = 4;
            this.setDeadAnimation(false);
        }
    }
}
