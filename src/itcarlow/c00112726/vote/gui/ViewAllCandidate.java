package itcarlow.c00112726.vote.gui;

import itcarlow.c00112726.vote.datastructures.LinkedList;
import itcarlow.c00112726.vote.entity.Candidate;
import itcarlow.c00112726.vote.util.GUIUtilities;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

public class ViewAllCandidate extends JDialog {

    private final int width = 520;
    private final int height = 520;

    private JPanel contentPane;
    private JPanel candidatePanel;
    private JScrollPane scrollPane;

    public ViewAllCandidate(JFrame parent) {
        super(parent, "Add New Candidate", true);
        setLayout(new BorderLayout());

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
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        candidatePanel = new JPanel(new GridBagLayout());
        scrollPane = new JScrollPane(candidatePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        final JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        final JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dispose());

        btnPanel.add(okButton);
        add(btnPanel, BorderLayout.SOUTH);

        final LinkedList<Candidate> candidates = Candidate.getCandidateList();
        int size = candidates.size();
        for(int i = 0; i < size; i++) {
            final Candidate current = candidates.get(i);
            final CandidatePanel panel = new CandidatePanel(current);
            GUIUtilities.addGridItem(candidatePanel, panel, 0, i, 1, 1, GridBagConstraints.CENTER);
        }
    }

    private static class CandidatePanel extends JPanel {

        private Candidate candidate;

        private JTextField txtFullName;
        private JTextField txtParty;
        private JCheckBox chkDelete;

        private static final int DEFAULT_IMAGE_WIDTH = 128;
        private static final int DEFAULT_IMAGE_HEIGHT = 128;

        public CandidatePanel(Candidate candidate) {
            super();
            this.candidate = candidate;

            setLayout(new GridBagLayout());
            setBorder(new EtchedBorder());
            generateGUI();
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

            txtFullName = new JTextField(30);
            txtFullName.setEditable(false);
            txtFullName.setHorizontalAlignment(JTextField.CENTER);
            txtFullName.setText(candidate.getFirstName() + " " + candidate.getLastName());
            GUIUtilities.addGridItem(this, txtFullName, 2, 0, 4, 1, GridBagConstraints.CENTER);

            GUIUtilities.addGridItem(this, new JLabel("Party:"), 2, 1, 2, 1, GridBagConstraints.EAST);
            txtParty = new JTextField(15);
            txtParty.setEditable(false);
            txtParty.setHorizontalAlignment(JTextField.CENTER);
            txtParty.setText(candidate.getParty());
            GUIUtilities.addGridItem(this, txtParty, 4, 1, 2, 1, GridBagConstraints.WEST);
        }
    }
}
