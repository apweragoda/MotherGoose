package kncstorytellerapp.mothergoose;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class storiesIContributed extends AppCompatActivity {

    ListView lv;
    databaseHelper helper = new databaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories_icontributed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        lv = (ListView)findViewById(R.id.listViewStorieIContributed);


        SharedPreferences shp = getSharedPreferences("userInfo",CONTEXT_IGNORE_SECURITY);
        String UID=shp.getString("l_uid", "");
        String UName=shp.getString("l_uname","");


        Cursor cursor = helper.getStoriesIContributed(UID);
        String[] stories =new String[cursor.getCount()];
        String[] author = new String[cursor.getCount()];
        byte[] img=null;
        Bitmap[] bmp=new Bitmap[cursor.getCount()];
        final String[] storyId=new String[cursor.getCount()];
        String liksC[] = new String[cursor.getCount()];

        int i=0;
        if(cursor.moveToFirst()){

            do{
                storyId[i]=cursor.getString(0);
                stories[i]=cursor.getString(2);
                author[i]=cursor.getString(4);
                liksC[i]=cursor.getString(5);
                img=cursor.getBlob(3);
                bmp[i] = BitmapFactory.decodeByteArray(img, 0, img.length);
                i=i+1;

            }
            while (cursor.moveToNext());
        }


        ArrayAdapter<String> adapter = new listRowAdapter(this, stories,author,liksC,bmp );
        lv.setAdapter(adapter);


        //list view item onclick
        lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String story = storyId[position];

                        Intent i = new Intent(getApplicationContext(), story.class);
                        i.putExtra("storyID", story);
                        startActivity(i);
                    }
                }
        );
    }

}
