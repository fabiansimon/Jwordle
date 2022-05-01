import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    //a list of all possible 5 letter words in English
    public static HashSet<String> dictionary = new HashSet<>();

    //a smaller list of words for selecting the target word from (i.e. the word the player needs to guess)
    public static ArrayList<String> targetWords = new ArrayList<>();

    // guess Words
    public static ArrayList<String> guessedWords = new ArrayList<>();

    // guessed Letter
    public static Map<Character, Character> guessedLetters = new HashMap<>();

    // Game data
    public static int GAME_LENGTH = 5;
    public static int WORD_LENGTH = 5;
    public static int points;
    public static String targetWord;
    public static boolean gameOver = false;
    public static boolean didWin = false;

    private static String currentGuess = "";
    private static String keyboardLetters = "QWERTYUIOP\nASDFGHJKL\nZXCVBNM";

    public static void main(String[] args) {

        //load in the two word lists
        try{
            Scanner in_dict  = new Scanner(new File("gameDictionary.txt"));
            while (in_dict.hasNext()){
                dictionary.add(in_dict.next());
            }

            Scanner in_targets = new Scanner(new File("targetWords.txt"));
            while (in_targets.hasNext()){
                targetWords.add(in_targets.next());
            }

            in_dict.close();
            in_targets.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        new JWordleGUI();
        startGame();

        /*
        while (true) {
            startGame();
        }
         */
    }

    //use this method for selecting a word. It's important for marking that the word you have selected is printed out to the console!
    public static String getTarget(){
        Random r = new Random();
        String target = targetWords.get(r.nextInt(targetWords.size()));
        //don't delete this line.
        System.out.println(target);
        return target;
    }

    private static void startGame() {
        Scanner inputScanner =  new Scanner(System.in);
        targetWord = getTarget().toUpperCase(Locale.ROOT);

        while (guessedWords.size() < GAME_LENGTH && !gameOver) {

            boolean isValid = false;

            while (!isValid) {
                inputWord();
                isValid = getIsValid(currentGuess);
            }

            addGuess(currentGuess);
            printStructure();
            checkGameStatus(currentGuess);
        }

        if (didWin) System.out.println(Colors.GREEN.getColor() + "Congrats you have won in just " + guessedWords.size() + " tries" + Colors.RESET.getColor());
        if (!didWin) System.out.println(Colors.RED.getColor() + "You have lost, the word you were looking for was " + targetWord + Colors.RESET.getColor());

        System.out.println("Play again? Y/N");
        String answer = inputScanner.nextLine().toUpperCase(Locale.ROOT).trim();

        if (answer.contains("Y")) {
            resetGame();
        } else {
            System.exit(0);
        }
    }

    private static void resetGame() {
        gameOver = false;
        didWin = false;
        guessedWords = new ArrayList<>();
    }

    public static boolean getIsValid(String guess) {
        Pattern p = Pattern.compile("[^a-z]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(guess);
        boolean reg = m.find();
        boolean dic = dictionary.contains(guess.toLowerCase(Locale.ROOT));

        if (guess.length() == WORD_LENGTH && !reg && dic) return true;

        System.out.println("Please enter a valid guess");
        return false;
    }

    private static void inputWord() {
        Scanner inputScanner =  new Scanner(System.in);
        System.out.println("Please enter your guess");
        currentGuess = inputScanner.nextLine().toUpperCase(Locale.ROOT).trim();
    }

    public static void checkGameStatus(String guess) {
        if (targetWord.equals(guess)) {
            gameOver = true;
            didWin = true;
            return;
        }

        if (guessedWords.size() == GAME_LENGTH) {
            gameOver = true;
            didWin = false;
            return;
        }
    }

    public static void addGuess(String guessInput) {
        String guess = guessInput;
        guessedWords.add(guess);

        // g == wrongly guessed || c == correct || p == wrong position

        for (int i = 0; i < guessInput.length(); i++) {
            Character c = guessInput.charAt(i);
            int rank = getRank(c, i);
            guessedLetters.put(c, rank == 1 ? 'c' : rank == 2 ? 'p' : 'g');
        }
    }

    public static int getRank(Character letter, int index) {
        if (letter == targetWord.charAt(index)) return 1;
        if (targetWord.contains(String.valueOf(letter))) return 2;
        return 0;
    }

    public static Colors getColor(int rank) {
        if (rank == 1) return Colors.GREEN;
        if (rank == 2) return Colors.YELLOW;
        return Colors.GRAY;
    }

    private static void printStructure() {
        String output = "\n" + "tries " + guessedWords.size() + "/" + GAME_LENGTH + "\n\n";

        for (int i = 0; i < GAME_LENGTH; i++) {
            if (guessedWords.size()-1 < i) {
                output = output + Colors.WHITE.getString() + "_ _ _ _ _" + Colors.RESET.getString() + "\n";
            } else {
                String word = guessedWords.get(i);
                for (int j = 0; j < word.length(); j++) {
                    char letter = word.charAt(j);
                    output = output + getColor(getRank(letter, j)).getString() + letter + Colors.RESET.getString() + " ";
                }
                output = output + "\n";
            }
        }

        output += "\n";

        for (int i = 0; i < keyboardLetters.length(); i++) {
            output = output + Colors.WHITE.getString() + keyboardLetters.charAt(i) + Colors.RESET.getString() + " ";
        }

        System.out.println(output);
    }
}
