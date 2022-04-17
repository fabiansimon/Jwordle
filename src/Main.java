import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    //a list of all possible 5 letter words in English
    public static HashSet<String> dictionary = new HashSet<>();

    //a smaller list of words for selecting the target word from (i.e. the word the player needs to guess)
    public static ArrayList<String> targetWords = new ArrayList<>();

    // guessWords
    private static ArrayList<HashMap<Character, Character>> guessedWords = new ArrayList<>();

    // Game data
    private static int GAME_LENGTH = 5;
    private static boolean gameOver = false;
    private static String targetWord;

    public static void main(String[] args) {
        Scanner inputScanner =  new Scanner(System.in);

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

        targetWord = getTarget().toUpperCase(Locale.ROOT);

        while (guessedWords.size() < GAME_LENGTH && !gameOver) {
            System.out.println("Please enter your guess");
            String guess = inputScanner.nextLine();

            checkWord(guess);
            addGuess(guess);

            printStructure();
        }

        /*
        guessedWords.add("Hello");
        guessedWords.add("Friend");

        System.out.println(getTarget());
        System.out.println(printStructure());*/

    }

    //use this method for selecting a word. It's important for marking that the word you have selected is printed out to the console!
    public static String getTarget(){
        Random r = new Random();
        String target = targetWords.get(r.nextInt(targetWords.size()));
        //don't delete this line.
        System.out.println(target);
        return target;
    }

    private static void checkWord(String guess) {
        if (targetWord.equals(guess)) {
            System.out.println("CORRECT");
            gameOver = true;
            return;
        }
    }

    private static void addGuess(String guessInput) {
        String guess = guessInput.toUpperCase(Locale.ROOT).trim();

        for (int i = 0; i < targetWord.length(); i++) {
            Character gL = guess.charAt(i);
            Character tL = targetWord.charAt(i);

            HashMap<Character, Character> word = new HashMap<>();
            if (tL == gL) System.out.println("CORRECT LETTER: " + tL);
        }

        System.out.println(guess);
    }

    private static void printStructure() {
        String outputString = "";

        for (int i = 0; i < GAME_LENGTH; i ++) {
            String addedString = "";
            if (i < guessedWords.size()) {
                // addedString = guessedWords.get(i);
            } else {
                addedString = "_____";
            }
            outputString = outputString + "\n" + addedString;
        }

        System.out.println(outputString);
    }
}
