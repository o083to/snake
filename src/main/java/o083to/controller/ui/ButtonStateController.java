package o083to.controller.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonStateController implements ActionListener {

    private static final boolean[][] STATES = {
            {false,     true,       true},
            {true,      false,      true},
            {false,     false,      false}
    };
    private static final int START_CLICKED = 0;
    private static final int PAUSE_CLICKED = 1;
    private static final int STOP_CLICKED = 2;

    private final JButton startButton;
    private final JButton pauseButton;
    private final JButton stopButton;

    public ButtonStateController(JButton startButton, JButton pauseButton, JButton stopButton) {
        this.startButton = startButton;
        this.pauseButton = pauseButton;
        this.stopButton = stopButton;
    }

    public void actionPerformed(ActionEvent e) {
        Object clickedButton = e.getSource();
        if (clickedButton == startButton) {
            setStates(START_CLICKED);
        } else if (clickedButton == pauseButton) {
            setStates(PAUSE_CLICKED);
        } else if (clickedButton == stopButton) {
            setStates(STOP_CLICKED);
        }
    }

    private void setStates(int clicked) {
        startButton.setEnabled(STATES[clicked][0]);
        pauseButton.setEnabled(STATES[clicked][1]);
        stopButton.setEnabled(STATES[clicked][2]);
    }
}
