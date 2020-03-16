package com.example.flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;
import java.lang.String;

public class MainActivity extends AppCompatActivity {

    FlashcardDatabase flashcardDatabase; //FlashcardDatabase Instance
    List<Flashcard> allFlashcards; //List Object of Flashcard Objects
    int currentCardDisplayedIndex = 0; //Denoting index of current card displayed


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton newScreen;
        ImageButton next_button;
        Button delete_button;

        final int REQUEST_CODE = 100;

        flashcardDatabase = new FlashcardDatabase(this);
        allFlashcards = flashcardDatabase.getAllCards();
        for(Flashcard f : allFlashcards){
            Log.v(f.getQuestion(),f.getAnswer());
        }

        newScreen = (ImageButton) findViewById(R.id.newScreenBtn);
        next_button = (ImageButton) findViewById(R.id.nextBtn);
        delete_button = (Button) findViewById(R.id.deleteBtn);

        final TextView answer = (TextView) findViewById(R.id.answer);
        final TextView question = (TextView) findViewById(R.id.flashcard_question);

        //This is what the app will display when you start up
        if (allFlashcards != null && allFlashcards.size() > 0) {
            question.setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
            answer.setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
        }

        question.setOnClickListener(new View.OnClickListener(){
            boolean isInvisible = true;
            @Override
            public void onClick(View v){
                if(isInvisible == true){
                    answer.setVisibility(View.VISIBLE);
                    isInvisible = false;
                } else{
                    answer.setVisibility(View.INVISIBLE);
                    isInvisible = true;
                }
            }
        });

        newScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCard();
            }
        });

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCardDisplayedIndex++;

                if(currentCardDisplayedIndex > allFlashcards.size() - 1){
                    currentCardDisplayedIndex = 0;
                }

                if(allFlashcards != null && allFlashcards.size() > 0) {
                    question.setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                    answer.setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                } else{
                    question.setText(" ");
                    answer.setText(" ");
                }
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allFlashcards.remove(currentCardDisplayedIndex);
                flashcardDatabase.deleteCard(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                if(allFlashcards != null && allFlashcards.size() > 0) {

                    question.setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                    answer.setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                } else{
                    question.setText(" ");
                    answer.setText(" ");
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) { // this 100 needs to match the 100 we used when we called startActivityForResult!
            String prompt = data.getExtras().getString("Question"); // 'string1' needs to match the key we used when we put the string in the Intent
            String response = data.getExtras().getString("Answer");

            ((TextView) findViewById(R.id.flashcard_question)).setText(prompt);
            ((TextView) findViewById(R.id.answer)).setText(response);

            flashcardDatabase.insertCard(new Flashcard(prompt, response));
            allFlashcards = flashcardDatabase.getAllCards();
        }
    }

    public void addCard() {
        Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
        MainActivity.this.startActivityForResult(intent, 100);
        setResult(Activity.RESULT_OK, intent);
    }

}
