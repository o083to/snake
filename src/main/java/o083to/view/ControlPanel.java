package o083to.view;

import o083to.controller.ui.ButtonStateController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ControlPanel extends JPanel {

    private static final Dimension BUTTON_SIZE = new Dimension(32, 32);
    private static final Color BUTTON_COLOR = Color.ORANGE;
    private static final Color BACKGROUND_COLOR = Color.DARK_GRAY;
    private static final String PATH_TO_START_ICON = "/start.png";
    private static final String PATH_TO_PAUSE_ICON = "/pause.png";
    private static final String PATH_TO_STOP_ICON = "/stop.png";

    private final JButton startButton;
    private final JButton pauseButton;
    private final JButton stopButton;
    private final ButtonStateController buttonStateController;

    public ControlPanel() {
        setBackground(BACKGROUND_COLOR);
        startButton = createButton(PATH_TO_START_ICON);
        pauseButton = createButton(PATH_TO_PAUSE_ICON);
        pauseButton.setEnabled(false);
        stopButton = createButton(PATH_TO_STOP_ICON);
        stopButton.setEnabled(false);
        Box box = Box.createHorizontalBox();
        box.add(startButton);
        box.add(pauseButton);
        box.add(stopButton);
        buttonStateController = new ButtonStateController(startButton, pauseButton, stopButton);
        startButton.addActionListener(buttonStateController);
        pauseButton.addActionListener(buttonStateController);
        stopButton.addActionListener(buttonStateController);
        add(box);
    }

    public ButtonStateController getButtonStateController() {
        return buttonStateController;
    }

    public void addPauseButtonListener(ActionListener listener) {
        pauseButton.addActionListener(listener);
    }

    public void addStartButtonListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }

    public void addStopButtonListener(ActionListener listener) {
        stopButton.addActionListener(listener);
    }

    private JButton createButton(String pathToIcon) {
        JButton button = new JButton();
        button.setPreferredSize(BUTTON_SIZE);
        button.setBackground(BUTTON_COLOR);
        try {
            Image img = ImageIO.read(getClass().getResource(pathToIcon));
            button.setIcon(new ImageIcon(img));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return button;
    }
}
