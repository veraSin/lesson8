package xogame;

import javax.swing.*;
import java.awt.*;

public class StartNewGameWindow extends JFrame {
    private final GameWindow gameWindow;

    private static final int MIN_FIELD_SIZE = 3;
    private static final int MAX_FIELD_SIZE = 10;
    private static final int MIN_WIN_LEN = 3;
    private static final int MAX_WIN_LEN = 10;
    private static final String STR_FIELD_WIDTH = "Field width: ";
    private static final String STR_FIELD_HEIGHT = "Field height: ";
    private static final String STR_WIN_LEN = "Winning length: ";

    private JRadioButton jrbHumvsAi = new JRadioButton("Human vs Ai", true);
    private JRadioButton jrbHumvsHum = new JRadioButton("Human vs Human");
    private ButtonGroup gameMode = new ButtonGroup();

    private JSlider slFieldSizeX;
    private JSlider slFieldSizeY;
    private JSlider slWinLength;

    public StartNewGameWindow(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        setBounds(250, 250, 400, 400);
        setTitle("Game menu");
        setLayout(new GridLayout(13, 1));

        addGameControlMode();
        addGameControlFieldAndWinLen();

        JButton btnNewGame = new JButton("Start a game");

        btnNewGame.addActionListener(e -> {
            btnStartGameClick();
        });
        add(btnNewGame);

        setVisible(false);
    }

    void btnStartGameClick() {
        int gameMode;
        if (jrbHumvsAi.isSelected()) {
            gameMode = Map.MODE_H_V_A;
        } else {
            gameMode = Map.MODE_H_V_H;
        }
        int fieldSizeX = slFieldSizeX.getValue();
        int fieldSizeY = slFieldSizeY.getValue();
        int winLength = slWinLength.getValue();

        Logic.SIZE_X = fieldSizeX;
        Logic.SIZE_Y = fieldSizeY;
        Logic.DOTS_TO_WIN = winLength;
        Logic.initMap();
        Logic.ended = false;
//        Logic.printMap();

        gameWindow.startNewGame(gameMode, fieldSizeX, fieldSizeY, winLength);
        setVisible(false);
    }

    void addGameControlMode() {
        add(new JLabel("Choose gaming mode: "));
        gameMode.add(jrbHumvsAi);
        gameMode.add(jrbHumvsHum);
        add(jrbHumvsAi);
        add(jrbHumvsHum);
    }

    void addGameControlFieldAndWinLen() {
        add(new JLabel("Choose field width: "));
        JLabel lblFieldSize = new JLabel(STR_FIELD_WIDTH + MIN_FIELD_SIZE);
        add(lblFieldSize);
        slFieldSizeX = new JSlider(MIN_FIELD_SIZE, MAX_FIELD_SIZE, MIN_FIELD_SIZE);

        slFieldSizeX.addChangeListener(e -> {
            int currentFieldSize = slFieldSizeX.getValue();
            lblFieldSize.setText(STR_FIELD_WIDTH + currentFieldSize);
            slWinLength.setMaximum(currentFieldSize);
        });

        add(slFieldSizeX);

        add(new JLabel("Choose field height: "));
        JLabel lblFieldSizeY = new JLabel(STR_FIELD_HEIGHT + MIN_FIELD_SIZE);
        add(lblFieldSizeY);
        slFieldSizeY = new JSlider(MIN_FIELD_SIZE, MAX_FIELD_SIZE, MIN_FIELD_SIZE);

        slFieldSizeY.addChangeListener(e -> {
            int currentFieldSize = slFieldSizeY.getValue();
            lblFieldSizeY.setText(STR_FIELD_HEIGHT + currentFieldSize);
            slWinLength.setMaximum(currentFieldSize);
        });

        add(slFieldSizeY);

        add(new JLabel("Choose winning length: "));
        JLabel lblWinLen = new JLabel(STR_WIN_LEN + MIN_WIN_LEN);
        add(lblWinLen);
        slWinLength = new JSlider(MIN_WIN_LEN, MIN_WIN_LEN, MIN_WIN_LEN);

        slWinLength.addChangeListener(e -> {
            lblWinLen.setText(STR_WIN_LEN + slWinLength.getValue());
        });

        add(slWinLength);

    }
}
