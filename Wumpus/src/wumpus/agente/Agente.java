/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wumpus.agente;
import java.util.ArrayList;
import wumpus.ambiente.Ambiente;

/**
 *
 * @author Rodrigo
 */
public class Agente {
    // Constantes do agente
    private final int DIRECAO_CIMA = 0;
    private final int DIRECAO_DIREITA = 1;
    private final int DIRECAO_BAIXO = 2;
    private final int DIRECAO_ESQUERDA = 3;
    private final int SENTIDO_ESQUERDA = 0;
    private final int SENTIDO_DIREITA = 1;
    // Atributos do agente
    private Ambiente ambiente;
    private int direcaoAtual;
    private ArrayList<Integer> direcoesVolta;
    private ArrayList<Casa> visitadas;
    private ArrayList<Casa> possiveis;
    private boolean possuiOuro;
    private Casa posicaoAtual;
    
    public Agente(Ambiente ambiente){
        this.ambiente = ambiente;
        this.direcaoAtual = DIRECAO_CIMA;
        this.possuiOuro = false;
        int[] x = {0,0};
        this.posicaoAtual = new Casa(x,ambiente.getPercepcao());
        posicaoAtual.setOk();
        posicaoAtual.setVisitada();
        direcoesVolta = new ArrayList<>();
        possiveis = new ArrayList<>();
        visitadas = new ArrayList<>();
        visitadas.add(posicaoAtual);
    }
    
    public void voltaComeco(){
        
    }
    
    public boolean matarWumpus(){
        return ambiente.atirar();
    }
    
    public boolean pegarOuro(){
        return ambiente.pegar();
    }
    
    public ArrayList<Casa> buscarCasasPossiveis(){
        return null;
    }
    
    public boolean irParaCasa(int x,int y){
        return false;
    }
    
    public void iniciar(){
        
    }
}
