package xogame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Map extends JPanel {
    public static final int MODE_H_V_A = 0;
    public static final int MODE_H_V_H = 1;

    int fieldSizeX;
    int fieldSizeY;
    int winLength;

    int cellHeight;
    int cellWidth;

    boolean isInitialised = false;

    public Map() {
        setBackground(Color.ORANGE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!Logic.ended) {
                    int cellX = e.getX() / cellWidth;
                    int cellY = e.getY() / cellHeight;
                    System.out.println(cellX + " " + cellY);
                    Logic.humanTurn(cellX, cellY );
                    repaint();
                }
            }
        });
    }

    void startNewGame(int mode, int fieldSizeX, int fieldSizeY, int winLength) {
//        System.out.println(mode + " " + fieldSizeX + " " + fieldSizeY + " " + winLength);
        this.fieldSizeX = fieldSizeX;
        this.fieldSizeY = fieldSizeY;
        this.winLength = winLength;
        isInitialised = true;

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }

    private void render(Graphics g) {
        if (!isInitialised) {
            return;
        }

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        cellWidth = panelWidth / fieldSizeX;
        cellHeight = panelHeight / fieldSizeY;

        for (int i = 0; i < fieldSizeY; i++) {
            int y = i * cellHeight;
            g.drawLine(0, y, panelWidth, y);
        }

        for (int i = 0; i < fieldSizeX; i++) {
            int x = i * cellWidth;
            g.drawLine(x, 0, x, panelHeight);
        }


        for (int i = 0; i < Logic.SIZE_Y; i++) {
            for (int j = 0; j < Logic.SIZE_X; j++) {
                if(Logic.map[i][j] == Logic.DOT_X){
                    drawX(g,j,i);
                }
                if(Logic.map[i][j] == Logic.DOT_O){
                    drawO(g,j,i);
                }
            }
        }

        if (Logic.ended) {
            drawResult(g, Logic.winner);
            if (Logic.winnerLine.length > 0)
                drawWinLine(g, Logic.winnerLine);
        }
    }

    private void drawWinLine(Graphics g, int[] par) {
        g.setColor(new Color(0, 255, 0));
        int x1, x2, y1, y2;
        x1 = par[0] * cellWidth + cellWidth / 2;
        x2 = par[0] * cellWidth + cellWidth / 2 + par[2] * (par[4] - 1) * cellWidth;
        y1 = par[1] * cellHeight + cellHeight / 2;
        y2 = par[1] * cellHeight + cellHeight / 2 + par[3] * (par[4] - 1) * cellHeight;
        g.drawLine(x1, y1, x2, y2);
    }

    private void drawResult(Graphics g, String text){
        g.setColor(new Color(255,0, 255));
        Font font = new Font("Serif", Font.PLAIN, 24);
        g.setFont(font);
        g.drawString(text, 200, 200);
    }

    private void drawO(Graphics g, int cellX, int cellY){
        g.setColor(new Color(255,0,0));
        g.drawOval(cellX * cellWidth , cellY * cellHeight, cellWidth,cellHeight );
    }

    private void drawX(Graphics g, int cellX, int cellY){
        g.setColor(new Color(0,0, 255));
        g.drawLine(cellX * cellWidth , cellY * cellHeight, cellX * cellWidth + cellWidth,cellY * cellHeight + cellHeight );
        g.drawLine(cellX * cellWidth + cellWidth , cellY * cellHeight, cellX * cellWidth,cellY * cellHeight + cellHeight );
    }

}
