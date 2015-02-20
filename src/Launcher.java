import itcarlow.c00112726.vote.gui.Window;

import javax.swing.SwingUtilities;

/**
 * Created by Mike on 18/02/2015.
 */
public class Launcher {
    public static  void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                final Window window = new Window();
                window.setVisible(true);
            }
        });
    }
}
