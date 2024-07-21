import java.awt.*;

public interface GamePiece {

    void setPosition(int x,int y);
    int getPositionX();
    int getPositionY();
    Rectangle getBoundingBox();
    void draw();
    boolean isTouching(GamePiece otherPiece);

}
