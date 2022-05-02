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

        targetWord = getTarget().toUpperCase();
        new JWordleGUI();
    }

    //use this method for selecting a word. It's important for marking that the word you have selected is printed out to the console!
    public static String getTarget(){
        Random r = new Random();
        String target = targetWords.get(r.nextInt(targetWords.size()));
        //don't delete this line.
        System.out.println(target);
        return target;
    }

    public static void resetGame() {
        gameOver = false;
        didWin = false;
        targetWord = getTarget().toUpperCase();
        guessedWords = new ArrayList<>();
        guessedLetters = new HashMap<>() ;
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

    public static int getRank(String letter, int index) {
        Character cLetter = letter.charAt(index);

        // feats
        // roofs

        if (cLetter == targetWord.charAt(index)) return 1;
        if (targetWord.contains(String.valueOf(cLetter))) {
            long totalTarChar = targetWord.chars().filter(ch -> ch == cLetter).count();
            if (totalTarChar > 1) {

            }
            return 2;
        }
        return 0;
    }

    public static Colors getColor(int rank) {
        if (rank == 1) return Colors.GREEN;
        if (rank == 2) return Colors.YELLOW;
        return Colors.GRAY;
    }
}
