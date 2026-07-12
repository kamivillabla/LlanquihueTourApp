package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JTextField;

/** Campo de texto que muestra un texto de ejemplo (placeholder) cuando está vacío. */
public class PlaceholderTextField extends JTextField {

    private static final Color COLOR_PLACEHOLDER = new Color(150, 150, 150);

    private final String placeholder;

    public PlaceholderTextField(String placeholder) {
        this.placeholder = placeholder;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!getText().isEmpty() || placeholder == null) {
            return;
        }
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(COLOR_PLACEHOLDER);
        g2.setFont(getFont().deriveFont(Font.ITALIC));
        int y = (getHeight() - g2.getFontMetrics().getHeight()) / 2 + g2.getFontMetrics().getAscent();
        g2.drawString(placeholder, getInsets().left, y);
        g2.dispose();
    }
}
