package o083to.view;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends Panel {

    private static final Color SCORE_TEXT_COLOR = new Color(0xdaa520);
    private static final Color GAME_OVER_TEXT_COLOR = new Color(0xff4500);
    private static final Font FONT = new Font("Monospaced", Font.BOLD, 20);
    private static final String GAME_OVER_MSG = "Game over!";

    private final JLabel scoreValueLabel = new JLabel("0");
    private final JLabel gameOver = new JLabel("");

    public ScorePanel() {
        setLayout(new BorderLayout());
        add(scoreValueLabel, BorderLayout.LINE_END);
        scoreValueLabel.setForeground(SCORE_TEXT_COLOR);
        scoreValueLabel.setFont(FONT);
        setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        gameOver.setForeground(GAME_OVER_TEXT_COLOR);
        gameOver.setFont(FONT);
        add(gameOver, BorderLayout.LINE_START);
    }

    public void setScore(int value) {
        scoreValueLabel.setText(String.valueOf(value));
    }

    public void writeGameOver() {
        gameOver.setText(GAME_OVER_MSG);
    }
}
