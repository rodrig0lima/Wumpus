package wumpus.agente;
/**
 *
 * @author Rodrigo
 */
public class Casa {
    // Constantes da classe para os estados
    public static final int DESCONHECIDO = 0;
    public static final int POSSIBILIDADE = 1;
    public static final int EXISTE = 2;
    public static final int NAO_EXISTE = 3;
    // Atributos da casa
    private int posicaoX;
    private int posicaoY;
    private boolean fedor;
    private boolean brisa;
    private boolean brilho;
    private boolean choque;
    private boolean grito;
    private boolean visitada;
    // Estados
    private int estadoWumpus;
    private int estadoBuraco;

    public Casa(int posicoes[],boolean sensacoes[]){
        this.posicaoX = posicoes[0];
        this.posicaoY = posicoes[1];
        this.fedor = sensacoes[0];
        this.brisa = sensacoes[1];
        this.brilho = sensacoes[2];
        this.choque = sensacoes[3];
        this.grito = sensacoes[4];
        this.estadoWumpus = DESCONHECIDO;
        this.estadoBuraco = DESCONHECIDO;
        this.visitada = false;
    }

  public void setPosicaoX(int posicaoX) {
    this.posicaoX = posicaoX;
  }

  public void setPosicaoY(int posicaoY) {
    this.posicaoY = posicaoY;
  }
    
    
    
    public int getPosicaoX(){
        return posicaoX;
    }

    public int getPosicaoY(){
        return posicaoY;
    }

    public boolean isFedor(){
        return fedor;
    }

    public boolean isBrisa(){
        return brisa;
    }

    public boolean isBrilho(){
        return brilho;
    }

    public boolean isChoque(){
        return choque;
    }

    public boolean isGrito(){
        return grito;
    }

    public boolean isVisitada(){
        return visitada;
    }
    
    public boolean isBuraco(){
        return estadoBuraco==EXISTE;
    }
    
    public boolean isWumpus(){
        return estadoWumpus==EXISTE;
    }
    
    public boolean isOk(){
        return estadoWumpus==NAO_EXISTE&&estadoBuraco==NAO_EXISTE;
    }
    
    public boolean chanceWumpus(){
        return estadoWumpus==POSSIBILIDADE;
    }
    
    public boolean chanceBuraco(){
        return estadoBuraco==POSSIBILIDADE;
    }
    
    public void setWumpus(int estado){
        this.estadoWumpus = estado;
    }
    
    public void setBuraco(int estado){
        this.estadoBuraco = estado;
    }
    
    public void setOk(){
        this.estadoBuraco = NAO_EXISTE;
        this.estadoWumpus = NAO_EXISTE;
    }
    
    public void setSensacoes(boolean sensacoes[]){
        this.fedor = sensacoes[0];
        this.brisa = sensacoes[1];
        this.brilho = sensacoes[2];
        this.choque = sensacoes[3];
        this.grito = sensacoes[4];
    }
    
    public void getSensacoes(){
      String p = "CASA SENSAÇÕES \n [" + fedor + ", " + brisa + ", " + brilho + ", " + choque +
                ", " + grito + "]";
      System.out.println(p);
    }
    public void setVisitada(){
        this.visitada = true;
    }
}
