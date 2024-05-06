/*
    Description: this program is used for creating items in the game "Archipelago".
 */

public class ItemCreator {

    private UI ui = new UI();
    private Mode mode = new Mode(ui);

    public ItemCreator() {
        ui.createUI(mode.getCh());
    }

    public static void main(String[] args) {
        ItemCreator ItemCreator = new ItemCreator();
    }
}
