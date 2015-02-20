package itcarlow.c00112726.vote.util;

import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class GUIUtilities {

    private GUIUtilities() {
    }

    /**
     * GridBagLayout is highly flexible, but setting all the constraints for each component
     * can lead to the code becoming convoluted. A simple helper method can remove the extra
     * code and make adding components using GridBagLayout easier and cleaner.
     * @param panel The JPanel we are adding to
     * @param component The component we wish to add
     * @param x The column to add the component to
     * @param y The row to add the component to
     * @param width The width the component should be
     * @param height The height the the component should be
     * @param anchor The alignment of component
     */
    public static void addGridItem(JPanel panel, JComponent component, int x, int y, int width, int height, int anchor) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.weightx = 0.5;      // a hint on apportioning space
        gbc.weighty = 0.5;
        gbc.insets = new Insets(5, 5, 5, 5);   // padding
        gbc.anchor = anchor;    // applies if fill is NONE
        gbc.fill = GridBagConstraints.NONE;

        panel.add(component, gbc);
    }

}
