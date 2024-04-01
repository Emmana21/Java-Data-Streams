import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                FileSearchGUI gui = new FileSearchGUI();
                gui.setVisible(true);
            }
        });
    }
}
