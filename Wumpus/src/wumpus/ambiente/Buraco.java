/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wumpus.ambiente;

/**
 *
 * @author Fabio
 */
public class Buraco extends ElementoJogo {

    public Buraco(int posX, int posY) {
        super(posX, posY);
    }
    
    public String toString() {
        return "[" + this.getPosX() + "," + this.getPosY() + "]";
    }
}
