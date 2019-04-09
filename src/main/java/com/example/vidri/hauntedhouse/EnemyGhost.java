package com.example.vidri.hauntedhouse;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.constraint.solver.widgets.Rectangle;

import java.util.Vector;


public class EnemyGhost {

    private Bitmap image;                   //Ghost image
    private int x;                          //x position
    private int y;                          //y position
    private int damage = 2;                 //ghost damage
    CharacterSprite player;                 //Player character
    Rect bounds;                            //Bounding rectangle for collision detection
    int height;                             //image height
    int width;                              //image width
    private int speed;                      //Speed of ghost

    //Constructor
    public EnemyGhost(Bitmap bitmap, CharacterSprite player) {
        image = bitmap;
        this.x = 200;
        this.y = 200;
        speed = 1;
        this.player = player;

        height = image.getHeight();
        width = image.getWidth();

        bounds = new Rect(x, y, (x + height), (y+ width));
    }

    //Draw ghost on canvas
    public void draw(Canvas canvas) {

        canvas.drawBitmap(image, x, y, null);
    }

    //Reset ghost after damaging player
    public void resetEnemy() {

        this.x = 0;
        this.y = 0;

        bounds = new Rect(x, y, (x + width), (y+ height));
    }


    //Update bounding rectangle and move ghost
    public void update() {
        bounds = new Rect(x, y, (x + width), (y+ height));
        if(player.getX() > x) {
           x =  x + speed;
        }else {
            x = x - speed;
        }

        if(player.getY() > y) {
            y = y + speed;
        }else {
            y = y - speed;
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

    public int getSpeed() {
        return speed;
    }

    public int getDamage() {
        return damage;


    }

    public Rect getBounds() {
        return bounds;
    }
}



