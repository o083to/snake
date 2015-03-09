package o083to.view.panel;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

    private static final Color BACKGROUND_COLOR = new Color(0x555a5c);

    public Panel() {
        setBackground(BACKGROUND_COLOR);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Color baseColor = getBackground();
        Color darkerColor = baseColor.darker();
        int w = getWidth();
        int h = getHeight();
        GradientPaint gp = new GradientPaint(
                0, 0, baseColor, 0, h, darkerColor);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }
}
