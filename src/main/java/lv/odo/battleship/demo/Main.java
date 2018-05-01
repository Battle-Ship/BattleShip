package lv.odo.battleship.demo;

import lv.odo.battleship.Cell;
import lv.odo.battleship.Controller;
import lv.odo.battleship.Field;
import lv.odo.battleship.Game;
import lv.odo.battleship.Helper;
import lv.odo.battleship.Player;
import lv.odo.battleship.SingleGameControllerImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public final static int PLAYER_NAME_BLINK_INTERVAL = 1000;

    public static final String PLAYER_NAME = "Player";

    public static final int CELL_SIZE = 30;

    //one dimension of each cell
    public static final int FIELD_DIMENSION = 10;

    //calculate game window size
    public static final int WINDOW_WIDTH = CELL_SIZE * 2 * FIELD_DIMENSION + CELL_SIZE * 4;

    //calculate game window height
    public static final int WINDOW_HEIGHT = CELL_SIZE * FIELD_DIMENSION + CELL_SIZE * 9;

    //maximal number of ships
    public static final int SHIPS = 20;

    //maximal length of ship
    public static final int SHIP_SIZE = 4;

    //possible configuration of fleet
    //it can have only 4 single ships, 3 double, 2 triple and 1 quadruple
    public static final int[] POSSIBLE_FLEET = {4, 3, 2, 1};

    private JFrame frame;
    private BoardTable leftTable;
    private BoardTable rightTable;
    private JPanel leftShipsPanel;
    private JPanel rightShipsPanel;
    private JLabel myLaunchLabel;
    private JLabel enemyLaunchLabel;

    private Game currentGame;

    private Status status = Status.GAME_SELECT;

    public enum Status {
        GAME_SELECT, PLACE_SHIP, ALL_SHIPS_PLACED, MY_TURN, ENEMY_TURN, ENEMY_PLACES_SHIPS, END_GAME
    }

    private JLabel gameStatusLabel;

    public static Controller processor = new SingleGameControllerImpl();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main window = new Main();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Main() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //we set layout to place game objects on game window
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu mnHelp = new JMenu("Help");
        menuBar.add(mnHelp);

        JTextPane txtpnHelpTextAbout = new JTextPane();
        txtpnHelpTextAbout.setText("Help Text About game");
        mnHelp.add(txtpnHelpTextAbout);

        JPanel panel = new JPanel();
        menuBar.add(panel);

        this.gameStatusLabel = new JLabel("");
        gameStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(gameStatusLabel);
        buildStartMenuWindow();
    }

    private void buildBattlefield() {
        this.currentGame = processor.createGame(new Player(PLAYER_NAME));
        JPanel bottom = new JPanel();
        frame.getContentPane().add(bottom, BorderLayout.SOUTH);

        this.myLaunchLabel = new JLabel();
        bottom.add(myLaunchLabel);
        myLaunchLabel.setVisible(false);
        Button buttonEnd = new Button("End game");
        bottom.add(buttonEnd);
        buttonEnd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                status = Status.GAME_SELECT;
                frame.getContentPane().removeAll();
                buildStartMenuWindow();
                frame.getContentPane().invalidate();
                frame.getContentPane().validate();
            }
        });
        this.enemyLaunchLabel = new JLabel();
        bottom.add(enemyLaunchLabel);
        this.enemyLaunchLabel.setVisible(false);
        Dimension dimension = new Dimension(CELL_SIZE * FIELD_DIMENSION, CELL_SIZE * FIELD_DIMENSION);
        JPanel left = new JPanel();
        FlowLayout leftLayout = (FlowLayout) left.getLayout();
        leftLayout.setVgap(0);
        left.setMinimumSize(dimension);
        frame.getContentPane().add(left, BorderLayout.WEST);
        JPanel leftField = new JPanel();
        left.add(leftField);
        leftField.setLayout(new BorderLayout(0, 0));
        leftTable = new BoardTable(processor.getMyField(0).getCells());
        leftField.add(leftTable, BorderLayout.CENTER);
        final JLabel playerName = new JLabel(currentGame.getMe().getName());
        leftField.add(playerName, BorderLayout.NORTH);
        playerName.setFont(new Font("Tahoma", Font.PLAIN, 22));
        playerName.setHorizontalAlignment(SwingConstants.CENTER);
        leftShipsPanel = new JPanel();
        leftShipsPanel.setLayout(new BoxLayout(leftShipsPanel, BoxLayout.Y_AXIS));
        leftField.add(leftShipsPanel, BorderLayout.SOUTH);
        refreshMyPanel(leftShipsPanel, processor.getMyField(currentGame.getId()));
        //we need to handle clicks on table with player field
        leftTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (status == Status.PLACE_SHIP || status == Status.ALL_SHIPS_PLACED) {
                    //we get column number
                    int column = leftTable.columnAtPoint(e.getPoint());
                    //and row number
                    int row = leftTable.rowAtPoint(e.getPoint());
                    //we get cell from table by column and row number
                    Cell target = (Cell) leftTable.getValueAt(row, column);
                    //We pass game id and cell to place our ship and get the result
                    int result = processor.placeShipInCell(currentGame.getId(), target);
                    if (result == 0) {
                        if (!target.isShip()) {
                            status = Status.PLACE_SHIP;
                            gameStatusLabel.setText("Ship is deleted");
                        } else {
                            gameStatusLabel.setText("Ship is placed");
                        }
                    } else if (result == 1) {
                        status = Status.ALL_SHIPS_PLACED;
                        gameStatusLabel.setText("All ships are placed");
                    }
                    updateMyField();
                } else {
                    gameStatusLabel.setText("You can't place ship in current game mode");
                }
            }
        });
        JPanel right = new JPanel();
        FlowLayout rightLayout = (FlowLayout) right.getLayout();
        rightLayout.setVgap(0);
        right.setMinimumSize(dimension);
        frame.getContentPane().add(right, BorderLayout.EAST);
        JPanel rightField = new JPanel();
        right.add(rightField);
        rightField.setLayout(new BorderLayout(0, 0));
        rightTable = new BoardTable(processor.getEnemyField(0).getCells());
        rightField.add(rightTable, BorderLayout.CENTER);
        final JLabel enemyName = new JLabel(currentGame.getEnemy().getName());
        rightField.add(enemyName, BorderLayout.NORTH);
        enemyName.setFont(new Font("Tahoma", Font.PLAIN, 22));
        enemyName.setHorizontalAlignment(SwingConstants.CENTER);

        refreshGameStatus();

        rightShipsPanel = new JPanel();

        rightShipsPanel.setLayout(new BoxLayout(rightShipsPanel, BoxLayout.Y_AXIS));
        rightField.add(rightShipsPanel, BorderLayout.SOUTH);

        Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
        frame.getContentPane().add(rigidArea, BorderLayout.CENTER);
        //for correct display of enemy statistics, we need to get actual information about enemy cells
        refreshEnemyPanel(rightShipsPanel, currentGame.getEnemy().getField());
        //we need to handle clicks on table with enemy field
        rightTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (status == Status.MY_TURN) {
                    //we get column number
                    int column = rightTable.columnAtPoint(e.getPoint());
                    //and row number
                    int row = rightTable.rowAtPoint(e.getPoint());
                    //we get cell from table by column and row number
                    Cell target = (Cell) rightTable.getValueAt(row, column);
                    //We pass game id and cell to make a shot and get the cell after shot
                    Cell updated = processor.shot(currentGame.getId(), target);
                    if (updated.getStatus() == '*') {
                        gameStatusLabel.setText("Missed");
                    } else if (updated.getStatus() == 'x') {
                        gameStatusLabel.setText("Hit!");
                    } else if (updated.getStatus() == '.') {
                        gameStatusLabel.setText("Killed!");
                    }
                    //refresh table model to see result
                    rightTable.refreshTable(processor.getEnemyField(currentGame.getId()).getCells());
                    refreshEnemyPanel(rightShipsPanel, currentGame.getEnemy().getField());
                } else {
                    gameStatusLabel.setText("You can't attack now");
                }
            }

        });
    }

    private JButton getShipIndicator(Color color, boolean transparent) {
        JButton ship = new JButton();
        ship.setHorizontalAlignment(SwingConstants.LEFT);
        ship.setForeground(new Color(255, 255, 0));
        ship.setPreferredSize(new Dimension(15, 15));
        ship.setBackground(color);
        if (transparent) {
            ship.setOpaque(false);
            ship.setContentAreaFilled(false);
            ship.setBorderPainted(true);
        }
        return ship;
    }

    //this method builds component with ship indicators in the bottom of player field
    private void refreshMyPanel(JPanel myPanel, Field field) {
        List<List<Cell>> fleet = Helper.processFleet(field);
        myPanel.removeAll();
        List<JPanel> panels = new ArrayList<JPanel>(SHIP_SIZE);
        for (int j = 0; j < SHIP_SIZE; j++) {
            JPanel panel = new JPanel();
            ((FlowLayout) panel.getLayout()).setAlignment(FlowLayout.LEFT);
            panels.add(panel);
            myPanel.add(panel);
        }
        int[] shipsLeft = POSSIBLE_FLEET.clone();
        for (int i = 0; i < fleet.size(); i++) {
            shipsLeft[fleet.get(i).size() - 1]--;
        }
        for (int i = 0; i < fleet.size(); i++) {
            JPanel group = panels.get(fleet.get(i).size() - 1);
            for (int j = 0; j < fleet.get(i).size(); j++) {
                if (fleet.get(i).get(j).getStatus() == 's') {
                    group.add(getShipIndicator(Color.YELLOW, false));
                } else {
                    group.add(getShipIndicator(Color.RED, false));
                }
            }
            group.add(Box.createRigidArea(new Dimension(5, 0)));
        }
        for (int i = 0; i < shipsLeft.length; i++) {
            int add = shipsLeft[i];
            for (int j = 0; j < add * (i + 1); j++) {
                panels.get(i).add(getShipIndicator(Color.WHITE, true));
                if ((j + 1) % (i + 1) == 0) {
                    panels.get(i).add(Box.createRigidArea(new Dimension(5, 0)));
                }
            }
        }
        frame.getContentPane().invalidate();
        frame.getContentPane().validate();
    }

    private void refreshEnemyPanel(JPanel shipPanel, Field field) {
        Cell[][] cells = field.getCells();
        shipPanel.removeAll();
        List<JPanel> panels = new ArrayList<JPanel>(SHIP_SIZE);
        for (int j = 0; j < SHIP_SIZE; j++) {
            JPanel panel = new JPanel();
            ((FlowLayout) panel.getLayout()).setAlignment(FlowLayout.LEFT);
            panels.add(panel);
            shipPanel.add(panel);
        }
        int playerLiveShips = 0;
        int playerKilledShips = 0;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j].getStatus() == 's') {
                    playerLiveShips++;
                }
                if (cells[i][j].getStatus() == 'x' || cells[i][j].getStatus() == '.') {
                    playerKilledShips++;
                }
            }
        }
        for (int i = 0; i < playerKilledShips + playerLiveShips; i++) {
            if (i < playerLiveShips) {
                panels.get(i % SHIP_SIZE).add(getShipIndicator(Color.YELLOW, false));
            } else {
                panels.get(i % SHIP_SIZE).add(getShipIndicator(Color.RED, false));
            }
        }
        frame.getContentPane().invalidate();
        frame.getContentPane().validate();
    }

    private void buildStartMenuWindow() {
        JPanel panelCenter = new JPanel();
        panelCenter.setBackground(new Color(224, 255, 255));
        panelCenter.setForeground(Color.BLUE);
        frame.getContentPane().add(panelCenter, BorderLayout.CENTER);
        panelCenter.setLayout(new BorderLayout(0, 60));

        JPanel panel0 = new JPanel();
        panel0.setBackground(new Color(224, 255, 255));
        FlowLayout fl_panel0 = (FlowLayout) panel0.getLayout();
        fl_panel0.setVgap(0);
        fl_panel0.setHgap(40);
        panelCenter.add(panel0, BorderLayout.NORTH);

        JButton btnSingleplayer = new JButton();
        ImageIcon singleplayerIcon = new ImageIcon("images/singleplayer.png");
        btnSingleplayer.setIcon(singleplayerIcon);
        // to remote the spacing between the image and button's borders
        btnSingleplayer.setMargin(new Insets(0, 0, 0, 0));
        // to remove the border
        btnSingleplayer.setBorder(null);
        btnSingleplayer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                status = Status.PLACE_SHIP;
                frame.getContentPane().removeAll();
                buildBattlefield();
                frame.getContentPane().invalidate();
                frame.getContentPane().validate();
            }
        });
        btnSingleplayer.setBackground(new Color(224, 255, 255));
        panel0.add(btnSingleplayer);

        JButton btnMultiplayer = new JButton();
        ImageIcon multiplayerIcon = new ImageIcon("images/multiplayer.png");

        Component rigidArea = Box.createRigidArea(new Dimension(10, 20));
        panel0.add(rigidArea);
        btnMultiplayer.setIcon(multiplayerIcon);
        // to remote the spacing between the image and button's borders
        btnMultiplayer.setMargin(new Insets(0, 0, 0, 0));
        // to remove the border
        btnMultiplayer.setBorder(null);
        btnMultiplayer.setBackground(new Color(224, 255, 255));
        panel0.add(btnMultiplayer);

        JPanel panel1 = new JPanel();
        panel1.setBackground(new Color(224, 255, 255));
        FlowLayout fl_panel1 = (FlowLayout) panel1.getLayout();
        fl_panel1.setVgap(0);
        fl_panel1.setHgap(20);
        panelCenter.add(panel1, BorderLayout.CENTER);

        JButton btnSettings = new JButton();
        ImageIcon settingsIcon = new ImageIcon("images/settings.png");
        btnSettings.setIcon(settingsIcon);
        // to remote the spacing between the image and button's borders
        btnSettings.setMargin(new Insets(0, 0, 0, 0));
        // to remove the border
        btnSettings.setBorder(null);
        btnSettings.setBackground(new Color(224, 255, 255));
        panel1.add(btnSettings);

        JButton btnExit = new JButton();
        ImageIcon exitIcon = new ImageIcon("images/exit.png");

        Component rigidArea_1 = Box.createRigidArea(new Dimension(30, 20));
        panel1.add(rigidArea_1);
        btnExit.setIcon(exitIcon);
        // to remote the spacing between the image and button's borders
        btnExit.setMargin(new Insets(0, 0, 0, 0));
        // to remove the border
        btnExit.setBorder(null);
        btnExit.setBackground(new Color(224, 255, 255));
        panel1.add(btnExit);
        //we need to handle click on button exit to exit program
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel panelTop = new JPanel();
        FlowLayout fl_panelTop = (FlowLayout) panelTop.getLayout();
        fl_panelTop.setVgap(30);
        panelTop.setBackground(new Color(224, 255, 255));
        frame.getContentPane().add(panelTop, BorderLayout.NORTH);

        JLabel lblNewLabel = new JLabel("Battleship");
        lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 35));
        lblNewLabel.setForeground(new Color(0, 0, 128));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panelTop.add(lblNewLabel);
    }

    private void refreshGameStatus() {
        Timer timer = new Timer(PLAYER_NAME_BLINK_INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                int turn = processor.playGame(0, currentGame.getMe());
                if (turn == 2) {
                    setEnemyPlaceIcon();
                }
                if(status != Status.PLACE_SHIP) {
                    if (turn == 0) {
                        updateMyField();
                        updateEnemyField();
                        setMyTurnIcon();
                        status = Status.MY_TURN;
                    } else if (turn == 1) {
                        updateMyField();
                        updateEnemyField();
                        setEnemyTurnIcon();
                        status = Status.ENEMY_TURN;
                    }
                } else {
                    setMePlaceIcon();
                }
            }
        });
        timer.start();
    }

    private void setMyTurnIcon() {
        ImageIcon myLaunchIcon = new ImageIcon("images/leftLaunch.png");
        myLaunchLabel.setIcon(myLaunchIcon);
        this.myLaunchLabel.setVisible(true);
        this.enemyLaunchLabel.setVisible(false);
    }

    private void setEnemyTurnIcon() {
        ImageIcon enemyLaunchIcon = new ImageIcon("images/rightLaunch.png");
        enemyLaunchLabel.setIcon(enemyLaunchIcon);
        this.enemyLaunchLabel.setVisible(true);
        this.myLaunchLabel.setVisible(false);
    }

    private void setMePlaceIcon() {
        ImageIcon location = new ImageIcon("images/location.png");
        myLaunchLabel.setIcon(location);
        this.myLaunchLabel.setVisible(true);
    }

    private void setEnemyPlaceIcon() {
        ImageIcon location = new ImageIcon("images/location.png");
        enemyLaunchLabel.setIcon(location);
        this.enemyLaunchLabel.setVisible(true);
    }

    private void updateEnemyField() {
        Field field = processor.getEnemyField(currentGame.getId());
        rightTable.refreshTable(field.getCells());
        refreshEnemyPanel(rightShipsPanel, field);
    }

    private void updateMyField() {
        Field field = processor.getMyField(currentGame.getId());
        leftTable.refreshTable(field.getCells());
        refreshMyPanel(leftShipsPanel, field);
    }

}

