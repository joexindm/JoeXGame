/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package joexgame;

import audio.AudioPlayer;
import environment.Environment;
import environment.GraphicsPalette;
import environment.Grid;
import image.ResourceTools;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author kevin.lawrence
 */
class SnakeEnvironment extends Environment {

    Snake snake;
    Grid grid;

    private Difficulty difficulty = Difficulty.EASY;
    private ArrayList<Point> redapples;
    private ArrayList<Point> yellowapples;
    private ArrayList<Point> poisonbottles;
    private ArrayList<Point> stars;
    private ArrayList<Point> blocks;
    private int score = 0;
    private GameState gameState = GameState.PAUSED;

    int speed = SLOW;
    int moveCounter = speed;

    public static final int SLOW = 3;
    public static final int MEDIUM = 2;
    public static final int FAST = 1;
    public static final int VERYFAST = 1 / 2;

    public SnakeEnvironment() {
    }

    @Override
    public void initializeEnvironment() {
        this.setBackground(ResourceTools.loadImageFromResource("resources/StarBackground.jpg"));

        grid = new Grid();
        grid.setCellHeight(25);
        grid.setCellWidth(25);
        grid.setColumns(50);
        grid.setRows(30);
        grid.setPosition(new Point(100, 200));

        this.blocks = new ArrayList<Point>();
        for (int i = - 1; i <= this.grid.getColumns(); i++) {
            this.blocks.add(new Point(i, - 1));
            this.blocks.add(new Point(i, this.grid.getRows()));
        }

        for (int i = 0; i <= this.grid.getRows(); i++) {
            this.blocks.add(new Point(- 1, i));
            this.blocks.add(new Point(this.grid.getColumns(), i));
        }

        this.redapples = new ArrayList<Point>();
        for (int i = 0; i < 3; i++) {
            this.redapples.add(getRandomPoint());
        }

        this.yellowapples = new ArrayList<Point>();
        for (int i = 0; i < 1; i++) {
            this.yellowapples.add(getRandomPoint());
        }

        this.poisonbottles = new ArrayList<Point>();
        for (int i = 0; i < 10; i++) {
            this.poisonbottles.add(getRandomPoint());
        }

        this.stars = new ArrayList<Point>();
        for (int i = 0; i < 1; i++) {
            this.stars.add(getRandomPoint());
        }

        snake = new Snake();
        snake.getBody().add(new Point(5, 5));
        snake.getBody().add(new Point(5, 4));
        snake.getBody().add(new Point(5, 3));
        snake.getBody().add(new Point(4, 3));

    }

    @Override
    public void timerTaskHandler() {
        if (this.gameState == GameState.RUNNING) {
            if (snake != null) {
                if (moveCounter <= 0) {
                    moveCounter = speed;
                    moveSnake();
                    if (isSnakeOutOfBounds()) {
                        gameState = GameState.ENDED;
                        AudioPlayer.play("/resources/smw_lost_a_life.wav");
                    }
                    if (selfHitTest()) {
                        gameState = GameState.ENDED;
                        AudioPlayer.play("/resources/smw_lost_a_life.wav");
                    }
                    checkSnakeIntersection();
                } else {
                    moveCounter--;
                }
            }
        }
    }

