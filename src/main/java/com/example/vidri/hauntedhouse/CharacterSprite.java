package com.example.vidri.hauntedhouse;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;
import android.support.v7.widget.AppCompatImageView;

public class CharacterSprite extends AppCompatImageView {

    private Bitmap image;                       //Image of player character
    private float x;                            //X position
    private float y;                            //Y position
    Rect bounds;                                //Bounding rectangle for collision detection
    private int speed = 60;                     //Character speed
    private int height;                         //Image height
    private int width;                          //Image width
    private int lives;                          //Number of player lives
    private float score;                        //Player score




    //Constructor
    public CharacterSprite(Context context, Bitmap bmp) {
        super(context);
        image = bmp;
        this.x = 300;
        this.y = 800;

        lives = 3;
        score = 0;

        height = image.getHeight();
        width = image.getWidth();

        bounds = new Rect((int) x, (int) y, (int)(x + width), (int)(y + height));

    }


    //Draw player on canvas
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawBitmap(image, x, y, null);
    }

    //Update bound of player character
    public void update() {

        bounds = new Rect((int) x, (int) y, (int)(x + width), (int)(y + height));

    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getSpeed() {
        return speed;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }


    public Rect getBounds() {
        return bounds;
    }

    public int getBoundsRight() {
        return bounds.right;
    }

    public void loseLife() {
        lives = lives -1;
    }

    public int getLives() { return lives; }

    public void setLives(int lives) {
        this.lives = lives;
    }

    //Set score
    public void setScore(boolean captured, float score) {

        //If urn is captured, calculate score - faster time will have a higher score
        if (captured) {
            if (score != 0) {
                score = (1 / score) * 10000;
                this.score = score;
            }
        }
        else            //If urn is not captured, score is just time
        {
            this.score = score;
        }
    }

    public float getScore() {
        return score;
    }



}
