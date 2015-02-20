package itcarlow.c00112726.vote.gui;

import itcarlow.c00112726.vote.datastructures.LinkedList;
import itcarlow.c00112726.vote.entity.Candidate;
import itcarlow.c00112726.vote.util.GUIUtilities;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class DeleteCandidateDialog extends JDialog {

    private final int width = 520;
    private final int height = 520;

    private JPanel contentPane;
    private JPanel candidatePanel;
    private JScrollPane scrollPane;

    private LinkedList<CandidatePanel> allCandidatePanels;

    public DeleteCandidateDialog(JFrame parent) {
        super(parent, "Delete Candidate(s)", true);
        setLayout(new BorderLayout());

        allCandidatePanels = new LinkedList<>();

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

        final JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> dispose());

        final JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(e -> clear());

        final JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> delete());

        btnPanel.add(btnCancel);
        btnPanel.add(btnClear);
        btnPanel.add(btnDelete);
        add(btnPanel, BorderLayout.SOUTH);

        final LinkedList<Candidate> candidates = Candidate.getCandidateList();
        int size = candidates.size();
        for(int i = 0; i < size; i++) {
            final Candidate current = candidates.get(i);
            final CandidatePanel panel = new CandidatePanel(current);
            GUIUtilities.addGridItem(candidatePanel, panel, 0, i, 1, 1, GridBagConstraints.CENTER);
            allCandidatePanels.add(panel);
        }
    }

    private void delete() {
        for(int i = 0; i < allCandidatePanels.size(); i++) {
            final CandidatePanel cp = allCandidatePanels.get(i);
            if(cp.markedForDeletion()) {
                final Candidate current = cp.getCandidate();
                Candidate.deleteCandidate(current);
                allCandidatePanels.remove(i);
                candidatePanel.remove(cp);
                candidatePanel.revalidate();
                candidatePanel.repaint();
                JOptionPane.showMessageDialog(this, "The following Candidate was removed:\n" + current, "Candidate Deleted", JOptionPane.INFORMATION_MESSAGE);
                i--;
            }
        }
    }

    private void clear() {
        for(CandidatePanel p : allCandidatePanels) {
            p.setMarkedForDeletion(false);
        }
    }

    private static class CandidatePanel extends JPanel {

        private Candidate candidate;

        private JTextField txtFullName;
        private JTextField txtParty;
        private JCheckBox chkDelete;

        public CandidatePanel(Candidate candidate) {
            super();
            this.candidate = candidate;

            setLayout(new GridBagLayout());
            setBorder(new EtchedBorder());
            generateGUI();
        }

        private Candidate getCandidate() {
            return candidate;
        }

        public void setMarkedForDeletion(boolean delete) {
            chkDelete.setSelected(delete);
        }

        private boolean markedForDeletion() {
            return chkDelete.isSelected();
        }

        private void generateGUI() {

            final JPanel imagePanel = new JPanel() {
                @Override
                public Dimension getPreferredSize() {
                    return new Dimension(128, 128);
                }
            };
            imagePanel.setBorder(new TitledBorder("Candidate"));
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

            GUIUtilities.addGridItem(this, new JLabel("Delete:"), 2, 2, 2, 1, GridBagConstraints.EAST);
            chkDelete = new JCheckBox();
            GUIUtilities.addGridItem(this, chkDelete, 4, 2, 2, 1, GridBagConstraints.WEST);
        }
    }
}
