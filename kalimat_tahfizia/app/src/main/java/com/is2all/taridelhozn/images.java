package com.is2all.taridelhozn;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class images extends listview {

    private InterstitialAd mInterstitialAd;

    TextView txt ;
    int count = 0;

    ////////// Start onCreate  /////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        txt =(TextView)findViewById(R.id.textView4);
        txt.setMovementMethod(new ScrollingMovementMethod());

        //////// Start // Ads Admob Interstitial ///////////////
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
        //////// End // Ads Admob Interstitial ///////////////

        txt=(TextView)findViewById(R.id.textView4);
        checkBox2 = (CheckBox) findViewById(R.id.checkBox2);

        Bundle b = getIntent().getExtras();
        int vule = b.getInt("vule");
        boolean savecheck = b.getBoolean("savecheck");
        txt.setText(listarray[vule]);
        checkBox2.setChecked(savecheck);
        count = vule ;

        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

    }
    ///////////////////////////// End onCreate///////

    public void back(View view) {
        txt=(TextView)findViewById(R.id.textView4);
        count--;
        if (count < 0){
            count = listarray.length-1;
        }
        txt.setText(listarray[count]);
        checkBox2.setChecked(items.get(count).box);

        Toast.makeText(this, ""+count, Toast.LENGTH_SHORT).show();
    }

    public void next(View view) {
         txt=(TextView)findViewById(R.id.textView4);
        count++;
        if (count > listarray.length-1){
            count = 0;
        }
        txt.setText(listarray[count]);
        checkBox2.setChecked(items.get(count).box);

        Toast.makeText(this, ""+count,Toast.LENGTH_SHORT).show();
    }



    public void copy(View view) {

         txt=(TextView)findViewById(R.id.textView4);

        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            clipboard.setText(txt.getText().toString());
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", txt.getText().toString());
            clipboard.setPrimaryClip(clip);

        }
        Toast.makeText(this, "تم نسخ المقولة ، شاركها مع أصدقائك", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void save() {

        SharedPreferences sharedPreferences = this.getSharedPreferences("boxch", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("checknox" + count, checkBox2.isChecked());
        editor.apply();

        count++;
        if (checkBox2.isChecked()) {
            Toast.makeText(this, " تم إضافة العنصر "+count+"إلى المفضلة ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, " تم إزالة العنصر"+count+" من المفضلة ", Toast.LENGTH_SHORT).show();
        }
        count--;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }


}
