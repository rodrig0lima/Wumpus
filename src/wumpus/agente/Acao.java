/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wumpus.agente;

/**
 *
 * @author Joao
 */
public class Acao {
  int sentido;
  boolean andou = false;
  public Acao(int sentido, boolean andou) {
    this.sentido = sentido;
    this.andou = andou;
  }

  public int getSentido() {
    return sentido;
  }

  public boolean getAndou() {
    return andou;
  }
  
  
}
