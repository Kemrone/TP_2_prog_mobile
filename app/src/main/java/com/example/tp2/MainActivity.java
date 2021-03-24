package com.example.tp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button envoyer = null;
    Button reset = null;
    EditText taille = null;
    EditText poids = null;
    CheckBox commentaire = null;
    RadioGroup group = null;
    TextView result = null;
    Spinner spinnerctrl;

    private final String texteInit = "Cliquez sur le bouton « Calculer l'IMC » pour obtenir un résultat.";







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        envoyer = (Button)findViewById(R.id.calcul);
        reset = (Button)findViewById(R.id.reset);
        taille = (EditText)findViewById(R.id.taille);
        poids = (EditText)findViewById(R.id.poids);
        commentaire = (CheckBox)findViewById(R.id.commentaire);
        group = (RadioGroup)findViewById(R.id.group);
        result = (TextView)findViewById(R.id.result);
        envoyer.setOnClickListener(envoyerListener);
        reset.setOnClickListener(resetListener);
        commentaire.setOnClickListener(checkedListener);
        taille.addTextChangedListener(textWatcher);
        poids.addTextChangedListener(textWatcher);


    };

    private View.OnClickListener envoyerListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            //  on récupère la taille
            String t = taille.getText().toString();
            // On récupère le poids
            String p = poids.getText().toString();
            float tValue = 0;
            float pValue = 0;
            if(t.isEmpty() == false) {
                tValue = Float.valueOf(t);
            }


            // Puis on vérifie que la taille est cohérente
            if(tValue <= 0 || t.isEmpty())
                Toast.makeText(MainActivity.this, R.string.error1, Toast.LENGTH_SHORT).show();
            else {
                if(p.isEmpty()==false) {
                    pValue = Float.valueOf(p);
                }
                if(pValue <= 0 || p.isEmpty())
                    Toast.makeText(MainActivity.this, R.string.error2, Toast.LENGTH_SHORT).show();
                else {
                    // Si l'utilisateur a indiqué que la taille était en centimètres
                    // On vérifie que la Checkbox sélectionnée est la deuxième à l'aide de son identifiant
                    if (group.getCheckedRadioButtonId() == R.id.radio_centimetre) tValue = tValue / 100;
                    float imc = pValue / (tValue * tValue);
                    String resultat=getString(R.string.resultat) + imc+" . ";
                    if(commentaire.isChecked()) resultat += interpreteIMC(imc);
                    result.setText(resultat);
                }
            }
        }
    };

    private String interpreteIMC(float imc) {
        String res = "";
        if(imc<16.5){
            res = getString(R.string.famine);
        }
        else if(imc>=16.5 && imc <18.5){
            res = getString(R.string.maigreur);
        }
        else if(imc>=18.5 && imc <25){
            res = getString(R.string.normal);
        }
        else if(imc>=25 && imc <30){
            res = getString(R.string.surpoids);
        }
        else if(imc>=30 && imc <35){
            res = getString(R.string.obesiteModeree);
        }
        else if(imc>=35 && imc <40){
            res = getString(R.string.severe);
        }
        else if(imc>=40 ){
            res = getString(R.string.morbide);
        }

        return res;

    }


    private View.OnClickListener resetListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            poids.getText().clear();
            taille.getText().clear();
            result.setText(texteInit);
        }
    };


    private View.OnClickListener checkedListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if(((CheckBox)v).isChecked()) {
                result.setText(texteInit);
            }
        }
    };




    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            result.setText(texteInit);
            if (taille.getText().toString().indexOf('.') > 0) {
                group.check(R.id.radio_metre);
            }
        }


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }

    };




}