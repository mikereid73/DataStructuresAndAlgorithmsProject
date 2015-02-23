package itcarlow.c00112726.vote.gui;

import itcarlow.c00112726.vote.datastructures.LinkedList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by Mike on 23/02/2015.
 */
public class WinnerDialog extends JDialog {
    public WinnerDialog(JFrame parent, BufferedImage winner) {
        super(parent, "WINNER", true);

        setContentPane(new MyPanel(winner));
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }
}

/**
 * Created by Mike on 21/02/2015.
 */
class MyPanel extends JPanel implements ActionListener {

    private final LinkedList<Circle> circles = new LinkedList<>();
    private final Timer timer = new Timer(10, this);
    private static final Random random = new Random();
    private BufferedImage winner;
    private final Font font = new Font("helvetica", Font.PLAIN, 72);

    private static final int WIDTH = 720;
    private static final int HEIGHT = 480;

    public MyPanel(BufferedImage winner) {
        this.winner = winner;
        for (int i = 0; i < 50; i++) {
            addCircle();
        }
        start();
    }

    public void start() {
        timer.start();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    private void update() {
        for (int i = 0; i < circles.size(); i++) {
            final Circle current = circles.get(i);
            if (current.update()) {
                circles.remove(current);
                i--;
                addCircle();
            }
        }
    }

    private void addCircle() {
        final int radius = random.nextInt(25) + 25;
        final int x = random.nextInt(WIDTH - 2 * radius);
        final int y = random.nextInt(HEIGHT - 2 * radius);
        circles.add(new Circle(x, y, radius));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);

        g.drawImage(winner, WIDTH/2 - 250, HEIGHT/2 - 250, 500, 500, null);

        for (int i = 0; i < circles.size(); i++) {
            final Circle current = circles.get(i);
            current.draw(g2d);
        }

        g2d.setFont(font);
        drawCenteredString("!!!WINNER!!!", g2d);
    }

    public void drawCenteredString(String s, Graphics2D g) {
        FontMetrics fm = g.getFontMetrics();
        int x = (WIDTH - fm.stringWidth(s)) / 2;
        int y = (fm.getAscent() + (HEIGHT - (fm.getAscent() + fm.getDescent())) / 2);
        g.drawString(s, x, y);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        update();
        repaint();
    }
}

