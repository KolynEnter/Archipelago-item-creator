import javax.swing.*;
import java.awt.*;

public class UI extends UIObject {

    private final JFrame window;
    private final Container container;
    private JPanel screen;

    public UI() {
        setLookAndFeel();
        window = assignWindow();
        window.setUndecorated(true);
        window.getRootPane().setWindowDecorationStyle(JRootPane.QUESTION_DIALOG);
        container = assignContainer(window);
    }

    public void createUI(Mode.ChoiceHandler ch) {
        screen = assignScreen(ch);
        container.add(screen, "Center");
        window.setVisible(true);
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
