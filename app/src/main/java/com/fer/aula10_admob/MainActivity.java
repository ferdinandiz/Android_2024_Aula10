package com.fer.aula10_admob;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    AdView adView;
    InterstitialAd mInterstitialAd;
    Random r;
    Button btnA, btnB, btnC, btnD, btnReset, btnFacil, btnDificil;
    RelativeLayout relative;
    TextView texto;
    String[] cores = new String[4];
    String[] corRand = {"azul", "verde", "vermelho"};
    int value, i=0, dificuldade = 0;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnA = findViewById(R.id.btn01);
        btnB = findViewById(R.id.btn02);
        btnC = findViewById(R.id.btn03);
        btnD = findViewById(R.id.btn04);
        btnReset = findViewById(R.id.btnReset);
        btnFacil = findViewById(R.id.btnFacil);
        btnDificil = findViewById(R.id.btnDificil);
        texto = findViewById(R.id.dificuldade);
        relative = findViewById(R.id.relative);

        adView = findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder().build();
        r = new Random();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                Toast.makeText(MainActivity.this, "Comercial rodando...", Toast.LENGTH_SHORT).show();
            }
        });

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Toast.makeText(MainActivity.this, "Falhou para abrir. \nErro: "+loadAdError, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Toast.makeText(MainActivity.this, "Anúncio carregado!", Toast.LENGTH_SHORT).show();
            }
        });

        adView.loadAd(adRequest);

        //Intersticial AD
        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                mInterstitialAd = null;
                Toast.makeText(MainActivity.this, "Falha ao abrir intersticial!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                mInterstitialAd = interstitialAd;
            }
        });

        //Lógica do Jogo

        btnFacil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnFacil.setEnabled(false);
                btnFacil.setVisibility(View.INVISIBLE);
                btnDificil.setEnabled(false);
                btnDificil.setVisibility(View.INVISIBLE);
                texto.setVisibility(View.INVISIBLE);
                relative.setVisibility(View.VISIBLE);
            }
        });

        btnDificil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnFacil.setEnabled(false);
                btnFacil.setVisibility(View.INVISIBLE);
                btnDificil.setEnabled(false);
                btnDificil.setVisibility(View.INVISIBLE);
                texto.setVisibility(View.INVISIBLE);
                relative.setVisibility(View.VISIBLE);
                dificuldade = 1;
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        do{
            value = r.nextInt(3)+1;
            if(value == 1) cores[i] = corRand[0];
            else if(value == 2) cores[i] = corRand[1];
            else cores[i] = corRand[2];
            i++;
        }while (i < 4);

        setaCores(btnA, cores[0]);
        setaCores(btnB, cores[1]);
        setaCores(btnC, cores[2]);
        setaCores(btnD, cores[3]);

        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cores[0] = trocaCor(cores[0]);
                setaCores(btnA, cores[0]);
                cores[1] = trocaCor(cores[1]);
                setaCores(btnB, cores[1]);
                if(dificuldade == 1){
                    cores[3] = trocaCor(cores[3]);
                    setaCores(btnD, cores[3]);
                }
                vericaCores();
            }
        });

        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cores[0] = trocaCor(cores[0]);
                setaCores(btnA, cores[0]);
                cores[1] = trocaCor(cores[1]);
                setaCores(btnB, cores[1]);
                cores[2] = trocaCor(cores[2]);
                setaCores(btnC, cores[2]);
                vericaCores();
            }
        });

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cores[1] = trocaCor(cores[1]);
                setaCores(btnB, cores[1]);
                cores[2] = trocaCor(cores[2]);
                setaCores(btnC, cores[2]);
                cores[3] = trocaCor(cores[3]);
                setaCores(btnD, cores[3]);
                vericaCores();
            }
        });

        btnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cores[2] = trocaCor(cores[2]);
                setaCores(btnC, cores[2]);
                cores[3] = trocaCor(cores[3]);
                setaCores(btnD, cores[3]);
                if(dificuldade == 1){
                    cores[0] = trocaCor(cores[0]);
                    setaCores(btnA, cores[0]);
                }
                vericaCores();
            }
        });

    }

    private void vericaCores() {
        if(cores[0].equals("verde") &&
            cores[1].equals("verde") &&
            cores[2].equals("verde") &&
            cores[3].equals("verde")){
            Toast.makeText(this, "Parabéns, você ganhou!", Toast.LENGTH_SHORT).show();
            btnA.setEnabled(false);
            btnB.setEnabled(false);
            btnC.setEnabled(false);
            btnD.setEnabled(false);
        }
    }

    private String trocaCor(String cor) {
        switch (cor){
            case "azul":
                cor = "verde";
                break;
            case "verde":
                cor = "vermelho";
                break;
            case "vermelho":
                cor = "azul";
                break;
        }
        return cor;
    }

    private void setaCores(Button btn, String cor) {
        if(cor.equals("azul")){
            btn.setBackgroundColor(Color.BLUE);
        } else if (cor.equals("verde")) {
            btn.setBackgroundColor(Color.GREEN);
        } else {
            btn.setBackgroundColor(Color.RED);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mInterstitialAd != null)
            mInterstitialAd.show(MainActivity.this);
    }

}