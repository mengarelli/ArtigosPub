package br.com.tibomenga.artigospub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

public class CarregandoActivity extends AppCompatActivity {
        String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carregando);

        if(getIntent().getBooleanExtra("SAIR", false)){
            finish();
            System.exit(0);
        } else {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            String password = sharedPrefs.getString("password","");
            if (password.equalsIgnoreCase("")) {
                Intent intent = new Intent(getApplicationContext(), CriarSenhaActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(getApplicationContext(), DigitarSenhaActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
