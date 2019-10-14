package com.example.tic_tac_toe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button[][] buttons = new Button[3][3];
    private boolean player1_turn = true;

    private int round_count = 0;
    private int playe1_point = 0;
    private int playe2_point = 0;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);


        textViewPlayer1 = findViewById(R.id.player_1);
        textViewPlayer2 = findViewById(R.id.player_2);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String button_id = "button_" + i + j;

                int res_id = getResources().getIdentifier(button_id, "id", getPackageName());
                buttons[i][j] = findViewById(res_id);

                buttons[i][j].setOnClickListener(this);
            }
        }


        Button buttonReset = findViewById(R.id.button_reset);

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetgame();
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (player1_turn) {
            ((Button) v).setText("x");
        } else {
            ((Button) v).setText("o");
        }


        round_count++;


        if (checkforWin()) {
            if (player1_turn) {
                player1_wins();
            } else {
                player2_wins();
            }
        } else if (round_count == 9) {
            draw();
        } else {
            player1_turn = !player1_turn;
        }

    }

    private boolean checkforWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private void player2_wins() {
        playe2_point++;
        Toast.makeText(getApplicationContext(), "Player 2 Wins", Toast.LENGTH_LONG).show();

        updatePonitText();
        resetBoard();
    }

    private void player1_wins() {
        playe1_point++;
        Toast.makeText(getApplicationContext(), "Player 1 Wins", Toast.LENGTH_LONG).show();
        updatePonitText();
        resetBoard();

    }

    private void draw() {
        Toast.makeText(getApplicationContext(), "Draw", Toast.LENGTH_LONG).show();
        resetBoard();

    }

    private void updatePonitText() {
        textViewPlayer1.setText("Player 1 : " + playe1_point);
        textViewPlayer2.setText("Player 2 : " + playe2_point);

    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                buttons[i][j].setText("");
        }

        round_count = 0;
        player1_turn = true;
    }

    private void resetgame() {
        playe1_point = 0;
        playe2_point = 0;
        updatePonitText();
        resetBoard();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outstate) {
        super.onSaveInstanceState(outstate);
        outstate.putInt("roundcount", round_count);
        outstate.putInt("p1", playe1_point);
        outstate.putInt("p2", playe2_point);
        outstate.putBoolean("p1turn", player1_turn);


    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        round_count = savedInstanceState.getInt("roundcount");
        playe1_point = savedInstanceState.getInt("p1");
        playe2_point = savedInstanceState.getInt("p2");
        player1_turn = savedInstanceState.getBoolean("p1turn");
    }
}
