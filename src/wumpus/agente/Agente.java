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
    private ArrayList<Casa> visitadas;
    private ArrayList<Casa> possiveis;
    private boolean possuiOuro;
    private Casa posicaoAtual;
    private int[] cordAtual;
    private final int limiteInferior = 1;
    private final int limiteEsquerda = 1;
    private int limiteSuperior = 200000;
    private int limiteDireita = 200000;

    public Agente(Ambiente ambiente) {
        this.ambiente = ambiente;
        this.direcaoAtual = DIRECAO_CIMA;
        this.possuiOuro = false;
        possiveis = new ArrayList<>();
        visitadas = new ArrayList<>();
        int x[] = {1,1};
        cordAtual = x;
    }
    
    public boolean andar(){
        return ambiente.avancar();
    }
    
    public boolean girar(int sentido){
        switch(sentido){
            case SENTIDO_DIREITA:
                direcaoAtual = (direcaoAtual+1)%4;
            break;
            case SENTIDO_ESQUERDA:
                direcaoAtual = (direcaoAtual-1);
                direcaoAtual = (direcaoAtual==-1?direcaoAtual=3:direcaoAtual);
            break;
        }
        return ambiente.girar(sentido);
    }
    

    public boolean matarWumpus() {
        return ambiente.atirar();
    }
    
    public boolean foiVisitada(int x,int y){
        return (!visitadas.stream().anyMatch((visitada) -> ((visitada.getPosicaoX()==x)&&(visitada.getPosicaoY()==y))));
    }
    
    public Casa buscarCasa(int x,int y){
        for(Casa visitada : visitadas){
            if((visitada.getPosicaoX()==x)&&(visitada.getPosicaoY()==y))
                return visitada;
        }
        for(Casa possivel : possiveis){
            if((possivel.getPosicaoX()==x)&&(possivel.getPosicaoY()==y))
                return possivel;
        }
        return null;
    }

    public boolean pegarOuro() {
        return ambiente.pegar();
    }

    public ArrayList<Casa> buscarCasasPossiveis() {
        return null;
    }
    
    public void buscarOuro() {
        posicaoAtual = buscarCasa(cordAtual[0],cordAtual[1]);
        int voltar = 1;
        while (possuiOuro==false) {
            if(posicaoAtual==null){
                posicaoAtual = new Casa(cordAtual,ambiente.getPercepcao());
            }
            posicaoAtual.setSensacoes(ambiente.getPercepcao());
            if(!visitadas.contains(posicaoAtual)){
                visitadas.add(posicaoAtual);
                posicaoAtual.setVisitada();
            }
            posicaoAtual.setOk();
            System.out.println(posicaoAtual);
            Casa cima = casaAcima();
            Casa baixo = casaAbaixo();
            Casa esquerda = casaEsquerda();
            Casa direita = casaDireita();
            adicionarPossivel(cima);
            adicionarPossivel(baixo);
            adicionarPossivel(esquerda);
            adicionarPossivel(direita);
            if(posicaoAtual.isChoque()){
                switch (direcaoAtual) {
                    case DIRECAO_CIMA:
                        limiteSuperior = cordAtual[1];
                    break;
                    case DIRECAO_DIREITA:
                        limiteDireita = cordAtual[0];
                    break;
                }
            }
            if(posicaoAtual.isBrilho()){
                System.out.println("Achou ouro");
                possuiOuro = true;
                ambiente.pegar();
                navegarPara(1, 1);
                ambiente.sair();
            }
            if(posicaoAtual.isBrisa()){
                chanceBuracoVertical(cima);
                chanceBuracoVertical(baixo);
                chanceBuracoHorizontal(esquerda);
                chanceBuracoHorizontal(direita);
            }
            if(posicaoAtual.isFedor()){
                chanceWumpusVertical(cima);
                chanceWumpusVertical(baixo);
                chanceWumpusHorizontal(esquerda);
                chanceWumpusHorizontal(direita);
            }
            analisa(cima);
            analisa(baixo);
            analisa(esquerda);
            analisa(direita);
            if(posicaoAtual.semSensacoes()){
                if((cima!=null)&&(cima.taOk())&&(!visitadas.contains(cima))){
                    voltar = 1;
                    irParaCima();
                }else if((direita!=null)&&(direita.taOk())&&(!visitadas.contains(direita))){
                    voltar = 1;
                    irParaDireita();
                }else if((esquerda!=null)&&(esquerda.taOk())&&(!visitadas.contains(esquerda))){
                    voltar = 1;
                    irParaEsquerda();
                }else if((baixo!=null)&&(baixo.taOk())&&(!visitadas.contains(baixo))){
                    voltar = 1;
                    irParaBaixo();
                }else{
                    int tam = visitadas.size();
                    if(voltar<=tam){
                        Casa anterior = visitadas.get(tam-voltar);
                        navegarPara(anterior.getPosicaoX(), anterior.getPosicaoY());
                        voltar++;
                    }else{
                        navegarPara(1,1);
                        ambiente.sair();
                        break;
                    }
                }
            }else{
                int tam = visitadas.size();
                Casa anterior = visitadas.get(tam-voltar);
                navegarPara(anterior.getPosicaoX(), anterior.getPosicaoY());
                voltar++;
            }   
        }
            //break;
    }
    
    public Casa casaAcima(){
        if(cordAtual[1]+1>limiteSuperior){
            return null;
        }
        Casa casa = buscarCasa(cordAtual[0], cordAtual[1] + 1);
        if(casa==null){
            int[] cord = {cordAtual[0],cordAtual[1]+1};
            casa = new Casa(cord);
        }
        return casa;
    }
    
    public Casa casaAbaixo(){
        if(cordAtual[1]-1<limiteInferior){
            return null;
        }
        Casa casa = buscarCasa(cordAtual[0], cordAtual[1]-1);
        if(casa==null){
            int[] cord = {cordAtual[0],cordAtual[1]-1};
            casa = new Casa(cord);
        }
        return casa;
    }
    
    public Casa casaEsquerda(){
        if(cordAtual[0]-1<limiteEsquerda){
            return null;
        }
        Casa casa = buscarCasa(cordAtual[0]-1, cordAtual[1]);
        if(casa==null){
            int[] cord = {cordAtual[0]-1,cordAtual[1]};
            casa = new Casa(cord);
        }
        return casa;
    }
    
    public Casa casaDireita(){
        if(cordAtual[0]+1>limiteDireita){
            return null;
        }
        Casa casa = buscarCasa(cordAtual[0]+1, cordAtual[1]);
        if(casa==null){
            int[] cord = {cordAtual[0]+1,cordAtual[1]};
            casa = new Casa(cord);
        }
        return casa;
    }
    
    public void chanceWumpusHorizontal(Casa casa){
        if(casa!=null){
            if((!casa.isVisitada())&&(!casa.isWumpus())&&(!casa.isFedorVertical())){
                casa.setFedorHorizontal(true);
                casa.chanceWumpus();
            }else if(casa.isFedorVertical()){
                casa.setFedorHorizontal(true);
                casa.setWumpus(2);
                System.out.println("Wumpus na ("+casa.getPosicaoX()+","+casa.getPosicaoY()+")");
            }
        }
    }
    
    public void chanceWumpusVertical(Casa casa){
        if(casa!=null){
            if((!casa.isVisitada())&&(!casa.isWumpus())&&(!casa.isFedorHorizontal())){
                casa.setFedorVertical(true);
                casa.chanceWumpus();
            }else if(casa.isFedorHorizontal()){
                casa.setFedorVertical(true);
                casa.setWumpus(2);
                System.out.println("Wumpus na ("+casa.getPosicaoX()+","+casa.getPosicaoY()+")");
            }
        }
    }
    
    public void chanceBuracoHorizontal(Casa casa){
        if(casa!=null){
            if((!casa.isVisitada())&&(!casa.isBuraco())&&(!casa.isBrisaVertical())){
                casa.setBrisaHorizontal(true);
                casa.chanceBuraco();
            }else if(casa.isBrisaVertical()){
                casa.setBrisaHorizontal(true);
                casa.setBuraco(2);
                System.out.println("Buraco na ("+casa.getPosicaoX()+","+casa.getPosicaoY()+")");
            }
        }
    }
    
    public void chanceBuracoVertical(Casa casa){
        if(casa!=null){
            if((!casa.isVisitada())&&(!casa.isBuraco())&&(!casa.isBrisaHorizontal())){
                casa.setBrisaVertical(true);
                casa.chanceBuraco();
            }else if(casa.isBrisaHorizontal()){
                casa.setBrisaVertical(true);
                casa.setBuraco(2);
                System.out.println("Buraco na ("+casa.getPosicaoX()+","+casa.getPosicaoY()+")");
            }
        }
    }
    
    public void adicionarPossivel(Casa casa){
        if(casa!=null){
            casa.setOk();
            possiveis.add(casa);
        }
    }
    
    public void navegarPara(int x,int y){
        System.out.println("Indo para: ("+x+","+y+")");
        ArrayList<Casa> navegadas = new ArrayList<>();
        int destX = x-cordAtual[0];
        int destY = y-cordAtual[1];
        while(cordAtual[0]!=x||cordAtual[1]!=y){
            Casa cima = casaAcima();
            Casa baixo = casaAbaixo();
            Casa esquerda = casaEsquerda();
            Casa direita = casaDireita();
            if(destX>0){ //Caso o x seja maior, ele deve ir para a direita
                if((direita.isVisitada())&&(!navegadas.contains(direita))){
                    irParaDireita();
                    navegadas.add(direita);
                    destX--;
                }else if((cima!=null)&&(cima.isVisitada())&&(!navegadas.contains(cima))){
                    irParaCima();
                    navegadas.add(cima);
                    destY--;
                }else if((baixo!=null)&&(baixo.isVisitada())&&(!navegadas.contains(baixo))){
                    irParaBaixo();
                    navegadas.add(baixo);
                    destY++;
                }else if((esquerda!=null)&&(esquerda.isVisitada())&&(!navegadas.contains(esquerda))){
                    irParaEsquerda();
                    navegadas.add(esquerda);
                    destX++;
                }else{
                    navegadas = new ArrayList<>();
                }
            }else if(destX<0){
                if((esquerda.isVisitada())&&(!navegadas.contains(esquerda))){
                    irParaEsquerda();
                    navegadas.add(esquerda);
                    destX++;
                }else if((cima!=null)&&(cima.isVisitada())&&(!navegadas.contains(cima))){
                    irParaCima();
                    navegadas.add(cima);
                    destY--;
                }else if((baixo!=null)&&(baixo.isVisitada())&&(!navegadas.contains(baixo))){
                    irParaBaixo();
                    navegadas.add(baixo);
                    destY++;
                }else if((direita!=null)&&(direita.isVisitada())&&(!navegadas.contains(direita))){
                    irParaDireita();
                    navegadas.add(direita);
                    destX--;
                }else{
                    navegadas = new ArrayList<>();
                }
            }else if(destY>0){
                if((cima.isVisitada())&&(!navegadas.contains(cima))){
                    irParaCima();
                    navegadas.add(cima);
                    destY--;
                }else if((esquerda!=null)&&(esquerda.isVisitada())&&(!navegadas.contains(esquerda))){
                    irParaEsquerda();
                    navegadas.add(esquerda);
                    destX++;
                }else if((direita!=null)&&(direita.isVisitada())&&(!navegadas.contains(direita))){
                    irParaDireita();
                    navegadas.add(direita);
                    destX--;
                }else if((baixo!=null)&&(baixo.isVisitada())&&(!navegadas.contains(baixo))){
                    irParaBaixo();
                    navegadas.add(baixo);
                    destY++;
                }else{
                    navegadas = new ArrayList<>();
                }
            }else if(destY<0){
                if((baixo.isVisitada())&&(!navegadas.contains(baixo))){
                    irParaBaixo();
                    navegadas.add(baixo);
                    destY++;
                }else if((esquerda!=null)&&(esquerda.isVisitada())&&(!navegadas.contains(esquerda))){
                    irParaEsquerda();
                    navegadas.add(esquerda);
                    destX++;
                }else if((direita!=null)&&(direita.isVisitada())&&(!navegadas.contains(direita))){
                    irParaDireita();
                    navegadas.add(direita);
                    destX--;
                }else if((cima!=null)&&(cima.isVisitada())&&(!navegadas.contains(cima))){
                    irParaCima();
                    navegadas.add(cima);
                    destY--;
                }else{
                    navegadas = new ArrayList<>();
                }
            }else{
                navegadas = new ArrayList<>();
            }
        }
    }
    
    public boolean irParaDireita(){
        switch(direcaoAtual){
            case DIRECAO_CIMA:
                girar(1);
            break;
            case DIRECAO_BAIXO:
                girar(0);
            break;
            case DIRECAO_ESQUERDA:
                girar(1);
                girar(1);
        }
        if(andar()){
            posicaoAtual = casaDireita();
            cordAtual[0]++;
            return true;
        }
        return false;
    }
    
    public boolean irParaCima(){
        switch(direcaoAtual){
            case DIRECAO_DIREITA:
                girar(0);
            break;
            case DIRECAO_BAIXO:
                girar(1);
                girar(1);
            break;
            case DIRECAO_ESQUERDA:
                girar(1);
        }
        if(andar()){
            posicaoAtual = casaAcima();
            cordAtual[1]++;
            return true;
        }else{
            limiteSuperior = cordAtual[1];
            return false;
        }
    }
    
    public boolean irParaEsquerda(){
        switch(direcaoAtual){
            case DIRECAO_CIMA:
                girar(0);
            break;
            case DIRECAO_DIREITA:
                girar(1);
                girar(1);
            break;
            case DIRECAO_BAIXO:
                girar(1);
        }
        if(andar()){
            posicaoAtual = casaEsquerda();
            cordAtual[0]--;
            return true;
        }
        return false;
    }
    
    public boolean irParaBaixo(){
        switch(direcaoAtual){
            case DIRECAO_CIMA:
                girar(1);
                girar(1);
            break;
            case DIRECAO_DIREITA:
                girar(1);
            break;
            case DIRECAO_ESQUERDA:
                girar(0);
        }
        if(andar()){
            posicaoAtual = casaAbaixo();
            cordAtual[1]--;
            return true;
        }
        return false;
    }
    
    public void analisa(Casa casa){
        if(casa!=null&&!casa.chanceBuraco()&&!casa.chanceWumpus()&&(!casa.isBrisa())&&(!casa.isFedor())){
            casa.setOk();
        }
    }
}
