package de.richard.alex.flashbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.LinkedList;
import java.util.List;

public class HauptmenuActivity extends AppCompatActivity {

    public static final String EXTRA_STACK;

    static {
        EXTRA_STACK = "de.richard.alex.flashbox.extra.STACK";
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hauptmenu);

        final Button singlePlayer = findViewById(R.id.sp);
        final Button playbutton = findViewById(R.id.Settings);

        singlePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSP();
            }
        });

        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSettings();
            }
        });

    }

    private void startSP() {
        Intent intent = new Intent(this, SPBrowseActivity.class);
        intent.putExtra(EXTRA_STACK, 0);
        startActivity(intent);
    }

    private void startSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }




    // for testing
    public static List<CardStack> getExampleStacks() {

        List<CardStack> stacklist = new LinkedList<CardStack>();

        CardStack res = new CardStack("Richard","AW");
        for (int i = 0;i<3;i++) {
            res.addCard(new Card("Wer erfand die Zündkerze?", "Thomas Eddison", "Robert Bosch", "DJ Bobo", "Robert Bosch erfand die Zündkerze, von ihm aus rührt auch der Name der Firma Bosch", 1));
            res.addCard(new Card("Was ist Brot?", "Stärke", "Eiweiß", "Fett", "Brot ist ein Poly-Maltose-Sacharid und damit Stärke", 0));
            res.addCard(new Card("Woraus bestehen Myzellen?", "aus DNA", "aus Fetten", "aus Proteinen", "Myzellen sind Lipidmembrankugeln", 1));
        }
        stacklist.add(res);
        res = new CardStack("Googelus","Cool");
        for (int i = 0;i<3;i++) {
            res.addCard(new Card("Wer bist du?", "Ich", "Du", "Dein schlimmster Alptraum", "Ich weiß, dumme Frage", 0));
            res.addCard(new Card("1+2=", "e^(45) - 12", "pi", "(3^2 - sqrt(9))/2", "(9 - 3)/2 = 3 = 1+2", 2));
            res.addCard(new Card("Wieviel wiegt 1kg Wasser?", "nix", "1 liter", "1000g", "1000g = 1kg", 1));
            res.addCard(new Card("Woraus ist Käse?","aus Füßen","aus Parmesan","aus Milch","meistens zumindest",2));
        }
        stacklist.add(res);
        res = new CardStack("Sarah","Chemie");
        for (int i = 0;i<3;i++) {
            res.addCard(new Card("Welches ist die meistgesprochene Sprache?", "English", "Madarin", "Spanisch", "Englisch, die Frage war nicht nach der Muttersrpache", 0));
            res.addCard(new Card("Wie lang dauerte der 100 jährige Krieg?", "100 Jahre", "13 Jahre", "116 Jahre", "Der hundertjährige Krieg dauerte 116 Jahre", 2));
            res.addCard(new Card("Sind Fernsehfragen zu einfach?", "Ja", "Kaninchen", "", "?", 0));
        }
        stacklist.add(res);

        return(stacklist);
    }






}
