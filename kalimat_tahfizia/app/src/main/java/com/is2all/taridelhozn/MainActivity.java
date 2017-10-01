package com.is2all.taridelhozn;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;
    MediaPlayer mediaPlayer;
    CheckBox boxvol ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //////// Start Ads Admob Interstitial ///////////////

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-6359773224465392/3517448863");
        MobileAds.initialize(this, "YOUR_ADMOB_APP_ID");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());

            }
        });

        //////// End Ads Admob Interstitial ///////////////

    }

    public void start(View view) {
        Intent form1 = new Intent(this, listview.class);
        startActivity(form1);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

  ///////////////Start Share to Menu ///////////////
        if (item.getItemId()==R.id.share){
            Intent myintent = new Intent(Intent.ACTION_SEND) ;
            myintent.setType("text/plain");

            String body = "شارك البرنامج مع أصدقائك \n" + "\n"+
                    "https://play.google.com/store/apps/details?id=com.is2all.taridelhozn";
            String sub = "مشاركة البرنامج \n";

            myintent.putExtra(Intent.EXTRA_SUBJECT,sub);
            myintent.putExtra(Intent.EXTRA_TEXT,body);
            startActivity(Intent.createChooser(myintent,"مشاركة"));
        }
    /////////// End Share to Menu ///////////////

    /////////////// Start About to Menu///////////////
        if (item.getItemId()==R.id.about){
            AlertDialog.Builder builder=new AlertDialog.Builder(this) ;

            builder.setTitle("حول البرنامج");
            builder.setMessage("   الاصدار v1.0 \n " +"\n"+
                    " تم إنجاز هذا البرنامج من طرف محمد الحيرش \n" +"\n"+
                    " جمعت لكم في هذا البرنامج أروع ما قيل في دفع الهم والحزن والكرب \n وإن كان كان طلب كبير على البرنامج سيتم تطويره إلى الأفضل إن شاء الله تعالى... \n" +"\n"+
                    "   med_9292@hotmail.fr");

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                }
            });

            builder.show();
        }
   /////////////// End About to Menu ///////////////

  /////////////// Start Exit to Menu ///////////////
        if (item.getItemId()==R.id.exit){
            super.onBackPressed();
            moveTaskToBack(true);
            finish();
        }
 /////////////// End Exit to Menu///////////////

        return super.onOptionsItemSelected(item);
    }

    public void fav(View view) {
        Intent fav = new Intent(this, favorites.class);
        startActivity(fav);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        finish();
    }

    @Override
    protected void onPause(){
        super.onPause();
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    public void klm(View view) {

        Intent klm = new Intent(this, com.is2all.taridelhozn.klm.class);
        startActivity(klm);
    }

    public void end(View view) {
        super.onBackPressed();
        moveTaskToBack(true);
        finish();
    }

}