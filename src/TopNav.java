import javax.swing.*;
import java.awt.*;

public class TopNav extends JPanel {

    TopNav() {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.BLUE);
        topPanel.setPreferredSize(new Dimension(100, 100));

        JLabel guessLabel = new JLabel("1/5");
        JLabel score = new JLabel("1.2376 pts");

        //topPanel.add(guessLabel);
        //topPanel.add(score);
    }

}
