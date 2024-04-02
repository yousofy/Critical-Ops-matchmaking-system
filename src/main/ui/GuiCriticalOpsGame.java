package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import model.Event;
import model.EventLog;
import model.Player;
import model.Server;
import persistence.JsonReader;
import persistence.JsonWriter;

// Description: Critical Ops Matchmaking system graphical based interface with the
//              code being inspired by the example projects and java documentation
public class GuiCriticalOpsGame {
    private static final String JSON_STORE = "./data/server.json";
    private Server sv;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JFrame frame;
    private EventLog el = EventLog.getInstance();

    // MODIFIES: this
    // EFFECTS: runs the matchmaking application by displaying a splash image first
    public GuiCriticalOpsGame() {
        splashScreen();
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        runGame();
    }

    // MODIFIES: this
    // EFFECTS: displays a splash screen with the Critical Ops logo for 2.5 seconds
    private void splashScreen() {
        JFrame splashScreen = new JFrame();
        splashScreen.setUndecorated(true);
        splashScreen.setSize(540, 540);
        splashScreen.setLocationRelativeTo(null);

        ImageIcon imageIcon = new ImageIcon("data/splash image.jpg");
        JLabel label = new JLabel(imageIcon);
        splashScreen.add(label);

        splashScreen.setVisible(true);

        new Timer(2500, e -> splashScreen.dispose()).start();
    }

    // MODIFIES: this
    // EFFECTS: constructs application and creates the panel
    private void runGame() {
        sv = new Server();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        frame = new JFrame("Critical Ops Matchmaking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(540, 540);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            // EFFECTS: Prints all the events to console when the window is closed
            public void windowClosing(WindowEvent e) {
                for (Event next : el) {
                    System.out.println(next.toString() + "\n");
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: places all the necessary components on the panel
    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        createButton(panel, "Create Player", 10, 140, e -> createPlayer());
        createButton(panel, "View Players", 180, 140, e -> viewPlayers());
        createButton(panel, "Queue Matchmaking", 10, 305, e -> matchmake());
        createButton(panel, "Claim Rewards", 180, 305, e -> claimRewards());
        createButton(panel, "View Match History", 350, 305, e -> viewHistory());
        createButton(panel, "Report Player", 350, 140, e -> reportPlayer());
        createButton(panel, "View Skins", 10, 470, e -> viewSkins());
        createButton(panel, "Save Server", 180, 470, e -> saveServer());
        createButton(panel, "Load Server", 350, 470, e -> loadServer());

        createImage(panel, "data/profile icon.png", 10, 10);
        createImage(panel, "data/players icon.jpg", 180, 10);
        createImage(panel, "data/matchmaking icon.jpeg", 10, 175);
        createImage(panel, "data/rewards icon.jpg", 180, 175);
        createImage(panel, "data/history icon.jpeg", 350, 175);
        createImage(panel, "data/report icon.jpeg", 350, 10);
        createImage(panel, "data/gun icon.jpg", 10, 340);
        createImage(panel, "data/save icon.jpg", 180, 340);
        createImage(panel, "data/load icon.jpg", 350, 340);
    }

    // MODIFIES: this
    // EFFECTS: adds image to the panel with the given image path and position
    private void createImage(JPanel panel, String imagePath, int x, int y) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image scaledImage = icon.getImage().getScaledInstance(125, -1, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImage);
        JLabel label = new JLabel(icon);
        label.setBounds(x,y,160, icon.getIconHeight());
        panel.add(label);
    }

    // MODIFIES: this
    // EFFECTS: adds button to the panel with the given ActionListener, name and position
    private void createButton(JPanel panel, String text, int x, int y, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 160, 25);
        panel.add(button);
        button.addActionListener(actionListener);
    }

    // MODIFIES: this
    // EFFECTS: creates a player with the given name and game mode
    private void createPlayer() {
        String name = JOptionPane.showInputDialog(frame, "Enter player name:");
        if (name == null) {
            return;
        }
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Invalid player name. Please try again.");
            return;
        }
        Object[] possibleValues = {"1v1", "Defuse", "GunGame"};
        Object selectedValue = JOptionPane.showInputDialog(frame, "Choose a game mode:", "Game Mode",
                JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);
        if (selectedValue != null) {
            Player player = new Player(name, selectedValue.toString());
            String output = sv.addPlayer(player);
            JOptionPane.showMessageDialog(frame, output);
        }
    }

