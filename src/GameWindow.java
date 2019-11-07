package xogame;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    private static final int WIN_HEIGHT = 555;
    private static final int WIN_WIDTH = 505;
    private static final int WIN_POS_X = 200;
    private static final int WIN_POS_Y = 200;

    private static StartNewGameWindow startNewGameWindow;
    private static Map field;

    public GameWindow() {
        setBounds(WIN_POS_X, WIN_POS_Y, WIN_WIDTH, WIN_HEIGHT);
        setTitle("TicTacToe");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel jPanel = new JPanel(new GridLayout(1, 2));
        JButton btnNewGame = new JButton("Start new game");
        JButton btnExit = new JButton(" Exit");

        jPanel.add(btnNewGame);
        jPanel.add(btnExit);

        add(jPanel, BorderLayout.SOUTH);

        field = new Map();
        add(field, BorderLayout.CENTER);
        startNewGameWindow = new StartNewGameWindow(this);

        btnExit.addActionListener(e -> {
            System.exit(0);
        });

        btnNewGame.addActionListener(e->{
            startNewGameWindow.setVisible(true);
        });

        setVisible(true);
    }

    void startNewGame(int mode, int fieldSizeX , int fieldSizeY, int winLength){
        field.startNewGame(mode, fieldSizeX, fieldSizeY, winLength);
    }
}
