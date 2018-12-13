package de.richard.alex.flashbox;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class PlayActivity extends AppCompatActivity {

    static CardStack stackbox;
    static Card currentCard;
    static boolean turn;
    static int cardNumber;
    static int points;

    // make Layout
    TextView question;
    Button button_1;
    Button button_2;
    Button button_3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        // Get Layout
        question = findViewById(R.id.question);
        button_1 = findViewById(R.id.answer1);
        button_2 = findViewById(R.id.answer2);
        button_3 = findViewById(R.id.answer3);


        //TODO: Get Stack
        int stacknumber =  Integer.parseInt(getIntent().getStringExtra(HauptmenuActivity.EXTRA_STACK));
        stackbox = HauptmenuActivity.getExampleStacks().get(stacknumber);


        // initialise
        points = 0;
        cardNumber = 0;
        turn = true;
        currentCard = stackbox.getCard(cardNumber);
        refreshCard();



        button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (turn) {
                    flash(0);
                } else {
                    nextCard();
                }
                turn = !(turn);
            }
        });

        button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (turn) {
                    flash(1);
                } else {
                    nextCard();
                }
                turn = !(turn);
            }
        });

        button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (turn) {
                    flash(2);
                } else {
                    nextCard();
                }
                turn = !(turn);
            }
        });
    }


    private void flash(int mychoice) {

        if (currentCard.getCorrect_answer() == mychoice) {
            //Correct Answer
            question.setText("Richtig! \n" + currentCard.getExplanation());
            points++;
        } else {
            //Incorrect Answer
            question.setText("Falsch, \n" + currentCard.getExplanation());
            // points-- ?
        }

        switch (currentCard.getCorrect_answer()) {
            case 0:
                button_1.setBackgroundColor(Color.GREEN);
                button_2.setBackgroundColor(Color.RED);
                button_3.setBackgroundColor(Color.RED);
                break;
            case 1:
                button_1.setBackgroundColor(Color.RED);
                button_2.setBackgroundColor(Color.GREEN);
                button_3.setBackgroundColor(Color.RED);
                break;
            case 2:
                button_1.setBackgroundColor(Color.RED);
                button_2.setBackgroundColor(Color.RED);
                button_3.setBackgroundColor(Color.GREEN);
                break;
            default:

        }
    }


    private void nextCard() {
        cardNumber++;
        currentCard = stackbox.getCard(cardNumber);

        if (currentCard == null) {
            //TODO: Auswertung

                finish();

        }

        question.setText(currentCard.getQuestion());

        button_1.setText(currentCard.getAnswers()[0]);
        button_1.setBackgroundColor(Color.GRAY);

        button_2.setText(currentCard.getAnswers()[1]);
        button_2.setBackgroundColor(Color.GRAY);

        button_3.setText(currentCard.getAnswers()[2]);
        button_3.setBackgroundColor(Color.GRAY);

    }


    private void refreshCard() {
        question.setText(currentCard.getQuestion());

        button_1.setText(currentCard.getAnswers()[0]);
        button_1.setBackgroundColor(Color.GRAY);

        button_2.setText(currentCard.getAnswers()[1]);
        button_2.setBackgroundColor(Color.GRAY);

        button_3.setText(currentCard.getAnswers()[2]);
        button_3.setBackgroundColor(Color.GRAY);
    }

}
