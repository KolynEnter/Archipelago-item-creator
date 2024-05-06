import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class UIObject {

    private final int windowWidth = 400;
    private final int windowHeight = 600;
    private final Font font = new Font("Georgia", Font.BOLD, 25);

    private JPanel screen;

    private JTextField searchField;
    private JScrollPane feedbackScrollPane;
    private JPanel feedbackPanel;

    private JPanel respondPanel;
    private JPanel itemPanel;

    public UIObject() {

    }

    public void pressItem(Mode.ChoiceHandler ch, Item item) {
        feedbackScrollPane.setVisible(false);
        itemPanel = assignJPanel(windowHeight*2/3);
        JPanel southPanel = assignSouthPanel(ch, item.getDescription());
        itemPanel.add(southPanel, "South");
        screen.add(itemPanel);
        itemPanel.add(nameLabel(item.getName(), item.getCode()), "North");

        try {
            itemPanel.add(assignItemImage(item), "Center");
        } catch (IOException e) {
            e.printStackTrace();
        }

        screen.revalidate();
        screen.repaint();
    }

    private JPanel assignSouthPanel(Mode.ChoiceHandler ch, String itemDescription) {
        JPanel sp = assignJPanel(windowHeight*2/9);
        JButton backButton = assignBackButton(ch);
        sp.add(backButton, "South");
        sp.add(descriptionLabel(itemDescription), "Center");

        return sp;
    }

    private JLabel descriptionLabel(String description) {
        JLabel jLabel = assignJLabel("<html>"+description+"</html>");
        jLabel.setFont(new Font("Georgia", Font.BOLD, 15));
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);

        return jLabel;
    }

    public JPanel assignItemImage(Item item) throws IOException {
        JPanel imagePanel = assignJPanel(windowHeight*5/9);
        BufferedImage myPicture = ImageIO.read(new File(item.getPicture()));
        Image newImage = myPicture.getScaledInstance(myPicture.getWidth()*56/20, myPicture.getHeight()*56/20, Image.SCALE_DEFAULT);
        JLabel picLabel = new JLabel(new ImageIcon(newImage));
        imagePanel.add(picLabel);

        return imagePanel;
    }

    private JLabel nameLabel(String name, int code) {
        JLabel jLabel =  assignJLabel(name + ": " + code);
        jLabel.setFont(new Font("Georgia", Font.BOLD, 20));
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);

        return jLabel;
    }

    private JButton assignBackButton(Mode.ChoiceHandler ch) {
        return assignJButton("Back", "Back", ch);
    }

    public void getFeedBack(ArrayList<Item> allItems, Mode.ChoiceHandler ch) {
        feedbackPanel.removeAll();
        ArrayList<Item> targetItems = findItems(allItems);
        ArrayList<JButton> combines = new ArrayList<>();

        for (Item item : targetItems) {
            JButton button = new JButton();
            button.setText(item.getName());
            button.setActionCommand(item.getName());
            combines.add(button);
        }
        feedbackPanel.setPreferredSize(new Dimension(windowWidth, combines.size() * windowHeight/8));

         for (JButton button : combines) {
             button.setPreferredSize(new Dimension(windowWidth, windowHeight/8));
             button.setBackground(Color.white);
             button.setForeground(Color.darkGray);
             button.setFont( new Font("Georgia", Font.BOLD, 15));
             button.addActionListener(ch);
             button.setFocusPainted(false);
             feedbackPanel.add(button);
         }

        feedbackPanel.revalidate();
        feedbackPanel.repaint();
    }

    private ArrayList<Item> findItems(ArrayList<Item> allItems) {
        // find items that have name starts with the string in search field.
        String searchString = searchField.getText();
        ArrayList<Item> targetItems = new ArrayList<>();

        for (Item item : allItems) {
            if (item.getName().toLowerCase().contains(searchString.toLowerCase())) {
                targetItems.add(item);
            }
        }

        return targetItems;
    }

    public void refreshSearchField() {
        searchField.setText("");
        searchField.revalidate();
        searchField.repaint();
    }

    public JPanel assignScreen(Mode.ChoiceHandler ch) {
        screen = assignJPanel(windowHeight);
        assignSearchField(ch);
        screen.add(searchField, "North");
        assignFeedbackScrollPane();
        screen.add(feedbackScrollPane, "Center");
        assignRespondPanel(ch);
        screen.add(respondPanel, "South");

        return screen;
    }

    private void assignSearchField(Mode.ChoiceHandler ch) {
        searchField = new JFormattedTextField();
        searchField.addActionListener(ch);
        searchField.setFont(font);
        searchField.setForeground(Color.white);
        searchField.setBackground(Color.black);
    }

    private void assignFeedbackPanel() {
        feedbackPanel = assignJPanel(windowHeight*2/3);
        feedbackPanel.setLayout(new FlowLayout());
    }

    private void assignFeedbackScrollPane() {
        assignFeedbackPanel();
        feedbackScrollPane = new JScrollPane(feedbackPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        feedbackScrollPane.setMinimumSize(new Dimension(windowWidth, windowHeight*2/3));
        feedbackScrollPane.setPreferredSize(new Dimension(windowWidth, windowHeight*2/3));
    }

    private void assignRespondPanel(Mode.ChoiceHandler ch) {
        respondPanel = assignJPanel(windowHeight/5);
        respondPanel.setLayout(new GridLayout(2, 2));
        JButton refreshButton = assignRefreshButton(ch);
        JButton confirmButton = assignConfirmButton(ch);
        JButton updateButton = assignUpdateButton(ch);
        JButton appendButton = assignAppendButton(ch);

        respondPanel.add(refreshButton);
        respondPanel.add(confirmButton);
        respondPanel.add(appendButton);
        respondPanel.add(updateButton);
    }

    private JButton assignAppendButton(Mode.ChoiceHandler ch) {
        return assignJButton("Append", "Append", ch);
    }

    private JButton assignUpdateButton(Mode.ChoiceHandler ch) {
        return assignJButton("Update", "Update", ch);
    }

    private JButton assignRefreshButton(Mode.ChoiceHandler ch) {
        return assignJButton("Refresh", "Refresh", ch);
    }

    private JButton assignConfirmButton(Mode.ChoiceHandler ch) {
        return assignJButton("Confirm", "Confirm", ch);
    }

    public JPanel assignJPanel(int height) {
        JPanel screen = new JPanel();
        screen.setBackground(Color.white);
        screen.setPreferredSize(new Dimension(windowWidth, height));
        screen.setLayout(new BorderLayout());
        return screen;
    }

    public JLabel assignJLabel(String content) {
        JLabel jLabel = new JLabel(content);
        jLabel.setForeground(Color.darkGray);
        jLabel.setFont(font);

        return jLabel;
    }

    public JButton assignJButton(String text, String command, Mode.ChoiceHandler ch) {
        JButton jButton = new JButton(text);
        jButton.setBackground(Color.white);
        jButton.setForeground(Color.darkGray);
        jButton.setFont(font);
        jButton.setOpaque(true);
        jButton.setBorderPainted(false);
        jButton.addActionListener(ch);
        jButton.setActionCommand(command);
        jButton.setFocusPainted(false);

        return jButton;
    }

    public JFrame assignWindow() {
        JFrame window = new JFrame();
        window.setSize(windowWidth, windowHeight);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.black);
        window.setLayout(new BorderLayout());
        window.setResizable(false);
        return window;
    }

    public Container assignContainer(JFrame window) {
        Container container = window.getContentPane();
        container.setPreferredSize(new Dimension(windowWidth, windowHeight));

        return container;
    }

    public String getCurrentField() {
        return searchField.getText();
    }

    public JScrollPane getFeedbackScrollPane() {
        return feedbackScrollPane;
    }

    public JPanel getItemPanel() {
        return itemPanel;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }
}
