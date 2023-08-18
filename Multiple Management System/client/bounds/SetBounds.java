package bounds;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

public class SetBounds
{
    Rectangle rec = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
    public int screenHeight = rec.height;
    public int screenWidth = rec.width;

    public SetBounds(){}
    
    // component x-pos
    public int getMyXPose(double ratio)
    {
        return (int)(screenWidth*ratio);
    }

    // component y-pos
    public int getMyYPose(double ratio)
    {
        return (int)(screenHeight*ratio);
    }

    // component height
    public int getMyHeight(double ratio)
    {
        return (int)(screenHeight*ratio);
    }

    // component width
    public int getMyWidth(double ratio)
    {
        return (int)(screenWidth*ratio);
    }

}
