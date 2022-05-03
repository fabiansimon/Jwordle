import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameLogic {

    //a list of all possible 5-letter words in English
    public static HashSet<String> dictionary = new HashSet<>();

    //a smaller list of words for selecting the target word from (i.e. the word the player needs to guess)
    public static ArrayList<String> targetWords = new ArrayList<>();

    // guess Words
    public static ArrayList<String> guessedWords = new ArrayList<>();

    // guessed Letter
    public static Map<Character, Character> guessedLetters = new HashMap<>();

    // Game data
    public static int GAME_LENGTH = 6;
    public static int WORD_LENGTH = 5;
    public static int points;
    public static String targetWord;
    public static boolean gameOver = false;
    public static boolean didWin = false;
    public static double timeTotal = 0.0;

    //use this method for selecting a word. It's important for marking that the word you have selected is printed out to the console!
    public static String getTarget(){
        Random r = new Random();
        String target = targetWords.get(r.nextInt(targetWords.size()));
        //don't delete this line.
        System.out.println(target);
        return target;
    }

    // reset game
    public static void resetGame() {
        gameOver = false;
        didWin = false;
        targetWord = getTarget().toUpperCase();
        guessedWords = new ArrayList<>();
        guessedLetters = new HashMap<>() ;
        points = 0;
        timeTotal = 0;
    }

    // check if input is valid - if not return correct error message
    public static int getIsValid(String guess) {
        Pattern p = Pattern.compile("[^a-z]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(guess);
        boolean reg = m.find();
        boolean dic = dictionary.contains(guess.toLowerCase(Locale.ROOT));

        if (guess.length() == WORD_LENGTH && !reg && dic) return -1;
        if (reg) return 0;
        if (guess.length() > WORD_LENGTH) return 1;
        if (!dic) return 2;
        return 3;
    }

    // add guess with corresponding rank
    public static void addGuess(String guessInput) {
        String guess = guessInput;
        guessedWords.add(guess);

        // g == wrongly guessed || c == correct || p == wrong position
        for (int i = 0; i < guessInput.length(); i++) {
            Character c = guessInput.charAt(i);
            int rank = getRank(guessInput, i);
            guessedLetters.put(c, rank == 1 ? 'c' : rank == 2 ? 'p' : 'g');
        }
    }

    // get rank of letter (if guess correctly || wrong position || no in word)
    public static int getRank(String inputWord, int index) {
        Character cLetter = inputWord.charAt(index);

        if (cLetter == targetWord.charAt(index)) return 1;
        if (targetWord.contains(String.valueOf(cLetter))) {
            long targetCharAmt = targetWord.chars().filter(ch -> ch == cLetter).count();
            String preString = inputWord.substring(0,index);

            long preStringCharAmt = preString.chars().filter(ch -> ch == cLetter).count();
            if (preStringCharAmt < targetCharAmt) return 2;
        }
        return 0;
    }

    // turn the rank into the associated color
    public static Colors getColor(int rank) {
        if (rank == 1) return Colors.GREEN;
        if (rank == 2) return Colors.YELLOW;
        return Colors.GRAY;
    }

    // create result string to copy to clipboard
    public static String getResultString() {
        String resultString;

        resultString = "Total time: " + GameLogic.timeTotal + " seconds\n" + "Guessed in " + guessedWords.size() + " out of " + GAME_LENGTH + " tries" + "\n" + points + " pts" + "\n\n";

        for (int i = 0; i < guessedWords.size(); i++) {
            for (int j = 0; j < WORD_LENGTH; j++) {
                int rank = getRank(guessedWords.get(i), j);
                String color = rank == 2 ? "🟨️" : rank == 1 ? "🟩" : "⬜️";
                resultString = resultString + color + " ";
            }
            resultString = resultString + "\n";
        }
        return resultString;
    };

}
