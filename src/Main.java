import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        //load in the two word lists
        try{
            Scanner in_dict  = new Scanner(new File("gameDictionary.txt"));
            while (in_dict.hasNext()){
                GameLogic.dictionary.add(in_dict.next());
            }

            Scanner in_targets = new Scanner(new File("targetWords.txt"));
            while (in_targets.hasNext()){
                GameLogic.targetWords.add(in_targets.next());
            }

            in_dict.close();
            in_targets.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        GameLogic.targetWord = GameLogic.getTarget().toUpperCase();
        new JWordleGUI();
    }
}
