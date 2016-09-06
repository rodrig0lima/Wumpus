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
public class Wumpus extends ElementoJogo {
    private boolean vivo;

    public Wumpus(int posX, int posY) {
        super(posX, posY);
        vivo = true;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }
    
    public String toString() {
        return "[" + this.getPosX() + "," + this.getPosY() +
                " " + (this.vivo ? "V" : "M") + "]";
    }
    
}
