package ui;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import model.Player;
import model.Server;
import persistence.JsonReader;
import persistence.JsonWriter;

public class GuiCriticalOpsGame {
    private static final String JSON_STORE = "./data/server.json";
    private Server sv;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JFrame frame;

    public GuiCriticalOpsGame() {
        runGame();
    }

    private void runGame() {
        sv = new Server();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        frame = new JFrame("Critical Ops Matchmaking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(540, 140);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);
        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        createButton(panel, "Create Player", 10, 10, e -> createPlayer());
        createButton(panel, "View Players", 180, 10, e -> viewPlayers());
        createButton(panel, "Queue Matchmaking", 10, 70, e -> queueMatchmaking());
        createButton(panel, "Claim Rewards", 180, 70, e -> claimRewards());
        createButton(panel, "View Match History", 350, 70, e -> viewMatchHistory());
        createButton(panel, "Report Player", 350, 10, e -> reportPlayer());
        createButton(panel, "View Skins", 10, 40, e -> viewSkins());
        createButton(panel, "Save Server", 180, 40, e -> saveServer());
        createButton(panel, "Load Server", 350, 40, e -> loadServer());
    }

    private void createButton(JPanel panel, String text, int x, int y, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 160, 25);
        panel.add(button);
        button.addActionListener(actionListener);
    }

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
        playersFrame.setVisible(true);
    }

    private void queueMatchmaking() {
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

    private void viewMatchHistory() {
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
            historyFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(frame, "Player does not exist in the server. Please try again.");
        }
    }

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
            skinsFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(frame, "Player does not exist in the server. Please try again.");
        }
    }

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

    private void loadServer() {
        try {
            sv = jsonReader.read();
            JOptionPane.showMessageDialog(frame, "Loaded server from " + JSON_STORE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Unable to read from file: " + JSON_STORE);
        }
    }
}


