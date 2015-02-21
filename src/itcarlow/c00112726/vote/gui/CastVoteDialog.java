package itcarlow.c00112726.vote.gui;

import itcarlow.c00112726.vote.datastructures.LinkedList;
import itcarlow.c00112726.vote.entity.BallotPaper;
import itcarlow.c00112726.vote.entity.Candidate;
import itcarlow.c00112726.vote.util.GUIUtilities;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class CastVoteDialog extends javax.swing.JDialog {

    private final int width = 520;
    private final int height = 520;

    private JPanel contentPane;
    private JPanel candidatePanel;
    private JScrollPane scrollPane;

    private LinkedList<CandidatePanel> allCandidates;

    public CastVoteDialog(javax.swing.JFrame parent) {
        super(parent, "Please Select Candidate Preferences", true);
        setLayout(new java.awt.BorderLayout());

        allCandidates = new LinkedList<>();

        generateGUI();

        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    private void generateGUI() {
        contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        candidatePanel = new JPanel(new GridBagLayout());
        scrollPane = new JScrollPane(candidatePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        final LinkedList<Candidate> candidates = Candidate.getCandidateList();
        int size = candidates.size();
        for(int i = 0; i < size; i++) {
            final Candidate current = candidates.get(i);
            final CandidatePanel panel = new CandidatePanel(current);
            GUIUtilities.addGridItem(candidatePanel, panel, 0, i, 1, 1, GridBagConstraints.CENTER);
            allCandidates.add(panel);
        }
        final JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        final JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> dispose());

        final JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(e -> clear());

        final JButton btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(e -> submit());

        btnPanel.add(btnCancel);
        btnPanel.add(btnClear);
        btnPanel.add(btnSubmit);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void submit() {
        if(!validateBallot()) {
            JOptionPane.showMessageDialog(this, "1 or more preference conflicts.", "Error", JOptionPane.OK_OPTION);
            return;
        }
        final BallotPaper paper = new BallotPaper();
        for(CandidatePanel p : allCandidates) {
            paper.addCandidate(p.getPreference(), p.getCandidate());
        }
        final Candidate winner = paper.removeCurrentWinner();
        winner.add(paper);
    }

    private boolean validateBallot() {
        final LinkedList<Integer> prefs = new LinkedList<>();

        for(CandidatePanel p : allCandidates) {
            int preference = p.getPreference();
            if(prefs.contains(preference) || preference < 0) {
                return false;
            }
            else {
                prefs.add(preference);
            }
        }

        return true;
    }

    private void clear() {
        for(CandidatePanel p : allCandidates) {
            p.setPreference(-1);
        }
    }

    private static class CandidatePanel extends JPanel {

        private Candidate candidate;
        private JComboBox<Integer> preferenceComboBox;

        private static final int DEFAULT_IMAGE_WIDTH = 128;
        private static final int DEFAULT_IMAGE_HEIGHT = 128;

        public CandidatePanel(Candidate candidate) {
            super();
            this.candidate = candidate;

            setLayout(new GridBagLayout());
            setBorder(new EtchedBorder());
            generateGUI();
        }

        public Candidate getCandidate() {
            return candidate;
        }

        public int getPreference() {
            int preference;
            try {
                preference = Integer.parseInt(preferenceComboBox.getSelectedItem().toString());
            }
            catch (Exception e) {
                preference = -1;
            }

            return preference;
        }

        public void setPreference(int i) {
            preferenceComboBox.setSelectedIndex(i);
        }

        private void generateGUI() {

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
            GUIUtilities.addGridItem(this, imagePanel, 0, 0, 2, 3, GridBagConstraints.CENTER);

            final JTextField nameTextField = new JTextField(30);
            nameTextField.setEditable(false);
            nameTextField.setHorizontalAlignment(JTextField.CENTER);
            nameTextField.setText(candidate.getFirstName() + " " + candidate.getLastName());
            GUIUtilities.addGridItem(this, nameTextField, 2, 0, 4, 1, GridBagConstraints.CENTER);

            final JLabel partyLabel = new JLabel("Party: ");
            GUIUtilities.addGridItem(this, partyLabel, 2, 1, 2, 1, GridBagConstraints.EAST);

            final JTextField partyNameTextField = new JTextField(15);
            partyNameTextField.setEditable(false);
            partyNameTextField.setHorizontalAlignment(JTextField.CENTER);
            partyNameTextField.setText(candidate.getParty());
            GUIUtilities.addGridItem(this, partyNameTextField, 4, 1, 2, 1, GridBagConstraints.WEST);

            final JLabel preferenceLabel = new JLabel("Preference: ");
            GUIUtilities.addGridItem(this, preferenceLabel, 2, 2, 2, 1, GridBagConstraints.EAST);

            preferenceComboBox = new JComboBox<>();
            for(int i = 1; i <= Candidate.numberOfCandidates(); i++) {
                preferenceComboBox.addItem(i);
            }
            preferenceComboBox.setSelectedIndex(-1);
            GUIUtilities.addGridItem(this, preferenceComboBox, 4, 2, 2, 1, GridBagConstraints.WEST);
        }
    }
}
