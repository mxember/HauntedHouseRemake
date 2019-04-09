package com.example.vidri.hauntedhouse;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class DrawBackground {

    private int height;                   //Height of screen
    private int width;                    //Width of screen
    private static Rect rect1;            //Rectangles to draw background
    private static Rect rect2;
    private static Rect rect3;
    private static Rect rect4;
    private static Rect rect5;
    private static Rect rect6;
    private static Rect rect7;
    private static Rect rect8;
    private static Rect rect9;
    private static Rect rect10;
    private static Rect rect11;
    private static Rect rect12;
    private static Rect rect13;
    private static Rect rect14;
    private static Rect exitRect;                   //Exit rectangle
    private static Rect warpRectLeftTop;            //To warp player to another spot
    private static Rect warpRectRightTop;           //To warp player to another spot
    private static Rect warpRectRightBottom;        //To warp player to another spot

    //Constructor, with screen height and width
    public DrawBackground (int hgt, int wid){
        height = hgt;
        width =  wid;
    }

    //Draw the background
    public void draw(Canvas canvas) {
        if (canvas != null) {
            canvas.drawColor(Color.BLACK);
            Paint paint = new Paint();
            paint.setColor(Color.rgb(0, 0, 250));

            rect1 = new Rect(0, 0, 60, height);
            rect2 = new Rect(0, 0, 200,height/2);
            rect3 = new Rect(0, (height/2)+200, 200, height);
            rect4 = new Rect(0, 0, width/4, 120);
            rect5 = new Rect(width/2, 0, (width/2)+200, height/2);
            rect6 = new Rect(width/2, (height/2)+200, (width/2)+200, height);
            rect7 = new Rect((width/4)+200, 0, (width/2) + (width/4), 120);
            rect8 = new Rect(0, (height-200), (width/4), height);
            rect9 = new Rect(((width/4)+200), (height-200), ((width/2) + (width/4)), height);
            rect10 = new Rect((width-60), 0, width, height);
            rect11 = new Rect((width-150), 0, width, height/2);
            rect12 = new Rect((width-150), (height/2)+200, width, height);
            rect13 = new Rect(((width/2) + (width/4) + 200), 0, width, 120);
            rect14 = new Rect(((width/2) + (width/4) + 200), (height-200), width, height);

            //Draw exit rectangle for collision detection
            exitRect = new Rect((width/4), (height-100), (width/2), height);
            Paint paint2 = new Paint();
            paint2.setColor(Color.TRANSPARENT);

            canvas.drawRect(exitRect, paint2);

            //Draw exit rectangles for warping
            warpRectLeftTop = new Rect((width/4), 0, (width/2), 60);
            warpRectRightTop = new Rect((width/2+ 200), 0, (width/2) + (width/4) + 200, 60);
            warpRectRightBottom = new Rect((width/2 + 200), height-200, ((width/2) + (width/4) + 200), height);

            canvas.drawRect(warpRectLeftTop, paint2);
            canvas.drawRect(warpRectRightTop, paint2);
            canvas.drawRect(warpRectRightBottom, paint2);


            //Draw background
            canvas.drawRect(rect1, paint); //leftmost, full height
            canvas.drawRect(rect2, paint); //leftmost, half height, top
            canvas.drawRect(rect3, paint); //leftmost, half height, bottom
            canvas.drawRect(rect4, paint); //leftmost, top horizontal
            canvas.drawRect(rect5, paint); //halfway vertical top
            canvas.drawRect(rect6, paint); //halfway vertical bottom
            canvas.drawRect(rect7, paint); //halfway, top horizontal
            canvas.drawRect(rect8, paint); //leftmost, bottom, horizontal
            canvas.drawRect(rect9, paint); //halfway, bottom, horizontal
            canvas.drawRect(rect10, paint); //rightmost, full height
            canvas.drawRect(rect11, paint); //rightmost, half height, top
            canvas.drawRect(rect12, paint); //rightmost, half height, bottom
            canvas.drawRect(rect13, paint); //rightmost, top, horizontal
            canvas.drawRect(rect14, paint); //rightmost, bottom, horizontal


        }
    }

    public static Rect getRect1() {
        return rect1;
    }

    public static Rect getRect2() {
        return rect2;
    }

    public static Rect getRect3() {
        return rect3;
    }

    public static Rect getRect4() {
        return rect4;
    }

    public static Rect getRect5() {
        return rect5;
    }

    public static Rect getRect6() {
        return rect6;
    }

    public static Rect getRect7() {
        return rect7;
    }

    public static Rect getRect8() {
        return rect8;
    }

    public static Rect getRect9() {
        return rect9;
    }

    public static Rect getRect10() {
        return rect10;
    }

    public static Rect getRect11() {
        return rect11;
    }

    public static Rect getRect12() {
        return rect12;
    }

    public static Rect getRect13() {
        return rect13;
    }

    public static Rect getRect14() {
        return rect14;
    }

    public static Rect getExitRect() {return exitRect; }

    public static Rect getWarpRectLeftTop() {return warpRectLeftTop; }

    public static Rect getWarpRectRightTop() { return warpRectRightTop; }

    public static Rect getWarpRectRightBottom() { return warpRectRightBottom; }

}
