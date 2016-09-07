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
public class Cacador extends ElementoJogo {
    private boolean vivo;
    private int direcao;
    private int numFlechas;
    private boolean carregandoOuro;
    
    public Cacador(int posX, int posY, int numFlechas) {
        super(posX, posY);
        vivo = true;
        direcao = 0;
        this.numFlechas = numFlechas;
        carregandoOuro = false;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    public int getDirecao() {
        return direcao;
    }

    public void setDirecao(int direcao) {
        this.direcao = direcao;
    }

    public int getNumFlechas() {
        return numFlechas;
    }

    public void setNumFlechas(int numFlechas) {
        this.numFlechas = numFlechas;
    }

    public boolean isCarregandoOuro() {
        return carregandoOuro;
    }

    public void setCarregandoOuro(boolean carregandoOuro) {
        this.carregandoOuro = carregandoOuro;
    }
    
    public void girar(int sentido) {
        // Esquerda
        if(sentido == 0) {
            direcao -= 90;
            if(direcao == -90)
                direcao = 270;
        }
        // Direita
        if(sentido == 1) {
            direcao += 90;
            if(direcao == 360)
                direcao = 0;
        }
    }
  
    public String toString() {
        return "[" + this.getPosX() + "," + this.getPosY() +
                " " + (this.vivo ? "V" : "M") + "] " + this.direcao;
    }
}
