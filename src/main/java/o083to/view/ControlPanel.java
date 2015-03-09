package o083to.view;

import o083to.controller.ui.ButtonStateController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ControlPanel extends Panel {

    private static final Dimension BUTTON_SIZE = new Dimension(32, 32);
    private static final String PATH_TO_START_ICON = "/start.png";
    private static final String PATH_TO_START_DISABLED_ICON = "/start_disabled.png";
    private static final String PATH_TO_PAUSE_ICON = "/pause.png";
    private static final String PATH_TO_PAUSE_DISABLED_ICON = "/pause_disabled.png";
    private static final String PATH_TO_STOP_ICON = "/stop.png";
    private static final String PATH_TO_STOP_DISABLED_ICON = "/stop_disabled.png";

    private final JButton startButton;
    private final JButton pauseButton;
    private final JButton stopButton;
    private final ButtonStateController buttonStateController;

    public ControlPanel() {
        startButton = createButton(PATH_TO_START_ICON, PATH_TO_START_DISABLED_ICON);
        pauseButton = createButton(PATH_TO_PAUSE_ICON, PATH_TO_PAUSE_DISABLED_ICON);
        pauseButton.setEnabled(false);
        stopButton = createButton(PATH_TO_STOP_ICON, PATH_TO_STOP_DISABLED_ICON);
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

    private JButton createButton(String pathToIcon, String pathToDisabledIcon) {
        JButton button = new JButton();
        button.setPreferredSize(BUTTON_SIZE);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        try {
            Image img = ImageIO.read(getClass().getResource(pathToIcon));
            button.setIcon(new ImageIcon(img));
            Image disImg = ImageIO.read(getClass().getResource(pathToDisabledIcon));
            button.setDisabledIcon(new ImageIcon(disImg));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return button;
    }
}
