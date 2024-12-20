package game.snake.controllers;

import game.snake.App;
import game.snake.entities.Entity;
import game.snake.entities.Food;
import game.snake.entities.Player;
import game.snake.game.*;
import game.snake.game.Settings;
import game.snake.ui.util.Controller;
import game.snake.utility.ObjectCache;
import game.snake.utility.Vector2;
import game.snake.utility.color.Color;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Glow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class Snake extends Controller {
    private static final HashMap<KeyCode, Vector2> DIRECTIONS = new HashMap<>();
    private static final HashMap<String, AudioClip> CLIPS = new HashMap<>();

    private Thread run;
    private long startMem;
    private final AtomicReference<Grid<Entity>> grid = new AtomicReference<>();
    private final AtomicLong startTime = new AtomicLong(), currentTime = new AtomicLong(), previousTime = new AtomicLong();
    private final AtomicBoolean running = new AtomicBoolean(false), singlePlayer = new AtomicBoolean(true);
    private final CopyOnWriteArrayList<Entity> ENTITIES = new CopyOnWriteArrayList<>();

    private final ObjectCache<Rectangle> links = new ObjectCache<>(Rectangle::new);
    private final ObjectCache<Rectangle> fruits = new ObjectCache<>(Rectangle::new);
    private Painting background, effects;
    private World world, garden;

    @FXML private HBox bar;
    @FXML private Canvas bckgrnd, effcts;
    @FXML private AnchorPane snks, farm;
    @FXML private Label lengthLabel, fruitLabel, speedLabel, effectLabel, timeLabel;

    // -- Initialization -- \\
    /**
     * Finds a {@link URL} from {@code filePath}
     * @param filePath the path to the file
     * @return a {@link URL} originating at {@code filePath}
     * @throws NullPointerException if {@code filePath} is {@code null}
     */
    private URL getResource(String filePath) {
        return App.class.getResource(filePath);
    }

    @Override public void initialize() {
        startMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        DIRECTIONS.put(KeyCode.W, Vector2.Y_AXIS.mul(-1));
        DIRECTIONS.put(KeyCode.A, Vector2.X_AXIS.mul(-1));
        DIRECTIONS.put(KeyCode.S, Vector2.Y_AXIS);
        DIRECTIONS.put(KeyCode.D, Vector2.X_AXIS);

        CLIPS.put("Coin", new AudioClip(getResource("audio/Coin.wav").toExternalForm()));
        CLIPS.put("PowerUp", new AudioClip(getResource("audio/PowerUp.wav").toExternalForm()));
        CLIPS.put("PickUp", new AudioClip(getResource("audio/PickUp.wav").toExternalForm()));

        Platform.runLater(() -> {
            double size = bar.getScene().getHeight() - bar.getHeight();
            bar.getScene().getWindow().setWidth(size);

            background = new Painting(bckgrnd, size);
            effects = new Painting(effcts, size);

            world = new World(snks);
            garden = new World(farm);

            links.setOnRetrieve((link) -> {
                double paneSize = world.getPaneSize();

                link.setX(-paneSize);
                link.setY(-paneSize);

                switch (Settings.getGraphicsLevel()) {
                    case 3:
                    case 2:
                    case 1:
                        link.setWidth(paneSize-4);
                        link.setHeight(paneSize-4);
                        break;
                    default:
                        link.setWidth(paneSize);
                        link.setHeight(paneSize);
                }
            });
            links.setOnReturn((link) -> {
                double paneSize = world.getPaneSize();

                link.setX(-paneSize);
                link.setY(-paneSize);
            });

            fruits.setOnRetrieve((fruit) -> {
                double paneSize = garden.getPaneSize();

                fruit.setX(-paneSize);
                fruit.setY(-paneSize);

                switch (Settings.getGraphicsLevel()) {
                    case 3:
                    case 2:
                        //fruit.setRadius(garden.getPaneSize() / 2);
                        fruit.setArcWidth(paneSize);
                        fruit.setArcHeight(paneSize);
                    case 1:
                        fruit.setWidth(paneSize-1);
                        fruit.setHeight(paneSize-1);
                        break;
                    default:
                        fruit.setWidth(paneSize);
                        fruit.setHeight(paneSize);
                        //fruit.setRadius(garden.getPaneSize() / 2);
                }
            });
            fruits.setOnReturn((fruit) -> {
                double paneSize = garden.getPaneSize();

                fruit.setX(-(paneSize+5));
                fruit.setY(-(paneSize+5));

                /*double radius = (paneSize / 2);

                fruit.setCenterX(-(paneSize + radius));
                fruit.setCenterY(-(paneSize + radius));*/
            });

            background.setBackground(Settings.getBackgroundColor());

            startGame();
            bar.getScene().getRoot().requestFocus();
        });
    }

    @Override public void onCloseRequest() {
        if (run != null && run.isAlive()) {run.interrupt();}
    }

    @FXML private void action(KeyEvent event) {
        if (!running.get() || !"WASD".contains(event.getCode().getChar())) {return;}

        Player player = (Player)ENTITIES.get("WASD".contains(event.getCode().getChar()) ? 0 : 1);
        Vector2 direction = DIRECTIONS.get(event.getCode());

        if (player.getLastDirection().dot(direction) >= 0) {player.setDirection(direction, links.getObject());}

        event.consume();
    }

    @FXML private void restart(MouseEvent event) {
        if (!running.get()) {startGame();}
        event.consume();
    }

    private void addPlayers() {
        Player playerOne = new Player();
        playerOne.setColors(Settings.getPlayerOneColor());
        //playerOne.setColors(Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.INDIGO, Color.VIOLET);

        playerOne.setPosition(Settings.START_POS);
        ENTITIES.add(playerOne);

        if (!singlePlayer.get()) {
            Player playerTwo = new Player();
            playerTwo.setDirection(Settings.START_DIRECTION.equals(Vector2.X_AXIS) ? Vector2.X_AXIS.mul(-1) : Settings.START_DIRECTION);
            playerTwo.setColors(Settings.getPlayerTwoColor());
            ENTITIES.add(playerTwo);
        }
    }

    private void startGame() {
        if (!running.get()) {
            int gridSize = Settings.getGridSize();

            grid.set(new Grid<>(gridSize));
            singlePlayer.set(Settings.getSinglePlayer());

            background.clear();
            effects.clear();
            ENTITIES.clear();

            world.clear();
            garden.clear();

            background.setBackground(Settings.getBackgroundColor());
            effects.setEffect(null);

            long time = new Date().getTime();
            run = new Thread(() -> {
                long count = 0;
                while (running.get()) {
                    long elapsedTime = (new Date().getTime() - startTime.get())/25;

                    if (elapsedTime > count) {
                        count = elapsedTime;
                        updateFrame();
                    }

                    try {Thread.sleep(25);} catch (InterruptedException e) {
                        running.set(false);
                        Thread.currentThread().interrupt();
                    }
                }
            });

            Platform.runLater(() -> {
                links.setOnCreation((link) -> {
                    double paneSize = world.getPaneSize();

                    link.setX(-paneSize);
                    link.setY(-paneSize);

                    world.add(link);
                });
                fruits.setOnCreation((fruit) -> {
                    double paneSize = garden.getPaneSize();

                    fruit.setX(-(paneSize+5));
                    fruit.setY(-(paneSize+5));

                    garden.add(fruit);
                });

                addPlayers();
                spawnFruit();

                previousTime.set(time);
                startTime.set(time);
                running.set(true);
                run.start();
            });
        }
    }

    private void stopGame() {
        if (running.get()) {running.set(false);}
        ENTITIES.clear();

        Bloom bloom = new Bloom();
        bloom.setThreshold(0.5);
        bloom.setInput(new Glow(0.5));

        effects.setEffect(bloom);
        effects.drawText("GAME OVER");
    }

    private void playSound(String audioClip) {
        AudioClip clip = CLIPS.get(audioClip);
        if (clip != null) {Platform.runLater(() -> clip.play());}
    }

    private void adjustTail(game.snake.entities.Snake snake) {
        double paneSize = world.getPaneSize();

        while (snake.getTailSize() > snake.getHealth()) {
            Rectangle link = snake.shrinkTail();
            Vector2 position = snake.getPath().remove().getKey();

            if (snake.isGhosted(position)) {
                snake.renew(position);
            } else {
                grid.get().removeItem((int)position.Y, (int)position.X);
                links.returnObject(link);
            }
        }

        while (snake.getTailSize() < snake.getHealth()) {
            Rectangle link = links.getObject();
            snake.growTail(link);
        }
    }

    private void spawnFruit() {
        int x = (int)(Math.random() * Settings.getGridSize());
        int y = (int)(Math.random() * Settings.getGridSize());

        while (grid.get().isOccupied(y, x)) {
            x = (int)(Math.random() * Settings.getGridSize());
            y = (int)(Math.random() * Settings.getGridSize());
        }

        PowerUp powerUp = (Math.random() <= Settings.getPowerUpChance()) ? new PowerUp() : null;

        Food fruit = new Food((powerUp != null) ? (powerUp.getExtraHealth() == -1) ? 0 : powerUp.getExtraHealth() : (int)(Math.random() * 5) + 1);
        fruit.setPower(powerUp);

        fruit.setPosition(new Vector2(x, y));
        fruit.setColors((powerUp != null) ? powerUp.color() : new Color(200, 0, 0));

        grid.get().addItem(fruit, y, x);
        ENTITIES.add(fruit);
    }

    private int loopNum(int num, int limit) {
        return (num < 0) ? limit + (num % limit) : (num % limit);
    }

    private void updateStats() {
        if (ENTITIES.size() == 0) {return;}
        //long currentMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();String.format("Memory: %f",(double)(currentMem - startMem)/1024)

        Platform.runLater(() -> {
            Player playerOne = (Player)ENTITIES.get(0);
            String effectName = playerOne.getEffectName();

            lengthLabel.setText(String.format("Length: %d", playerOne.getHealth()));
            speedLabel.setText(String.format("Speed: %d", playerOne.getSpeed()));
            fruitLabel.setText(String.format("Fruit: %d", playerOne.getEaten()));
            effectLabel.setText(String.format("Effect: %s %s", effectName, effectName.equals("None") ? "" : "| "+playerOne.getEffectTime()));
            timeLabel.setText(String.format("Time: %s", (new Date().getTime() - startTime.get())/1000f));
        });
    }

    private void stripGrid(Queue<Pair<Vector2,Vector2>> path, int health) {
        while (path.size() > health) {
            Vector2 position = path.remove().getKey();
            grid.get().removeItem((int)position.Y, (int)position.X);
        }
    }

    private void updateFrame() {
        currentTime.set(new Date().getTime());
        double deltaTime = (currentTime.get() - previousTime.get())/1000d;

        //if (ENTITIES.size() > (singlePlayer.get() ? 1 : 2)) {fruits.clear();}

        for (int i=ENTITIES.size()-1; i>=0; i--) {
            if (!running.get()) {ENTITIES.clear(); return;}
            Entity entity = ENTITIES.get(i);

            if (entity instanceof game.snake.entities.Snake) {
                game.snake.entities.Snake snake = (game.snake.entities.Snake)entity;

                Vector2 direction = snake.getDirection();
                Vector2 position = snake.getPosition();
                Vector2 targetPos = position.add(direction.mul(snake.getSpeed() * deltaTime));

                if (snake.isActive()) {snake.nullify();}

                int gridSize = grid.get().getSize();
                int x = (int)targetPos.X, y = (int)targetPos.Y;
                boolean exceedsBounds = (x < 0 || y < 0 || x >= gridSize || y >= gridSize);

                if (snake.canLoop()) {
                    if (exceedsBounds) {
                        double xFraction = (x < 0) ? (1 - Math.abs(targetPos.X % 1)) : (targetPos.X % 1);
                        double yFraction = (y < 0) ? (1 - Math.abs(targetPos.Y % 1)) : (targetPos.Y % 1);

                        x = loopNum(x, gridSize);
                        y = loopNum(y, gridSize);

                        targetPos = new Vector2(x + xFraction, y + yFraction);
                    }
                    snake.setPosition(targetPos);
                } else if (exceedsBounds) {
                    stopGame();
                    break;
                } else {
                    snake.setPosition(targetPos);
                }
                stripGrid(snake.getPath(), snake.getHealth());

                if ((int)position.X == (int)targetPos.X && (int)position.Y == (int)targetPos.Y) {continue;}
                position = new Vector2(x, y);

                if (grid.get().isOccupied(y, x)) {
                    Entity targetEntity = grid.get().getItem(y, x);

                    if (targetEntity != null) {
                        if (targetEntity instanceof Food) {
                            Food food = (Food)targetEntity;

                            snake.eat(food);
                            Platform.runLater(() -> playSound("Coin"));

                            grid.get().removeItem(y, x);
                            fruits.returnObject((Rectangle)food.getFruit());

                            spawnFruit();
                        } else if (snake.isIntangible()) {
                            snake.expunge(position);
                        } else {
                            snake.setColors(Color.WHITE);
                            Vector2 pos = snake.getPosition();

                            //fruits.drawEntity(snake, new Vector2((int)pos.X, (int)pos.Y));
                            adjustTail(snake);
                            stopGame();
                            break;
                        }
                    }
                }

                grid.get().setItem(snake, y, x);
                adjustTail(snake);
                snake.render(world.getPaneSize());
            } else if (entity instanceof Food) {
                Rectangle fruit = fruits.getObject();
                fruit.setFill(entity.getPrimaryColor().toPaint());
                ((Food)entity).setFruit(fruit);

                garden.setEffect(((Food) entity).getEffect());
                garden.add(fruit, entity.getPosition());
                ENTITIES.remove(i);
            }
        }

        updateStats();
        previousTime.set(currentTime.get());
    }
}