/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package joexgame;

import environment.ApplicationStarter;
import java.awt.Dimension;

/**
 *
 * @author Owner
 */
public class JoeXGame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        start();
    }

    private static void start() {
        ApplicationStarter.run(new String[0], "X Game", new Dimension(1500,1050), new SnakeEnvironment());
    }
    
}
