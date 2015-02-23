package itcarlow.c00112726.vote.gui;

import itcarlow.c00112726.vote.datastructures.LinkedList;
import itcarlow.c00112726.vote.entity.Candidate;
import itcarlow.c00112726.vote.util.GUIUtilities;
import sun.awt.image.GifImageDecoder;
import sun.awt.image.ImageWatched;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by Mike on 22/02/2015.
 */
public class ResultsPanel extends JPanel implements ActionListener {

    private JPanel mainPanel;
    private JPanel candidatePanel;
    private JPanel roundsPanel;

    // Allows rounds to be calculated slow enough to be watched, instead of all at once.
    private Timer calculateResultsTimer;
    private Timer celebrationTimer;

    private int currentRound = 0;

    private LinkedList<Candidate> candidatesInTheRunning;

    private Candidate winner;

    public ResultsPanel() {
        super();
        setLayout(new BorderLayout());

        candidatesInTheRunning = Candidate.getCandidateList();

        generateGUI();

        calculateResultsTimer = new Timer(1000, this);
    }

    private void generateGUI() {
        // V-Scroll for entire window
        final JScrollPane mainPanelScrollPane = new JScrollPane();
        mainPanelScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainPanelScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanelScrollPane.setViewportView(mainPanel);

        candidatePanel = new JPanel(new GridBagLayout());
        GUIUtilities.addGridItem(candidatePanel, new JLabel("Candidate"), 0, 0, 1, 1, GridBagConstraints.WEST);
        for (int i = 0; i < candidatesInTheRunning.size(); i++) {
            final Candidate candidate = candidatesInTheRunning.get(i);
            final CandidatePanel panel = new CandidatePanel(candidate);
            GUIUtilities.addGridItem(candidatePanel, panel, 0, i + 1, 1, 1, GridBagConstraints.WEST);
        }

        // H-scroll for rounds
        final JScrollPane roundsPanelScrollPane = new JScrollPane();
        roundsPanelScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        roundsPanelScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        roundsPanel = new JPanel();
        roundsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        roundsPanelScrollPane.setViewportView(roundsPanel);

        mainPanel.add(candidatePanel, BorderLayout.WEST);
        mainPanel.add(roundsPanel, BorderLayout.CENTER);

        add(mainPanelScrollPane, BorderLayout.CENTER);
    }

    public void start() {
        calculateResultsTimer.setInitialDelay(1000);
        calculateResultsTimer.start();
    }

    public void stop() {
        calculateResultsTimer.stop();
        BufferedImage image = winner.getImage();
        WinnerDialog wd = new WinnerDialog(null, image);
        wd.setVisible(true);
    }

    private Candidate getLowestPreferences() {
        final LinkedList<Candidate> candidates = candidatesInTheRunning.getCopy();
        final LinkedList<Candidate> lowestPreferences = new LinkedList<>();
        Candidate lowest = candidates.peek();

        for (Candidate c : candidates) {
            if (c.getNumberOfVotes() < lowest.getNumberOfVotes()) {
                lowestPreferences.clear();
                lowestPreferences.add(c);
                lowest = c;
            } else if (c.getNumberOfVotes() == lowest.getNumberOfVotes()) {
                lowestPreferences.add(c);
            }
        }

        final Random random = new Random();
        final Candidate loser = lowestPreferences.get(random.nextInt(lowestPreferences.size()));

        return loser;
    }

    private void calculateNextRound() {
        if (currentRound >= Candidate.numberOfCandidates()) {
            stop();
            return;
        }
        currentRound++;

        Candidate loser = getLowestPreferences();
        roundsPanel.add(generateRoundsPanel(loser), 0);

        candidatesInTheRunning.remove(loser);

        loser.reassignBallotPapers(candidatesInTheRunning);
        roundsPanel.revalidate();
    }

