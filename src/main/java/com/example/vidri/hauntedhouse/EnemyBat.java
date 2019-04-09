package com.example.vidri.hauntedhouse;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;


public class EnemyBat {

    private Bitmap image;                       //Image of bat
    private int x;                              //X position
    private int y;                              //Y position
    private int damage = 1;                     //How much "life" bat takes from player
    private CharacterSprite player;             //The player
    Rect bounds;                                //Bounds for collision checking
    private int width;                          //Width of image
    private int height;                         //Height of image


    private int speed;                          //Speed of bat
    boolean collide;                            //If collision has happened

    //Constructor
    public EnemyBat(Bitmap bitmap, CharacterSprite player) {
        image = bitmap;
        this.x = 700;
        this.y = 600;
        speed = 2;
        this.player = player;


        //Create rectangle around image for collision detection
        height = image.getHeight();
        width = image.getWidth();
        bounds = new Rect(x, y, (x + width), (y+ height));

    }

    //Draw bat on canvas
    public void draw(Canvas canvas) {

        canvas.drawBitmap(image, x, y, null);
    }

    //Reset bat after touching player
    public void resetEnemy() {

        this.x = 1000;
        this.y = 600;

        bounds = new Rect(x, y, (x + width), (y+ height));
    }


    //Update bat
    public void update() {

        //Update bounds
        bounds = new Rect(x,  y, (x + width), (y + height));

        //Track player and move towards
        if (player.getX() > x) {
            x = x + speed;
        } else {
            x = x - speed;
        }

        if (player.getY() > y) {
            y = y + speed;
        } else {
            y = y - speed;
        }

        //Check for wall collision
        collide = wallCollide(bounds, DrawBackground.getRect5());
        collide = wallCollide(bounds, DrawBackground.getRect6());

    }

    public Bitmap getBitmap() {
        return image;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public int getDamage() {
        return damage;
    }

    public Rect getBounds() {
        return bounds;
    }

    //Method for dealing with movement of bat during wall collision
    public boolean wallCollide(Rect rect1, Rect rect2) {

        if (rect1.intersect(rect2))
        {
            if (rect2.equals(DrawBackground.getRect5()))
            {
                x = x - 10;
                y = y + 20;
            }
            else if (rect2.equals(DrawBackground.getRect6()))
            {
                x = x - 10;
                y = y - 20;
            }




            return true;
        } else {
            return false;
        }
    }
}