    // EFFECTS: displays all current players in the server
    private void viewPlayers() {
        String[] columnNames = {"Name", "Game Mode", "Rank", "Banned"};

        Object[][] data = new Object[sv.getPlayers().size()][4];
        int i = 0;
        for (Player player : sv.getPlayers()) {
            data[i][0] = player.getName();
            data[i][1] = player.getGameMode();
            data[i][2] = player.getDisplayRank();
            data[i][3] = player.isBanned();
            i++;
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        JFrame playersFrame = new JFrame("Players");
        playersFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        playersFrame.add(scrollPane);
        playersFrame.pack();
        playersFrame.setLocationRelativeTo(null);
        playersFrame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: finds a match for the given player if possible
    private void matchmake() {
        String name = JOptionPane.showInputDialog(frame, "Enter the name of the player you would like"
                + " to start matchmaking for:");
        if (name == null || sv.findPlayer(name) == null) {
            JOptionPane.showMessageDialog(frame, "Player does not exist in the server. Please try again.");
            return;
        }
        Object[] possibleValues = {"Win", "Lose"};
        Object selectedValue = JOptionPane.showInputDialog(frame, "Choose the outcome "
                        + "of the matchmaking:", "Match Outcome",
                JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);
        if (selectedValue != null) {
            boolean win = selectedValue.equals("Win");
            JOptionPane.showMessageDialog(frame, sv.matchmaking(sv.findPlayer(name), win));
        }
    }

    // MODIFIES: this
    // EFFECTS: claims rewards for the given player if applicable
    private void claimRewards() {
        String name = JOptionPane.showInputDialog(frame, "Enter the name of the player you would "
                + "like to claim rewards for:");
        if (name == null) {
            return;
        }
        if (sv.findPlayer(name) != null) {
            JOptionPane.showMessageDialog(frame, sv.findPlayer(name).claimSkins());
        } else {
            JOptionPane.showMessageDialog(frame, "Player does not exist in the server. Please try again.");
        }
    }

    // EFFECTS: displays the given player's match history
    private void viewHistory() {
        String name = JOptionPane.showInputDialog(frame, "Enter the name of the player you"
                + " would like to view the history of:");
        if (name == null) {
            return;
        }
        Player player = sv.findPlayer(name);
        if (player != null) {
            JFrame historyFrame = new JFrame("Match History");
            historyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            JTextArea textArea = new JTextArea();
            for (String match : player.getHistory()) {
                textArea.append(match + "\n");
            }
            historyFrame.add(new JScrollPane(textArea));
            historyFrame.pack();
            historyFrame.setLocationRelativeTo(null);
            historyFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(frame, "Player does not exist in the server. Please try again.");
        }
    }

    // MODIFIES: this
    // EFFECTS: reports the player specified
    private void reportPlayer() {
        String name = JOptionPane.showInputDialog(frame, "Enter the name of the player you would like to report:");
        if (name == null) {
            return;
        }
        if (sv.findPlayer(name) != null) {
            JOptionPane.showMessageDialog(frame, sv.findPlayer(name).handleReport());
        } else {
            JOptionPane.showMessageDialog(frame, "Player does not exist in the server. Please try again.");
        }
    }

    // EFFECTS: displays the given player's skins in their inventory
    private void viewSkins() {
        String name = JOptionPane.showInputDialog(frame, "Enter the name of the player you"
                + " would like to view the inventory of:");
        if (name == null) {
            return;
        }
        Player player = sv.findPlayer(name);
        if (player != null) {
            JFrame skinsFrame = new JFrame("Skins");
            skinsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            JTextArea textArea = new JTextArea();
            for (String skin : player.getSkins()) {
                textArea.append(skin + "\n");
            }
            skinsFrame.add(new JScrollPane(textArea));
            skinsFrame.pack();
            skinsFrame.setLocationRelativeTo(null);
            skinsFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(frame, "Player does not exist in the server. Please try again.");
        }
    }

    // EFFECTS: saves the server to file
    private void saveServer() {
        try {
            jsonWriter.open();
            jsonWriter.write(sv);
            jsonWriter.close();
            JOptionPane.showMessageDialog(frame, "Saved server to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(frame, "Unable to write to file: " + JSON_STORE);
        }   
    }

    // MODIFIES: this
    // EFFECTS: loads server from file
    private void loadServer() {
        try {
            sv = jsonReader.read();
            JOptionPane.showMessageDialog(frame, "Loaded server from " + JSON_STORE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Unable to read from file: " + JSON_STORE);
        }
    }
}