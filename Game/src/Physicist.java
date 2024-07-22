import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Physicist implements GamePiece, ActionListener {
    Color color;
    int centerX,centerY;
    Apple aimingApple;
    float aimingAngle;
    float aimingForce;
    Field field;

    //Some helpers fır optimizing draw() method that can be called many,many times
    int x,y;

    //Boundary helpers
    private final int physicistHeight = (int) (1.5*Field.PHYSICIST_SIZE_IN_PIXELS);
    private Rectangle boundingBox;


    //Create default,red physicist
    public Physicist(){
        this(RED);
    }

    //Create a physicist of the given color
    public Physicist(Color color){
        setColor(color);
        aimingAngle=90.0f;
        aimingForce = 60.0f;
        getNewApple();
    }
    public void setAimingAngle(float angle){
        aimingAngle = angle;
    }

    public void setAimingForce(Float force){
        if (force<0){
            force=0.0f;
        }
        aimingForce = force;
    }

    //Sets color
    public void setColor(Color color){
        this.color = color;
    }

    @Override
    public void setPosition(int x, int y) {
int offset = (int)(Field.PHYSICIST_SIZE_IN_PIXELS/2.0F);
    }

    @Override
    public int getPositionX() {
        return 0;
    }

    @Override
    public int getPositionY() {
        return 0;
    }

    @Override
    public Rectangle getBoundingBox() {
        return null;
    }

    @Override
    public void draw() {

    }

    @Override
    public boolean isTouching(GamePiece otherPiece) {
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
