package com.example.vidri.hauntedhouse;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;                      //Thread to run game

    private EnemyBat enemyBat;                      //Enemy bat object
    private CharacterSprite player;                 //Player character object
    private EnemyGhost enemyGhost;                  //Enemy ghost object
    private Urn urn;                                //Urn object

    DrawBackground background;                      //Background maze


    private boolean enemyCollide;                   //Check for enemy collision
    private boolean urnCollide;                     //Check for urn collision
    private boolean exitForWin = false;

    private int height;                             //Height of screen
    private int width;                              //Width of screen


    long starttime;                                 //Start of game
    long millis;                                    //Milliseconds
    int seconds;                                    //Seconds, for scoring


    //Create game view and initiate thread
    public GameView(Context context) {
        super(context);


        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    //Create surface and begin game
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();


        //Get width and height of screen to draw background
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        height = metrics.heightPixels;
        width = metrics.widthPixels;


        //Character bitmaps
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.eyes_default);
        player = new CharacterSprite(getContext(), bmp);

        enemyBat = new EnemyBat(BitmapFactory.decodeResource(getResources(), R.drawable.bat1), player);
        enemyGhost = new EnemyGhost(BitmapFactory.decodeResource(getResources(), R.drawable.ghost), player);
        urn = new Urn(BitmapFactory.decodeResource(getResources(), R.drawable.urn), height, width);
        

        //Draw background and set character on screen
        background = new DrawBackground(height, width);


        //Set onTouchListener to screen, for character movement
        this.setOnTouchListener(onTouchListener());


    }

    //Screen onTouchListener
    private OnTouchListener onTouchListener() {
        return new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                float tx;                       //Touch x-coordinate
                float ty;                       //Touch y-coordinate
                float deltaX;                   //Difference between touch and player x-coordinate
                float deltaY;                   //Difference between touch and player y-coordinate

                float direction;                //Direction vector of character movement

                //Get touch action
                int action = motionEvent.getAction();

                //Determine movement
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        tx = motionEvent.getX();
                        ty = motionEvent.getY();

                        deltaX = tx - player.getX();
                        deltaY = ty - player.getY();

                        //Determine direction vector
                        direction = (float) Math.atan2(deltaY, deltaX);

                        //Check for collision with middle obstacles
                        if (checkCollision(player.getBounds(), DrawBackground.getRect5()) ||
                                checkCollision(player.getBounds(), DrawBackground.getRect6())) {

                                player.setX(player.getX() - 100);
                                player.setY(player.getY() + 10);
                        }

                        //Check for collision with left side obstacles
                        if (checkCollision(player.getBounds(), DrawBackground.getRect2()) ||
                                checkCollision(player.getBounds(), DrawBackground.getRect3()) ||
                                checkCollision(player.getBounds(), DrawBackground.getRect1())) {

                            player.setX(player.getX() + 100);
                            player.setY(player.getY() + 10);
                        }

                        //Check for collision with right side obstacles
                        if (checkCollision(player.getBounds(), DrawBackground.getRect10()) ||
                                checkCollision(player.getBounds(), DrawBackground.getRect11()) ||
                                checkCollision(player.getBounds(), DrawBackground.getRect12())) {

                            player.setX(player.getX() - 100);
                            player.setY(player.getY() + 10);
                        }

                        //Check for collision with top obstacles
                        if (checkCollision(player.getBounds(), DrawBackground.getRect4()) ||
                                checkCollision(player.getBounds(), DrawBackground.getRect7()) ||
                                checkCollision(player.getBounds(), DrawBackground.getRect13())){

                            player.setX(player.getX() + 10);
                            player.setY(player.getY() + 100);
                        }

                        //Check for collision with bottom obstacles
                        if (checkCollision(player.getBounds(), DrawBackground.getRect8()) ||
                                checkCollision(player.getBounds(), DrawBackground.getRect9()) ||
                                checkCollision(player.getBounds(), DrawBackground.getRect14())) {

                            player.setX(player.getX() + 10);
                            player.setY(player.getY() - 40);
                        }

                        //Warp player if leave through top left exit to top right exit
                        if (checkCollision(player.getBounds(), DrawBackground.getWarpRectLeftTop()))
                        {
                            player.setX(((width/2) + (width/4)));
                            player.setY(100);
                        }

                        //Warp player if leave through top right to top left exit
                        if (checkCollision(player.getBounds(), DrawBackground.getWarpRectRightTop()))
                        {
                            player.setX((width/4) + 100);
                            player.setY(100);
                        }

                        //Warp player if leave through bottom right to top left exit
                        if (checkCollision(player.getBounds(), DrawBackground.getWarpRectRightBottom()))
                        {
                            player.setX((width/2) + (width/4));
                            player.setY(100);
                        }


                        //Otherwise move player
                        else {

                            //Move character towards touched coordinates
                            player.setX(player.getX() + (player.getSpeed() * (float) Math.cos(direction)));
                            player.setY(player.getY() + (player.getSpeed() * (float) Math.sin(direction)));

                        }
                        invalidate();

                        break;
                    case MotionEvent.ACTION_MOVE:

                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        };

    }


    //Stop thread and stop game
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    //Update game view, counting seconds
    public void update() {

        millis = System.currentTimeMillis() - starttime;
        seconds = (int) (millis / 1000);
        seconds = seconds % 60;


    }


    //Draw canvas with game components
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {


            //Create black background
            canvas.drawColor(Color.BLACK);
            Paint paint = new Paint();

            //Draw maze background on canvas
            background.draw(canvas);

            //Create timer in upper right corner
            paint.setColor(Color.argb(255, 249, 129, 0));
            paint.setTextSize(50);
            canvas.drawText("Time: " + seconds, (width - 250), 50, paint);

            //Place characters on canvas
            player.draw(canvas);
            player.update();

            urn.draw(canvas);
            urn.update(height, width);

            enemyGhost.draw(canvas);
            enemyGhost.update();
            enemyBat.draw(canvas);
            enemyBat.update();

            //Start clock
            starttime = 0;

            //Check for enemy collisions and vibrate if collision occurs
            enemyCollide = checkCollision(player.getBounds(), enemyBat.getBounds());
            if (enemyCollide) {

                Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);

                v.vibrate(400);

                player.loseLife();

                enemyBat.resetEnemy();
            }

            enemyCollide = checkCollision(player.getBounds(), enemyGhost.getBounds());
            if (enemyCollide) {

                Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);

                v.vibrate(800);

               player.loseLife();

               enemyGhost.resetEnemy();
            }

            //Determine if urn is captured through collision detection
            urnCollide = isCaptured();

            //Determine if player is exiting house - player cannot exit without urn
            exitForWin = isExiting();


            //If player has urn and leaves the house
            if ((urn.getX() == 0 && urn.getY() == 0) && exitForWin) {


                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        winTheGame();
                    }
                });



            }

            //Game over if player loses lives
            if (player.getLives() == 0) {

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        gameOver();
                    }
                });
            }


        }
    }

    //Check for collision between to objects
    public boolean checkCollision(Rect rect1, Rect rect2) {

        return (rect1.intersect(rect2));


    }


    //Determine if urn is captured, if so, move it to upper left
    public boolean isCaptured() {

        boolean captured = checkCollision(player.getBounds(), urn.getBounds());

        if (captured) {
            urn.setX(0);
            urn.setY(0);

        }

        return captured;

    }

    //Check to see if player intersects door to leave house
    public boolean isExiting() {

        boolean exiting = checkCollision(player.getBounds(), DrawBackground.getExitRect());

        return exiting;
    }



    //Stop the main thread and calculate score
    public void winTheGame() {

        //Convert seconds to score
        float score = seconds;

        //Score determined in CharacterSprite
        player.setScore(true, score);

        //Set to int to cut decimals
        int intScore = (int) player.getScore();

        //Get old high score
        SharedPreferences oldEasyScore = getContext().getSharedPreferences("EasyModeScore", Context.MODE_PRIVATE);
        int easyScore1 = oldEasyScore.getInt("easy_score", 0);

        //If the new score is higher than the old one, add it to SharedPreferences
        if (intScore > easyScore1) {

            //Store high score in shared preferences
            SharedPreferences easyScore = getContext().getSharedPreferences("EasyModeScore", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = easyScore.edit();

            editor.putInt("easy_score", intScore);
            editor.commit();
        }


        //Build dialog to ask player what to do next
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle(getContext().getString(R.string.win_game) + "      " + "Score: " + intScore);

        builder.setItems(R.array.win_dialog_items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: //Share Score
                                shareScore(player.getScore());
                                break;
                            case 1: //restart game
                                //thread.setRunning(true);
                                //thread.start();

                                break;
                        }
                    }
                }
        );

        builder.setNegativeButton(getContext().getString(R.string.cancel), null);

        builder.create().show();

        thread.setRunning(false);
    }


    //Game Over, player score is the time spent in house
    public void gameOver() {


        player.setScore(false, seconds);

        float score = player.getScore();

        int intScore = (int) score;

        //Get old high score
        SharedPreferences oldEasyScore = getContext().getSharedPreferences("EasyModeScore", Context.MODE_PRIVATE);
        int easyScore1 = oldEasyScore.getInt("easy_score", 0);

        //If the new score is higher than the old one, add it to SharedPreferences
        if (intScore > easyScore1) {

            //Store high score in shared preferences
            SharedPreferences easyScore = getContext().getSharedPreferences("EasyModeScore", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = easyScore.edit();

            editor.putInt("easy_score", intScore);
            editor.commit();
        }

        //Create dialog to ask player what to do next
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle(getContext().getString(R.string.game_over) + "      " + "Score: " + intScore);

        builder.setItems(R.array.win_dialog_items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: //Share Score
                                shareScore(player.getScore());
                                break;
                            case 1: //restart game



                                //Intent intent = new Intent(gContext, MainActivityFragment.class);
                                //gContext.startActivity(intent);
                                //thread.setRunning(true);
                               // thread.start();
                                break;
                        }
                    }
                }
        );

        builder.setNegativeButton(getContext().getString(R.string.cancel), null);

        builder.create().show();

        thread.setRunning(false);

    }

    //Intent to share score
    private void shareScore(double score) {

        int intScore = (int) score;

        String message = "My new Haunted House Score: " + intScore;

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, message);

        getContext().startActivity(Intent.createChooser(shareIntent, getContext().getString(R.string.share_score)));
    }
}



