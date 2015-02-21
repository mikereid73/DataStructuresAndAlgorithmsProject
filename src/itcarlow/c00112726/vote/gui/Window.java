package itcarlow.c00112726.vote.gui;

import itcarlow.c00112726.vote.entity.Candidate;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Mike on 18/02/2015.
 */
public class Window extends JFrame {

    private static final String TITLE = "Vote Counting App";
    private JPanel contentPane;
    private JScrollPane mainScrollPain;

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

        loadTestData();

        generateGUI();

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void generateGUI() {

        mainScrollPain = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(mainScrollPain, BorderLayout.CENTER);



        final JPanel toolPanel = new JPanel();
        toolPanel.setLayout(new GridLayout(3, 2, 10, 5));
        toolPanel.setBorder(new TitledBorder("Options"));

        final JButton btnAddNewCandidate = new JButton("Add New Candidate");
        btnAddNewCandidate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final AddNewCandidateDialog newCandidateDialog = new AddNewCandidateDialog(null);
                newCandidateDialog.setVisible(true);
            }
        });

        final JButton btnRemoveCandidate = new JButton("Delete Candidate");
        btnRemoveCandidate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final DeleteCandidateDialog deleteCandidateDialog = new DeleteCandidateDialog(null);
                deleteCandidateDialog.setVisible(true);
            }
        });

        final JButton btnViewAllCandidates = new JButton("View All Candidates");
        btnViewAllCandidates.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final ViewAllCandidate viewAllCandidate = new ViewAllCandidate(null);
                viewAllCandidate.setVisible(true);
            }
        });

        final JButton btnCastNewVote = new JButton("Cast New Vote");
        btnCastNewVote.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            final CastVoteDialog castVoteDialog = new CastVoteDialog(null);
                castVoteDialog.setVisible(true);
            }
        });

        final JButton btnKnockoutRound = new JButton("Knockout Round");
        btnKnockoutRound.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        final JButton btnExitApplication = new JButton("Exit");
        btnExitApplication.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the application?",
                        "Exit Application", JOptionPane.OK_CANCEL_OPTION);
                if(result == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });

        toolPanel.add(btnAddNewCandidate);
        toolPanel.add(btnCastNewVote);
        toolPanel.add(btnRemoveCandidate);
        toolPanel.add(btnKnockoutRound);
        toolPanel.add(btnViewAllCandidates);
        toolPanel.add(btnExitApplication);

        contentPane.add(toolPanel, BorderLayout.SOUTH);
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
}
