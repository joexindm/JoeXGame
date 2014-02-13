/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package joexgame;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author kevin.lawrence
 */
class Snake {

    private Direction direction = Direction.RIGHT;
    private ArrayList<Point> body;
    private int growthCounter;

    {
        setBody(new ArrayList<Point>());
    }

    public void move() {
        int x = 0;
        int y = 0;

        switch (getDirection()) {
            case UP:
                x = 0;
                y = -1;
                break;

            case DOWN:
                x = 0;
                y = 1;
                break;

            case RIGHT:
                x = 1;
                y = 0;
                break;

            case LEFT:
                x = -1;
                y = 0;

        }
        getBody().add(0, new Point(getHead().x + x, getHead().y + y));
        if (growthCounter <= 0) {
            getBody().remove(getBody().size() - 1);
        } else {
            growthCounter--;
        }
    }

    void shrink(int shrink) {
        for (int i = 0; i < shrink; i++) {
            if (body.size() > 2) {
                body.remove(body.size() - 1);
            }
        }
    }

    public Point getHead() {
        return getBody().get(0);
    }

    /**
     * @return the direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(Direction direction) {
        if (((this.direction == Direction.LEFT) && (direction != Direction.RIGHT))
                || ((this.direction == Direction.RIGHT) && (direction != Direction.LEFT))
                || ((this.direction == Direction.UP) && (direction != Direction.DOWN))
                || ((this.direction == Direction.DOWN) && (direction != Direction.UP))) {
            this.direction = direction;
        }
    }

    /**
     * @return the body
     */
    public ArrayList<Point> getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(ArrayList<Point> body) {
        this.body = body;
    }

    /**
     * @return the growthCounter
     */
    public int getGrowthCounter() {
        return growthCounter;
    }

    /**
     * @param growthCounter the growthCounter to set
     */
    public void setGrowthCounter(int growthCounter) {
        this.growthCounter = growthCounter;
    }


}
