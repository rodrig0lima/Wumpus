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
    private final int DIRECAO_ESQUERDA = 2;
    private final int DIRECAO_BAIXO = 3;
    private final int SENTIDO_ESQUERDA = 0;
    private final int SENTIDO_DIREITA = 1;
    // Atributos do agente
    private Ambiente ambiente;
    private int direcaoAtual;
    private ArrayList<Integer> direcoesVolta;
    private ArrayList<Casa> visitadas;
    private ArrayList<Casa> possiveis;
    private ArrayList<Acao> acoes = new ArrayList();
    private int nAcoes = 0;
    private boolean possuiOuro;
    private Casa posicaoAtual;

    public Agente(Ambiente ambiente) {
        this.ambiente = ambiente;
        this.direcaoAtual = DIRECAO_CIMA;
        this.possuiOuro = false;
        int[] x = {1, 1};
        this.posicaoAtual = new Casa(x, ambiente.getPercepcao());
        posicaoAtual.setOk();
        posicaoAtual.setVisitada();
        direcoesVolta = new ArrayList<>();
        possiveis = new ArrayList<>();
        visitadas = new ArrayList<>();
        visitadas.add(posicaoAtual);
    }
    
    public void andar(){
        ambiente.avancar();
        nAcoes++;
    }
    
    public void andarL(){
        ambiente.avancar();
        acoes.add(new Acao(3, true));
        nAcoes++;
    }
    
    public void girar(int sentido){
        switch(sentido){
            case SENTIDO_DIREITA:
                direcaoAtual = (direcaoAtual+1)%4;
            break;
            case SENTIDO_ESQUERDA:
                direcaoAtual = (direcaoAtual-1);
                direcaoAtual = (direcaoAtual==-1?direcaoAtual=3:direcaoAtual);
            break;
        }
        ambiente.girar(sentido);
    }
    
    public void girarEAndar(int sentido){
        ambiente.girar(sentido);
        ambiente.avancar();
        acoes.add(new Acao(sentido, true));
        nAcoes++;
    }
    
    public void girarL(int sentido){
        girar(sentido);
        acoes.add(new Acao(sentido, false));
        nAcoes++;
    }

    public void voltaComeco() {
        while (posicaoAtual.getPosicaoX() != 1 && posicaoAtual.getPosicaoY() != 1) {
            voltar();
        }
    }

    public void voltar() {
        for (Acao acao : acoes) {
            System.out.println(acao.getSentido() + " " + acao.getAndou());
        }
        System.out.println("VOLTEI!!");
        switch (acoes.get(nAcoes - 1).getSentido()) {
            case 0:
                System.out.println("Voltando: Girei");
                girar(1);
                acoes.remove(nAcoes - 1);
                nAcoes--;
                break;
            case 1:
                System.out.println("Voltando: Girei");
                girar(0);
                acoes.remove(nAcoes - 1);
                nAcoes--;
                break;
            case 3:
                girar(SENTIDO_ESQUERDA);
                girar(SENTIDO_ESQUERDA);
                direcaoAtual = 3 - direcaoAtual;
                for (Acao acao : acoes) {
                    if (acao.getSentido() == 3) {
                        acao.setSentido(2);
                    }
                }
                break;
            default:
                break;
        }

        if (acoes.get(nAcoes - 1).getAndou()) {
            ambiente.avancar();
            acoes.remove(nAcoes - 1);
            nAcoes--;
            switch (direcaoAtual) {
                case DIRECAO_CIMA:
                    posicaoAtual.setPosicaoX(posicaoAtual.getPosicaoX() + 1);
                    System.out.println("Eu estou aq: " + posicaoAtual.getPosicaoX() + "," + posicaoAtual.getPosicaoY());
                    break;
                case DIRECAO_BAIXO:
                    posicaoAtual.setPosicaoX(posicaoAtual.getPosicaoX() - 1);
                    System.out.println("Eu estou aq: " + posicaoAtual.getPosicaoX() + "," + posicaoAtual.getPosicaoY());
                    break;
                case DIRECAO_ESQUERDA:
                    posicaoAtual.setPosicaoX(posicaoAtual.getPosicaoY() - 1);
                    System.out.println("Eu estou aq: " + posicaoAtual.getPosicaoX() + "," + posicaoAtual.getPosicaoY());
                    break;
                case DIRECAO_DIREITA:
                    posicaoAtual.setPosicaoX(posicaoAtual.getPosicaoY() + 1);
                    System.out.println("Eu estou aq: " + posicaoAtual.getPosicaoX() + "," + posicaoAtual.getPosicaoY());
                    break;
            }
        } else {
            voltar();
        }
    }

    public boolean matarWumpus() {
        return ambiente.atirar();
    }
    
    public boolean foiVisitada(int x,int y){
        for(Casa visitada : visitadas){
            if((visitada.getPosicaoX()==x)&&(visitada.getPosicaoY()==y))
                return true;
        }
        return false;
    }

    public boolean pegarOuro() {
        return ambiente.pegar();
    }

    public ArrayList<Casa> buscarCasasPossiveis() {
        return null;
    }

    public boolean irParaCasa(int x, int y) {
        return false;
    }
    
    public void buscarOuro() {
        while (!ambiente.pegar()) {
            System.out.println("Estou aqui: " + posicaoAtual.getPosicaoX() + "," + posicaoAtual.getPosicaoY());
            System.out.println("Percepção: [fedor, brisa, brilho, choque, grito]");
            boolean[] percepções = ambiente.getPercepcao();
            String p = "[" + percepções[0] + ", " + percepções[1] + ", " + percepções[2] + ", " + percepções[3]
                    + ", " + percepções[4] + "]";
            posicaoAtual.getSensacoes();
            System.out.println(p);
            if (posicaoAtual.isBrisa()) {
                System.out.println("TEM BRISA!");
                voltar();
            } else if (posicaoAtual.isChoque()) {
                System.out.println("TEM CHOQUE!");
                voltar();
            } else if (posicaoAtual.isFedor()) {
                System.out.println("TEM FEDOR!");
                voltar();
            } else if (posicaoAtual.isBrilho()) {
                System.out.println("TEM BRILHO!");
                ambiente.pegar();
            } else {
                int[] x = {posicaoAtual.getPosicaoX(), posicaoAtual.getPosicaoY()};
                System.out.println("TUDO LIMPO!");
                x[0] = posicaoAtual.getPosicaoX() + 1;
                x[1] = posicaoAtual.getPosicaoY();
                if (!foiVisitada(x[0], x[1])) {
                    System.out.println("Entrou na posição: " + x[0] + "," + x[1]);
                    direcaoAtual = DIRECAO_CIMA;
                    switch (direcaoAtual) {
                        case DIRECAO_CIMA:
                            andarL();
                        break;
                        case DIRECAO_BAIXO:
                            girarL(SENTIDO_ESQUERDA);
                            girarEAndar(SENTIDO_ESQUERDA);
                            break;
                        case DIRECAO_ESQUERDA:
                            girarEAndar(SENTIDO_DIREITA);
                            break;
                        case DIRECAO_DIREITA:
                            girarL(SENTIDO_ESQUERDA);
                            girarEAndar(SENTIDO_ESQUERDA);
                            break;
                    }
                    posicaoAtual = new Casa(x, ambiente.getPercepcao());
                    visitadas.add(posicaoAtual);
                } else {
                    x[0] = posicaoAtual.getPosicaoX();
                    x[1] = posicaoAtual.getPosicaoY() + 1;
                    if (!foiVisitada(x[0], x[1])) {
                        posicaoAtual = new Casa(x, ambiente.getPercepcao());
                        visitadas.add(posicaoAtual);
                        System.out.println("Entrou na posição: " + x[0] + "," + x[1]);
                        direcaoAtual = DIRECAO_DIREITA;
                        switch (direcaoAtual) {
                            case DIRECAO_CIMA:
                                girarEAndar(SENTIDO_DIREITA);
                                break;
                            case DIRECAO_BAIXO:
                                girarEAndar(SENTIDO_ESQUERDA);
                                break;
                            case DIRECAO_ESQUERDA:
                                girarL(SENTIDO_ESQUERDA);
                                girarEAndar(SENTIDO_ESQUERDA);
                                break;
                            case DIRECAO_DIREITA:
                                andarL();
                                break;
                        }
                        posicaoAtual = new Casa(x, ambiente.getPercepcao());
                        visitadas.add(posicaoAtual);

                    } else {

                        x[0] = posicaoAtual.getPosicaoX();
                        x[1] = posicaoAtual.getPosicaoY() - 1;
                        if (!foiVisitada(x[0], x[1])) {
                            posicaoAtual = new Casa(x, ambiente.getPercepcao());
                            visitadas.add(posicaoAtual);
                            System.out.println("Entrou na posição: " + x[0] + "," + x[1]);
                            direcaoAtual = DIRECAO_ESQUERDA;
                            switch (direcaoAtual) {
                                case DIRECAO_CIMA:
                                    girarEAndar(SENTIDO_ESQUERDA);
                                    break;
                                case DIRECAO_BAIXO:
                                    girarEAndar(SENTIDO_DIREITA);
                                    break;
                                case DIRECAO_ESQUERDA:
                                    andarL();
                                    break;
                                case DIRECAO_DIREITA:
                                    girarL(SENTIDO_ESQUERDA);
                                    girarEAndar(SENTIDO_ESQUERDA);
                                    break;
                            }
                            posicaoAtual = new Casa(x, ambiente.getPercepcao());
                            visitadas.add(posicaoAtual);

                        } else {
                            x[0] = posicaoAtual.getPosicaoX() - 1;
                            x[1] = posicaoAtual.getPosicaoY();
                            if (!foiVisitada(x[0], x[1])) {
                                posicaoAtual = new Casa(x, ambiente.getPercepcao());
                                visitadas.add(posicaoAtual);
                                System.out.println("Entrou na posição: " + x[0] + "," + x[1]);
                                direcaoAtual = DIRECAO_BAIXO;
                                switch (direcaoAtual) {
                                    case DIRECAO_CIMA:
                                        andarL();
                                        break;
                                    case DIRECAO_BAIXO:
                                        girarL(SENTIDO_DIREITA);
                                        girarEAndar(SENTIDO_DIREITA);
                                        break;
                                    case DIRECAO_ESQUERDA:
                                        girarEAndar(SENTIDO_DIREITA);
                                        break;
                                    case DIRECAO_DIREITA:
                                        girarEAndar(SENTIDO_DIREITA);
                                        break;
                                }
                                posicaoAtual = new Casa(x, ambiente.getPercepcao());
                                visitadas.add(posicaoAtual);
                            }
                        }
                    }
                }
            }
        }
    }
}
