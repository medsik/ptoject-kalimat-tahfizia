package com.is2all.taridelhozn;

import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class imagesfv extends listview {

    TextView txt ;
    int count = 0;
    private InterstitialAd mInterstitialAd;

    //////////Start  /////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagesfv);

        TextView txt=(TextView)findViewById(R.id.textViewfv);
       txt.setMovementMethod(new ScrollingMovementMethod());

        Bundle b = getIntent().getExtras();
        int vule = b.getInt("vule");
        txt.setText(listarray[vule]);
        count = vule ;

        //////// Start // Ads Admob Interstitial ///////////////

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-6359773224465392/3517448863");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });

        //////// End // Ads Admob Interstitial ///////////////
    }

    /////////////////////////////End///////

    public void copy(View view) {

        TextView txt=(TextView)findViewById(R.id.textViewfv);

        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(txt.getText().toString());
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", txt.getText().toString());
            clipboard.setPrimaryClip(clip);

        }
        Toast.makeText(this, "تم نسخ المقولة، شاركها مع أصدقائك", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

}