    @Override
    public void keyPressedHandler(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_M) {
            moveSnake();
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            setDirection(Direction.UP);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            setDirection(Direction.DOWN);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            setDirection(Direction.RIGHT);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            setDirection(Direction.LEFT);
        } else if (e.getKeyCode() == KeyEvent.VK_1) {
            setDifficulty(Difficulty.EASY);
        } else if (e.getKeyCode() == KeyEvent.VK_2) {
            setDifficulty(Difficulty.NORMAL);
        } else if (e.getKeyCode() == KeyEvent.VK_3) {
            setDifficulty(Difficulty.HARD);
        } else if (e.getKeyCode() == KeyEvent.VK_4) {
            setDifficulty(Difficulty.INSANE);
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            //toggle the PAUSED/RUNNING state
            if (gameState == GameState.RUNNING) {
                gameState = GameState.PAUSED;
            } else if (gameState == GameState.PAUSED) {
                gameState = GameState.RUNNING;
            }
        }
    }

    private void moveSnake() {
        if (snake != null) {
            snake.move();
        }
    }

    private void setDirection(Direction direction) {
        if (snake != null) {
            snake.setDirection(direction);
        }
    }

    @Override
    public void keyReleasedHandler(KeyEvent e) {
    }

    @Override
    public void environmentMouseClicked(MouseEvent e) {
    }

    @Override
    public void paintEnvironment(Graphics graphics) {
        if (grid != null) {
            grid.paintComponent(graphics);

            //draw blocks
            if (this.blocks != null) {
                for (int i = 0; i < this.blocks.size(); i++) {
                    graphics.setColor(new Color(10, 100, 255));
                    graphics.fill3DRect(this.grid.getCellPosition(this.blocks.get(i)).x, this.grid.getCellPosition(this.blocks.get(i)).y, this.grid.getCellWidth(), this.grid.getCellHeight(), true);
                }
            }

            if (this.redapples != null) {
                for (int i = 0; i < this.redapples.size(); i++) {
                    GraphicsPalette.drawApple(graphics, this.grid.getCellPosition(this.redapples.get(i)), this.grid.getCellSize());
                }
            }
            if (this.yellowapples != null) {
                for (int i = 0; i < this.yellowapples.size(); i++) {
                    GraphicsPalette.drawApple(graphics, this.grid.getCellPosition(this.yellowapples.get(i)), this.grid.getCellSize(), Color.YELLOW);
                }
            }
            graphics.setColor(Color.GREEN);
            if (snake != null) {
                Point cellLocation;
                for (int i = 0; i < snake.getBody().size(); i++) {
                    cellLocation = grid.getCellPosition(snake.getBody().get(i));
                    graphics.fillOval(cellLocation.x, cellLocation.y, grid.getCellWidth(), grid.getCellHeight());
                }
            }

            if (this.poisonbottles != null) {
                for (int i = 0; i < this.poisonbottles.size(); i++) {
                    GraphicsPalette.drawPoisonBottle(graphics, this.grid.getCellPosition(this.poisonbottles.get(i)), this.grid.getCellSize(), Color.RED);
                }
            }

            if (this.stars != null) {
                for (int i = 0; i < this.stars.size(); i++) {
                    GraphicsPalette.drawSixPointStar(graphics, this.grid.getCellPosition(this.stars.get(i)), this.grid.getCellSize(), Color.YELLOW);
                }
            }
        }

        graphics.setColor(Color.red);
        graphics.setFont(new Font("Calibri", Font.BOLD, 100));
        graphics.drawString("Score : " + score, 800, 100);

        if (gameState == GameState.ENDED) {
            graphics.setColor(Color.red);
            graphics.setFont(new Font("Calibri", Font.BOLD, 200));
            graphics.drawString("GAME OVER", 150, 500);
        }
    }

    /**
     * @return the difficulty
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * @param difficulty the difficulty to set
     */
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;

        switch (difficulty) {
            case EASY:
                this.speed = SLOW;
                break;

            case NORMAL:
                this.speed = MEDIUM;
                break;

            case HARD:
                this.speed = FAST;
                break;

            case INSANE:
                this.speed = VERYFAST;
                break;

        }

    }

    private void checkSnakeIntersection() {
        for (int i = 0; i < this.redapples.size(); i++) {
            if (this.snake.getHead().equals(this.redapples.get(i))) {
                this.redapples.get(i).setLocation(getUniqueRandomPoint());
                this.snake.setGrowthCounter(1);
                this.score += 50;
                AudioPlayer.play("/resources/smw_coin.wav");

            }
        }
        for (int i = 0; i < this.yellowapples.size(); i++) {
            if (this.snake.getHead().equals(this.yellowapples.get(i))) {
                this.yellowapples.get(i).setLocation(getUniqueRandomPoint());
                this.snake.setGrowthCounter(1);
                this.score += 100;
                AudioPlayer.play("/resources/smw_dragon_coin.wav");
            }
        }
        for (int i = 0; i < this.poisonbottles.size(); i++) {
            if (this.snake.getHead().equals(this.poisonbottles.get(i))) {
                this.poisonbottles.get(i).setLocation(getUniqueRandomPoint());
                this.score -= 100;
                AudioPlayer.play("/resources/smw_pipe.wav");
            }
        }
        for (int i = 0; i < this.stars.size(); i++) {
            if (this.snake.getHead().equals(this.stars.get(i))) {
                this.stars.get(i).setLocation(getUniqueRandomPoint());
                //this.snake.setGrowthCounter(3);
                this.snake.shrink(1);
                this.score += 400;
                AudioPlayer.play("/resources/smw_power-up.wav");
            }
        }
    }

    private boolean isSnakeOutOfBounds() {
        return ((this.snake.getHead().x <= - 1)
                || (this.snake.getHead().x >= this.grid.getColumns())
                || (this.snake.getHead().y <= - 1)
                || (this.snake.getHead().y >= this.grid.getRows()));
        
//        if ((this.snake.getHead().x <= - 1)
//                || (this.snake.getHead().x >= this.grid.getColumns())
//                || (this.snake.getHead().y <= - 1)
//                || (this.snake.getHead().y >= this.grid.getRows())) {
//            return true;
//        } else {
//            return false;
//        }

    }
    
    private boolean selfHitTest() {
        for (int i = 1; i < this.snake.getBody().size(); i++) {
            if (this.snake.getBody().get(i).equals(this.snake.getHead())){
              return true;
            }
        }
        return false;
    
    }

    private Point getRandomPoint() {
        return new Point((int) (Math.random() * this.grid.getColumns()), (int) (Math.random() * this.grid.getRows()));
    }

    private Point getUniqueRandomPoint() {
        boolean unique = false;
        Point uniquePoint = new Point();
        ArrayList<ArrayList<Point>> gridObjects = new ArrayList<ArrayList<Point>>();
        gridObjects.add(redapples);
        gridObjects.add(yellowapples);
        gridObjects.add(snake.getBody());
        gridObjects.add(poisonbottles);
        gridObjects.add(stars);
        gridObjects.add(blocks);

        do {
            uniquePoint = getRandomPoint();
            for (int goCounter = 0; goCounter < gridObjects.size(); goCounter++) {
                unique = !intersects(uniquePoint, gridObjects.get(goCounter));
                if (!unique) {
                    break;
                }
            }
        } while (!unique);

        return uniquePoint;
    }

    public boolean intersects(Point location, ArrayList<Point> locations) {
        for (int i = 0; i < locations.size(); i++) {
            if (location.equals(locations.get(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean intersects(ArrayList<Point> locationsFirst, ArrayList<Point> locationsSecond) {

        for (int first = 0; locationsFirst.size() < 10; first++) {
            for (int second = 0; second < locationsSecond.size(); second++) {
                if (locationsFirst.get(first).equals(locationsSecond.get(second))) {
                    return true;
                }
            }
        }
        return false;
    }
}
