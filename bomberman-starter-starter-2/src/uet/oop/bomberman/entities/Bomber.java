package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends Entity {
    private static double timeAnimation = 0;
    private static int timeRunAnimation = 3;
    private double timeToDead = 4;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        animate();
    }

    public void animate() {
        if (this.DeadAnimation && this.timeDeadAnimation > 0) {
            timeAnimation = (timeAnimation > timeToDead) ? 0 : timeAnimation + 0.15;
            this.setImg((Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, (int) timeAnimation, timeRunAnimation).getFxImage()));
            timeDeadAnimation -= 0.06;
        } else if (timeDeadAnimation <= 0) {
            this.setImg(Sprite.grass.getFxImage());
            this.setAlive(false);
            timeDeadAnimation = 4;
            this.setDeadAnimation(false);
        }
    }

    @Override
    public boolean getIsAlive() {
        return super.getIsAlive();
    }

    @Override
    public void setAlive(boolean alive) {
        super.setAlive(alive);
    }
}
