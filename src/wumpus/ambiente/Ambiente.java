package wumpus.ambiente;
/**
 *
 * @author Fabio
 */
public class Ambiente {
    // Constantes
    private final int TAMANHO_MAXIMO = 25;
    private final int TAMANHO_MINIMO = 4;
    private final int MAXIMO_WUMPUS = 20;
    private final int MAXIMO_BURACOS = 80;
    private final int MINIMO_BURACOS = 3;
    private final int EJ_CACADOR_VIVO = 0;
    private final int EJ_CACADOR_MORTO = 1;
    private final int EJ_CACADOR_DESISTIU = 2;
    private final int EJ_CACADOR_VENCEU = 3;
    
    // Variáveis
    private int estadoJogo;
    private int altura;
    private int largura;
    private int numeroBuracos;
    private int numeroWumpus;
    private boolean hChoque;
    private boolean hGrito;
    private Cacador cacador;
    private Wumpus[] ambWumpus;
    private Ouro ouro;
    private Buraco[] buracos;

    // Construtor/Inicializador do Ambiente
    public Ambiente(int altura, int largura, int numeroBuracos, int numeroWumpus) {
        // Estado Inicial do Jogo
        this.estadoJogo = this.EJ_CACADOR_VIVO;
        // Altura
        if(altura > 0 && altura < TAMANHO_MINIMO)
            altura = TAMANHO_MINIMO;
        if(altura > TAMANHO_MAXIMO)
            altura = TAMANHO_MAXIMO;
        while(altura < TAMANHO_MINIMO) {
                altura = (new java.util.Random()).nextInt(TAMANHO_MAXIMO) + 1;
        }
        this.altura = altura;
        // Largura
        if(largura > 0 && largura < TAMANHO_MINIMO)
            largura = TAMANHO_MINIMO;
        if(largura > TAMANHO_MAXIMO)
            largura = TAMANHO_MAXIMO;
        while(largura < TAMANHO_MINIMO) {
                largura = (new java.util.Random()).nextInt(TAMANHO_MAXIMO) + 1;
        }
        this.largura = largura;
        // Buracos
        if(numeroBuracos > 0 && numeroBuracos < MINIMO_BURACOS)
            numeroBuracos = MINIMO_BURACOS;
        if(numeroBuracos > MAXIMO_BURACOS)
            numeroBuracos = MAXIMO_BURACOS;
        while(numeroBuracos < MINIMO_BURACOS) {
                numeroBuracos = (new java.util.Random()).nextInt(MAXIMO_BURACOS) + 1;
        }
        this.numeroBuracos = numeroBuracos;
        this.buracos = new Buraco[numeroBuracos];
        for(int i=0; i < numeroBuracos; i++) {
            boolean achei = true;
            int pX = 0, pY = 0;
            do {
                pX = (new java.util.Random()).nextInt(largura) + 1;
                pY = (new java.util.Random()).nextInt(altura) + 1;
                // Evitar buracos  próximos à caverna inicial
                if(!((pX == 1 && pY < 3) || (pY == 1 && pX < 3))) {
                    // Evitar buracos em local repetido
                    achei = false;
                    for(int a=0; a<i; a++) {
                        if(buracos[a].getPosX() == pX && buracos[a].getPosY() == pY) {
                            achei = true;
                            break;
                        }
                    }
                }
            } while(achei);
            this.buracos[i] = new Buraco(pX, pY);
        }
        // Wumpus
        if(numeroWumpus == 0) {
            // Sorteio
            numeroWumpus = (new java.util.Random()).nextInt((int)(altura*largura*0.05)) + 1;
        }
        if(numeroWumpus > this.MAXIMO_WUMPUS)
            numeroWumpus = this.MAXIMO_WUMPUS;
        this.numeroWumpus = numeroWumpus;
        this.ambWumpus = new Wumpus[numeroWumpus];
        for(int i=0; i < numeroWumpus; i++) {
            boolean achei = true;
            int pX = 0, pY = 0;
            do {
                pX = (new java.util.Random()).nextInt(largura) + 1;
                pY = (new java.util.Random()).nextInt(altura) + 1;
                // Evitar wumpus próximos à caverna inicial
                if(!((pX == 1 && pY < 3) || (pY == 1 && pX < 3))) {
                    // Evitar wumpus em local repetido
                    achei = false;
                    for(int a=0; a<i; a++) {
                        if(ambWumpus[a].getPosX() == pX && ambWumpus[a].getPosY() == pY) {
                            achei = true;
                            break;
                        }
                    }
                    // Evitar wumpus em local de buracos
                    if(!achei)
                        for(int a=0; a<numeroBuracos; a++) {
                            if(buracos[a].getPosX() == pX && buracos[a].getPosY() == pY) {
                                achei = true;
                                break;
                            }
                        }
                }
            } while(achei);
            this.ambWumpus[i] = new Wumpus(pX, pY);
        }
        // Ouro
        boolean achei = true;
        int pX = 0, pY = 0;
        do {
            pX = (new java.util.Random()).nextInt(largura) + 1;
            pY = (new java.util.Random()).nextInt(altura) + 1;
            // Evitar ouro próximos à caverna inicial
            if((pX == 1 && pY < 3) || (pY == 1 && pX < 3))
                continue;
            // Evitar ouro em local de buracos
            achei = false;
            for(int a=0; a<numeroBuracos; a++) {
                if(buracos[a].getPosX() == pX && buracos[a].getPosY() == pY) {
                    achei = true;
                    break;
                }
            }
        } while(achei);
        this.ouro = new Ouro(pX, pY);
        // Caçador
        this.cacador = new Cacador(1, 1, this.numeroWumpus);
        // Houve Choque
        this.hChoque = false;
        // Houve Grito
        this.hGrito = false;
        // Imprime Informação
        System.out.println("\n>>> UNIVERSIDADE ESTADUAL DO SUDOESTE DA BAHIA - UESB");
        System.out.println(">>> DEPARTAMENTO DE CIÊNCIAS EXATAS E NATURAIS - DCET");
        System.out.println(">>> DISCIPLINA SISTEMAS INTELIGENTES - 2016.1");
        System.out.println("\n>>> SIMULADOR DO \"MUNDO DO WUMPUS\"");
        System.out.println("\n>>> AMBIENTE INICIALIZADO:");
        System.out.println("- Altura: " + this.altura);
        System.out.println("- Largura: " + this.largura);
        System.out.println("- Número de Buracos: " + this.numeroBuracos);
        System.out.print("  ");
        for(int i=0; i < this.numeroBuracos; i++)
            System.out.print(this.buracos[i] + " ");
        System.out.println("");
        System.out.println("- Número de Wumpus: " + this.numeroWumpus);
        System.out.print("  ");
        for(int i=0; i < this.numeroWumpus; i++)
            System.out.print(this.ambWumpus[i] + " ");
        System.out.println("\nOuro: " + this.ouro);
        System.out.println("Caçador: " + this.cacador);
    }
    
