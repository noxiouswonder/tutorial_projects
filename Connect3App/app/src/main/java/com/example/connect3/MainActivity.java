package com.example.connect3;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int player = 1;//1: Red & 2: Yellow
    int[] gamestate = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    int [][] winningPositions = {{0, 1, 2} , {3, 4, 5} , {6, 7, 8}, {0, 3, 6} , {1, 4, 7} , {2, 5, 8} , {0, 4, 8} , {2, 4, 6}};
    int tappedBox;
    String tappedBoxString;
    boolean gameActive = true;


    public void dropIn(View view){
        ImageView cookie = (ImageView) view;
        tappedBoxString = cookie.getTag().toString();
        tappedBox = Integer.parseInt(tappedBoxString);
        if (gamestate[tappedBox] == 0 && gameActive){
            gamestate[tappedBox] = player;
            cookie.setTranslationY(-1500);
            Log.i("Box Occupied " , tappedBoxString);
            if (player == 1){
                cookie.setImageResource(R.drawable.red);
                gamestate[tappedBox] = player;
                player = 2;
            } else if (player == 2){
                cookie.setImageResource(R.drawable.yellow);
                gamestate[tappedBox] = player;
                player = 1;

            }

            cookie.animate().translationYBy(1500).rotation(7200).setDuration(500);

            for (int[] winningPosition : winningPositions) {
                String winner = "";
                if (gamestate[winningPosition[0]] == gamestate[winningPosition[1]] && gamestate[winningPosition[1]] == gamestate[winningPosition[2]] && gamestate[winningPosition[2]] != 0) {
                    //someone has won;
                    gameActive = false;
                    if (gamestate[winningPosition[0]] == 1) {

                        //player 1 has won
                        winner = "Red";

                    } else if (gamestate[winningPosition[0]] == 2) {

                        //player 2 has won
                        winner = "Yellow";
                    }
                    TextView textView = (TextView) findViewById(R.id.textView);
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(winner + " Wins!");
                }
            }

        }

    }

    public void restart(View view){
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setVisibility(View.INVISIBLE);
        Log.i("Info: " , "TextView Successful!");

        ImageView iv1 = (ImageView) findViewById(R.id.imageView1);
        ImageView iv2 = (ImageView) findViewById(R.id.imageView2);
        ImageView iv3 = (ImageView) findViewById(R.id.imageView3);
        ImageView iv4 = (ImageView) findViewById(R.id.imageView4);
        ImageView iv5 = (ImageView) findViewById(R.id.imageView5);
        ImageView iv6 = (ImageView) findViewById(R.id.imageView6);
        ImageView iv7 = (ImageView) findViewById(R.id.imageView7);
        ImageView iv8 = (ImageView) findViewById(R.id.imageView8);
        ImageView iv9 = (ImageView) findViewById(R.id.imageView9);

        ImageView[] IMGS = {iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8, iv9};
        for(int i = 0; i < 9; i++) {
            IMGS[i].setImageDrawable(null);
        }




        // GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        /*Log.i("Info: " , "Board identified!");
        for (int i = 0; i < gridLayout.getChildCount(); i++){
            Log.i("Info: " , "For loop started!");
            ImageView cookie = (ImageView) gridLayout.getChildAt(i);
            Log.i("Info: " , "Image View set!");
            cookie.setImageDrawable(null);
            Log.i("Info: " , "Image set to null!");
        }*/
        Log.i("Info: " , "Images nullified!");
        for (int i = 0; i < 9; i++){
            gamestate[i] = 0;
        }

        player = 1;//1: Red & 2: Yellow
        gameActive = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}