import java.lang.Math;
import java.util.Scanner;
import java.io.File;

public class Wordle {
    public static final String RESET = "\u001B[0m";
    public static final String RED_BRIGHT = "\u001B[091m";
    public static final String YELLOW_BRIGHT = "\u001B[093m";
    public static final String GREEN_BRIGHT = "\u001B[092m";
    public static final String BLUE_BRIGHT = "\u001B[094m";
    public static final String MAGENTA_BRIGHT = "\u001B[095m";


    public String[] words;

    public int numWords;
    public int currentWordIndex;
    public Boolean currentWordIsCorrect;

    public Wordle() {
        words = new String[100];
        numWords = 0;
        currentWordIndex = 1;
        currentWordIsCorrect = false;
    }

    //PreviousList makes use of the words  from the file dic.txt

    public void previousList() throws Exception {
        File file = new File("C:\\Users\\Senyo\\Downloads\\dic.txt");
        Scanner in = new Scanner(file);


        double counterWords = Math.random();


        while (in.hasNext()) {
            if (counterWords >= this.words.length) {
                // File contains more words, more than we can use
                break;
            }


            this.words[(int) counterWords] = in.next();
            this.numWords++;
            counterWords++;
            System.out.println(counterWords);

        }

        in.close();
    }
    //WordComingAfter gets the another word from the list of available words. It will print empty if all the words have been used up

    public String WordComingAfter() {


        this.currentWordIndex++;
        if (this.currentWordIndex < this.numWords) {
            String word = this.words[this.currentWordIndex];
            return word;
        }
        return "";
    }

    public Boolean IsItWord(Character c, String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c) {
                return true;
            }
        }
        return false;
    }

    public String CompareGuess(String guess) {
        String result = "";
        String currentWord = this.words[this.currentWordIndex];

        for (int i = 0; i < currentWord.length(); i++) {
            if (currentWord.charAt(i) == guess.charAt(i)) {
                result = result + GREEN_BRIGHT + guess.charAt(i) + RESET;
            } else if (this.IsItWord(guess.charAt(i), currentWord)) {
                result = result + YELLOW_BRIGHT + guess.charAt(i) + RESET;
            } else {
                result = result + RED_BRIGHT + guess.charAt(i) + RESET;
            }
        }

        return result;
    }

    public static void main(String[] args) throws Exception {
        // Scanner is used to get user's input
        Scanner in = new Scanner(System.in);
        String userInput = "";

        Wordle game = new Wordle();
        game.previousList();
        String currentWord = game.WordComingAfter();
        String[] guesses = new String[100];
        int finiteGuessing = -1;

        do {

            System.out.println("Current word is your next guess");

            System.out.println("What is the " + currentWord.length() + "-letter word!");
            userInput = in.nextLine();

            if (userInput.equals("help")) {
                System.out.println(MAGENTA_BRIGHT + "You need to guess a five letter word\n" + RESET);
                continue;
            } else if (userInput.equals("?")) {
                System.out.println(BLUE_BRIGHT + "Letters placed in the right positions are in green. So try again: " + RESET);
                System.exit(0);
            } else if (userInput.equals("giveup")) {
                System.out.println(RED_BRIGHT + "Why? See Word: " + currentWord + RESET);
                System.exit(0);
            }// increasing the number of times user has to guess

            finiteGuessing++;
            System.out.println("");

            for (int i = 0; i < finiteGuessing; i++) {
                System.out.println(guesses[i]);
            }
            if (currentWord.equals(userInput)) {
                System.out.println(GREEN_BRIGHT + currentWord + RESET);
                System.out.println("Good job, you got the word in " + (finiteGuessing + 1) + " tries!");
                System.out.println("You rock");

                currentWord = game.WordComingAfter();
                guesses = new String[100];
                finiteGuessing = 1;
            } else {
                System.out.println(game.CompareGuess(userInput));
                guesses[finiteGuessing] = userInput;
            }

        } while (userInput != "");

    }
}

