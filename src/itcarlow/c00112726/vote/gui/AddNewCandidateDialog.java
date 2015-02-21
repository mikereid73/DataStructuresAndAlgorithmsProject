package itcarlow.c00112726.vote.gui;

import itcarlow.c00112726.vote.entity.Candidate;
import itcarlow.c00112726.vote.util.GUIUtilities;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AddNewCandidateDialog extends JDialog {

    private JPanel contentPane;
    private JPanel imagePanel;

    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JComboBox<String> cmbParty;
    private BufferedImage image;

    private static final int DEFAULT_IMAGE_WIDTH = 128;
    private static final int DEFAULT_IMAGE_HEIGHT = 128;

    // Temporary
    private final String[] parties = {
            "- SELECT -",
            "Fine Fail",
            "Fine Gael",
            "Sinn Fein",
            "Labour",
            "Independent"
    };

    public AddNewCandidateDialog(JFrame parent) {
        super(parent, "Add New Candidate", true);
        setLayout(new BorderLayout());

        generateGUI();

        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    private void generateGUI() {
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        imagePanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(DEFAULT_IMAGE_WIDTH, DEFAULT_IMAGE_HEIGHT);
            }

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
            }
        };
        imagePanel.setBorder(new TitledBorder(""));
        GUIUtilities.addGridItem(mainPanel, imagePanel, 0, 0, 1, 3, GridBagConstraints.CENTER);

        GUIUtilities.addGridItem(mainPanel, new JLabel("First Name:"), 1, 0, 1, 1, GridBagConstraints.EAST);
        GUIUtilities.addGridItem(mainPanel, new JLabel("Last Name:"), 1, 1, 1, 1, GridBagConstraints.EAST);
        GUIUtilities.addGridItem(mainPanel, new JLabel("Party:"), 1, 2, 1, 1, GridBagConstraints.EAST);

        txtFirstName = new JTextField(15);
        GUIUtilities.addGridItem(mainPanel, txtFirstName, 2, 0, 1, 1, GridBagConstraints.WEST);

        txtLastName = new JTextField(15);
        GUIUtilities.addGridItem(mainPanel, txtLastName, 2, 1, 1, 1, GridBagConstraints.WEST);

        cmbParty = new JComboBox<>(parties);
        cmbParty.setSelectedIndex(-1);
        GUIUtilities.addGridItem(mainPanel, cmbParty, 2, 2, 300, 1, GridBagConstraints.WEST);

        final JButton btnBrowse = new JButton("Browse");
        btnBrowse.addActionListener(e -> browseImage());
        GUIUtilities.addGridItem(mainPanel, btnBrowse, 0, 3, 1, 1, GridBagConstraints.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        // Add the buttons to the form, OK and Cancel. Require a different layout
        // so we add them to their own panel.
        final JPanel btnPanel = new JPanel(); // Components added right to left
        btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        final JButton  btnOK = new JButton("OK");
        btnOK.addActionListener(e -> submit());

        final JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> dispose());

        btnPanel.add(btnCancel);
        btnPanel.add(btnOK);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private boolean validateInput() {
        String firstName = txtFirstName.getText().trim();
        String lastName = txtLastName.getText().trim();
        int party = cmbParty.getSelectedIndex();
        if (firstName.equals("") || lastName.equals("") || party < 0) {
            return false;
        }
        return true;
    }

    private void submit() {
        if(!validateInput()) {
            JOptionPane.showMessageDialog(this, "One or more fields are invalid.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else {
            final String firstName = txtFirstName.getText().trim();
            final String lastName = txtLastName.getText().trim();
            final String party = cmbParty.getSelectedItem().toString();
            final Candidate candidate = new Candidate(firstName, lastName, party);
            if(image != null) {
                candidate.setImage(image);
            }
            Candidate.addCandidate(candidate);
            final String message = "The following Candidate has been added\n" +
                    "\nName: " + firstName + " " + lastName + "\nParty: " + party;
            JOptionPane.showMessageDialog(this, message, "Candidate Added", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }

    private void browseImage() {
        JFileChooser fc = new JFileChooser();
        // Obtain image file types supported by Java
        final String[] suffices = ImageIO.getReaderFileSuffixes();
        // Create a filter for each file type and apply it to the file chooser
        for (int i = 0; i < suffices.length; i++) {
            FileFilter filter = new FileNameExtensionFilter(suffices[i], suffices[i]);
            fc.addChoosableFileFilter(filter);
        }
        //fc.setAcceptAllFileFilterUsed(false);

        // Display it
        final int result = fc.showDialog(this, "Select a file to open");
        if(result == JFileChooser.APPROVE_OPTION) {
            try {
                final File file = fc.getSelectedFile();
                image = ImageIO.read(file);
                contentPane.revalidate();
                contentPane.repaint();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        else {
            dispose();
        }
    }
}
