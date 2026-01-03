import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The main GUI class for the Hangman game.
 */
public class HangmanGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private HangmanModel model;
    private DrawingPanel drawingPanel;

    /**
     * Constructor for HangmanGUI. It sets up the game interface and initialises game components.
     */
    public HangmanGUI() {
        model = new HangmanModel();
        setTitle("Hangman Game");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        drawingPanel = new DrawingPanel();
        add(drawingPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        JButton newGameButton = new JButton("New");
        newGameButton.addActionListener(e -> {
            model.resetGame();
            drawingPanel.repaint();
            requestFocusInWindow(); // Focus returns to the frame after pressing New Game.
        });
        southPanel.add(newGameButton);
        add(southPanel, BorderLayout.SOUTH);

        addKeyListener(new KeyAdapter() {
            
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (Character.isLowerCase(c) && !model.isGameOver()) {
                    model.processGuess(c);
                    drawingPanel.repaint();
                }
            }
        });

        setVisible(true);
        requestFocusInWindow(); // Focus starts on the frame to capture keyboard input
    }

    /**
     * Inner class to handle the drawing of the Hangman image and guessed words.
     */
    private class DrawingPanel extends JPanel {
        private static final long serialVersionUID = 1L;

		protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.WHITE);

            int reversedIndex = 6 - model.getIncorrectGuesses();
            String imageFile = "/hangman" + reversedIndex + ".gif";
            Image hangmanImage = loadImage(imageFile);
            g.drawImage(hangmanImage, getWidth() / 2 - 50, 10, 100, 100, this);

            g.setFont(new Font("Monospaced", Font.BOLD, 18));
            String displayWord = model.getDisplayWord();
            FontMetrics metrics = g.getFontMetrics();
            int x = (getWidth() - metrics.stringWidth(displayWord)) / 2;
            g.drawString(displayWord, x, getHeight() - 60);

            String status = model.isWordGuessed() ? "You won!" :
                            model.isGameOver() ? "You lost! (" + model.getSecretWord() + ")" :
                            model.getRemainingGuesses() + " guesses left";
            x = (getWidth() - metrics.stringWidth(status)) / 2;
            g.drawString(status, x, getHeight() - 40);

            int y = getHeight() - 20;
            String alphabet = "abcdefghijklmnopqrstuvwxyz";
            for (int i = 0; i < alphabet.length(); i++) {
                char letter = alphabet.charAt(i);
                if (model.getGuessedLetters().contains(letter)) {
                    g.setColor(Color.BLUE);
                } else {
                    g.setColor(Color.LIGHT_GRAY);
                }
                g.drawString(String.valueOf(letter), getWidth() / 2 - 182 + i * 14, y);
            }
        }

        /**
         * loadImage method to load an image file from the resources.
         * <BR>
         * @param fileName The name of the file to load.
         * @return         The loaded image.
         */
        private Image loadImage(String fileName) {
            return Toolkit.getDefaultToolkit().getImage(getClass().getResource(fileName));
        }
    }
}
