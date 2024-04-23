package Estrutura;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Jogo extends JFrame implements KeyListener {
    private static final long serialVersionUID = 1L;
    private Tabuleiro tabuleiro;
    private int larguraCelula = 50;
    private int alturaCelula = 50;
    private boolean[][] letrasSelecionadas;
    private int linhaCursor = 0;
    private int colunaCursor = 0;
    private ArrayList<Character> letrasMarcadas;

    private boolean jogoIniciado = false;
    private boolean jogoEncerrado = false;
    private int palavrasEncontradas = 0;
    private int palavrasTotais = 7;

    private JTextArea telaInicio;
    private Timer timer;

    public Jogo() {
        setTitle("Jogo de Caça-Palavras");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        iniciarTelaInicio();

        this.tabuleiro = new Tabuleiro();
        this.letrasSelecionadas = new boolean[Tabuleiro.TAMANHO_PADRAO][Tabuleiro.TAMANHO_PADRAO];
        this.letrasMarcadas = new ArrayList<>();
        addKeyListener(this);

        setVisible(true);
    }

    private void iniciarTelaInicio() {
        telaInicio = new JTextArea();
        telaInicio.setEditable(false);
        telaInicio.append("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        telaInicio.append("			             BEM VINDO AO JOGO\n");
        telaInicio.append("			                CAÇA-PALAVRAS\n");
        telaInicio.append("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        telaInicio.append("			              AS REGRAS SÃO:\n");
        telaInicio.append("                                    - ENCONTRE 4 PALAVRAS NO TABULEIRO\n");
        telaInicio.append("                                    - USE AS SETAS ⭠ , ⭢, ⭡ e ↓ DO TECLADO PARA ESCOLHER AS LETRAS\n");
        telaInicio.append("                                      APERTE \"S\" PARA SELECIONAR AS LETRAS DA PALAVRA ENCONTRADA\n");
        telaInicio.append("                                      APERTE \"V\" PARA VERIFICAR SE A PALAVRA É VÁLIDA.\n");
        telaInicio.append("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        telaInicio.append("\n");
        telaInicio.append("                                                     ESPERE UM POUQUINHO... O JOGO JÁ VAI COMEÇAR\n");
        telaInicio.append("                                                                                    BOA SORTE!\n");
        telaInicio.append("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

        add(telaInicio);

        timer = new Timer(15000, e -> iniciarJogo());
        timer.setRepeats(false);
        timer.start();
    }

    private void removerTelaInicio() {
        if (telaInicio != null) {
            remove(telaInicio);
            telaInicio = null;
        }
    }

    private void verificarGanhou() {
        if (palavrasEncontradas + 3 >= palavrasTotais) {
            jogoEncerrado = true;
            JTextArea telaFim = new JTextArea();
            telaFim.setEditable(false);
            telaFim.append("----------------------------------------------------------------------------------------\n");
            telaFim.append("MEUS PARABÉNS, VOCÊ COMPLETOU O CAÇA-PALAVRAS!\n");
            telaFim.append("\n");
            telaFim.append("ATÉ MAIS!\n");
            telaFim.append("----------------------------------------------------------------------------------------\n");
            add(telaFim);
        }
    }

    private void iniciarJogo() {
        removerTelaInicio();
        jogoIniciado = true;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (jogoIniciado && !jogoEncerrado) {
            desenharTabuleiro(g);
            desenharCursor(g);
        }
    }

    private void desenharTabuleiro(Graphics g) {
        char[][] matriz = tabuleiro.matriz;
        for (int linha = 0; linha < matriz.length; linha++) {
            for (int coluna = 0; coluna < matriz[linha].length; coluna++) {
                char letra = matriz[linha][coluna];
                g.setColor(Color.WHITE);
                g.fillRect(coluna * larguraCelula, linha * alturaCelula, larguraCelula, alturaCelula);
                g.setColor(Color.BLACK);
                g.drawRect(coluna * larguraCelula, linha * alturaCelula, larguraCelula, alturaCelula);
                g.drawString(String.valueOf(letra), coluna * larguraCelula + larguraCelula / 2 - 5, linha * alturaCelula + alturaCelula / 2 + 5);
            }
        }
    }

    private void desenharCursor(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(colunaCursor * larguraCelula, linhaCursor * alturaCelula, larguraCelula, alturaCelula);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (jogoIniciado && !jogoEncerrado) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_S:
                    marcarLetra();
                    break;
                case KeyEvent.VK_V:
                    verificarPalavra();
                    break;
                case KeyEvent.VK_UP:
                    moverCursorCima();
                    repaint();
                    break;
                case KeyEvent.VK_DOWN:
                    moverCursorBaixo();
                    repaint();
                    break;
                case KeyEvent.VK_LEFT:
                    moverCursorEsquerda();
                    repaint();
                    break;
                case KeyEvent.VK_RIGHT:
                    moverCursorDireita();
                    repaint();
                    break;
            }
        }
    }

    private void moverCursorCima() {
        if (linhaCursor > 0) {
            linhaCursor--;
        }
    }

    private void moverCursorBaixo() {
        if (linhaCursor < Tabuleiro.TAMANHO_PADRAO - 1) {
            linhaCursor++;
        }
    }

    private void moverCursorEsquerda() {
        if (colunaCursor > 0) {
            colunaCursor--;
        }
    }

    private void moverCursorDireita() {
        if (colunaCursor < Tabuleiro.TAMANHO_PADRAO - 1) {
            colunaCursor++;
        }
    }

    private void marcarLetra() {
        letrasSelecionadas[linhaCursor][colunaCursor] = true;
        letrasMarcadas.add(tabuleiro.matriz[linhaCursor][colunaCursor]);
        repaint();
    }

    private void verificarPalavra() {
        StringBuilder palavra = new StringBuilder();
        for (Character letra : letrasMarcadas) {
            palavra.append(letra);
        }
        String palavraStr = palavra.toString();
        if (tabuleiro.validarPalavra(palavraStr)) {
            System.out.println("Muito bem! A palavra '" + palavraStr + "' foi encontrada.");
            tabuleiro.substituirPalavra(palavraStr);
            palavrasEncontradas++;
            verificarGanhou();
        } else {
            System.out.println("Eita, parece que você errou! Tente novamente.");
            limparSelecao();
        }
        letrasMarcadas.clear();
        repaint();
    }

    private void limparSelecao() {
        for (int i = 0; i < letrasSelecionadas.length; i++) {
            for (int j = 0; j < letrasSelecionadas[i].length; j++) {
                letrasSelecionadas[i][j] = false;
            }
        }
        letrasMarcadas.clear();
    }

    @Override
    public void keyReleased(KeyEvent e) {
     
    }
}

