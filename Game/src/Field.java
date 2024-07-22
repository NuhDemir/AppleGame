import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class Field extends JComponent implements ActionListener {
    public static final float GRAVITY = 9.8f;  // feet per second per second
    public static final int STEP = 40;   // duration of an animation frame in milliseconds
    public static final int APPLE_SIZE_IN_PIXELS = 30;
    public static final int TREE_WIDTH_IN_PIXELS = 60;
    public static final int TREE_HEIGHT_IN_PIXELS = 2 * TREE_WIDTH_IN_PIXELS;
    public static final int PHYSICIST_SIZE_IN_PIXELS = 75;
    public static final int MAX_TREES = 12;

    Color startColor = new Color(0x003664); // #003664
    Color endColor = new Color(0xEFF4F7);   // #EFF4F7
    Random random = new Random();

    Physicist physicist;
    int myScore = 0;
    String[] scores = new String[3];
    JLabel[] scoreLabels = new JLabel[3];
    List<Apple> apples = Collections.synchronizedList(new ArrayList<>());
    List<Tree> trees = Collections.synchronizedList(new ArrayList<>());

    boolean animating = false;
    Timer animationTimer;

    JLabel winLabel;
    JButton startButton;

    public Field() {
        setLayout(null);
        winLabel = new JLabel("You Won!", SwingConstants.CENTER);
        winLabel.setFont(new Font("Poppins", Font.BOLD, 36));
        winLabel.setForeground(new Color(0x8E9A9A)); // #8E9A9A
        winLabel.setBounds(0, 0, AppleToss.FIELD_WIDTH, AppleToss.FIELD_HEIGHT);
        winLabel.setVisible(false);
        add(winLabel);

        startButton = new JButton("Start Game");
        startButton.setFont(new Font("Poppins", Font.BOLD, 18));
        startButton.setBackground(new Color(0x69716F)); // #69716F
        startButton.setForeground(Color.WHITE);
        startButton.setBounds(AppleToss.FIELD_WIDTH / 2 - 100, AppleToss.FIELD_HEIGHT / 2 - 25, 200, 50);
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);
        startButton.addActionListener(e -> setupNewGame());
        add(startButton);

        // Adding a translucent background to the start button
        startButton.setOpaque(true);
        startButton.setContentAreaFilled(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gradient = new GradientPaint(0, 0, startColor, getWidth(), getHeight(), endColor);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        if (physicist != null) {
            physicist.draw(g);
        }
        for (Tree t : trees) {
            t.draw(g);
        }
        for (Apple a : apples) {
            a.draw(g);
        }
    }

    public void setPlayer(Physicist p) {
        physicist = p;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (animating && "repaint".equals(event.getActionCommand())) {
            for (Apple a : apples) {
                a.step();
                detectCollisions(a);
            }
            repaint();
            cullFallenApples();
        }
    }

    public void startTossFromPlayer(Physicist physicist) {
        if (!animating) {
            System.out.println("Starting animation!");
            animating = true;
            startAnimation();
        }
        if (animating) {
            // Check to make sure we have an apple to toss
            if (physicist.aimingApple != null) {
                Apple apple = physicist.takeApple();
                apple.toss(physicist.aimingAngle, physicist.aimingForce);
                apples.add(apple);
                Timer appleLoader = new Timer(800, physicist);
                appleLoader.setActionCommand("New Apple");
                appleLoader.setRepeats(false);
                appleLoader.start();
            }
        }
    }

    void cullFallenApples() {
        Iterator<Apple> iterator = apples.iterator();
        while (iterator.hasNext()) {
            Apple a = iterator.next();
            if (a.getCollidedPiece() != null) {
                GamePiece otherPiece = a.getCollidedPiece();
                if (otherPiece instanceof Physicist) {
                    hitPhysicist((Physicist) otherPiece);
                } else if (otherPiece instanceof Tree) {
                    hitTree((Tree) otherPiece);
                }
                iterator.remove();
            } else if (a.getPositionY() > 600) {
                System.out.println("Culling apple");
                iterator.remove();
            }
        }
        if (apples.size() <= 0) {
            animating = false;
            if (animationTimer != null && animationTimer.isRunning()) {
                animationTimer.stop();
            }
        }
    }

    void detectCollisions(Apple apple) {
        // Check for other apples
        for (Apple a : apples) {
            if (apple.isTouching(a)) {
                System.out.println("Touching another apple!");
                return;
            }
        }
        // Check our physicist
        if (apple.isTouching(physicist)) {
            System.out.println("Touching a physicist!");
            return;
        }

        // Check for trees
        for (Tree t : trees) {
            if (apple.isTouching(t)) {
                System.out.println("Touching a tree!");
                return;
            }
        }
    }

    void hitPhysicist(Physicist physicist) {
        // do any scoring or notifications here
    }

    void hitTree(Tree tree) {
        // do any scoring or notifications here
        myScore += 10;
        trees.remove(tree);
        setScore(1, String.valueOf(myScore));
        checkForWin();
    }

    void checkForWin() {
        if (trees.size() == 0) {
            winLabel.setVisible(true);
        }
    }

    void startAnimation() {
        if (animationTimer == null) {
            animationTimer = new Timer(STEP, this);
            animationTimer.setActionCommand("repaint");
            animationTimer.setRepeats(true);
            animationTimer.start();
        } else if (!animationTimer.isRunning()) {
            animationTimer.restart();
        }
    }

    public String getScore(int playerNumber) {
        return scores[playerNumber];
    }

    public void setScore(int playerNumber, String score) {
        scores[playerNumber] = score;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                String newScore = " Player " + playerNumber + ": " + score;
                scoreLabels[playerNumber].setText(newScore);
            }
        });
    }

    public String getWinner() {
        int score2 = -1;
        try {
            score2 = Integer.parseInt(scores[2]);
        } catch (NumberFormatException nfe) {
            System.err.println("Couldn't parse the other player's score: " + scores[2]);
        }
        if (myScore == score2) {
            return "It's a tie!";
        } else if (myScore > score2) {
            return "You won!";
        } else {
            return "They won.";
        }
    }

    private int goodX() {
        // at least half the width of the tree plus a few pixels
        int leftMargin = TREE_WIDTH_IN_PIXELS / 2 + 5;
        // now find a random number between a left and right margin
        int rightMargin = AppleToss.FIELD_WIDTH - leftMargin;

        // And return a random number starting at the left margin
        return leftMargin + random.nextInt(rightMargin - leftMargin);
    }

    /**
     * Helper method to return a good y value for a tree so it's not off the top or bottom of the screen.
     *
     * @return y value within the bounds of the playing field height
     */
    private int goodY() {
        // at least half the height of the "leaves" plus a few pixels
        int topMargin = TREE_WIDTH_IN_PIXELS / 2 + 5;
        // a little higher off the bottom
        int bottomMargin = AppleToss.FIELD_HEIGHT - TREE_HEIGHT_IN_PIXELS;

        // And return a random number starting at the top margin but not past the bottom
        return topMargin + random.nextInt(bottomMargin - topMargin);
    }

    public void setupNewGame() {
        winLabel.setVisible(false);
        startButton.setVisible(false);  // Hide start button when game starts
        // Clear out any old trees
        trees.clear();

        // Now create some trees for target practice
        for (int i = trees.size(); i < Field.MAX_TREES; i++) {
            Tree t = new Tree();
            t.setPosition(goodX(), goodY());
            // Trees can be close to each other and overlap, but they shouldn't intersect our physicist
            while (physicist.isTouching(t)) {
                // We do intersect this tree, so let's try again
                t.setPosition(goodX(), goodY());
                System.err.println("Repositioning an intersecting tree...");
            }
            trees.add(t);
        }
        repaint();
    }
}
