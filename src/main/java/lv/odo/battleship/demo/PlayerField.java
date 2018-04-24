package lv.odo.battleship.demo;
import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.Button;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JDesktopPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTable;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
public class PlayerField {
private JFrame frame;
private JTable table;
private JTable table_1;

/**
* Launch the application.
*/
public static void main(String[] args) {
EventQueue.invokeLater(new Runnable() {
public void run() {
try {
PlayerField window = new PlayerField();
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
public PlayerField() {
initialize();

}

/**
* Initialize the contents of the frame.
*/
private void initialize() {
frame = new JFrame();
frame.setBounds(100, 100, 860, 330);

frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.getContentPane().setLayout(new CardLayout(0, 0));

TableField tf = new TableField();
JTable table_1 = new JTable(tf);
table_1.setPreferredSize(new Dimension(330,330));


}


}
