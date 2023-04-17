package uet.oop.bomberman.entities;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.graphics.Sprite;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import uet.oop.bomberman.BombermanGame.*;

public class CreateMap {
    private static List<Entity> entities = new ArrayList<>();
    private static List<Entity> stillObjects = new ArrayList<>();
    private static List<Entity> bombs = new ArrayList<>();
    private static List<Entity> items = new ArrayList<>();
    static Canvas canvas;
    public static void loadMap(String mapSrc) {
        String map = "res/levels/" + mapSrc;
        File file = new File(map);
        try {
                Scanner sc = new Scanner(file);
            int l = sc.nextInt();
            int r = sc.nextInt();
            int c = sc.nextInt();
            String s = sc.nextLine();
            canvas.setWidth(Sprite.SCALED_SIZE * c * 1);
            canvas.setHeight(Sprite.SCALED_SIZE * (r + 2) * 1);
            for (int i = 2; i < r + 2; i++) {
                s = sc.nextLine();
                for (int j = 0; j < c; j++) {
                    switch (s.charAt(j)) {
                        case '#':
                            stillObjects.add(new Wall(j, i, Sprite.wall.getFxImage()));
                            break;
                        case '*':
                            stillObjects.add(new Brick(j, i, Sprite.brick.getFxImage()));
                            break;
                        case 'x':
                            items.add(new Portal(j, i, Sprite.portal.getFxImage()));
                            break;
                        case 'p':
                            entities.add(new Bomber(j, i, Sprite.player_down.getFxImage()));
                            break;
                        case '1':
                            entities.add(new Balloom(j, i, Sprite.balloom_left1.getFxImage()));
                            break;
                        case '2':
                            entities.add(new Balloom(j, i, Sprite.oneal_right1.getFxImage()));
                            break;
                        case 'b':
                            items.add(new BombItem(j, i, Sprite.bomb.getFxImage()));
                            break;
                        case 'f':
                            items.add(new FlameItem(j, i, Sprite.powerup_flames.getFxImage()));
                            break;
                        case 's':
                            items.add(new speedItem(j, i, Sprite.powerup_speed.getFxImage()));
                            break;
                    }
                    if (s.charAt(j) != '#' && s.charAt(j) != '*') {
                        stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