    // Gera Percepção do Agente
    public boolean[] getPercepcao() {
        if(this.estadoJogo != this.EJ_CACADOR_VIVO) {
            // Jogo Teminado
            System.out.println("ATENÇÃO! JOGO TERMINADO!!!");
            return null;
        }
        boolean fedor = false, brisa = false, brilho = false, choque = false,
                grito = false;
        // Fedor
        for(int i=0; i < this.numeroWumpus; i++) {
            if(this.cacador.ehAdjacente(ambWumpus[i])) {
                fedor = true;
                break;
            }
        }
        // Brisa
        for(int i=0; i < this.numeroBuracos; i++) {
            if(this.cacador.ehAdjacente(buracos[i])) {
                brisa = true;
                break;
            }
        }
        // Brilho
        if(!this.cacador.isCarregandoOuro()) {
            if(this.cacador.getPosX() == this.ouro.getPosX() && 
                    this.cacador.getPosY() == this.ouro.getPosY())
                brilho = true;
        }
        // Choque
        if(this.hChoque) {
            choque = true;
            this.hChoque = false;
        }
        // Grito
        if(this.hGrito) {
            grito = true;
            this.hGrito = false;
        }
        boolean[] percepcao = {fedor, brisa, brilho, choque, grito};
        System.out.println("Percepção: [fedor, brisa, brilho, choque, grito]");
        String p = "[" + fedor + ", " + brisa + ", " + brilho + ", " + choque +
                ", " + grito + "]";
        System.out.println(p);
        return percepcao;
    }
    
    // Ação Pegar o Ouro
    public boolean pegar() {
        boolean pegar = false;
        if(this.estadoJogo != this.EJ_CACADOR_VIVO) {
            // Jogo Teminado
            System.out.println("ATENÇÃO! JOGO TERMINADO!!!");
            return pegar;
        }
        if(!this.cacador.isCarregandoOuro()) {
            if(this.cacador.getPosX() == this.ouro.getPosX() && 
                    this.cacador.getPosY() == this.ouro.getPosY()) {
                pegar = true;
                this.cacador.setCarregandoOuro(true);
                System.out.println("O CAÇADOR PEGOU O OURO!!!");
            } else {
                System.out.println("O OURO NÃO ESTÁ AQUI!!!");
            }
        } else {
            System.out.println("O CAÇADOR JÁ ESTÁ CARREGANDO O OURO!!!");
        }
        return pegar;
    }
    
    // Ação Girar
    public boolean girar(int sentido){
        boolean girar = false;
        if(this.estadoJogo != this.EJ_CACADOR_VIVO) {
            // Jogo Teminado
            System.out.println("ATENÇÃO! JOGO TERMINADO!!!");
            return girar;
        }
        if(sentido == 0 || sentido == 1) {
            this.cacador.girar(sentido);
            girar = true;
            System.out.println("O CAÇADOR GIROU PARA A " + (sentido == 0 ? "ESQUERDA" : "DIREITA") + "!!!");
        }
        return girar;
    }
    
