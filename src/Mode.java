import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Mode {

    private final UI ui;
    private final ChoiceHandler ch = new ChoiceHandler();

    private FileManager fileManager = new FileManager();

    public Mode(UI ui) {
        this.ui = ui;
    }

    public class ChoiceHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String actionCommand = e.getActionCommand();
            switch (actionCommand) {
                case "Refresh":
                    ui.refreshSearchField();
                    break;
                case "Confirm":
                    ui.getFeedBack(fileManager.getAllItem(), ch);
                    break;
                case "Back":
                    ui.getFeedbackScrollPane().setVisible(true);
                    ui.getItemPanel().setVisible(false);
                    break;
                case "Append":
                    String field = ui.getCurrentField();
                    fileManager.appendNewItem(field);
                    break;
                case "Update":
                    field = ui.getCurrentField();
                    fileManager.updateItem(field);
                    break;
                default:
                    Item item = fileManager.getCorrespondingItem(actionCommand);
                    ui.pressItem(ch, item);
                    break;
            }
        }
    }

    public UI getUi() {
        return ui;
    }

    public ChoiceHandler getCh() {
        return ch;
    }
}
