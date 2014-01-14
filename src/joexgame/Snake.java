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
    private Direction direction = Direction.UP;
    private ArrayList<Point> body;
    
    {
        setBody(new ArrayList<Point>());
    }
    
    public void move(){
        int x = 0;
        int y = 0;
        
        switch (direction){
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
        body.add(0, new Point(getHead().x + x, getHead().y + y));
        body.remove(body.size() - 1);
    }
    
    private Point getHead() {
        return body.get(0);
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

}
