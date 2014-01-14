/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package joexgame;

import environment.Environment;
import environment.Grid;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author kevin.lawrence
 */
class SnakeEnvironment extends Environment {

    Snake snake;
    Grid grid;
    
    private Difficulty difficulty = Difficulty.EASY;
    
    int speed = SLOW;
    int moveCounter = speed;
    
    
    public static final int SLOW = 5;
    public static final int MEDIUM = 3;
    public static final int FAST = 1;
    public static final int VERYFAST = 1/2;
    
    

    public SnakeEnvironment() {
    }

    @Override
    public void initializeEnvironment() {
        grid = new Grid();
        grid.setCellHeight(30);
        grid.setCellWidth(30);
        grid.setColumns(40);
        grid.setRows(25);
        grid.setPosition(new Point(50, 50));

        snake = new Snake();
        snake.getBody().add(new Point(5, 5));
        snake.getBody().add(new Point(5, 4));
        snake.getBody().add(new Point(5, 3));
        snake.getBody().add(new Point(4, 3));

    }

    @Override
    public void timerTaskHandler() {
        if (moveCounter <= 0){
            moveCounter = speed;
            moveSnake();
        } else {
            moveCounter--;
        }
    }

    @Override
    public void keyPressedHandler(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_M){
            moveSnake();
        } else if (e.getKeyCode() == KeyEvent.VK_UP){
            setDirection(Direction.UP);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN){
            setDirection(Direction.DOWN);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            setDirection(Direction.RIGHT);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT){
            setDirection(Direction.LEFT);
        } else if (e.getKeyCode() == KeyEvent.VK_1){
            setDifficulty(Difficulty.EASY);
        } else if (e.getKeyCode() == KeyEvent.VK_2){
            setDifficulty(Difficulty.NORMAL);
        } else if (e.getKeyCode() == KeyEvent.VK_3){
            setDifficulty(Difficulty.HARD);
        } else if (e.getKeyCode() == KeyEvent.VK_4){
            setDifficulty(Difficulty.INSANE);
        }  
    }

    private void moveSnake() {
        if (snake != null){
            snake.move();
        }
    }

    private void setDirection(Direction direction) {
        if (snake != null){
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
        if (grid != null){
            grid.paintComponent(graphics);

            graphics.setColor(Color.GREEN);
            if (snake != null){
                Point cellLocation;
                for (int i = 0; i < snake.getBody().size(); i++) {
                    cellLocation = grid.getCellPosition(snake.getBody().get(i));
                    graphics.fillOval(cellLocation.x, cellLocation.y, grid.getCellWidth(), grid.getCellHeight());
                }
            }     
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
        
        switch (difficulty){
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

}
