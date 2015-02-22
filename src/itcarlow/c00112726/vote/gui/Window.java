package itcarlow.c00112726.vote.gui;

import itcarlow.c00112726.vote.entity.Candidate;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by Mike on 18/02/2015.
 */
public class Window extends JFrame implements WindowListener {

    private static final String TITLE = "Vote Counting App";
    private JPanel contentPane;
    private JScrollPane mainScrollPain;

    private JButton btnAddNewCandidate;
    private JButton btnDeleteCandidate;
    private JButton btnViewAllCandidates;
    private JButton btnBeginVoting;
    private JButton btnCastNewVote;
    private JButton btnCalculateResults;

    private static boolean VOTE_IN_PROGRESS = false;

    public Window() {
        super(TITLE);

        contentPane = new JPanel(new BorderLayout()) {
            private final int WIDTH = 1080;
            private final int HEIGHT = 680;

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(WIDTH, HEIGHT);
            }

        };
        setContentPane(contentPane);

        final int result = JOptionPane.showConfirmDialog(this, "Would you like to load sample data?",
                "Load Sample Data", JOptionPane.YES_NO_OPTION);
        if(result == JOptionPane.OK_OPTION) {
            loadTestData();
        }

        generateGUI();

        addWindowListener(this);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void generateGUI() {

        mainScrollPain = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(mainScrollPain, BorderLayout.CENTER);

        final JPanel toolPanel = new JPanel();
        toolPanel.setLayout(new GridLayout(1, 2, 5, 5));
        toolPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));

        final JPanel candidateSettingsPanel = new JPanel();
        candidateSettingsPanel.setLayout(new GridLayout(0, 1, 5, 5));
        candidateSettingsPanel.setBorder(new TitledBorder("Candidate Settings"));

        btnAddNewCandidate = new JButton("Add New Candidate");
        btnAddNewCandidate.addActionListener(e -> {
            final AddNewCandidateDialog newCandidateDialog = new AddNewCandidateDialog(this);
            newCandidateDialog.setVisible(true);
        });

        btnDeleteCandidate = new JButton("Delete Candidate");
        btnDeleteCandidate.addActionListener(e -> {
            final DeleteCandidateDialog deleteCandidateDialog = new DeleteCandidateDialog(this);
            deleteCandidateDialog.setVisible(true);
        });

       btnViewAllCandidates = new JButton("View All Candidates");
        btnViewAllCandidates.addActionListener(e -> {
            final ViewAllCandidate viewAllCandidate = new ViewAllCandidate(this);
            viewAllCandidate.setVisible(true);
        });

        candidateSettingsPanel.add(btnAddNewCandidate);
        candidateSettingsPanel.add(btnDeleteCandidate);
        candidateSettingsPanel.add(btnViewAllCandidates);
        toolPanel.add(candidateSettingsPanel);

        final JPanel voteControlsPanel = new JPanel();
        voteControlsPanel.setLayout(new GridLayout(0, 1, 5, 5));
        voteControlsPanel.setBorder(new TitledBorder("Vote Controls"));

        btnBeginVoting = new JButton("Begin Voting");
        btnBeginVoting.addActionListener(e -> {
            final int result = JOptionPane.showConfirmDialog(this, "By starting the voting process, " +
            "you cannot add or delete candidates \nuntil the vote has been complete. \nDo you wish to " +
            "continue?", "Begin Voting", JOptionPane.YES_NO_OPTION);

            if(result == JOptionPane.OK_OPTION) {
                beginVotingPreparation();
            }

        });

        btnCastNewVote = new JButton("Cast New Vote");
        btnCastNewVote.addActionListener(e -> {
        final CastVoteDialog castVoteDialog = new CastVoteDialog(this);
            castVoteDialog.setVisible(true);
        });
        btnCastNewVote.setEnabled(false);

        btnCalculateResults = new JButton("Calculate Results");
        btnCalculateResults.addActionListener(e -> {

        });
        btnCalculateResults.setEnabled(false);

        voteControlsPanel.add(btnBeginVoting);
        voteControlsPanel.add(btnCastNewVote);
        voteControlsPanel.add(btnCalculateResults);
        toolPanel.add(voteControlsPanel);

        contentPane.add(toolPanel, BorderLayout.SOUTH);
    }

    private void beginVotingPreparation() {
        VOTE_IN_PROGRESS = !VOTE_IN_PROGRESS;
        btnAddNewCandidate.setEnabled(!btnAddNewCandidate.isEnabled());
        btnDeleteCandidate.setEnabled(!btnDeleteCandidate.isEnabled());
        btnViewAllCandidates.setEnabled(!btnViewAllCandidates.isEnabled());
        btnBeginVoting.setEnabled(!btnBeginVoting.isEnabled());
        btnCastNewVote.setEnabled(!btnCastNewVote.isEnabled());
        btnCalculateResults.setEnabled(!btnCalculateResults.isEnabled());
    }

    private void loadTestData() {
        /*
            RANDOM TEST DATA - DELETE BEFORE SUBMITTING OTHERWISE YOU ARE AN IDIOT
         */
        try {
            Candidate c1 = new Candidate("Enda", "Kenny", "Fine Gael");
            c1.setImage(ImageIO.read(Candidate.class.getResourceAsStream("/test images/enda kenny.jpg")));
            Candidate.addCandidate(c1);

            Candidate c2 = new Candidate("Gerry", "Adams", "Sinn Fein");
            c2.setImage(ImageIO.read(Candidate.class.getResourceAsStream("/test images/gerry adams.jpg")));
            Candidate.addCandidate(c2);

            Candidate c3 = new Candidate("Joan", "Burton", "Fine Gael");
            c3.setImage(ImageIO.read(Candidate.class.getResourceAsStream("/test images/joan burton.jpg")));
            Candidate.addCandidate(c3);

            Candidate c4 = new Candidate("Luke \"Ming\"", "Flanagan", "Independent");
            c4.setImage(ImageIO.read(Candidate.class.getResourceAsStream("/test images/luke ming flanagan.jpg")));
            Candidate.addCandidate(c4);

            Candidate c5 = new Candidate("Micheal", "Martin", "Fine Fail");
            c5.setImage(ImageIO.read(Candidate.class.getResourceAsStream("/test images/micheal martin.jpg")));
            Candidate.addCandidate(c5);

            Candidate c6 = new Candidate("Stephen", "Donnelly", "Independent");
            c6.setImage(ImageIO.read(Candidate.class.getResourceAsStream("/test images/stephen donnelly.jpg")));
            Candidate.addCandidate(c6);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void windowClosing(WindowEvent e) {
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit the application?",
                "Exit Application", JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION) {
            System.exit(0);
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}
}
