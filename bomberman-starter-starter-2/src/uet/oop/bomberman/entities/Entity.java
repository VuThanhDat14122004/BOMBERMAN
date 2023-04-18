package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;

    protected Image img;

    protected boolean isAlive = true;
    protected boolean DeadAnimation = false;
    protected boolean checkLeft = true;
    protected boolean checkRight = true;
    protected boolean checkUp = true;
    protected boolean checkDown = true;
    protected double timeAnimate = 24;
    protected double timeDeadAnimation = 4;

    public boolean isCheckUp() {
        return checkUp;
    }

    public void setCheckUp(boolean checkUp) {
        this.checkUp = checkUp;
    }

    public boolean isCheckDown() {
        return checkDown;
    }

    public void setCheckDown(boolean checkDown) {
        this.checkDown = checkDown;
    }

    public boolean isCheckLeft() {
        return this.checkLeft;
    }

    public void setCheckLeft(boolean checkLeft) {
        this.checkLeft = checkLeft;
    }
    public boolean isCheckRight(){
        return this.checkRight;
    }
    public double getTimeAnimate(){
        return this.timeAnimate;
    }
    public void setTimeAnimate(double timeAnimate){
        this.timeAnimate = timeAnimate;
    }

    public boolean getDeadAnimation() {
        return this.DeadAnimation;
    }

    public void setDeadAnimation(boolean deadAnimation) {
        DeadAnimation = deadAnimation;
    }

    public double getTimeDeadAnimation() {
        return this.timeDeadAnimation;
    }

    public void setTimeDeadAnimation(double timeDeadAnimation) {
        this.timeDeadAnimation = timeDeadAnimation;
    }

    public void setCheckRight(boolean checkRight) {
        this.checkRight = checkRight;
    }

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity(int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImage() {
        return this.img;
    }
    public void setImg(Image img) {
        this.img = img;
    }
    public  boolean getIsAlive(){
        return this.isAlive;
    }

    public void setAlive(boolean alive) {
        this.isAlive = alive;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public abstract void update();
}
