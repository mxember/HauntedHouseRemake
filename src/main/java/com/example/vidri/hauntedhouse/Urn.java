package com.example.vidri.hauntedhouse;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.DisplayMetrics;

import java.util.Random;

public class Urn {

    private Bitmap image;                   //Urn image
    private int x;                          //Urn x position
    private int y;                          //Urn y position

    private int urnHeight;                  //Urn Height
    private int urnWidth;                   //Urn width

    Rect bounds;                            //Bounding rectangle for collision detection


    //Constructor
    public Urn(Bitmap bitmap, int height, int width) {
        image = bitmap;

        urnHeight = image.getHeight();
        urnWidth = image.getWidth();

        //Randomly generate urn location and set new bounds
        Random r = new Random();

        int randX = r.nextInt(width);
        int randY = r.nextInt(height);


        x = randX;
        y = randY;

        bounds = new Rect(x, y, (x + urnWidth), (y+ urnHeight));

    }

    //Draw urn on canvas
    public void draw(Canvas canvas) {

        canvas.drawBitmap(image, x, y, null);
    }

    //Update urn's bounding rectangle, and determine if urn has been placed in a wall
    public void update(int height, int width) {

        Random r = new Random();

        int randX;
        int randY;

        //If urn intersects any walls, redo x and y position
        if (bounds.intersect(DrawBackground.getExitRect()) || bounds.intersect(DrawBackground.getRect5()) ||
                bounds.intersect(DrawBackground.getRect6()) || bounds.intersect(DrawBackground.getRect13()) ||
        bounds.intersect(DrawBackground.getRect14()) || bounds.intersect(DrawBackground.getRect4())
        || bounds.intersect(DrawBackground.getRect1()) || bounds.intersect(DrawBackground.getRect2())
        || bounds.intersect(DrawBackground.getRect3()) || bounds.intersect(DrawBackground.getRect7()) ||
                bounds.intersect(DrawBackground.getRect8()) || bounds.intersect(DrawBackground.getRect9())
                || bounds.intersect(DrawBackground.getRect10()) || bounds.intersect(DrawBackground.getRect11()) ||
                bounds.intersect(DrawBackground.getRect12()))
        {

            randX = r.nextInt(width);
            randY = r.nextInt(height);

            x = randX;
            y = randY;

            bounds = new Rect(x, y, (x + urnWidth), (y+ urnHeight));

        }

    }

    public Bitmap getBitmap() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rect getBounds() {
        return bounds;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
