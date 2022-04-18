import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class JWordleGUI extends JFrame {

    private String targetWord;

    JWordleGUI(String targetWord) {

        this.targetWord = targetWord;

        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("JWordle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(300, 300));
        JPanel panel = new JPanel();

        panel.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));

        // textFields & labels
        JLabel titleLabel = new JLabel(targetWord);

        JTextField guessInput = new JTextField(5);
        guessInput.setActionCommand("guessInput");

        // Define buttons
        JButton mainButton = new JButton("Guess");
        mainButton.addActionListener(e -> {
            System.out.println("Clicked");
        });

        panel.add(titleLabel);
        panel.add(guessInput);
        panel.add(mainButton);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

    }

}
