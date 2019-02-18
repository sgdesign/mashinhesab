package com.duy.calculatorsgh;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//import com.squareup.picasso.Picasso;

public class Dialog extends Activity {

    TextView title , desc ;
    ImageView logo , baner ;
    Button openlink ;
    Typeface koodak ;
    String image_logo ;
    String image_baner ;
    String text_title ;
    String text_desc ;
    String text_btn ;
    String link_btn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        title = (TextView) findViewById(R.id.tvTitlePush);
        desc = (TextView) findViewById(R.id.tvDescPush);
        logo = (ImageView) findViewById(R.id.ivLogoPush);
        baner = (ImageView) findViewById(R.id.ivBanerPush);
        openlink = (Button) findViewById(R.id.btnClickPush);

        image_logo = getIntent().getStringExtra("image_logo");
        image_baner = getIntent().getStringExtra("image_baner");
        text_title = getIntent().getStringExtra("text_title");
        text_desc = getIntent().getStringExtra("text_desc");
        text_btn = getIntent().getStringExtra("text_btn");
        link_btn = getIntent().getStringExtra("link_btn");

        //koodak = Typeface.createFromAsset(getAssets(),"fonts/koodak.TTF");

        //Picasso.with(getApplicationContext())
            //    .load(Uri.parse(image_logo))
            ///    .into(logo);

       // Picasso.with(getApplicationContext())
         //       .load(Uri.parse(image_baner))
          //      .into(baner);

        title.setText(text_title);
       // title.setTypeface(koodak);
        desc.setText(text_desc);
        //desc.setTypeface(koodak);
        desc.setMovementMethod(new ScrollingMovementMethod());
        openlink.setText(text_btn);
        //openlink.setTypeface(koodak);

    }

    public void OpenLink (View view){
        Intent openintent = new Intent(Intent.ACTION_VIEW , Uri.parse(link_btn));
        startActivity(openintent);

        finish();
        System.exit(0);
    }

    @Override
    public void onBackPressed() {

    }

}
