package o083to.view;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {

    private static final Color BACKGROUND_COLOR = Color.DARK_GRAY;
    private static final Color TEXT_COLOR = Color.ORANGE;
    private static final Font FONT = new Font("Serif", Font.PLAIN, 24);

    private final JLabel scoreValueLabel = new JLabel("0");

    public ScorePanel() {
        setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout());
        add(scoreValueLabel, BorderLayout.LINE_END);
        scoreValueLabel.setForeground(TEXT_COLOR);
        scoreValueLabel.setFont(FONT);
        JLabel scoreTitleLabel = new JLabel("Score:");
        scoreTitleLabel.setForeground(TEXT_COLOR);
        scoreTitleLabel.setFont(FONT);
        add(scoreTitleLabel, BorderLayout.LINE_START);
        setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    }

    public void setScore(int value) {
        scoreValueLabel.setText(String.valueOf(value));
    }
}
