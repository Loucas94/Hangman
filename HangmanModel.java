import java.util.*;

/**
 * Represents the model of the Hangman game.
 */
public class HangmanModel {
    private String[] words = {"accretion", "boisterous", "dullard", "feigned", "haughty", 
                              "insipid", "noisome", "obdurate", "parsimonious", "sycophant"};
    private String secretWord;
    private Set<Character> guessedLetters;
    private int maxGuesses = 6;
    private int incorrectGuesses;

    /**
     * Constructor for the HangmanModel. Initialises a new game.
     */
    public HangmanModel() {
        resetGame();
    }

    /**
     * Resets the game to a new random word and resets the state of guesses and incorrect attempts.
     */
    public void resetGame() {
        Random random = new Random();
        secretWord = words[random.nextInt(words.length)];
        guessedLetters = new HashSet<>();
        incorrectGuesses = 0;
    }

    /**
     * Processes a letter guess made by the player.
     * <BR>
     * @param letter The letter guessed by the player.
     * @return       true if the guess is correct, false otherwise.
     */
    public boolean processGuess(char letter) {
        if (guessedLetters.contains(letter)) {
            return false;
        }
        guessedLetters.add(letter);
        if (!secretWord.contains(String.valueOf(letter))) {
            incorrectGuesses++;
            return false; // Incorrect guess.
        }
        return true; // Correct guess.
    }

    /**
     * Constructs a string representing the current guessed word.
     * <BR>
     * @return the display word with unguessed letters represented as '?'.
     */
    public String getDisplayWord() {
        StringBuilder display = new StringBuilder();
        for (char c : secretWord.toCharArray()) {
            if (guessedLetters.contains(c)) {
                display.append(c);
            } else {
                display.append('?');
            }
        }
        return display.toString();
    }

    /**
     * Returns the secret word of the current game.
     * <BR>
     * @return the secret word.
     */
    public String getSecretWord() {
        return secretWord;
    }

    /**
     * Gets the number of incorrect guesses made so far.
     * <BR>
     * @return the number of incorrect guesses.
     */
    public int getIncorrectGuesses() {
        return incorrectGuesses;
    }

    /**
     * Calculates the remaining guesses before the game is lost.
     * <BR>
     * @return the number of remaining guesses.
     */
    public int getRemainingGuesses() {
        return maxGuesses - incorrectGuesses;
    }

    /**
     * Checks if the game is over.
     * <BR>
     * @return true if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        return incorrectGuesses >= maxGuesses || isWordGuessed();
    }

    /**
     * Checks if the word has been completely guessed.
     * <BR>
     * @return true if the word is fully guessed, false otherwise.
     */
    public boolean isWordGuessed() {
        for (char c : secretWord.toCharArray()) {
            if (!guessedLetters.contains(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Provides the set of characters that have been guessed so far.
     * <BR>
     * @return a set containing the guessed letters.
     */
    public Set<Character> getGuessedLetters() {
        return guessedLetters;
    }

}

