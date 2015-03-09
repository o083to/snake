package o083to.view;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends Panel {

    private static final Color TEXT_COLOR = new Color(0xdaa520);
    private static final Font FONT = new Font("Monospaced", Font.BOLD, 24);

    private final JLabel scoreValueLabel = new JLabel("0");

    public ScorePanel() {
        setLayout(new BorderLayout());
        add(scoreValueLabel, BorderLayout.LINE_END);
        scoreValueLabel.setForeground(TEXT_COLOR);
        scoreValueLabel.setFont(FONT);
        setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    }

    public void setScore(int value) {
        scoreValueLabel.setText(String.valueOf(value));
    }
}
