import org.w3c.dom.css.Rect;

import java.awt.*;

public class GameUtilities {
    static boolean isPointInsideBox(int x, int y, Rectangle box){
        // Our own custom test. We could of course use box.contains(),
        // but we can practice some interesting conditional checking here.
        // Let's test left and right first
        if (x >= box.x && x <= (box.x + box.width)) {
            // Our x coordinate is ok, so check our y
            if (y >= box.y && y <= (box.y + box.height)) {
                return true;
            }
        }
        // x or y was outside the box, so return false
        return false;

    }
    static boolean doesBoxIntersect(Rectangle box,Rectangle other){
        // If any of the four corners of box are inside other, we intersect, so
        // let's check each one. Happily, that answer doesn't change if more
        // than one corner is contained in other, so we can return as soon as
        // we find the first contained corner.

        // Let's get some local copies of the corner coordinates
        // to make the call arguments easier to read.

        int x1 = box.x;
        int y1 = box.y;
        int x2 = x1+box.width;
        int y2 = y1+box.height;
        if (isPointInsideBox(x1,y1,other)){
            return true;
        }else if (isPointInsideBox(x2,y1,other)){
            return true;
        }else if (isPointInsideBox(x1,y2,other)){
            return true;
        } else if (isPointInsideBox(x2,y2,other)) {

            return true;

        }
        return false;
    }
    public static boolean doBoxesIntersect(Rectangle box1,Rectangle box2){
        // Another custom test. We could of course use box1.intersects(box2)
        // but we can practice method calls and some boolean logic here.
        if (doesBoxIntersect(box1, box2)) {
            // At least one of box1's points must be inside box2
            return true;
        } else if (doesBoxIntersect(box2, box1)) {
            // None of box1's points were in box2, but at least one of box2's points are inside box1
            return true;
        }
        // No intersections in either direction
        return false;
    }
}
