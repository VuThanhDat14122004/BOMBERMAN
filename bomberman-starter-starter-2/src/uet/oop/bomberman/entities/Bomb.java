package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;


import javax.swing.*;

public class Bomb extends Entity {

    private static int MAX_TIME_ANIMATION = 19000;
    private static double timeAnimationBomb = 0;
    private static int timeRunAnimationBomb = 3;
    private double timeExplore = 4;

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        setAnimate();
    }

    public void setAnimate() {
        if (timeAnimate > 4 && isAlive) {
            timeAnimationBomb = (timeAnimationBomb > MAX_TIME_ANIMATION) ? 0 : timeAnimationBomb + 0.15;
            this.setImg((Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, (int) timeAnimationBomb, timeRunAnimationBomb).getFxImage()));
            timeAnimate -= 0.15;
        } else if( timeAnimate <= 4 && timeAnimate > 0 && isAlive) {
            timeAnimationBomb = (timeAnimationBomb > timeExplore) ? 0 : timeAnimationBomb + 0.15;
            this.setImg((Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, (int) timeAnimationBomb, timeRunAnimationBomb).getFxImage()));
            timeAnimate -= 0.15;
        }
        else{
            timeAnimate = 24;
            this.setAlive(false);
        }
    }


    public double getTimeAnimate() {
        return this.timeAnimate;
    }

    public void setTimeAnimate(double timeAnimate) {
        this.timeAnimate = timeAnimate;
    }
}
