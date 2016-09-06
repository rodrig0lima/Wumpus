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
public abstract class ElementoJogo {
    private int posX;
    private int posY;

    public ElementoJogo(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
    
    public boolean ehAdjacente(ElementoJogo e) {
        boolean adjacente = false;
        if(e.getPosY() == posY && e.getPosX()-1 == posX)
            adjacente = true;
        if(e.getPosY() == posY && e.getPosX()+1 == posX)
            adjacente = true;
        if(e.getPosX() == posX && e.getPosY()-1 == posY)
            adjacente = true;
        if(e.getPosX() == posX && e.getPosY()+1 == posY)
            adjacente = true;
        return adjacente;
    }
    
}
