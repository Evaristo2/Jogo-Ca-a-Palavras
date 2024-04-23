package Estrutura;

import java.util.Random;

class Tabuleiro {
    public char[][] matriz;
    public static final int TAMANHO_PADRAO = 13;

    public Tabuleiro() {
        this.matriz = new char[TAMANHO_PADRAO][TAMANHO_PADRAO];
        preencherTabuleiro();
        inserirPalavras();
    }

    private void preencherTabuleiro() {
        Random random = new Random();
        for (int i = 0; i < TAMANHO_PADRAO; i++) {
            for (int j = 0; j < TAMANHO_PADRAO; j++) {
                char letra = (char) (random.nextInt(26) + 'A');
                matriz[i][j] = letra;
            }
        }
    }

    private void inserirPalavras() {
        String[] palavras = {"HONDA", "FIAT", "FORD", "FERRARI", "LEXUS", "VOLKSWAGEN", "CHEVROLET"};
        Random random = new Random();
        for (String palavra : palavras) {
            int linha = random.nextInt(TAMANHO_PADRAO);
            int coluna = random.nextInt(TAMANHO_PADRAO - palavra.length() + 1);
            for (int i = 0; i < palavra.length(); i++) {
                matriz[linha][coluna + i] = palavra.charAt(i);
            }
        }
    }

    public boolean validarPalavra(String palavra) {
        String[] palavras = {"HONDA", "FIAT", "FORD", "FERRARI", "LEXUS", "VOLKSWAGEN", "CHEVROLET"};
        for (String palavraTabuleiro : palavras) {
            if (palavraTabuleiro.equalsIgnoreCase(palavra)) {
                return true;
            }
        }
        return false;
    }

    public void substituirPalavra(String palavra) {
        for (int linha = 0; linha < matriz.length; linha++) {
            for (int coluna = 0; coluna < matriz[linha].length; coluna++) {
                if (matriz[linha][coluna] == palavra.charAt(0)) {
                    boolean encontrada = true;
                    for (int i = 1; i < palavra.length(); i++) {
                        if (coluna + i >= matriz[linha].length || matriz[linha][coluna + i] != palavra.charAt(i)) {
                            encontrada = false;
                            break;
                        }
                    }
                    if (encontrada) {
                        for (int i = 0; i < palavra.length(); i++) {
                            matriz[linha][coluna + i] = '-';
                        }
                        return;
                    }
                }
            }
        }
    }

    @SuppressWarnings("unused")
	private String[] obterPalavrasTabuleiro() {
        String[] palavrasTabuleiro = new String[TAMANHO_PADRAO * 2];
        for (int linha = 0; linha < matriz.length; linha++) {
            StringBuilder palavraHorizontal = new StringBuilder();
            StringBuilder palavraVertical = new StringBuilder();
            for (int coluna = 0; coluna < matriz[linha].length; coluna++) {
                palavraHorizontal.append(matriz[linha][coluna]);
                palavraVertical.append(matriz[coluna][linha]);
            }
            palavrasTabuleiro[linha] = palavraHorizontal.toString();
            palavrasTabuleiro[linha + TAMANHO_PADRAO] = palavraVertical.toString();
        }
        return palavrasTabuleiro;
    }
}