    // Ação Sair
    public boolean sair() {
        boolean sair = false;
        if(this.estadoJogo != this.EJ_CACADOR_VIVO) {
            // Jogo Teminado
            System.out.println("ATENÇÃO! JOGO TERMINADO!!!");
            return sair;
        }
        // Na posição (1,1)
        if(this.cacador.getPosX() == 1 && this.cacador.getPosY() == 1) {
            if(cacador.isCarregandoOuro()) {
                this.estadoJogo = this.EJ_CACADOR_VENCEU;
                System.out.println("FIM DO JOGO! O CAÇADOR VENCEU!!!");
            } else {
                this.estadoJogo = this.EJ_CACADOR_DESISTIU;
                System.out.println("FIM DO JOGO! O CAÇADOR DESISTIU!!!");
            }
            sair = true;
        }
        return sair;
    }
    
    // Ação Atirar
    public boolean atirar() {
        boolean atirar = false;
        if(this.estadoJogo != this.EJ_CACADOR_VIVO) {
            // Jogo Teminado
            System.out.println("ATENÇÃO! JOGO TERMINADO!!!");
            return atirar;
        }
        if(this.cacador.getNumFlechas() <= 0) {
            System.out.println("ATENÇÃO! CAÇADOR NÃO POSSUI MAIS FLECHAS!!!");
            return atirar;
        }
        int pX = this.cacador.getPosX();
        int pY = this.cacador.getPosY();
        int dr = this.cacador.getDirecao();
        boolean matou = false;
        for(int i = 0; i <= this.numeroWumpus; i++) {
            Wumpus w = this.ambWumpus[i];
            if(!w.isVivo())
                continue;
            // Para cima
            if(dr == 0) {
                if(w.getPosX() == pX && w.getPosY() > pY) {
                    w.setVivo(false);
                    matou = true;
                }
            }
            // Para baixo
            if(dr == 180) {
                if(w.getPosX() == pX && w.getPosY() < pY) {
                    w.setVivo(false);
                    matou = true;
                }
            }
            // Para direita
            if(dr == 90) {
                if(w.getPosY() == pY && w.getPosX() > pX) {
                    w.setVivo(false);
                    matou = true;
                }
            }
            // Para esquerda
            if(dr == 270) {
                if(w.getPosY() == pY && w.getPosX() < pX) {
                    w.setVivo(false);
                    matou = true;
                }
            }
        }
        atirar = true;
        // Diminui Número de Flechas
        this.cacador.setNumFlechas(this.cacador.getNumFlechas()-1);
        System.out.println("FLECHA DISPARADA COM SUCESSO!!!");
        // Atualiza Percepção
        if(matou)
            this.hGrito = true;
        return atirar;
    }
    
    // Ação Avançar
    public boolean avancar() {
        boolean avancar = false;
        if(this.estadoJogo != this.EJ_CACADOR_VIVO) {
            // Jogo Teminado
            System.out.println("ATENÇÃO! JOGO TERMINADO!!!");
            return avancar;
        }
        // Atualiza Coordenadas
        int pX = this.cacador.getPosX();
        int pY = this.cacador.getPosY();
        int dr = this.cacador.getDirecao();
        if(dr == 0)
            pY += 1;
        if(dr == 180)
            pY -= 1;
        if(dr == 90)
            pX += 1;
        if(dr == 270)
            pX -= 1;
        // Testar Choque
        if(pX < 1 || pX > this.largura || pY < 1 || pY > this.altura) {
            this.hChoque = true;
            return avancar;
        }
        // Salva Coordenadas
        avancar = true;
        this.cacador.setPosX(pX);
        this.cacador.setPosY(pY);
        if(this.cacador.isCarregandoOuro()) {
            this.ouro.setPosX(pX);
            this.ouro.setPosY(pY);
        }
        System.out.println("Cacador: " + this.cacador);
        for(int i=0; i < this.numeroBuracos; i++) {
            if(pX == this.buracos[i].getPosX() && pY == this.buracos[i].getPosY()) {
                System.out.println("FIM DO JOGO! O CAÇADOR CAIU EM UM BURACO!!!");
                this.estadoJogo = this.EJ_CACADOR_MORTO;
                break;
            }
        }
        for(int i=0; i < this.numeroWumpus; i++) {
            if(this.ambWumpus[i].isVivo() && pX == this.ambWumpus[i].getPosX() && pY == this.ambWumpus[i].getPosY()) {
                System.out.println("FIM DO JOGO! O CAÇADOR FOI DEVORADO PELO WUMPUS!!!");
                this.estadoJogo = this.EJ_CACADOR_MORTO;
                break;
            }
        }
        return avancar;
    }
}
