package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import javax.swing.*;

public class Oneal extends Entity {
    public Oneal(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        setAnimate();
    }
    public void setAnimate(){
        if(this.DeadAnimation && this.timeDeadAnimation > 0){
            this.setImg(Sprite.oneal_dead.getFxImage());
            timeDeadAnimation -= 0.15;
        } else if (timeDeadAnimation <= 0) {
            this.setAlive(false);
            timeDeadAnimation = 4;
            this.setDeadAnimation(false);
        }
    }
}
