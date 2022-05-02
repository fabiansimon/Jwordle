import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Locale;

public class JWordleGUI extends JFrame implements ActionListener {

    JLabel scoreLabel;
    JLabel guessLabel;
    JProgressBar timeBar;
    JTextField inputField;
    JPanel[] keyboardLetterPanels;
    JPanel[] letterPanels;

    private int ROUND_SECONDS = 60;
    private int timeLeft = ROUND_SECONDS;
    private static String keyboardLetters = "QWERTYUIOPASDFGHJKLZXCVBNM";
    private int borderRadius = 20;
    private int scoreAmt;
    private Font letterFont = new Font("SF Pro Rounded", Font.BOLD, 40);

    JWordleGUI() {
        Dimension dimension = new Dimension(1400, 1110);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Colors.BLACK.getColor());
        frame.setSize(dimension);
        frame.setMinimumSize(dimension);
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

        startTimer();
    }

    private JPanel topNavPanel(){
        // Panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Colors.BLACK.getColor());
        topPanel.setPreferredSize(new Dimension(100, 60));

        // Guess Label
        guessLabel = new JLabel("0/" + Main.GAME_LENGTH);
        guessLabel.setForeground(Color.white);

        // Score Label
        scoreLabel = new JLabel( Main.points + " pts");
        scoreLabel.setForeground(Color.white);

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
        topPanel.add(scoreLabel);
        topPanel.add(restartButton);

        return topPanel;
    }

    private JPanel playingFieldPanel() {
        JPanel playingPanel = new JPanel();
        playingPanel.setBackground(Colors.DARKBLACK.getColor());
        playingPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        RoundedPanel container = new RoundedPanel(40, Colors.DARKBLACK.getColor());
        container.setLayout(new GridLayout(0, Main.GAME_LENGTH, 55, 35));

        letterPanels = new JPanel[Main.WORD_LENGTH*Main.GAME_LENGTH];

        for (int i = 0; i < letterPanels.length; i++) {
            letterPanels[i] = getLetterBox("", Colors.BLACK.getColor());
            container.add(letterPanels[i]);
        }

        playingPanel.add(container, BorderLayout.CENTER);
        return playingPanel;
    }

    private JPanel keyboardPanel() {
        JPanel keyBoardView = new JPanel();
        keyBoardView.setBackground(Colors.BLACK.getColor());
        keyBoardView.setPreferredSize(new Dimension(100, 370));

        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(Colors.BLACK.getColor());
        inputPanel.setPreferredSize(new Dimension(0, 90));

        // add textField
        inputField = new JTextField();
        inputField.addActionListener(this);
        inputField.setPreferredSize(new Dimension(500, 70));
        inputField.setHorizontalAlignment(SwingConstants.CENTER);
        inputField.setFont(letterFont);
        inputField.addKeyListener(keyListener);
        DocumentFilter filter = new UppercaseDocumentFilter();
        ((AbstractDocument) inputField.getDocument()).setDocumentFilter(filter);

        inputPanel.add(inputField);

        JPanel keyboardContainer = new JPanel();
        keyboardContainer.setBackground(Colors.BLACK.getColor());

        keyboardContainer.setLayout(new GridLayout(0, 10, 10, 10));
        keyboardContainer.setBorder(BorderFactory.createEmptyBorder(10, 150, 10, 150));

        keyboardLetterPanels = new JPanel[keyboardLetters.length()];

        // add letterPanels
        for (int i = 0; i < keyboardLetters.length(); i++) {
            keyboardLetterPanels[i] = getLetterBox(String.valueOf(keyboardLetters.charAt(i)), Colors.WHITE.getColor());
            keyboardContainer.add(keyboardLetterPanels[i]);
        }

        keyBoardView.setLayout(new BorderLayout());
        keyBoardView.add(inputPanel, BorderLayout.NORTH);
        keyBoardView.add(keyboardContainer, BorderLayout.SOUTH);

        return keyBoardView;
    }

    private JPanel timerPanel() {
        JPanel timerView = new JPanel();
        timerView.setBackground(Colors.BLACK.getColor());;
        timerView.setPreferredSize(new Dimension(300, 300));
        UIManager.put("ProgressBar.selectionForeground", Color.WHITE);  //colour of

        timeBar = new JProgressBar();
        timeBar.setValue(ROUND_SECONDS);
        timeBar.setMaximum(ROUND_SECONDS);
        timeBar.setPreferredSize(new Dimension(50, 500));
        timeBar.setStringPainted(true);
        timeBar.setString(String.valueOf(timeLeft));
        timeBar.setOrientation(SwingConstants.VERTICAL);

        timerView.add(timeBar);

        return timerView;
    }

    private JPanel getLetterBox(String c, Color color) {
        JPanel letterBox = new RoundedPanel(borderRadius, color);
        letterBox.setLayout(new BorderLayout());
        letterBox.setPreferredSize(new Dimension(80,80));

        JLabel label = new JLabel(String.valueOf(c));
        label.setFont(letterFont);
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
            enterGuess();
        }

        if (e.getActionCommand() == "restartGame") {
            Main.resetGame();
            resetUI();
        }
    }


    private void enterGuess() {
        String targetWord = Main.targetWord;
        String inputWord = inputField.getText().trim().toUpperCase(Locale.ROOT);

        if (Main.getIsValid(inputWord)) {
            nextRound(inputWord);
        } else {
            JOptionPane.showMessageDialog(null, "Please only use 5 characters");
        }
    }

    private void updateUI() {

        // when game gets restarted
        if (Main.guessedWords.size() == 0) {

            for(int i = 0; i < letterPanels.length; i++) {
                JPanel cPanel = letterPanels[i];
                JLabel cLabel = (JLabel)cPanel.getComponent(0);
                cPanel.setBackground(Colors.BLACK.getColor());
                cLabel.setText("");
            }

            for (int i = 0; i < keyboardLetterPanels.length; i++) {
                JPanel cPanel = keyboardLetterPanels[i];
                cPanel.setBackground(Colors.WHITE.getColor());

                cPanel.repaint();
            }
            return;
        }

        // when game continues
        int round = Main.guessedWords.size()-1;
        String cWord = Main.guessedWords.get(round);

        for (int i = 0; i < Main.WORD_LENGTH; i++) {
            JPanel cPanel = letterPanels[i+(round*Main.WORD_LENGTH)];
            JLabel cLabel = (JLabel)cPanel.getComponent(0);
            Character letter = cWord.charAt(i);

            cPanel.setBackground(Main.getColor(Main.getRank(cWord, i)).getColor());
            cLabel.setText(String.valueOf(letter));
        }

        // change keyboardPanel letters to correct color
        for (int i = 0; i < keyboardLetterPanels.length; i++) {
            JPanel cPanel = keyboardLetterPanels[i];
            JLabel cLabel = (JLabel)cPanel.getComponent(0);
            Character c = cLabel.getText().charAt(0);

            if (Main.guessedLetters.containsKey(c)) {
                Character v = Main.guessedLetters.get(c);
                if (v.equals('c')) cPanel.setBackground(Colors.GREEN.getColor());
                if (v.equals('p')) cPanel.setBackground(Colors.YELLOW.getColor());
                if (v.equals('g')) cPanel.setBackground(Colors.GRAY.getColor());

                cPanel.repaint();
            }
        }

        // reset inputField
        inputField.setText("");

    }

    private void resultModal(boolean isWon) {
        String[] buttons = isWon ? new String[]{"Quit game", "Sure", "Share your result"} : new String[]{"Quit game", "Try again"};
        String title = isWon ? "Congrats!" : "Oops";
        String message = isWon ? "You've guessed it correctly in " + Main.guessedWords.size() + " tries!\n Want to play again?" : "That happens to the best of us. Want to try again?";
        int res = JOptionPane.showOptionDialog(null, message, title,
                JOptionPane.WARNING_MESSAGE, 0, null, buttons, buttons[isWon ? 2 : 1]);

        if (res == 0) System.exit(0);
        if (res == 1) {
            Main.resetGame();
            resetUI();
        }
        if (res == 2) {
            // share result
        }
    }

    private void resetUI() {
        inputField.setText("");
        guessLabel.setText(Main.guessedWords.size() + "/" + Main.GAME_LENGTH);
        scoreLabel.setText(String.valueOf(0));
        timeLeft = ROUND_SECONDS;
        updateUI();
    }

    private void nextRound(String inputWord) {
        Main.addGuess(inputWord);
        updateUI();

        boolean didWin = Main.targetWord.equals(inputWord);

        if (Main.guessedWords.size() == Main.GAME_LENGTH || didWin) {
            resultModal(didWin ? true : false);
            return;
        }
        timeLeft = ROUND_SECONDS;
    }

    private int getScore() {
        scoreAmt = scoreAmt + 150;
        return scoreAmt;
    }

    private void startTimer() {
        timeLeft = ROUND_SECONDS;

        while (timeLeft >= 0) {
            timeBar.setValue(timeLeft);
            timeBar.setString(String.valueOf(timeLeft));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timeLeft -= 1;
            if (timeLeft == 0) {
                resultModal(false);
            }
        }
    }

    KeyListener keyListener = new KeyListener() {
        public void keyPressed(KeyEvent keyEvent) {
            if (keyEvent.getKeyCode() == 10) {
                enterGuess();
            }
        }

        public void keyReleased(KeyEvent keyEvent) {
        }

        public void keyTyped(KeyEvent keyEvent) {
        }

    };
}
