import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JWordleGUI extends JFrame implements ActionListener {

    JLabel score;
    JProgressBar timeBar;

    private int GAME_LENGTH;
    private int WORD_LENGTH;
    private int ROUND_SECONDS = 60;
    private int currentTime = 60;
    private int points;
    private static String keyboardLetters = "QWERTYUIOPASDFGHJKLZXCVBNM";

    JWordleGUI(int GAME_LENGTH, int WORD_LENGTH, int points) throws InterruptedException {
        this.GAME_LENGTH = GAME_LENGTH;
        this.WORD_LENGTH = WORD_LENGTH;
        this.points = points;

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setMinimumSize(new Dimension(1100, 800));
        frame.setLayout(new BorderLayout(20, 20));

        JPanel leftPanel = new JPanel();

        leftPanel.setBackground(Color.GREEN);

        leftPanel.setPreferredSize(new Dimension(100, 100));

        frame.add(topNavPanel(), BorderLayout.NORTH);
        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(timerPanel(), BorderLayout.EAST);
        frame.add(keyboardPanel(), BorderLayout.SOUTH);
        frame.add(playingFieldPanel(), BorderLayout.CENTER);

        frame.setVisible(true);

        startTimer();
    }

    private JPanel topNavPanel(){
        // Panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.BLUE);
        topPanel.setPreferredSize(new Dimension(100, 100));

        // Guess Label
        JLabel guessLabel = new JLabel("1/5");
        guessLabel.setForeground(Color.white);

        // Score Label
        score = new JLabel( points + " pts");
        score.setForeground(Color.white);

        // Buttons
        JButton closeButton = new JButton("close");
        closeButton.setActionCommand("closeWindow");
        closeButton.addActionListener(this);

        JButton restartButton = new JButton("restart");
        restartButton.setActionCommand("restartGame");
        restartButton.addActionListener(this);

        topPanel.add(closeButton);
        topPanel.add(guessLabel);
        topPanel.add(score);
        topPanel.add(restartButton);

        return topPanel;
    }

    private JPanel playingFieldPanel() {
        JPanel playingPanel = new JPanel();
        playingPanel.setBackground(Color.green);
        playingPanel.setLayout(new GridLayout(0, GAME_LENGTH));

        JPanel[] letterPanels = new JPanel[WORD_LENGTH];

        for (int i = 0; i < GAME_LENGTH; i++) {
            for (int j = 0; j < letterPanels.length; j ++) {
                letterPanels[j] = getLetterBox(Double.toString(i*j));
                playingPanel.add(letterPanels[j]);
            }
        }
        return playingPanel;
    }

    private JPanel keyboardPanel() {
        JPanel keyBoardView = new JPanel();
        keyBoardView.setBackground(Color.BLACK);
        keyBoardView.setPreferredSize(new Dimension(100, 300));
        keyBoardView.setLayout(new GridLayout(0, 10));

        JPanel[] keyboardLetterPanels = new JPanel[keyboardLetters.length()];

        for (int i = 0; i < keyboardLetters.length(); i++) {
            keyboardLetterPanels[i] = getLetterBox(String.valueOf(keyboardLetters.charAt(i)));
            keyBoardView.add(keyboardLetterPanels[i]);
        }

        return keyBoardView;
    }

    private JPanel timerPanel() {
        JPanel timerView = new JPanel();
        timerView.setBackground(Color.gray);
        timerView.setPreferredSize(new Dimension(50, 300));

        timeBar.setValue(currentTime);
        timeBar.setMaximum(ROUND_SECONDS);
        timeBar.setOrientation(SwingConstants.VERTICAL);

        timerView.add(timeBar);
        return timerView;

    }

    private JPanel getLetterBox(String c) {
        JPanel letterBox = new JPanel();
        letterBox.setSize(new Dimension(40, 40 ));
        letterBox.setBackground(Color.white);
        JLabel label = new JLabel(String.valueOf(c));
        letterBox.add(label);

        return letterBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "closeWindow") {
            System.exit(0);
        }

        if (e.getActionCommand() == "restartGame") {
            points += 10;
            score.setText(points + " pts");
        }
    }

    public void startTimer() {
        int time = currentTime;

        while (time > 0) {

            timeBar.setValue(currentTime);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currentTime -= 1;
        }
    }
}
