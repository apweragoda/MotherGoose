package kncstorytellerapp.mothergoose;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.sql.Blob;

public class readStory extends AppCompatActivity {

    private ListView listView;
    databaseHelper helper = new databaseHelper(this);
    Button btn;
    Spinner cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_story);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       listView=(ListView)findViewById(R.id.listiew);
        btn = (Button)findViewById(R.id.btnSearch);
        cat = (Spinner)findViewById(R.id.spinner2cat);

        final Cursor cursor = helper.getAllStories();
        String[] stories =new String[cursor.getCount()];
        String[] author = new String[cursor.getCount()];
        String[] lks = new String[cursor.getCount()];
        byte[] img=null;
        Bitmap[] bmp=new Bitmap[cursor.getCount()];
        final String[] storyId=new String[cursor.getCount()];


     //   byte[][] img= new byte[cursor.getCount()][];

        int i=0;
        if(cursor.moveToFirst()){

            do{
                storyId[i]=cursor.getString(0);
                stories[i]=cursor.getString(2);
                author[i]=cursor.getString(4);
                lks[i]=cursor.getString(5);
                img=cursor.getBlob(3);
                bmp[i] = BitmapFactory.decodeByteArray(img, 0, img.length);
                i=i+1;

            }
            while (cursor.moveToNext());
        }


        ArrayAdapter<String> adapter = new listRowAdapter(this, stories,author,lks,bmp );
        listView.setAdapter(adapter);




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String category = cat.getSelectedItem().toString();
                Cursor cursorA;
                String check = "Non";
                if (category.equals(check)) {
                    cursorA = helper.getAllStories();


                } else {
                    cursorA = helper.getSearchedStories(category);
                }

                String[] storiesA = new String[cursorA.getCount()];
                String[] authorA = new String[cursorA.getCount()];
                byte[] imgA = null;
                Bitmap[] bmpA = new Bitmap[cursorA.getCount()];
                final String[] storyIdA = new String[cursorA.getCount()];
                String[] liks = new String[cursorA.getCount()];


                int i = 0;
                if (cursorA.moveToFirst()) {

                    do {
                        storyIdA[i] = cursorA.getString(0);
                        storiesA[i] = cursorA.getString(2);
                        authorA[i] = cursorA.getString(4);
                        liks[i]=cursorA.getString(5);
                        imgA = cursorA.getBlob(3);
                        bmpA[i] = BitmapFactory.decodeByteArray(imgA, 0, imgA.length);
                        i = i + 1;

                    }
                    while (cursorA.moveToNext());
                }


                ArrayAdapter<String> adapterA = new listRowAdapter(readStory.this, storiesA, authorA,liks, bmpA);
                listView.setAdapter(adapterA);
            }
        });

        //list view item onclick
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        //int x=position+1;

                        String story = storyId[position];

                        Intent i = new Intent(getApplicationContext(), story.class);
                        i.putExtra("storyID", story);
                        startActivity(i);
                    }
                }
        );
    }

}
