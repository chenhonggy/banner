package com.banner.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.banner.main.easybanner.Banner;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    private Banner banner;
    private List<Integer> imagelist = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagelist.add(R.drawable.a);
        imagelist.add(R.drawable.b);
        imagelist.add(R.drawable.c);
        imagelist.add(R.drawable.c);
        imagelist.add(R.drawable.c);
        banner = (Banner)findViewById(R.id.banner);
        banner.initImage(imagelist);

        banner.setItemListenner(new Banner.OnItemListenner() {
            @Override
            public void OnChecked(View v, int position) {
                Toast.makeText(MainActivity.this,position+"",Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_banner, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
