/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package joexgame;

/**
 *
 * @author Owner
 */
public class OptionsData {

    public OptionsData(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    
//<editor-fold defaultstate="collapsed" desc="Properties">
    private Difficulty difficulty = Difficulty.EASY;

    
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
    }
//</editor-fold>
    
}
