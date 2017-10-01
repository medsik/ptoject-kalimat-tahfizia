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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;

public class listview extends AppCompatActivity {

    TextView txttitle;
    TabHost host;
    CheckBox checkBox1, checkBox2;
    ArrayList<ListItem> items, itemslist2, itemslist3, itemslist1;
    boolean loadcheckbox = false;
    int positionvule = 0;
    Button btn;

    arraylist listv = new arraylist();
    String[] listarray = listv.listarray;
    private InterstitialAd mInterstitialAd;

    //////////////////////START onCreate////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        items = new ArrayList<ListItem>();

        int i = 0;
        while (i <= listarray.length-1) {  ///  طول المصفوفة /////
            items.add(new ListItem(listarray[i], "", true));
            items.get(i).savevalue = i;
            i++;
        }

        load();

        MyCustomAdapter myadapter1 = new MyCustomAdapter(items);
        ListView lst1 = (ListView) findViewById(R.id.listview1);
        lst1.setAdapter(myadapter1);

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
        Intent form2 = new Intent(this, images.class);
        form2.putExtra("vule", positionvule);
        form2.putExtra("savecheck", loadcheckbox);
        startActivity(form2);
    }

    ////////////////////////////////////////////////start Class adapter/////////
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
            View View1 = inflater.inflate(R.layout.itemtxt, null);

            txttitle = (TextView) View1.findViewById(R.id.textView);
            btn = (Button) View1.findViewById(R.id.buton2);
            checkBox1 = (CheckBox) View1.findViewById(R.id.checkBox1);

            txttitle.setText(Items.get(position).title);

/////////////Caption Button////////////
            try {

                btn.setText(""+items.get(position+1).savevalue);

            }catch (Exception e){
                Toast.makeText(listview.this, "انتهى حد الحكم", Toast.LENGTH_SHORT).show();
            }

            txttitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   int btnvalue = Items.get(position).savevalue;
                    positionvule = btnvalue;
                  loadcheckbox = Items.get(position).box;
                    positionvule = btnvalue;
                 runform2();

                }
            });


            checkBox1.setChecked(Items.get(position).box);
            //////Start and Save Loead Data///////////////////////////////////////////////////
            checkBox1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int btnvalue = Items.get(position).savevalue;
                    boolean truefalse = !Items.get(position).box;
                    Items.get(position).box = truefalse;

                    positionvule = btnvalue;
                    loadcheckbox = truefalse;
                    save();
                }
            });

//////End and Save Loead Data///////////////////////////////////////////////////

            checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                }
            });

            return View1;
        }
    }

    /////////////////////////////////////////////////////End Class adapter//////////
    public void load() {
        int i = 0;
        while (i <= listarray.length-1) { /// طول المصفوفة ///
            SharedPreferences sharedPreferences = this.getSharedPreferences("boxch", Context.MODE_PRIVATE);
            if (sharedPreferences != null) {
                boolean value = sharedPreferences.getBoolean("checknox" + i, false);
                items.get(i).box = value;
                i++;
            }
        }
    }


    public void save() {

        SharedPreferences sharedPreferences = this.getSharedPreferences("boxch", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("checknox" + positionvule, loadcheckbox);
        editor.apply();

        positionvule++;
        if (loadcheckbox) {
            Toast.makeText(this, " تم إضافة العنصر" + positionvule + " إلى المفضلة ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, " تم إزالة العنصر " + positionvule + "من المفضلة ", Toast.LENGTH_SHORT).show();
        }
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
