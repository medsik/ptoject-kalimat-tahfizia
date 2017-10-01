package com.is2all.taridelhozn;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;

import static com.is2all.taridelhozn.R.id.delfav;

public class favorites extends AppCompatActivity {
    public int vule = 0;
    TextView txttitle;
    ArrayList<ListItem> items;
    ArrayList<ListItem> items1;
    ListView lst1;
    int positionvule;
    CheckBox checkdel ;

    arraylist listv = new arraylist();
    String[] listarray = listv.listarray;

    private InterstitialAd mInterstitialAd;

    //////////////////////START onCreate////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        items = new ArrayList<ListItem>();
        items1 = new ArrayList<ListItem>();

        int i = 0;
        while (i <= listarray.length-1) {
            items.add(new ListItem(listarray[i]," ", true));
            i++;
        }

        ///////////////Start lOAD dATA ///////////
        checkdel = (CheckBox) findViewById(delfav);
        int sizelst = 0;
        int ii = 0;
        while (ii <= listarray.length-1) {
            SharedPreferences sharedPreferences = this.getSharedPreferences("boxch", Context.MODE_PRIVATE);
            if (sharedPreferences != null) {
                boolean value = sharedPreferences.getBoolean("checknox" + ii, false);
                items.get(ii).box = value;

                if (value) {

                    items1.add((new ListItem(listarray[ii]," ", true )));
                    int xx =  ii + 1;
                    items1.get(sizelst).savevalue = ii;
                    items1.get(sizelst).checksetring = " "+xx;
                    sizelst++;
                }
                ii++;
            }
        }
        sizelst = 0;

        ///////////////////////////End LOAD DATA /////////////

        MyCustomAdapter myadapter = new MyCustomAdapter(items1);
        lst1 = (ListView) findViewById(R.id.listviewfav);
        lst1.setAdapter(myadapter);

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

/////////////////////END onCreate////////////////////

    public void runform2() {
        Intent imagesfv2 = new Intent(this, imagesfv.class);
        imagesfv2.putExtra("vule", vule);
        startActivity(imagesfv2);
    }

    ////////////////////////////////////////////////start adapter/////////
    class MyCustomAdapter extends BaseAdapter {
        ArrayList<ListItem> Items = new ArrayList<ListItem>();

        MyCustomAdapter(ArrayList<ListItem> Items) {
            this.Items = Items;
        }

        @Override
        public int getCount() {
            return Items.size();
        }

        @Override
        public Object getItem(int position) {
            return Items.get(position).title;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View View1 = inflater.inflate(R.layout.itemfv, null);

            txttitle = (TextView) View1.findViewById(R.id.textView);
            txttitle.setText(Items.get(position).title);

            txttitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int btnvalue = Items.get(position).savevalue;
                    vule = btnvalue;
                    runform2();
                }
            });

            checkdel = (CheckBox) View1.findViewById(delfav);
            checkdel.setChecked(Items.get(position).box);
            checkdel.setText(Items.get(position).checksetring);
            checkdel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positionvule = items1.get(position).savevalue;
                    save();
                    items.clear();
                    items1.remove(position);
                    MyCustomAdapter myadapter = new MyCustomAdapter(items1);
                    lst1.setAdapter(myadapter);

                }
            });
            return View1;
        }
    }

    public void save() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("boxch", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("checknox" + positionvule, false);
        editor.apply();

        positionvule++;
        Toast.makeText(this, " تم إزالة العنصر"+positionvule+" من المفضلة ", Toast.LENGTH_SHORT).show();
        positionvule--;

    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }


}
