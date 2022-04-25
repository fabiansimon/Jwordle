import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Locale;

public class JWordleGUI extends JFrame implements ActionListener, KeyListener {

    JLabel score;
    JLabel timeLabel;
    JProgressBar timeBar;
    JTextField inputField;
    JPanel[] keyboardLetterPanels;
    JPanel[] letterPanels;

    private int ROUND_SECONDS = 1;
    private int timeLeft = ROUND_SECONDS;
    private static String keyboardLetters = "QWERTYUIOPASDFGHJKLZXCVBNM";

    JWordleGUI() {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Colors.GRAY.getColor());
        frame.setSize(1000, 800);
        frame.setMinimumSize(new Dimension(1100, 800));
        frame.setLayout(new BorderLayout(10,10));

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Colors.BLACK.getColor());
        leftPanel.setPreferredSize(new Dimension(300, 100));

        frame.add(topNavPanel(), BorderLayout.NORTH);
        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(timerPanel(), BorderLayout.EAST);
        frame.add(keyboardPanel(), BorderLayout.SOUTH);
        frame.add(playingFieldPanel(), BorderLayout.CENTER);

        frame.setVisible(true);

        //startTimer();
    }

    private JPanel topNavPanel(){
        // Panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Colors.BLACK.getColor());
        topPanel.setPreferredSize(new Dimension(100, 60));

        // Guess Label
        JLabel guessLabel = new JLabel("0/" + Main.GAME_LENGTH);
        guessLabel.setForeground(Color.white);

        // Score Label
        score = new JLabel( Main.points + " pts");
        score.setForeground(Color.white);

        // Buttons
        JButton closeButton = new JButton("close");
        closeButton.setActionCommand("closeWindow");
        closeButton.addActionListener(this);

        JButton restartButton = new JButton("restart");
        restartButton.setActionCommand("restartGame");
        restartButton.addActionListener(this);

        JButton enterButton = new JButton("Enter guess");
        enterButton.setActionCommand("enterGuess");
        enterButton.addActionListener(this);

        topPanel.add(enterButton);
        topPanel.add(closeButton);
        topPanel.add(guessLabel);
        topPanel.add(score);
        topPanel.add(restartButton);

        return topPanel;
    }

    private JPanel playingFieldPanel() {
        JPanel playingPanel = new JPanel();
        playingPanel.setBackground(Colors.DARKBLACK.getColor());
        playingPanel.setLayout(new GridLayout(0, Main.GAME_LENGTH));
        playingPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        letterPanels = new JPanel[Main.WORD_LENGTH*Main.GAME_LENGTH];

        for (int i = 0; i < letterPanels.length; i++) {
            letterPanels[i] = getLetterBox("", Colors.BLACK.getColor());
            playingPanel.add(letterPanels[i]);
        }
        return playingPanel;
    }

    private JPanel keyboardPanel() {
        JPanel keyBoardView = new JPanel();
        keyBoardView.setBackground(Colors.BLACK.getColor());
        keyBoardView.setPreferredSize(new Dimension(100, 300));
        keyBoardView.setLayout(new GridLayout(0, 10));
        keyBoardView.setBorder(BorderFactory.createEmptyBorder(10, 150, 10, 150));

        keyboardLetterPanels = new JPanel[keyboardLetters.length()];

        // add letterPanels
        for (int i = 0; i < keyboardLetters.length(); i++) {
            keyboardLetterPanels[i] = getLetterBox(String.valueOf(keyboardLetters.charAt(i)), Colors.WHITE.getColor());
            keyBoardView.add(keyboardLetterPanels[i]);
        }

        // add textField
        inputField = new JTextField();
        inputField.addActionListener(this);
        keyBoardView.add(inputField);

        return keyBoardView;
    }

    private JPanel timerPanel() {
        JPanel timerView = new JPanel();
        timerView.setBackground(Colors.BLACK.getColor());;
        timerView.setPreferredSize(new Dimension(300, 300));

        timeLabel = new JLabel();
        timeLabel.setText(String.valueOf(timeLeft));

        timeBar = new JProgressBar();
        timeBar.setValue(ROUND_SECONDS);
        timeBar.setMaximum(ROUND_SECONDS);
        timeBar.setOrientation(SwingConstants.VERTICAL);

        timerView.add(timeLabel);
        timerView.add(timeBar);

        return timerView;
    }

    private JPanel getLetterBox(String c, Color color) {
        JPanel letterBox = new RoundedPanel(15, color);
        letterBox.setLayout(new BorderLayout());

        JLabel label = new JLabel(String.valueOf(c));
        label.setFont(new Font("SF Pro Rounded", Font.BOLD, 40));
        label.setForeground(color == Colors.WHITE.getColor() ? Colors.BLACK.getColor() :  Colors.WHITE.getColor());
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        letterBox.add(label);

        return letterBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "closeWindow") {
            System.exit(0);
        }

        if (e.getActionCommand() == "enterGuess") {
            final String targetWord = Main.targetWord;
            String inputWord = inputField.getText().trim().toUpperCase(Locale.ROOT);

            if (Main.getIsValid(inputWord)) {
                Main.addGuess(inputWord);
                updateTiles();
                inputField.setText("");
                Main.checkGameStatus(inputWord);
            } else {
                JOptionPane.showMessageDialog(null, "Please only use 5 characters");
            }

            if (targetWord.equals(inputWord)) {
                JOptionPane.showMessageDialog(null, "Congrats, you've guessed it correctly");

            }

        }

        if (e.getActionCommand() == "restartGame") {
            if (timeLeft == -1) {
                startTimer();
        }

            timeLeft = ROUND_SECONDS;
            timeBar.setValue(ROUND_SECONDS);
            timeLabel.setText(String.valueOf(ROUND_SECONDS));
        }
    }

    private void updateTiles() {
        int round = Main.guessedWords.size()-1;
        String cWord = Main.guessedWords.get(round);

        for (int i = 0; i < Main.WORD_LENGTH; i++) {
            JPanel cPanel = letterPanels[i+(round*Main.WORD_LENGTH)];
            JLabel cLabel = (JLabel)cPanel.getComponent(0);
            Character letter = cWord.charAt(i);

            cPanel.setBackground(Main.getColor(letter, i).getColor());
            cLabel.setText(String.valueOf(letter));
        }

    }

    private void startTimer() {
        timeLeft = ROUND_SECONDS;

        while (timeLeft >= 0) {
            timeBar.setValue(timeLeft);
            timeLabel.setText(String.valueOf(timeLeft));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timeLeft -= 1;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("1");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("2");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("3");
    }
}
