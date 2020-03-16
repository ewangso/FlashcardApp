package com.example.flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        ImageButton backBtn;
        Button saveBtn = (Button) findViewById(R.id.saveBtn); //Button



        backBtn = (ImageButton) findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText question = (EditText) findViewById(R.id.questionText);
                EditText answer = (EditText) findViewById(R.id.answerText);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);


                intent.putExtra("Question", question.getText().toString()); //Passing info back to MainActivity
                intent.putExtra("Answer", answer.getText().toString());

                setResult(RESULT_OK, intent);
                //startActivityForResult(intent , 100);
                finish();
            }
        });







    }
}
