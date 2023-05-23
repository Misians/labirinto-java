import javax.swing.*;
import java.awt.*;

public class Labirinto extends JFrame {

    private static final char PAREDE_VERTICAL = '|';
    private static final char PAREDE_HORIZONTAL = '_';
    private static final char VAZIO = ' ';
    private static final char PAREDE_INTERNA = '#';
    private static final char INICIO = 'I';
    private static final char DESTINO = 'D';
    private static final int TAMANHO = 10;
    private static final double PROBABILIDADE = 0.5;
    private static final char CAMINHO = '.';
    private static final char SEM_SAIDA = 'x';

    private char[][] tabuleiro;
    private int linhaInicio;
    private int colunaInicio;
    private int linhaDestino;
    private int colunaDestino;
    private JPanel panel;

    public Labirinto() {
        tabuleiro = new char[TAMANHO][TAMANHO];
        inicializaMatriz();
        criarJanela();
        procurarCaminho(linhaInicio, colunaInicio);
        exibirLabirinto();
    }

    private void inicializaMatriz() {
        int i, j;
        for (i = 0; i < TAMANHO; i++) {
            tabuleiro[i][0] = PAREDE_VERTICAL;
            tabuleiro[i][TAMANHO - 1] = PAREDE_VERTICAL;
            tabuleiro[0][i] = PAREDE_HORIZONTAL;
            tabuleiro[TAMANHO - 1][i] = PAREDE_HORIZONTAL;
        }
        for (i = 1; i < TAMANHO - 1; i++) {
            for (j = 1; j < TAMANHO - 1; j++) {
                if (Math.random() > 0.9) {
                    tabuleiro[i][j] = PAREDE_INTERNA;
                } else {
                    tabuleiro[i][j] = VAZIO;
                }
            }
        }
        linhaInicio = gerarNumero(1, TAMANHO / 2 - 1);
        colunaInicio = gerarNumero(1, TAMANHO / 2 - 1);
        tabuleiro[linhaInicio][colunaInicio] = INICIO;

        linhaDestino = gerarNumero(TAMANHO / 2, TAMANHO - 2);
        colunaDestino = gerarNumero(TAMANHO / 2, TAMANHO - 2);
        tabuleiro[linhaDestino][colunaDestino] = DESTINO;
    }

    private static int gerarNumero(int minimo, int maximo) {
        int valor = (int) Math.round(Math.random() * (maximo - minimo));
        return minimo + valor;
    }

    private boolean posicaoVazia(int linha, int coluna) {
        return tabuleiro[linha][coluna] == VAZIO || tabuleiro[linha][coluna] == INICIO;
    }

    private boolean tentarCaminho(int proxLinha, int proxColuna) {
        boolean achou = false;
        if (tabuleiro[proxLinha][proxColuna] == DESTINO) {
            achou = true;
        } else if (posicaoVazia(proxLinha, proxColuna)) {
            tabuleiro[proxLinha][proxColuna] = CAMINHO;
            exibirLabirinto();
            delay();
            achou = procurarCaminho(proxLinha, proxColuna);
            if (!achou) {
            tabuleiro[proxLinha][proxColuna] = SEM_SAIDA;
            exibirLabirinto();
            delay();
            }
            }
            return achou;
            }
            private boolean procurarCaminho(int linhaAtual, int colunaAtual) {
                int proxLinha;
                int proxColuna;
                boolean achou = false;
            
                proxLinha = linhaAtual - 1;
                proxColuna = colunaAtual;
            
                achou = tentarCaminho(proxLinha, proxColuna);
            
                proxLinha = linhaAtual + 1;
                proxColuna = colunaAtual;
                if (!achou) {
                    achou = tentarCaminho(proxLinha, proxColuna);
                }
            
                proxLinha = linhaAtual;
                proxColuna = colunaAtual - 1;
                if (!achou) {
                    achou = tentarCaminho(proxLinha, proxColuna);
                }
            
                proxLinha = linhaAtual;
                proxColuna = colunaAtual + 1;
                if (!achou) {
                    achou = tentarCaminho(proxLinha, proxColuna);
                }
            
                return achou;
            }
            
            private void criarJanela() {
                setTitle("Labirinto");
                setSize(400, 400);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
                panel = new JPanel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        for (int i = 0; i < TAMANHO; i++) {
                            for (int j = 0; j < TAMANHO; j++) {
                                if (tabuleiro[i][j] == PAREDE_VERTICAL || tabuleiro[i][j] == PAREDE_HORIZONTAL ||
                                        tabuleiro[i][j] == PAREDE_INTERNA) {
                                    g.setColor(Color.BLACK);
                                } else if (tabuleiro[i][j] == INICIO) {
                                    g.setColor(Color.GREEN);
                                } else if (tabuleiro[i][j] == DESTINO) {
                                    g.setColor(Color.RED);
                                } else if (tabuleiro[i][j] == CAMINHO) {
                                    g.setColor(Color.YELLOW);
                                } else if (tabuleiro[i][j] == SEM_SAIDA) {
                                    g.setColor(Color.GRAY);
                                } else {
                                    g.setColor(Color.WHITE);
                                }
            
                                g.fillRect(j * 20, i * 20, 20, 20);
                            }
                        }
                    }
                };
            
                add(panel);
                setVisible(true);
            }
            
            private void exibirLabirinto() {
                panel.repaint();
                delay();
            }
            
            private void delay() {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            public static void main(String[] args) {
                new Labirinto();
            }
        }