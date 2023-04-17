package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.*;
public class Balloom extends Entity{
    public Balloom(int x, int y, Image image){
        super(x,y,image);
    }
    @Override
    public void update() {
        setAnimate();
    }
   public void setAnimate(){
       if(this.DeadAnimation && this.timeDeadAnimation > 0){
           this.setImg(Sprite.balloom_dead.getFxImage());
           timeDeadAnimation -= 0.15;
       } else if (timeDeadAnimation <= 0) {
           this.setAlive(false);
           timeDeadAnimation = 4;
           this.setDeadAnimation(false);
       }
   }
}
