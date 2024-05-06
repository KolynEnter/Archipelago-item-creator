import java.io.*;
import java.util.ArrayList;

// If you need to append/update an item, you must exactly type its name!

public class FileManager {

    private final ArrayList<Item> allItem = new ArrayList<>();

    public FileManager() {
        readAllItems();
    }

    public static void main(String[] args) {
        FileManager fileManager = new FileManager();
        fileManager.updateItem("Animal Teeth");
        fileManager.printAllItem();
    }

    public void readAllItems() {
        int count = 1;

        FileInputStream f = null;
        try {
            f = new FileInputStream("src/ItemList");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert f != null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(f));
        while (true) {
            try {
                if (!reader.ready()) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Item item = assignItem(reader.readLine(), count);
                allItem.add(item);
                count++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void appendNewItem(String itemLine) {
        // follow this format
        // item name@@description@@@pic name
        // or
        // item name

        if (containedAlready(itemLine) || itemLine.equals("")) {
            return;
        }

        try {
            Item item = assignItem(itemLine, 0);
            String line = item.getName();
            line += "@@" + item.getDescription();
            line += "@@@" + item.getPicture();

            Writer output = new BufferedWriter(new FileWriter("src/ItemList", true));
            output.append(line);
            output.append(System.lineSeparator());
            output.close();
            allItem.add(assignItem(itemLine, allItem.size()+1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateItem(String itemLine) {
        if (!containedAlready(itemLine) || itemLine.equals("")) {
            return;
        }

        Item item = assignItem(itemLine, 0);

        int targetCode = findCode(item.getName());
        item.setCode(targetCode);

        String[] tuple = itemLine.split("@@@");
        String nameAndDescription = tuple[0];

        // item name@@some description
        if (tuple.length == 1 && nameAndDescription.split("@@").length > 1) {
            item.setPicture(allItem.get(targetCode-1).getPicture());
        }

        // item name@@@some png
        if (tuple.length > 1 && nameAndDescription.split("@@").length == 1) {
            item.setDescription(allItem.get(targetCode-1).getDescription());
        }

        allItem.remove(targetCode-1);
        allItem.add(targetCode-1, item);
        updateFile();
    }

    private void updateFile() {
        try {
            FileWriter log = new FileWriter("src/ItemList");
            PrintWriter printWriter = new PrintWriter(log, false);
            printWriter.flush();
            for (Item item : allItem) {
                String line = item.getName();
                line += "@@" + item.getDescription();
                line += "@@@" + item.getPicture();
                log.write(line);
                log.write(System.lineSeparator());
            }

            printWriter.close();
            log.close();
        } catch (IOException e) {
            System.out.println("COULD NOT LOG!!");
            e.printStackTrace();
        }
    }

    public Item assignItem(String line, int code) {
        String[] item = line.split("@@@");
        String[] nameAndDescription = item[0].split("@@");
        String newItemName = nameAndDescription[0];
        String newItemDescription = null;
        if (nameAndDescription.length > 1) {
            newItemDescription = nameAndDescription[1];
        }
        String newItemPic = null;
        if (item.length > 1) {
            if (item[1].contains("src/")) {
                newItemPic = item[1];
            } else {
                newItemPic = "src/ItemPics/"+item[1];
            }
        }

        return new Item(newItemName, code, newItemDescription, newItemPic);
    }

    private boolean containedAlready(String itemLine) {
        String target = itemLine.split("@@@")[0].split("@@")[0];

        for (Item item : allItem) {
            if (item.getName().equals(target)) {
                return true;
            }
        }

        return false;
    }

    private int findCode(String newItemName) {
        for (Item item : allItem) {
            if (item.getName().equals(newItemName)) {
                return item.getCode();
            }
        }
        return -1;
    }

    public void printAllItem() {
        for (Item item : allItem) {
            System.out.println(item);
        }
    }

    public Item getCorrespondingItem(String itemName) {
        for (Item item : allItem) {
            if (item.getName().equals(itemName)) {
                return item;
            }
        }

        return null;
    }

    public ArrayList<Item> getAllItem() {
        return allItem;
    }
}
