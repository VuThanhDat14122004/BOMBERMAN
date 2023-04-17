package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.animation.*;
import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Light;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.Bomb;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class BombermanGame extends Application {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    //672x480.
    public static int move = 64;
    private GraphicsContext gc;
    private Canvas canvas;
    public List<Entity> entities = new ArrayList<>();
    public List<Entity> stillObjects = new ArrayList<>();
    public List<Entity> enemy = new ArrayList<>();
    public List<Entity> Bomb = new ArrayList<>();
    /**
     * vì mỗi lần enemy chạy 2 pixel
     */
    public static int[][] listObject = new int[496][208];
    /**
     * trong listObject
     * 0: grass
     * 1: balloom,oneal
     * 2: speedItem
     * 3: FlameItem
     * 4: Portal
     * 5: Bomb
     * 6: BombItem
     */
    private int maxBomb = 1;
    //ForMove
    private boolean checkLeftBalloom1 = true;
    private boolean checkRightBalloom1 = true;
    private boolean checkLeftBalloom2 = true;
    private boolean checkRightBalloom2 = true;
    private boolean checkLeftOneal1 = true;
    private boolean checkRightOneal1 = true;
    private boolean checkLeftOneal2 = true;
    private boolean checkRightOneal2 = true;
    private static boolean victory = false;
    private Boolean wPressed = false;
    private Boolean sPressed = false;

    private Boolean aPressed = false;
    private Boolean dPressed = false;
    private Boolean xPressed = false;
    private Boolean jPressed = false;
    private int flame = 0;
    public static boolean[][] checkMap = new boolean[WIDTH][HEIGHT];
    private static boolean endGame = false;
    //
    private static int MAX_TIME_ANIMATION = 19000;
    private static int timeAnimation = 0;
    private static int timeRunAnimation = 5;
    private static int timeAnimationBomb = 0;
    private static int timeRunAnimationBomb = 3;
    private static double timeExplore = 24;
    private static Stage primaryStage;
    private static Scene sceneGameOver;
    private static Entity primaryBomber;
    private Entity primaryBomb;
    private static double timeAnimationForBomb = 1;
    private static double timeToExplore = 5;
    private int powerUp = 1;
    private int powerDown = 1;
    private int powerLeft = 1;
    private int powerRight = 1;
    private int powerUpP = 1;
    private int powerDownP = 1;
    private int powerLeftP = 1;
    private int powerRightP = 1;
    private int powerUpE = 1;
    private int powerDownE = 1;
    private int powerLeftE = 1;
    private int powerRightE = 1;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        //menu
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(992, 416);
        //backGroundMenuStart
        Image image = new Image("/sprites/bombermanBackGround.png");
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setX(0.0);
        imageView.setY(0.0);
        imageView.setFitHeight(416);
        imageView.setFitWidth(992);
        //backGroundForButton
        Image image1 = new Image("/sprites/bombermanBackGroundButton.jpeg");
        ImageView imageView1 = new ImageView();
        imageView1.setImage(image1);
        imageView1.setFitWidth(248);
        imageView1.setFitHeight(66.56);
        imageView1.setPreserveRatio(true);
        //buttonStart
        Button buttonStart = new Button();
        buttonStart.setPrefSize(248, 66.56);
        buttonStart.setLayoutX(682);
        buttonStart.setLayoutY(124.8);
        buttonStart.setText("START");
        Image image2 = new Image("/sprites/bombermanBackGroundButton.jpeg");
        //backgroundForButtonStart
        BackgroundImage backgroundImage = new BackgroundImage(image2, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        buttonStart.setBackground(background);
        //buttonExit
        Button buttonExit = new Button();
        buttonExit.setPrefSize(248, 66.56);
        buttonExit.setLayoutX(682);
        buttonExit.setLayoutY(291.2);
        buttonExit.setText("EXIT");
        buttonExit.setBackground(background);//backGroundChoButtonExit
        buttonExit.setOnAction(e -> {
            System.exit(0);
        });
        anchorPane.getChildren().addAll(imageView, buttonStart, buttonExit);
        Scene scene1 = new Scene(anchorPane, 992, 416);//sceneChoMenuStart
        //menuGameOver
        Image image3 = new Image("/sprites/gameOverBackGround.png");
        ImageView imageView2 = new ImageView();
        imageView2.setImage(image3);
        imageView2.setX(0.0);
        imageView2.setY(0.0);
        imageView2.setFitWidth(300);
        imageView2.setFitHeight(400);
        AnchorPane anchorPane1 = new AnchorPane();
        anchorPane1.setPrefSize(300, 400);
        Button buttonPlayAgain = new Button();
        buttonPlayAgain.setPrefSize(100, 25);
        buttonPlayAgain.setLayoutX(20);
        buttonPlayAgain.setLayoutY(360);
        buttonPlayAgain.setBackground(background);
        buttonPlayAgain.setText("PLAY AGAIN?");

        Button buttonExit1 = new Button("EXIT?");
        buttonExit1.setPrefSize(100, 25);
        buttonExit1.setLayoutX(180);
        buttonExit1.setLayoutY(360);
        buttonExit1.setBackground(background);
        buttonExit1.setOnAction(e -> {
            System.exit(0);
        });
        Button buttonExit2 = new Button("EXIT?");
        buttonExit2.setPrefSize(100, 25);
        buttonExit2.setLayoutX(180);
        buttonExit2.setLayoutY(360);
        buttonExit2.setBackground(background);
        buttonExit2.setOnAction(e -> {
            System.exit(0);
        });
        anchorPane1.getChildren().addAll(imageView2, buttonPlayAgain, buttonExit1);
        Scene scene2 = new Scene(anchorPane1, 300, 400);//sceneForLose
        sceneGameOver = scene2;
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();
        Timeline t1 = new Timeline(new KeyFrame(Duration.millis(50000), e -> render()));
        t1.setCycleCount(Timeline.INDEFINITE);
        // victoryScene
        Image image4 = new Image("/sprites/victory.png");
        ImageView imageView3 = new ImageView();
        imageView3.setImage(image4);
        imageView3.setX(0.0);
        imageView3.setY(0.0);
        imageView3.setFitWidth(300);
        imageView3.setFitHeight(400);
        AnchorPane anchorPane2 = new AnchorPane();
        anchorPane2.setPrefSize(300, 400);
        anchorPane2.getChildren().addAll(imageView3, buttonExit2);
        Scene scene3 = new Scene(anchorPane2,300,400);
        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);//sceneGame
        //playAgain
        buttonPlayAgain.setOnAction(e -> {

            resetAll();
            entities.clear();
            endGame = false;
            move = 64;
            createMap2("Level1.txt");
            stage.setScene(scene);
            if (endGame) {
                stage.setScene(scene2);
                stage.show();
            }
            checkEndGame(entities.get(0), enemy);
            if (endGame) {
                stage.setScene(scene2);
                stage.show();
            }
            scene.setOnKeyPressed(ev -> {

                if (ev.getCode() == KeyCode.W) {
                    wPressed = true;
                    System.out.println("W");
                } else if (ev.getCode() == KeyCode.S) {
                    sPressed = true;
                    System.out.println("S");
                } else if (ev.getCode() == KeyCode.A) {
                    aPressed = true;
                    System.out.println("A");
                } else if (ev.getCode() == KeyCode.D) {
                    dPressed = true;
                    System.out.println("D");
                } else if (ev.getCode() == KeyCode.X) {
                    xPressed = true;
                }

                if (ev.getCode() == KeyCode.J) {
                    jPressed = true;

                }

                if (endGame) {
                    stage.setScene(scene2);
                    stage.show();
                }
                checkEndGame(entities.get(0), enemy);
                if (endGame) {
                    stage.setScene(scene2);
                    stage.show();
                } else {
                    setBomb();
                    update();
                    exploreBomb();
                    setMoveBomber(entities.get(0));
                    if (entities.get(0).getX() % 2 == 0 && entities.get(0).getY() % 2 == 0) {
                        if (listObject[entities.get(0).getX() / 2 + 16][entities.get(0).getY() / 2] == 3) {
                            powerUp = 2;
                            powerDown = 2;
                            powerLeft = 2;
                            powerRight = 2;
                            powerUpP = 2;
                            powerDownP = 2;
                            powerLeftP = 2;
                            powerRightP = 2;
                            powerUpE = 2;
                            powerDownE = 2;
                            powerLeftE = 2;
                            powerRightE = 2;
                            stillObjects.removeIf(H -> H instanceof FlameItem);
                            listObject[entities.get(0).getX() / 2 + 16][entities.get(0).getY() / 2] = 0;
                        }
                        if (listObject[entities.get(0).getX() / 2 - 16][entities.get(0).getY() / 2] == 3) {
                            powerUp = 2;
                            powerDown = 2;
                            powerLeft = 2;
                            powerRight = 2;
                            powerUpP = 2;
                            powerDownP = 2;
                            powerLeftP = 2;
                            powerRightP = 2;
                            powerUpE = 2;
                            powerDownE = 2;
                            powerLeftE = 2;
                            powerRightE = 2;
                            stillObjects.removeIf(H -> H instanceof FlameItem);
                            listObject[entities.get(0).getX() / 2 - 16][entities.get(0).getY() / 2] = 0;
                        }
                        if (listObject[entities.get(0).getX() / 2][entities.get(0).getY() / 2 + 16] == 3) {
                            powerUp = 2;
                            powerDown = 2;
                            powerLeft = 2;
                            powerRight = 2;
                            powerUpP = 2;
                            powerDownP = 2;
                            powerLeftP = 2;
                            powerRightP = 2;
                            powerUpE = 2;
                            powerDownE = 2;
                            powerLeftE = 2;
                            powerRightE = 2;
                            stillObjects.removeIf(H -> H instanceof FlameItem);
                            listObject[entities.get(0).getX() / 2][entities.get(0).getY() / 2 + 16] = 0;
                        }
                        if (listObject[entities.get(0).getX() / 2][entities.get(0).getY() / 2 - 16] == 3) {
                            powerUp = 2;
                            powerDown = 2;
                            powerLeft = 2;
                            powerRight = 2;
                            powerUpP = 2;
                            powerDownP = 2;
                            powerLeftP = 2;
                            powerRightP = 2;
                            powerUpE = 2;
                            powerDownE = 2;
                            powerLeftE = 2;
                            powerRightE = 2;
                            stillObjects.removeIf(H -> H instanceof FlameItem);
                            listObject[entities.get(0).getX() / 2][entities.get(0).getY() / 2 - 16] = 0;
                        }
                        if (listObject[entities.get(0).getX() / 2 + 16][entities.get(0).getY() / 2] == 2) {
                            move = 128;
                            stillObjects.removeIf(H -> H instanceof speedItem);
                            listObject[entities.get(0).getX() / 2 + 16][entities.get(0).getY() / 2] = 0;
                        }
                        if (listObject[entities.get(0).getX() / 2 - 16][entities.get(0).getY() / 2] == 2) {
                            move = 128;
                            stillObjects.removeIf(H -> H instanceof speedItem);
                            listObject[entities.get(0).getX() / 2 + 16][entities.get(0).getY() / 2] = 0;
                        }
                        if (listObject[entities.get(0).getX() / 2][entities.get(0).getY() / 2 + 16] == 2) {
                            move = 128;
                            stillObjects.removeIf(H -> H instanceof speedItem);
                            listObject[entities.get(0).getX() / 2 + 16][entities.get(0).getY() / 2] = 0;
                        }
                        if (listObject[entities.get(0).getX() / 2][entities.get(0).getY() / 2 - 16] == 2) {
                            move = 128;
                            stillObjects.removeIf(H -> H instanceof speedItem);
                            listObject[entities.get(0).getX() / 2 + 16][entities.get(0).getY() / 2] = 0;
                        }
                        if (listObject[entities.get(0).getX() / 2 + 16][entities.get(0).getY() / 2] == 6) {
                            maxBomb = 2;
                            stillObjects.removeIf(H -> H instanceof BombItem);
                            listObject[entities.get(0).getX() / 2 + 16][entities.get(0).getY() / 2] = 0;
                        }
                        if (listObject[entities.get(0).getX() / 2 - 16][entities.get(0).getY() / 2] == 6) {
                            maxBomb = 2;
                            stillObjects.removeIf(H -> H instanceof BombItem);
                            listObject[entities.get(0).getX() / 2 + 16][entities.get(0).getY() / 2] = 0;
                        }
                        if (listObject[entities.get(0).getX() / 2][entities.get(0).getY() / 2 + 16] == 6) {
                            maxBomb = 2;
                            stillObjects.removeIf(H -> H instanceof BombItem);
                            listObject[entities.get(0).getX() / 2 + 16][entities.get(0).getY() / 2] = 0;
                        }
                        if (listObject[entities.get(0).getX() / 2][entities.get(0).getY() / 2 - 16] == 6) {
                            maxBomb = 2;
                            stillObjects.removeIf(H -> H instanceof BombItem);
                            listObject[entities.get(0).getX() / 2 + 16][entities.get(0).getY() / 2] = 0;
                        }
                        if (listObject[entities.get(0).getX() / 2 + 16][entities.get(0).getY() / 2] == 4) {
                           if(enemy.isEmpty()){
                               primaryStage.setScene(scene3);
                               primaryStage.show();
                           }
                        }
                        if (listObject[entities.get(0).getX() / 2 - 16][entities.get(0).getY() / 2] == 4) {
                            if(enemy.isEmpty()){
                                primaryStage.setScene(scene3);
                                primaryStage.show();
                            }
                        }
                        if (listObject[entities.get(0).getX() / 2][entities.get(0).getY() / 2 + 16] == 4) {
                            if(enemy.isEmpty()){
                                primaryStage.setScene(scene3);
                                primaryStage.show();
                            }
                        }
                        if (listObject[entities.get(0).getX() / 2][entities.get(0).getY() / 2 - 16] == 4) {
                            if(enemy.isEmpty()){
                                primaryStage.setScene(scene3);
                                primaryStage.show();
                            }
                        }

                    }

                }
            });
            if (endGame) {
                stage.setScene(scene2);
                stage.show();
            }
            checkEndGame(entities.get(0), enemy);
            if (endGame) {
                stage.setScene(scene2);
                stage.show();
            }
            scene.setOnKeyReleased(keyEvent -> {
                if (endGame) {
                    stage.setScene(scene2);
                    stage.show();
                }
                checkEndGame(entities.get(0), enemy);
                if (endGame) {
                    stage.setScene(scene2);
                    stage.show();
                }
                if (keyEvent.getCode() == KeyCode.W) {
                    wPressed = false;
                } else if (keyEvent.getCode() == KeyCode.S) {
                    sPressed = false;
                } else if (keyEvent.getCode() == KeyCode.A) {
                    aPressed = false;
                } else if (keyEvent.getCode() == KeyCode.D) {
                    dPressed = false;
                } else if (keyEvent.getCode() == KeyCode.X) {
                    xPressed = false;
                } else if (keyEvent.getCode() == KeyCode.J) {
                    jPressed = false;
                }
            });
            if (endGame) {
                stage.setScene(scene2);
                stage.show();
            }
            checkEndGame(entities.get(0), enemy);
            if (endGame) {
                stage.setScene(scene2);
                stage.show();
            }
        });
        // StartPLay
        buttonStart.setOnAction(e -> {

            entities.clear();
            createMap2("Level1.txt");
            stage.setScene(scene);
            if (endGame) {
                stage.setScene(scene2);
                stage.show();
            }
            checkEndGame(entities.get(0), enemy);
            if (endGame) {
                stage.setScene(scene2);
                stage.show();
            }
            if(enemy.isEmpty()){
                primaryStage.setScene(scene3);
                primaryStage.show();
            }
            scene.setOnKeyPressed(ev -> {
                if (ev.getCode() == KeyCode.W) {
                    wPressed = true;
                    System.out.println("W");

                } else if (ev.getCode() == KeyCode.S) {
                    sPressed = true;
                    System.out.println("S");

                } else if (ev.getCode() == KeyCode.A) {
                    aPressed = true;
                    System.out.println("A");

                } else if (ev.getCode() == KeyCode.D) {
                    dPressed = true;
                    System.out.println("D");
                } else if (ev.getCode() == KeyCode.X) {
                    xPressed = true;
                }
                if (endGame) {
                    stage.setScene(scene2);
                    stage.show();
                }
                checkEndGame(entities.get(0), enemy);
                if (endGame) {
                    stage.setScene(scene2);
                    stage.show();
                } else {
                    setBomb();
                    update();
                    exploreBomb();
                    setMoveBomber(entities.get(0));
                    if (entities.get(0).getX() % 2 == 0 && entities.get(0).getY() % 2 == 0) {
                        if (listObject[entities.get(0).getX() / 2 + 16][entities.get(0).getY() / 2] == 3) {
                            powerUp = 2;
                            powerDown = 2;
                            powerLeft = 2;
                            powerRight = 2;
                            powerUpP = 2;
                            powerDownP = 2;
                            powerLeftP = 2;
                            powerRightP = 2;
                            powerUpE = 2;
                            powerDownE = 2;
                            powerLeftE = 2;
                            powerRightE = 2;
                            stillObjects.removeIf(H -> H instanceof FlameItem);
                            listObject[entities.get(0).getX() / 2 + 16][entities.get(0).getY() / 2] = 0;
                        }
                        if (listObject[entities.get(0).getX() / 2 - 16][entities.get(0).getY() / 2] == 3) {
                            powerUp = 2;
                            powerDown = 2;
                            powerLeft = 2;
                            powerRight = 2;
                            powerUpP = 2;
                            powerDownP = 2;
                            powerLeftP = 2;
                            powerRightP = 2;
                            powerUpE = 2;
                            powerDownE = 2;
                            powerLeftE = 2;
                            powerRightE = 2;
                            stillObjects.removeIf(H -> H instanceof FlameItem);
                            listObject[entities.get(0).getX() / 2 - 16][entities.get(0).getY() / 2] = 0;
                        }
                        if (listObject[entities.get(0).getX() / 2][entities.get(0).getY() / 2 + 16] == 3) {
                            powerUp = 2;
                            powerDown = 2;
                            powerLeft = 2;
                            powerRight = 2;
                            powerUpP = 2;
                            powerDownP = 2;
                            powerLeftP = 2;
                            powerRightP = 2;
                            powerUpE = 2;
                            powerDownE = 2;
                            powerLeftE = 2;
                            powerRightE = 2;
                            stillObjects.removeIf(H -> H instanceof FlameItem);
                            listObject[entities.get(0).getX() / 2][entities.get(0).getY() / 2 + 16] = 0;
                        }
                        if (listObject[entities.get(0).getX() / 2][entities.get(0).getY() / 2 - 16] == 3) {
                            powerUp = 2;
                            powerDown = 2;
                            powerLeft = 2;
                            powerRight = 2;
                            powerUpP = 2;
                            powerDownP = 2;
                            powerLeftP = 2;
                            powerRightP = 2;
                            powerUpE = 2;
                            powerDownE = 2;
                            powerLeftE = 2;
                            powerRightE = 2;
                            stillObjects.removeIf(H -> H instanceof FlameItem);
                            listObject[entities.get(0).getX() / 2][entities.get(0).getY() / 2 - 16] = 0;
                        }
                        if (listObject[entities.get(0).getX() / 2 + 16][entities.get(0).getY() / 2] == 2) {
                            move = 128;
                            stillObjects.removeIf(H -> H instanceof speedItem);
                            listObject[entities.get(0).getX() / 2 + 16][entities.get(0).getY() / 2] = 0;
                        }
                        if (listObject[entities.get(0).getX() / 2 - 16][entities.get(0).getY() / 2] == 2) {
                            move = 128;
                            stillObjects.removeIf(H -> H instanceof speedItem);
                            listObject[entities.get(0).getX() / 2 + 16][entities.get(0).getY() / 2] = 0;
                        }
                        if (listObject[entities.get(0).getX() / 2][entities.get(0).getY() / 2 + 16] == 2) {
                            move = 128;
                            stillObjects.removeIf(H -> H instanceof speedItem);
                            listObject[entities.get(0).getX() / 2 + 16][entities.get(0).getY() / 2] = 0;
                        }
                        if (listObject[entities.get(0).getX() / 2][entities.get(0).getY() / 2 - 16] == 2) {
                            move = 128;
                            stillObjects.removeIf(H -> H instanceof speedItem);
                            listObject[entities.get(0).getX() / 2 + 16][entities.get(0).getY() / 2] = 0;
                        }
                        if (listObject[entities.get(0).getX() / 2 + 16][entities.get(0).getY() / 2] == 6) {
                            maxBomb = 2;
                            stillObjects.removeIf(H -> H instanceof BombItem);
                            listObject[entities.get(0).getX() / 2 + 16][entities.get(0).getY() / 2] = 0;
                        }
                        if (listObject[entities.get(0).getX() / 2 - 16][entities.get(0).getY() / 2] == 6) {
                            maxBomb = 2;
                            stillObjects.removeIf(H -> H instanceof BombItem);
                            listObject[entities.get(0).getX() / 2 + 16][entities.get(0).getY() / 2] = 0;
                        }
                        if (listObject[entities.get(0).getX() / 2][entities.get(0).getY() / 2 + 16] == 6) {
                            maxBomb = 2;
                            stillObjects.removeIf(H -> H instanceof BombItem);
                            listObject[entities.get(0).getX() / 2 + 16][entities.get(0).getY() / 2] = 0;
                        }
                        if (listObject[entities.get(0).getX() / 2][entities.get(0).getY() / 2 - 16] == 6) {
                            maxBomb = 2;
                            stillObjects.removeIf(H -> H instanceof BombItem);
                            listObject[entities.get(0).getX() / 2 + 16][entities.get(0).getY() / 2] = 0;
                        }
                        if (listObject[entities.get(0).getX() / 2 + 16][entities.get(0).getY() / 2] == 4) {
                            if(enemy.isEmpty()){
                                primaryStage.setScene(scene3);
                                primaryStage.show();
                            }
                        }
                        if (listObject[entities.get(0).getX() / 2 - 16][entities.get(0).getY() / 2] == 4) {
                            if(enemy.isEmpty()){
                                primaryStage.setScene(scene3);
                                primaryStage.show();
                            }
                        }
                        if (listObject[entities.get(0).getX() / 2][entities.get(0).getY() / 2 + 16] == 4) {
                            if(enemy.isEmpty()){
                                primaryStage.setScene(scene3);
                                primaryStage.show();
                            }
                        }
                        if (listObject[entities.get(0).getX() / 2][entities.get(0).getY() / 2 - 16] == 4) {
                            if(enemy.isEmpty()){
                                primaryStage.setScene(scene3);
                                primaryStage.show();
                            }
                        }

                    }

                }
            });
            if (endGame) {
                stage.setScene(scene2);
                stage.show();
            }
            checkEndGame(entities.get(0), enemy);
            if (endGame) {
                stage.setScene(scene2);
                stage.show();
            }
            scene.setOnKeyReleased(keyEvent -> {
                checkEndGame(entities.get(0), enemy);
                if (endGame) {
                    stage.setScene(scene2);
                    stage.show();
                }
                if (keyEvent.getCode() == KeyCode.W) {
                    wPressed = false;
                } else if (keyEvent.getCode() == KeyCode.S) {
                    sPressed = false;
                } else if (keyEvent.getCode() == KeyCode.A) {
                    aPressed = false;
                } else if (keyEvent.getCode() == KeyCode.D) {
                    dPressed = false;
                } else if (keyEvent.getCode() == KeyCode.X) {
                    xPressed = false;
                } else if (keyEvent.getCode() == KeyCode.J) {
                    jPressed = false;
                }
            });
            if (endGame) {
                stage.setScene(scene2);
                stage.show();
            }
            checkEndGame(entities.get(0), enemy);
            if (endGame) {
                stage.setScene(scene2);
                stage.show();
            }
        });

        // Them sceneMenu vao stages
        stage.setScene(scene1);
        stage.show();
        t1.play();
        AnimationTimer timer = new AnimationTimer() {


            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();

    }

    public void resetAll() {
        wPressed = false;
        sPressed = false;
        aPressed = false;
        dPressed = false;
        xPressed = false;
        flame = 0;
        powerUp = 1;
        powerDown = 1;
        powerLeft = 1;
        powerRight = 1;
        powerUpP = 1;
        powerDownP = 1;
        powerLeftP = 1;
        powerRightP = 1;
        powerUpE = 1;
        powerDownE = 1;
        powerLeftE = 1;
        powerRightE = 1;
        move = 64;
        maxBomb = 1;
        stillObjects.clear();
        enemy.clear();
        entities.clear();
        Bomb.clear();
        for (int i = 0; i < 496; i++) {
            for (int j = 0; j < 208; j++) {
                listObject[i][j] = 0;
            }
        }
    }

    //checkEndGame
    public void checkEndGame(Entity bomber, List<Entity> enemy) {
        if (!entities.isEmpty()) {
            if (listObject[entities.get(0).getX() / 2 + 12][entities.get(0).getY() / 2] == 1) {
                primaryBomber.setDeadAnimation(true);
            }
            if (listObject[entities.get(0).getX() / 2 - 12][entities.get(0).getY() / 2] == 1) {
                primaryBomber.setDeadAnimation(true);
            }
            if (listObject[entities.get(0).getX() / 2][entities.get(0).getY() / 2 + 12] == 1) {
                primaryBomber.setDeadAnimation(true);
            }
            if (listObject[entities.get(0).getX() / 2][entities.get(0).getY() / 2 - 12] == 1) {
                primaryBomber.setDeadAnimation(true);
            }
            for (Entity e : enemy) {
                if (bomber.getX() + 28 >= e.getX() && bomber.getX() < e.getX() && bomber.getY() == e.getY()) {
                    primaryBomber.setDeadAnimation(true);
                }
                if (bomber.getX() - 28 <= e.getX() && bomber.getX() > e.getX() && bomber.getY() == e.getY()) {
                    primaryBomber.setDeadAnimation(true);
                }
                if (bomber.getY() + 28 >= e.getY() && bomber.getY() < e.getY() && bomber.getX() == e.getX()) {
                    primaryBomber.setDeadAnimation(true);
                }
                if (bomber.getY() - 28 <= e.getY() && bomber.getY() > e.getY() && bomber.getX() == e.getX()) {
                    primaryBomber.setDeadAnimation(true);
                }
                if (bomber.getX() >= e.getX() && bomber.getY() >= e.getY() && bomber.getX() - 28 <= e.getX() && bomber.getY() - 28 <= e.getY()) {
                    primaryBomber.setDeadAnimation(true);
                }
                if (bomber.getX() <= e.getX() && bomber.getY() >= e.getY() && bomber.getX() + 28 >= e.getX() && bomber.getY() - 28 <= e.getY()) {
                    primaryBomber.setDeadAnimation(true);
                }

                if (bomber.getX() >= e.getX() && bomber.getY() <= e.getY() && bomber.getX() - 28 <= e.getX() && bomber.getY() + 28 >= e.getY()) {
                    primaryBomber.setDeadAnimation(true);
                }
                if (bomber.getX() <= e.getY() && bomber.getY() <= e.getY() && bomber.getX() + 28 >= e.getX() && bomber.getY() + 28 >= e.getY()) {
                    primaryBomber.setDeadAnimation(true);
                }
            }

        }
    }

    public void createMap2(String mapSrc) {
        try {
            File url = new File("");
            File file = new File(url.getAbsolutePath() + "\\bomberman-starter-starter-2\\res\\levels\\" + mapSrc);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String s = bufferedReader.readLine();
            String[] parts = s.split(" ");
            int r = Integer.parseInt(parts[1]);
            int c = Integer.parseInt(parts[2]);
            for (int i = 0; i < c * 16; i++) {
                for (int j = 0; j < r * 16; j++) {
                    listObject[i][j] = 0;
                }
            }
            System.out.println(r);
            System.out.println(c);
            for (int j = 0; j < r; j++) {
                s = bufferedReader.readLine();
                for (int i = 0; i < c; i++) {
                    switch (s.charAt(i)) {
                        case '#':
                            stillObjects.add(new Wall(i, j, Sprite.wall.getFxImage()));
                            checkMap[i][j] = false;
                            break;
                        case '*':
                            stillObjects.add(new Grass(i, j, Sprite.grass.getFxImage()));
                            checkMap[i][j] = true;
                            listObject[i * 16][j * 16] = 0;
                            stillObjects.add(new Brick(i, j, Sprite.brick.getFxImage()));
                            checkMap[i][j] = false;
                            break;
                        case 'x':
                            stillObjects.add(new Grass(i, j, Sprite.grass.getFxImage()));
                            checkMap[i][j] = true;
                            listObject[i * 16][j * 16] = 0;
                            stillObjects.add(new Portal(i, j, Sprite.portal.getFxImage()));
                            checkMap[i][j] = true;
                            listObject[i * 16][j * 16] = 4;
                            break;
                        case 'p':
                            Entity bomber = new Bomber(i, j, Sprite.player_left.getFxImage());
                            primaryBomber = bomber;
                            stillObjects.add(new Grass(i, j, Sprite.grass.getFxImage()));
                            checkMap[i][j] = true;
                            listObject[i * 16][j * 16] = 0;
                            entities.add(bomber);
                            checkMap[i][j] = true;
                            break;
                        case '1':
                            stillObjects.add(new Grass(i, j, Sprite.grass.getFxImage()));
                            checkMap[i][j] = true;
                            listObject[i * 16][j * 16] = 0;
                            enemy.add(new Balloom(i, j, Sprite.balloom_left1.getFxImage()));
                            checkMap[i][j] = true;
                            listObject[i * 16][j * 16] = 1;
                            break;
                        case '2':
                            stillObjects.add(new Grass(i, j, Sprite.grass.getFxImage()));
                            checkMap[i][j] = true;
                            listObject[i * 16][j * 16] = 0;
                            enemy.add(new Oneal(i, j, Sprite.oneal_right1.getFxImage()));
                            checkMap[i][j] = true;
                            listObject[i * 16][j * 16] = 1;
                            break;
                        case 'b':
                            stillObjects.add(new Grass(i, j, Sprite.grass.getFxImage()));
                            checkMap[i][j] = true;
                            listObject[i * 16][j * 16] = 0;
                            stillObjects.add(new BombItem(i, j, Sprite.powerup_bombs.getFxImage()));
                            checkMap[i][j] = true;
                            listObject[i * 16][j * 16] = 6;
                            break;
                        case 'f':
                            stillObjects.add(new Grass(i, j, Sprite.grass.getFxImage()));
                            checkMap[i][j] = true;
                            listObject[i * 16][j * 16] = 0;
                            stillObjects.add(new FlameItem(i, j, Sprite.powerup_flames.getFxImage()));
                            checkMap[i][j] = true;
                            listObject[i * 16][j * 16] = 3;
                            break;
                        case 's':
                            stillObjects.add(new Grass(i, j, Sprite.grass.getFxImage()));
                            checkMap[i][j] = true;
                            listObject[i * 16][j * 16] = 0;
                            stillObjects.add(new speedItem(i, j, Sprite.powerup_speed.getFxImage()));
                            checkMap[i][j] = true;
                            listObject[i * 16][j * 16] = 2;
                            break;
                    }
                    if (s.charAt(i) != '#' && s.charAt(i) != '*' && s.charAt(i) != 'x' && s.charAt(i) != 'p' && s.charAt(i) != '1' && s.charAt(i) != '2' && s.charAt(i) != 'b' && s.charAt(i) != 'f' && s.charAt(i) != 's') {
                        stillObjects.add(new Grass(i, j, Sprite.grass.getFxImage()));
                        checkMap[i][j] = true;
                        listObject[i * 16][j * 16] = 0;
                    }
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public double distance(double x, double y) {
        double bomberX = Math.round(primaryBomber.getX() / 32);
        double bomberY = Math.round(primaryBomber.getY() / 32);
        double dt = Math.sqrt(Math.pow(x - bomberX, 2) + Math.pow(y - bomberY, 2));
        return dt;
    }

    public void findDirection(Entity e) {
        if(!e.getDeadAnimation() && e.getIsAlive()) {
            boolean left = false;
            boolean right = false;
            boolean up = false;
            boolean down = false;
            double minDistance = 999;
            double priDistance = distance(e.getX() / 32, e.getY() / 32);
            if (e.getX() % 32 == 0 && e.getY() % 32 == 0 && (checkMap[e.getX() / 32 + 1][e.getY() / 32]) && ((!checkMap[e.getX() / 32 - 1][e.getY() / 32] /*&& listObject[e.getX() / 2 - 16][e.getY() / 2] != 1*/) || distance(((e.getX() / 32) + 32) / 32, (e.getY() / 32)) <= distance(((e.getX() / 32) - 32) / 32, (e.getY() / 32)))) {
                e.setCheckLeft(false);
                e.setCheckRight(true);
            } else if (e.getX() % 32 == 0 && e.getY() % 32 == 0 && checkMap[e.getX() / 32 + 1][e.getY() / 32] && checkMap[e.getX() / 32 + 1][e.getY() / 32]) {
                if (distance(((e.getX() / 32) - 32) / 32, (e.getY() / 32)) < distance(((e.getX() / 32) + 32) / 32, (e.getY() / 32))) {
                    e.setCheckLeft(true);
                    e.setCheckRight(false);
                } else {
                    e.setCheckRight(true);
                    e.setCheckLeft(false);
                }
            } else if (e.getX() % 32 == 0 && e.getY() % 32 == 0 && (checkMap[e.getX() / 32 - 1][e.getY() / 32]) && ((!checkMap[e.getX() / 32 + 1][e.getY() / 32] /*&& listObject[e.getX() / 2 + 16][e.getY() / 2] != 1*/) || distance(((e.getX() / 32) - 32) / 32, (e.getY() / 32)) <= distance(((e.getX() / 32) + 32) / 32, (e.getY() / 32)))) {
                e.setCheckRight(false);
                e.setCheckLeft(true);
            } else if (e.getX() % 32 == 0 && e.getY() % 32 == 0 && !checkMap[e.getX() / 32 + 1][e.getY() / 32] && !checkMap[e.getX() / 32 - 1][e.getY() / 32]) {
                e.setCheckRight(false);
                e.setCheckLeft(false);
            }
            if (e.getX() % 32 == 0 && e.getY() % 32 == 0 && (checkMap[e.getX() / 32][e.getY() / 32 - 1]) && (!checkMap[e.getX() / 32][e.getY() / 32 + 1] || distance(e.getX() / 32, ((e.getY() / 32) - 32) / 32) <= distance(e.getX() / 32, ((e.getY() / 32) + 32) / 32))) {
                e.setCheckUp(true);
                e.setCheckDown(false);
            }
            if (e.getX() % 32 == 0 && e.getY() % 32 == 0 && checkMap[e.getX() / 32][e.getY() / 32 + 1] && checkMap[e.getX() / 32][e.getY() / 32 - 1]) {
                if (distance(((e.getX() / 32)) / 32, ((e.getY() - 32) / 32)) < distance(((e.getX() / 32)) / 32, ((e.getY() + 32) / 32))) {
                    e.setCheckUp(true);
                    e.setCheckDown(false);
                } else {
                    e.setCheckUp(false);
                    e.setCheckDown(true);
                }
            } else if (e.getX() % 32 == 0 && e.getY() % 32 == 0 && (checkMap[e.getX() / 32][e.getY() / 32 + 1]) && (!checkMap[e.getX() / 32][e.getY() / 32 - 1] || distance(e.getX() / 32, ((e.getY() / 32) + 32) / 32) <= distance(e.getX() / 32, ((e.getY() / 32) - 32) / 32))) {
                e.setCheckUp(false);
                e.setCheckDown(true);
            } else if (e.getX() % 32 == 0 && e.getY() % 32 == 0 && !checkMap[e.getX() / 32][e.getY() / 32 - 1] && !checkMap[e.getX() / 32][e.getY() / 32 + 1]) {
                e.setCheckUp(false);
                e.setCheckDown(false);
            }
            if (e.getX() % 32 == 0 && e.getY() % 32 == 0) {
                if (e.isCheckLeft() && e.isCheckUp()) {
                    System.out.println(1);
                    if (distance(e.getX() / 32 - 1, e.getY() / 32) < distance(e.getX() / 32, e.getY() / 32 - 1)) {
                        e.setCheckLeft(true);
                        e.setCheckUp(false);
                    } else {
                        e.setCheckUp(true);
                        e.setCheckLeft(false);
                    }
                }
                if (e.isCheckLeft() && e.isCheckDown()) {
                    System.out.println(2);
                    if (distance(e.getX() / 32 - 1, e.getY() / 32) < distance(e.getX() / 32, e.getY() / 32 + 1)) {
                        e.setCheckLeft(true);
                        e.setCheckDown(false);
                    } else {
                        e.setCheckDown(true);
                        e.setCheckLeft(false);
                    }
                }
                if (e.isCheckRight() && e.isCheckUp()) {
                    System.out.println(3);
                    if (distance(e.getX() / 32 + 1, e.getY() / 32) < distance(e.getX() / 32, e.getY() / 32 - 1)) {
                        e.setCheckRight(true);
                        e.setCheckUp(false);
                    } else {
                        e.setCheckUp(true);
                        e.setCheckRight(false);
                    }
                }
                if (e.isCheckRight() && e.isCheckDown()) {
                    System.out.println(4);
                    if (distance(e.getX() / 32 + 1, e.getY() / 32) < distance(e.getX() / 32, e.getY() / 32 + 1)) {
                        e.setCheckRight(true);
                        e.setCheckDown(false);
                    } else {
                        e.setCheckDown(true);
                        e.setCheckRight(false);
                    }
                }
            }
            if (e.isCheckLeft() && checkMoveLeft(e)) {
                moveLeftForEnemy(e);
                timeAnimation = (timeAnimation > MAX_TIME_ANIMATION) ? 0 : timeAnimation + 1;
                e.setImg((Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, timeAnimation, timeRunAnimation)).getFxImage());
                //findDirection(e);
            }
            if (e.isCheckRight() && checkMoveRight(e)) {
                moveRightForEnemy(e);
                timeAnimation = (timeAnimation > MAX_TIME_ANIMATION) ? 0 : timeAnimation + 1;
                e.setImg((Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, timeAnimation, timeRunAnimation)).getFxImage());
                // findDirection(e);
            }
            if (e.isCheckUp() && checkMoveUp(e)) {
                moveUpForEnemy(e);
                timeAnimation = (timeAnimation > MAX_TIME_ANIMATION) ? 0 : timeAnimation + 1;
                e.setImg((Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, timeAnimation, timeRunAnimation)).getFxImage());
                //findDirection(e);
            }
            if (e.isCheckDown() && checkMoveDown(e)) {
                moveDownForEnemy(e);
                timeAnimation = (timeAnimation > MAX_TIME_ANIMATION) ? 0 : timeAnimation + 1;
                e.setImg((Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, timeAnimation, timeRunAnimation)).getFxImage());
                //findDirection(e);
            }
        }
        else{
            e.setDeadAnimation(true);
            e.update();
            updateEnemy(e);
        }
    }

    //moveForBomber
    public void setMoveBomber(Entity bomber) {
        if (wPressed && checkMoveUp(bomber) == true) {
            bomber.setX(Math.round(bomber.getX() / 32) * 32);
            bomber.setY(bomber.getY() - move / 16);
            timeAnimation = (timeAnimation > MAX_TIME_ANIMATION) ? 0 : timeAnimation + 1;
            bomber.setImg((Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, timeAnimation, timeRunAnimation)).getFxImage());


        } else if (sPressed && checkMoveDown(bomber)) {
            bomber.setX(Math.round(bomber.getX() / 32) * 32);
            bomber.setY(bomber.getY() + move / 16);
            timeAnimation = (timeAnimation > MAX_TIME_ANIMATION) ? 0 : timeAnimation + 1;
            bomber.setImg((Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, timeAnimation, timeRunAnimation)).getFxImage());

        } else if (aPressed && checkMoveLeft(bomber)) {
            bomber.setY(Math.round(bomber.getY() / 32) * 32);
            bomber.setX(bomber.getX() - move / 16);
            timeAnimation = (timeAnimation > MAX_TIME_ANIMATION) ? 0 : timeAnimation + 1;
            bomber.setImg((Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, timeAnimation, timeRunAnimation)).getFxImage());

        } else if (dPressed && checkMoveRight(bomber)) {
            bomber.setY(Math.round(bomber.getY() / 32) * 32);
            bomber.setX(bomber.getX() + move / 16);
            timeAnimation = (timeAnimation > MAX_TIME_ANIMATION) ? 0 : timeAnimation + 1;
            bomber.setImg((Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, timeAnimation, timeRunAnimation)).getFxImage());

        }
    }

    public void setBomb() {
        if (xPressed && Bomb.size() <= maxBomb - 1) {
            Entity bomb = new Bomb((int) (Math.ceil(primaryBomber.getX() / 32)), (int) (Math.ceil(primaryBomber.getY() / 32)), Sprite.bomb.getFxImage());
            primaryBomb = bomb;
            Bomb.add(bomb);
            checkMap[bomb.getX() / 32][bomb.getY() / 32] = false;
            listObject[bomb.getX() / 16][bomb.getY() / 16] = 5;
            primaryBomb.setAlive(true);
        }

    }

    public void updateBrick() {
        for (Entity e : stillObjects) {
            if (e instanceof Brick) {
                e.update();
                if (!e.getIsAlive()) {
                    stillObjects.remove(e);
                }
            }
        }
    }

    public void updateEndgame() {
        if (endGame) {
            primaryStage.setScene(sceneGameOver);
            primaryStage.show();
        }
    }

    public void upDateBomber() {
        if (primaryBomber != null) {
            primaryBomber.update();
            if (!primaryBomber.getIsAlive()) {
                endGame = true;
            }
        }
    }

    public void exploreBomb() {
        int i = 0;
        int j = 0;
        int up = powerUp;
        int down = powerDown;
        int left = powerLeft;
        int right = powerRight;
        if (jPressed) {
            i = Bomb.get(0).getX() / 32;
            j = Bomb.get(0).getY() / 32;
            checkMap[i][j] = true;
            listObject[i * 16][j * 16] = 0;


            if (powerUpP == 2) {
                for (Entity e : stillObjects) {
                    if (e instanceof Brick || e instanceof Wall) {
                        if (e.getX() / 32 == i && (e.getY() / 32) + 1 == j) {
                            powerUpP = 1;
                        }
                    }
                }
            }
            if (powerDownP == 2) {
                for (Entity e : stillObjects) {
                    if (e instanceof Wall || e instanceof Brick) {
                        if (e.getX() / 32 == i && (e.getY() / 32) - 1 == j) {
                            powerDownP = 1;
                        }
                    }
                }
            }
            if (powerLeftP == 2) {
                for (Entity e : stillObjects) {
                    if (e instanceof Wall || e instanceof Brick) {
                        if ((e.getX() / 32) + 1 == i && e.getY() / 32 == j) {
                            powerLeftP = 1;
                        }
                    }
                }
            }
            if (powerRightP == 2) {
                for (Entity e : stillObjects) {
                    if (e instanceof Brick || e instanceof Wall) {
                        if ((e.getX() / 32) - 1 == i && e.getY() / 32 == j) {
                            powerRightP = 1;
                        }
                    }
                }
            }
            if (i * 32 + 32 * (1 + powerRightP) > primaryBomber.getX() && j * 32 == primaryBomber.getY() && i * 32 < primaryBomber.getX()) {
                primaryBomber.setDeadAnimation(true);
            }
            if (i * 32 - 32 * (1 + powerLeftP) < primaryBomber.getX() && j * 32 == primaryBomber.getY() && i * 32 > primaryBomber.getX()) {
                primaryBomber.setDeadAnimation(true);
            }
            if (i * 32 == primaryBomber.getX() && j * 32 + 32 * (1 + powerDownP) > primaryBomber.getY() && j * 32 < primaryBomber.getY()) {
                primaryBomber.setDeadAnimation(true);
            }
            if (i * 32 == primaryBomber.getX() && j * 32 - 32 * (1 + powerUpP) < primaryBomber.getY() && j * 32 > primaryBomber.getY()) {
                primaryBomber.setDeadAnimation(true);
            }
            if (i * 32 < primaryBomber.getX() && j * 32 < primaryBomber.getY() && j * 32 + 32 > primaryBomber.getY() && i * 32 + 32 * (1 + powerRightP) > primaryBomber.getX()) {
                primaryBomber.setDeadAnimation(true);
            }
            if (i * 32 < primaryBomber.getX() && j * 32 > primaryBomber.getY() && j * 32 - 32 < primaryBomber.getY() && i * 32 + 32 * (1 + powerRightP) > primaryBomber.getX()) {
                primaryBomber.setDeadAnimation(true);
            }
            if (i * 32 < primaryBomber.getX() && j * 32 > primaryBomber.getY() && i * 32 + 32 > primaryBomber.getX() && j * 32 - 32 * (1 + powerUpP) < primaryBomber.getY()) {
                primaryBomber.setDeadAnimation(true);
            }
            if (i * 32 > primaryBomber.getX() && j * 32 > primaryBomber.getY() && i * 32 - 32 < primaryBomber.getX() && j * 32 - 32 * (1 + powerUpP) < primaryBomber.getY()) {
                primaryBomber.setDeadAnimation(true);
            }
            if (i * 32 > primaryBomber.getX() && j * 32 > primaryBomber.getY() && j * 32 - 32 < primaryBomber.getY() && i * 32 - 32 * (1 + powerLeftP) < primaryBomber.getX()) {
                primaryBomber.setDeadAnimation(true);
            }
            if (i * 32 > primaryBomber.getX() && j * 32 < primaryBomber.getY() && j * 32 + 32 > primaryBomber.getY() && i * 32 - 32 * (1 + powerLeftP) < primaryBomber.getX()) {
                primaryBomber.setDeadAnimation(true);
            }
            if (i * 32 > primaryBomber.getX() && j * 32 < primaryBomber.getY() && i * 32 - 32 < primaryBomber.getX() && j * 32 + 32 * (1 + powerDownP) > primaryBomber.getY()) {
                primaryBomber.setDeadAnimation(true);
            }
            if (i * 32 < primaryBomber.getX() && j * 32 < primaryBomber.getY() && i * 32 + 32 > primaryBomber.getX() && j * 32 + 32 * (1 + powerDownP) > primaryBomber.getY()) {
                primaryBomber.setDeadAnimation(true);
            }
            if (i * 32 == primaryBomber.getX() && j * 32 == primaryBomber.getY()) {
                primaryBomber.setDeadAnimation(true);
            }
            powerUpP = up;
            powerDownP = down;
            powerLeftP = left;
            powerRightP = right;
            if (powerUpE == 2) {
                for (Entity e : stillObjects) {
                    if (e instanceof Brick || e instanceof Wall) {
                        if (e.getX() / 32 == i && (e.getY() / 32) + 1 == j) {
                            powerUpE = 1;
                            System.out.println(powerUpE);
                        }
                    }
                }
            }
            if (powerDownE == 2) {
                for (Entity e : stillObjects) {
                    if (e instanceof Wall || e instanceof Brick) {
                        if (e.getX() / 32 == i && (e.getY() / 32) - 1 == j) {
                            powerDownE = 1;
                            System.out.println(powerDownE);
                        }
                    }
                }
            }
            if (powerLeftE == 2) {
                for (Entity e : stillObjects) {
                    if (e instanceof Wall || e instanceof Brick) {
                        if ((e.getX() / 32) + 1 == i && e.getY() / 32 == j) {
                            powerLeftE = 1;
                        }
                    }
                }
            }
            if (powerRightE == 2) {
                for (Entity e : stillObjects) {
                    if (e instanceof Brick || e instanceof Wall) {
                        if ((e.getX() / 32) - 1 == i && e.getY() / 32 == j) {
                            powerRightE = 1;
                        }
                    }
                }
            }
            for (Entity e : enemy) {
                if (i * 32 + 32 * (1 + powerRightE) > e.getX() && j * 32 == e.getY() && i * 32 < e.getX()) {
                    e.setDeadAnimation(true);
                } else if (i * 32 - 32 * (1 + powerLeftE) < e.getX() && j * 32 == e.getY() && i * 32 > e.getX()) {
                    e.setDeadAnimation(true);
                } else if (i * 32 == e.getX() && j * 32 + 32 * (1 + powerDownE) > e.getY() && j * 32 < e.getY()) {
                    e.setDeadAnimation(true);
                } else if (i * 32 == e.getX() && j * 32 - 32 * (1 + powerUpE) < e.getY() && j * 32 > e.getY()) {
                    e.setDeadAnimation(true);
                } else if (i * 32 < e.getX() && j * 32 < e.getY() && j * 32 + 32 > e.getY() && i * 32 + 32 * (1 + powerRightE) > e.getX()) {
                    e.setDeadAnimation(true);
                } else if (i * 32 < e.getX() && j * 32 > e.getY() && j * 32 - 32 < e.getY() && i * 32 + 32 * (1 + powerRightE) > e.getX()) {
                    e.setDeadAnimation(true);
                } else if (i * 32 < e.getX() && j * 32 > e.getY() && i * 32 + 32 > e.getX() && j * 32 - 32 * (1 + powerUpE) < e.getY()) {
                    e.setDeadAnimation(true);
                } else if (i * 32 > e.getX() && j * 32 > e.getY() && i * 32 - 32 < e.getX() && j * 32 - 32 * (1 + powerUpE) < e.getY()) {
                    e.setDeadAnimation(true);
                } else if (i * 32 > e.getX() && j * 32 > e.getY() && j * 32 - 32 < e.getY() && i * 32 - 32 * (1 + powerLeftE) < e.getX()) {
                    e.setDeadAnimation(true);
                } else if (i * 32 > e.getX() && j * 32 < e.getY() && j * 32 + 32 > e.getY() && i * 32 - 32 * (1 + powerLeftE) < e.getX()) {
                    e.setDeadAnimation(true);
                } else if (i * 32 > e.getX() && j * 32 < e.getY() && i * 32 - 32 < e.getX() && j * 32 + 32 * (1 + powerDownE) > e.getY()) {
                    e.setDeadAnimation(true);
                } else if (i * 32 < e.getX() && j * 32 < e.getY() && i * 32 + 32 > e.getX() && j * 32 + 32 * (1 + powerDownE) > e.getY()) {
                    e.setDeadAnimation(true);
                }
            }
            powerUpE = up;
            powerDownE = down;
            powerLeftE = left;
            powerRightE = right;
            if (powerUp == 2) {
                for (Entity e : stillObjects) {
                    if (e instanceof Brick || e instanceof Wall) {
                        if (e.getX() / 32 == i && (e.getY() / 32) + 1 == j) {
                            powerUp = 1;
                        }
                    }
                }
            }
            if (powerDown == 2) {
                for (Entity e : stillObjects) {
                    if (e instanceof Wall || e instanceof Brick) {
                        if (e.getX() / 32 == i && (e.getY() / 32) - 1 == j) {
                            powerDown = 1;
                        }
                    }
                }
            }
            if (powerLeft == 2) {
                for (Entity e : stillObjects) {
                    if (e instanceof Wall || e instanceof Brick) {
                        if ((e.getX() / 32) + 1 == i && e.getY() / 32 == j) {
                            powerLeft = 1;
                        }
                    }
                }
            }
            if (powerRight == 2) {
                for (Entity e : stillObjects) {
                    if (e instanceof Brick || e instanceof Wall) {
                        if ((e.getX() / 32) - 1 == i && e.getY() / 32 == j) {
                            powerRight = 1;
                        }
                    }
                }
            }
            //stillObjects.removeIf(e -> e instanceof Brick && ((e.getX() / 32 == i + powerRight && e.getY() / 32 == j) || (e.getX() / 32 == i - powerLeft && e.getY() / 32 == j) || (e.getX() / 32 == i && e.getY() / 32 == j - powerUp) || (e.getX() / 32 == i && e.getY() / 32 == j + powerDown)));
            for (Entity f : stillObjects) {
                if (f instanceof Grass && ((f.getX() / 32 == i + powerRight && f.getY() / 32 == j) || (f.getX() / 32 == i - powerLeft && f.getY() / 32 == j) || (f.getX() / 32 == i && f.getY() / 32 == j - powerUp) || (f.getX() / 32 == i && f.getY() / 32 == j + powerDown) || f.getX() / 32 == i && f.getY() / 32 == j)) {
                    f.setDeadAnimation(true);

                }
            }
            for (Entity f : stillObjects) {
                if (f instanceof Grass) {
                    if (powerRight == 2) {
                        if (f.getX() / 32 == i + 1 && f.getY() / 32 == j) {
                            f.setDeadAnimation(true);
                        }
                    }
                    if (powerLeft == 2) {
                        if (f.getX() / 32 == i - 1 && f.getY() / 32 == j) {
                            f.setDeadAnimation(true);
                        }
                    }
                    if (powerUp == 2) {
                        if (f.getX() / 32 == i && f.getY() / 32 == j - 1) {
                            f.setDeadAnimation(true);
                        }
                    }
                    if (powerDown == 2) {
                        if (f.getX() / 32 == i && f.getY() / 32 == j + 1) {
                            f.setDeadAnimation(true);
                        }
                    }
                }
            }

            for (Entity f : stillObjects) {
                if (f instanceof Brick && ((f.getX() / 32 == i + powerRight && f.getY() / 32 == j) || (f.getX() / 32 == i - powerLeft && f.getY() / 32 == j) || (f.getX() / 32 == i && f.getY() / 32 == j - powerUp) || (f.getX() / 32 == i && f.getY() / 32 == j + powerDown))) {
                    f.setDeadAnimation(true);
                }
            }

            for (Entity e : stillObjects) {
                if (e instanceof Grass) {
                    if (e.getX() / 32 == i + powerRight && e.getY() / 32 == j) {
                        checkMap[e.getX() / 32][e.getY() / 32] = true;
                        listObject[e.getX() / 2][e.getY() / 2] = 0;
                    }
                    if (e.getX() / 32 == i - powerLeft && e.getY() / 32 == j) {
                        checkMap[e.getX() / 32][e.getY() / 32] = true;
                        listObject[e.getX() / 2][e.getY() / 2] = 0;
                    }
                    if (e.getX() / 32 == i && e.getY() / 32 == j - powerUp) {
                        checkMap[e.getX() / 32][e.getY() / 32] = true;
                        listObject[e.getX() / 2][e.getY() / 2] = 0;
                    }
                    if (e.getX() / 32 == i && e.getY() / 32 == j + powerDown) {
                        checkMap[e.getX() / 32][e.getY() / 32] = true;
                        listObject[e.getX() / 2][e.getY() / 2] = 0;
                    }

                }
            }
            powerUp = up;
            powerDown = down;
            powerLeft = left;
            powerRight = right;
            Bomb.remove(0);
            jPressed = false;
        }
        if (!Bomb.isEmpty() && i != 0 && j != 0) {
            primaryBomb = Bomb.get(0);
            if (((Bomb.get(0).getX() / 32 == i + powerRight && Bomb.get(0).getY() / 32 == j) || (Bomb.get(0).getX() / 32 == i - powerLeft && Bomb.get(0).getY() / 32 == j) || (Bomb.get(0).getX() / 32 == i && Bomb.get(0).getY() / 32 == j - powerUp) || (Bomb.get(0).getX() / 32 == i && Bomb.get(0).getY() / 32 == j + powerDown))) {
                jPressed = true;
            }
            if (((Bomb.get(0).getX() / 32 == i + 1 && Bomb.get(0).getY() / 32 == j) || (Bomb.get(0).getX() / 32 == i - 1 && Bomb.get(0).getY() / 32 == j) || (Bomb.get(0).getX() / 32 == i && Bomb.get(0).getY() / 32 == j - 1) || (Bomb.get(0).getX() / 32 == i && Bomb.get(0).getY() / 32 == j + 1))) {
                jPressed = true;
            }
            exploreBomb();
        }
    }


    public boolean checkMoveUp(Entity e) {
        if (((e.getY() % 32 == 0 && e.getX() % 32 <= 12) && (checkMap[Math.round((e.getX()) / 32)][(e.getY() / 32) - 1] == true)) || (e.getY() % 32 != 0 && e.getX() % 32 == 0)) {

            return true;
        }
        return false;

    }

    public boolean checkMoveDown(Entity e) {
        if (((e.getY() % 32 == 0 && e.getX() % 32 <= 12) && (checkMap[Math.round((e.getX()) / 32)][(e.getY() / 32) + 1] == true)) || (e.getY() % 32 != 0 && e.getX() % 32 == 0)) {
            return true;
        }
        return false;
    }

    public boolean checkMoveLeft(Entity e) {
        if (((e.getY() % 32 <= 12 && e.getX() % 32 == 0) && (checkMap[(e.getX() / 32) - 1][Math.round((e.getY() / 32))] == true)) || (e.getX() % 32 != 0 && e.getY() % 32 == 0)) {
            return true;
        }
        return false;
    }

    public boolean checkMoveRight(Entity e) {
        if (((e.getY() % 32 <= 12 && e.getX() % 32 == 0) && (checkMap[(e.getX() / 32) + 1][Math.round((e.getY() / 32))] == true)) || (e.getX() % 32 != 0 && e.getY() % 32 == 0)) {
            return true;
        }
        return false;
    }

    public void moveUpForEnemy(Entity e) {
        checkEndGame(entities.get(0), enemy);
        if (endGame) {
            update();
            primaryStage.setScene(sceneGameOver);
            primaryStage.show();
        }
        listObject[e.getX() / 2][e.getY() / 2] = 0;
        e.setY(e.getY() - 2);
        listObject[e.getX() / 2][e.getY() / 2] = 1;
    }

    public void moveDownForEnemy(Entity e) {
        checkEndGame(entities.get(0), enemy);
        if (endGame) {
            update();
            primaryStage.setScene(sceneGameOver);
            primaryStage.show();
        }
        listObject[e.getX() / 2][e.getY() / 2] = 0;
        e.setY(e.getY() + 2);
        listObject[e.getX() / 2][e.getY() / 2] = 1;
    }

    public void moveLeftForEnemy(Entity e) {
        checkEndGame(entities.get(0), enemy);
        if (endGame) {
            update();
            primaryStage.setScene(sceneGameOver);
            primaryStage.show();
        }
        listObject[e.getX() / 2][e.getY() / 2] = 0;
        e.setX(e.getX() - 2);
        listObject[e.getX() / 2][e.getY() / 2] = 1;
    }

    public void moveRightForEnemy(Entity e) {
        checkEndGame(entities.get(0), enemy);
        if (endGame) {
            update();
            primaryStage.setScene(sceneGameOver);
            primaryStage.show();
        }
        listObject[e.getX() / 2][e.getY() / 2] = 0;
        e.setX(e.getX() + 2);
        listObject[e.getX() / 2][e.getY() / 2] = 1;
    }

    public void updateEnemy(Entity e) {
        if (!e.getIsAlive()) {
            enemy.remove(e);
        }
    }

    public void setMoveEnemy(List<Entity> enemy) {
        if (!enemy.isEmpty()) {

            for (Entity e : enemy) {
                if (e instanceof Balloom ) {
                    if (e.getIsAlive() && !e.getDeadAnimation()) {

                        if (e.getX() % 32 == 0 && e.getY() % 32 == 0 && ((!checkMap[e.getX() / 32 - 1][e.getY() / 32] && listObject[e.getX() / 2 - 16][e.getY() / 2] != 1) )) {
                            e.setCheckLeft(false);
                            e.setCheckRight(true);
                        }
                        if (e.getX() % 32 == 0 && e.getY() % 32 == 0 && ((!checkMap[e.getX() / 32 + 1][e.getY() / 32] && listObject[e.getX() / 2 + 16][e.getY() / 2] != 1) )) {
                            e.setCheckRight(false);
                            e.setCheckLeft(true);
                        }
                        if (e.getX() % 32 == 0 && e.getY() % 32 == 0 && ((!checkMap[e.getX() / 32 + 1][e.getY() / 32]) && (!checkMap[e.getX() / 32 - 1][e.getY() / 32])) && (checkMap[e.getX() / 32][e.getY() / 32 - 1])) {
                            e.setCheckLeft(false);
                            e.setCheckRight(false);
                            e.setCheckUp(true);
                        }
                        if (e.getX() % 32 == 0 && e.getY() % 32 == 0 && ((!checkMap[e.getX() / 32 + 1][e.getY() / 32]) && (!checkMap[e.getX() / 32 - 1][e.getY() / 32])) && (!checkMap[e.getX() / 32][e.getY() / 32 - 1]) && (checkMap[e.getX() / 32][e.getY() / 32 + 1])) {
                            e.setCheckUp(false);
                            e.setCheckDown(true);
                            e.setCheckLeft(false);
                            e.setCheckRight(false);
                        }
                        if (e.getX() % 32 == 0 && e.getY() % 32 == 0 && ((!checkMap[e.getX() / 32 + 1][e.getY() / 32]) && (!checkMap[e.getX() / 32 - 1][e.getY() / 32])) && (!checkMap[e.getX() / 32][e.getY() / 32 - 1]) && (!checkMap[e.getX() / 32][e.getY() / 32 + 1])) {
                            e.setCheckUp(false);
                            e.setCheckDown(false);
                            e.setCheckLeft(false);
                            e.setCheckRight(false);
                        }
                        if (e.getIsAlive() && !e.getDeadAnimation()) {
                            if (!e.isCheckLeft() && e.isCheckRight()) {
                                if (e instanceof Balloom) {
                                    moveRightForEnemy(e);
                                    timeAnimation = (timeAnimation > MAX_TIME_ANIMATION) ? 0 : timeAnimation + 1;
                                    e.setImg((Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, timeAnimation, timeRunAnimation)).getFxImage());
                                }
                                if (e instanceof Oneal) {
                                    moveRightForEnemy(e);
                                    timeAnimation = (timeAnimation > MAX_TIME_ANIMATION) ? 0 : timeAnimation + 1;
                                    e.setImg((Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, timeAnimation, timeRunAnimation)).getFxImage());
                                }
                            }
                            if (e.isCheckLeft() && !e.isCheckRight()) {
                                if (e instanceof Balloom) {
                                    moveLeftForEnemy(e);
                                    timeAnimation = (timeAnimation > MAX_TIME_ANIMATION) ? 0 : timeAnimation + 1;
                                    e.setImg((Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, timeAnimation, timeRunAnimation)).getFxImage());
                                }
                                if (e instanceof Oneal) {
                                    moveLeftForEnemy(e);
                                    timeAnimation = (timeAnimation > MAX_TIME_ANIMATION) ? 0 : timeAnimation + 1;
                                    e.setImg((Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, timeAnimation, timeRunAnimation)).getFxImage());
                                }
                            }
                            if (e.isCheckLeft() && e.isCheckRight()) {
                                if (e instanceof Balloom) {
                                    moveLeftForEnemy(e);
                                    timeAnimation = (timeAnimation > MAX_TIME_ANIMATION) ? 0 : timeAnimation + 1;
                                    e.setImg((Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, timeAnimation, timeRunAnimation)).getFxImage());
                                }
                                if (e instanceof Oneal) {
                                    moveLeftForEnemy(e);
                                    timeAnimation = (timeAnimation > MAX_TIME_ANIMATION) ? 0 : timeAnimation + 1;
                                    e.setImg((Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, timeAnimation, timeRunAnimation)).getFxImage());
                                }
                            }
                            if (!e.isCheckLeft() && !e.isCheckRight() && e.isCheckUp()) {
                                if (e instanceof Balloom) {
                                    moveUpForEnemy(e);
                                    timeAnimation = (timeAnimation > MAX_TIME_ANIMATION) ? 0 : timeAnimation + 1;
                                    e.setImg((Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, timeAnimation, timeRunAnimation)).getFxImage());
                                }
                                if (e instanceof Oneal) {
                                    moveUpForEnemy(e);
                                    timeAnimation = (timeAnimation > MAX_TIME_ANIMATION) ? 0 : timeAnimation + 1;
                                    e.setImg((Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, timeAnimation, timeRunAnimation)).getFxImage());
                                }
                            }
                            if (!e.isCheckLeft() && !e.isCheckRight() && !e.isCheckUp() && e.isCheckDown()) {
                                if (e instanceof Balloom) {
                                    moveDownForEnemy(e);
                                    timeAnimation = (timeAnimation > MAX_TIME_ANIMATION) ? 0 : timeAnimation + 1;
                                    e.setImg((Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, timeAnimation, timeRunAnimation)).getFxImage());
                                }
                                if (e instanceof Oneal) {
                                    moveDownForEnemy(e);
                                    timeAnimation = (timeAnimation > MAX_TIME_ANIMATION) ? 0 : timeAnimation + 1;
                                    e.setImg((Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, timeAnimation, timeRunAnimation)).getFxImage());
                                }
                            }
                        }
                    } else {
                        e.update();
                        listObject[e.getX() / 2][e.getY() / 2] = 0;
                        updateEnemy(e);
                    }

                }
            }
        }
    }

    public void upDateGrass() {
        for (Entity e : stillObjects) {
            if (e instanceof Grass) {
                e.update();
            }
        }
        updateEndgame();
    }

    public void update() {
        entities.forEach(Entity::update);
        enemy.forEach(Entity::update);
        Bomb.forEach(Entity::update);
        if (Bomb.size() != 0 && Bomb.get(0).getIsAlive() == false) {
            jPressed = true;
            exploreBomb();
            if (endGame) {
                primaryStage.setScene(sceneGameOver);
                primaryStage.show();
            }
        }
        upDateGrass();
        updateBrick();
        upDateBomber();
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
        Bomb.forEach(g -> g.render(gc));
        enemy.forEach(g -> g.render(gc));
        if (!endGame) {
            setMoveEnemy(enemy);
            for( Entity e : enemy){
                if( e instanceof Oneal) {
                    findDirection(e);
                }
            }
        }
    }
}
