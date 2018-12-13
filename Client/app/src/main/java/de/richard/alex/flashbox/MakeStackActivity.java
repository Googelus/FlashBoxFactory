package de.richard.alex.flashbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MakeStackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_stack);

        //Get Layout
        final Button finish = findViewById(R.id.make_stack_finish);
        final EditText name = findViewById(R.id.make_stack_name);



        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CardStack("user",name.getText().toString());
                finish();
            }
        });


    }
}
