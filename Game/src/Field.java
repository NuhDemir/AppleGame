import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Field extends JComponent implements ActionListener {
    public static final float GRAVITY = 9.2f;//feet per second
    public static final int STEP = 50;
    public static final int APPLE_SIZE_IN_PIXELS=30;
    public static final int TREE_WIDTH_IN_PIXELS=60;
    public static final int TREE_GEIGHT_IN_PIXELS=2*TREE_WIDTH_IN_PIXELS;
    public static final int PHYSICIST_SIZE_IN_PIXELS =75;
    public static final int MAX_TREES=12;

    Color fieldColor = Color.GRAY;
    Random random = new Random();

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
