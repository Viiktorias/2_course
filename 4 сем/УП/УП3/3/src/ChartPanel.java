import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;

class ChartPanel extends JPanel {
    private Slice[] slices;
    private String title;
    private double scale;
    private String maxLen;

    public ChartPanel(double[] values, String[] names, String title) {
        setBackground(new Color(0xA0A0A0));
        slices = new Slice[values.length];
        maxLen = "";
        for (int i = 0; i < slices.length; i++) {
            slices[i] = new Slice(names[i], values[i], new Color((int) (Math.random() * 0x1000000)));
            scale += values[i];
            if (names[i].length() > maxLen.length())
                maxLen = names[i];
        }
        scale = 360 / scale;
        this.title = title;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        Font titleFont = new Font("Verdana", Font.BOLD, 30);
        Font labelFont = new Font("Verdana", Font.BOLD, 14);
// Вычисляем длину заголовка.
        FontRenderContext context = g2.getFontRenderContext();
        Rectangle2D titleBounds = titleFont.getStringBounds(title, context);
        int titleWidth = (int) titleBounds.getWidth();
        int titleHeight = (int) titleBounds.getHeight();
// Рисуем заголовок.
        int y = (int) -titleBounds.getY();
        int x = (panelWidth - titleWidth) / 2;
        g2.setFont(titleFont);
        g2.drawString(title, (float) x, (float) y);
// Вычисляем размеры меток диаграммы.
        LineMetrics labelMetrics = labelFont.getLineMetrics("", context);
        int labelHeight = (int) labelMetrics.getHeight();
        int labelWidth = 2 * labelHeight * maxLen.length() / 3;
        int radius = Math.min(panelHeight - titleHeight - labelHeight * 2 - 60, panelWidth - labelWidth * 2 - 20) / 2;
        x = panelWidth / 2;
        y = (panelHeight - titleHeight - labelHeight - 20) / 2 + titleHeight + labelHeight;
        g2.setFont(labelFont);
// Рисуем сектора.
        double angle = 0;
        for (int i = 0; i < slices.length; i++) {
// Заполняем и рисуем сектора.
            g2.setPaint(slices[i].color);
            g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.fillArc(x - radius, y - radius, 2 * radius, 2 * radius, (int) Math.round(angle), (int) Math.ceil(slices[i].value * scale));
            g2.setPaint(Color.BLACK);
            g2.drawArc(x - radius, y - radius, 2 * radius, 2 * radius, (int) Math.round(angle), (int) Math.ceil(slices[i].value * scale));
// Рисуем метку.
            g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            Rectangle2D curLabelBounds = labelFont.getStringBounds(slices[i].name, context);
            float curLabelWidth = (float) curLabelBounds.getWidth();
            g2.drawLine(x + (int) Math.round(radius * 0.9 * Math.cos((angle + slices[i].value * scale / 2) * Math.PI / 180)),
                    y - (int) Math.round(radius * 0.9 * Math.sin((angle + slices[i].value * scale / 2) * Math.PI / 180)),
                    x + (int) Math.round(radius * 1.1 * Math.cos((angle + slices[i].value * scale / 2) * Math.PI / 180)),
                    y - (int) Math.round(radius * 1.1 * Math.sin((angle + slices[i].value * scale / 2) * Math.PI / 180)));
            if ((angle + slices[i].value * scale / 2 > 90) && (angle + slices[i].value * scale / 2 <= 270)) {
                g2.drawString(slices[i].name, x - curLabelWidth - 3 + (int) Math.round(radius * 1.1 * Math.cos((angle + slices[i].value * scale / 2) * Math.PI / 180)),
                        y - (int) Math.round(radius * 1.1 * Math.sin((angle + slices[i].value * scale / 2) * Math.PI / 180)));
            } else {
                g2.drawString(slices[i].name, x + 3 + (int) Math.round(radius * 1.1 * Math.cos((angle + slices[i].value * scale / 2) * Math.PI / 180)),
                        y - (int) Math.round(radius * 1.1 * Math.sin((angle + slices[i].value * scale / 2) * Math.PI / 180)));
            }
            curLabelBounds = labelFont.getStringBounds(String.valueOf(slices[i].value), context);
            curLabelWidth = (float) curLabelBounds.getWidth();
            g2.drawString(String.valueOf(slices[i].value), x - curLabelWidth / 2 + (int) Math.round(radius * 0.7 * Math.cos((angle + slices[i].value * scale / 2) * Math.PI / 180)),
                    y - (int) Math.round(radius * 0.7 * Math.sin((angle + slices[i].value * scale / 2) * Math.PI / 180)));
// Увеличиваем угол.
            angle += slices[i].value * scale;
        }
    }
}

class Slice {
    String name;
    double value;
    Color color;

    public Slice(String name, double value, Color color) {
        this.name = name;
        this.value = value;
        this.color = color;
    }
}