    private JPanel generateRoundsPanel(Candidate loser) {
        final JPanel panel = new JPanel(new GridBagLayout());
        GUIUtilities.addGridItem(panel, new JLabel("Round " + currentRound), 0, 0, 1, 1, GridBagConstraints.CENTER);

        for (int i = 0; i < Candidate.numberOfCandidates(); i++) {
            final Candidate c = Candidate.getCandidateList().get(i);
            final JPanel p = new JPanel() {
                @Override
                public Dimension getPreferredSize() {
                    return new Dimension(120, 140);
                }
            };
            p.setBorder(new TitledBorder(""));
            final JLabel label = new JLabel();

            if (candidatesInTheRunning.size() == 1 && candidatesInTheRunning.contains(c)) {
                label.setText(Integer.toString(c.getNumberOfVotes()) + " and WINNER!!!");
                winner = c;
            } else if (candidatesInTheRunning.contains(c) && !c.equals(loser)) {
                label.setText(Integer.toString(c.getNumberOfVotes()));
            } else if (c.equals(loser)) {
                label.setText(Integer.toString(c.getNumberOfVotes()) + " and ELIMINATED!");
            } else {
                label.setText("ELIMINATED");
            }
            p.add(label);
            GUIUtilities.addGridItem(panel, p, 0, i + 1, 1, 1, GridBagConstraints.CENTER);
        }

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == calculateResultsTimer) {
            calculateNextRound();
        }
    }

    private static class CandidatePanel extends JPanel {

        private static final int DEFAULT_IMAGE_WIDTH = 128;
        private static final int DEFAULT_IMAGE_HEIGHT = 128;

        private Candidate candidate;

        public CandidatePanel(Candidate candidate) {
            this.candidate = candidate;
            setLayout(new GridBagLayout());
            setBorder(new TitledBorder(""));

            generateCandidatePanel();
        }

        private void generateCandidatePanel() {

            final JPanel imagePanel = new JPanel() {
                @Override
                public Dimension getPreferredSize() {
                    return new Dimension(DEFAULT_IMAGE_WIDTH, DEFAULT_IMAGE_HEIGHT);
                }

                @Override
                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(candidate.getImage(), 0, 0, getWidth(), getHeight(), null);
                }
            };

            imagePanel.setBorder(new TitledBorder(""));
            GUIUtilities.addGridItem(this, imagePanel, 0, 0, 4, 4, GridBagConstraints.CENTER);

            final JTextField nameTextField = new JTextField(15);
            nameTextField.setEditable(false);
            nameTextField.setHorizontalAlignment(JTextField.CENTER);
            nameTextField.setText(candidate.getFirstName() + " " + candidate.getLastName());
            GUIUtilities.addGridItem(this, nameTextField, 4, 0, 2, 1, GridBagConstraints.CENTER);

            final JTextField partyNameTextField = new JTextField(8);
            partyNameTextField.setEditable(false);
            partyNameTextField.setHorizontalAlignment(JTextField.CENTER);
            partyNameTextField.setText(candidate.getParty());
            GUIUtilities.addGridItem(this, partyNameTextField, 4, 1, 2, 1, GridBagConstraints.CENTER);

        }
    }
}



// A BIT OF FUN
class Circle {

    private static Color[] colors = {
            Color.RED,
            Color.GREEN,
            Color.BLUE,
            Color.CYAN,
            Color.ORANGE,
            Color.MAGENTA,
            Color.PINK
    };
    private static final Random colorGen = new Random();

    private int x;
    private int y;
    private int radius;
    private int maxRadius;
    private Color color;

    public Circle(int x, int y, int maxRadius) {
        this.x = x;
        this.y = y;
        this.radius = 0;
        this.maxRadius = maxRadius;
        color = colors[colorGen.nextInt(colors.length)];
    }

    public boolean update() {
        radius++;
        if(radius >= maxRadius) {
            return true;
        }
        return false;
    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        g.setStroke(new BasicStroke(3));
        g.fillOval((int)x - radius, (int)y - radius, 2*radius, 2*radius);
        g.setStroke(new BasicStroke(1));
    }

}