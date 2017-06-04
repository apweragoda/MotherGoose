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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class story extends AppCompatActivity {

    private ListView listView;
    TextView storyNameText;
    TextView mainStory;
    TextView authName;
    TextView likeCount;
    ImageView imageView;
    Button contribute;
    databaseHelper helper = new databaseHelper(this);
    ImageView like;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView=(ListView)findViewById(R.id.storyView);
        storyNameText=(TextView)findViewById(R.id.storyNameTextView);
        mainStory=(TextView)findViewById(R.id.mainStoryTextView);
        imageView=(ImageView)findViewById(R.id.storyImageView);
        like = (ImageView)findViewById(R.id.likeBtn);
        contribute=(Button)findViewById(R.id.btncontribute);
        likeCount=(TextView)findViewById(R.id.likeCount);
        authName=(TextView)findViewById(R.id.storyNameTextViewAuth);

        Intent ii=getIntent();
        Bundle b=ii.getExtras();
        final String sid=(String)b.get("storyID");

//        //get likes count
        int likes = helper.likesCount(sid);
        likeCount.setText(String.valueOf(likes));

        //get main story
        Cursor cursor1 = helper.getSelectedStoryDetails(sid);
        String owner=null;
        String storyName=null;
        String story=null;
        byte[] image=null;

        int j=0;
        if(cursor1.moveToFirst()){

            do{

                owner=cursor1.getString(0);
                storyName=cursor1.getString(1);
                story=cursor1.getString(2);
                image=cursor1.getBlob(3);
                j=j+1;

            }
            while (cursor1.moveToNext());
        }
        storyNameText.setText(storyName);
        mainStory.setText(story);
        Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
        imageView.setImageBitmap(bmp);
        authName.setText(owner);

        //get sub stories
        Cursor cursor2 = helper.getCorrectStory(sid);
        String[] stories = new String[cursor2.getCount()];
        String[] authors = new String[cursor2.getCount()];

        int i=0;
        if(cursor2.moveToFirst()){

            do{

                stories[i]=cursor2.getString(1);
                authors[i]=cursor2.getString(2);
                i=i+1;

            }
            while (cursor2.moveToNext());
        }


        ArrayAdapter<String> adapter = new storyAdapter(this, stories,authors );


        listView.setAdapter(adapter);

        contribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int x = helper.contributionCount(sid);
                if (x >= 10) {
                    Toast pass = Toast.makeText(story.this, "Fail To Add Contribution, Story is Complete.", Toast.LENGTH_SHORT);
                    pass.show();
                } else {
                    Intent i = new Intent(getApplicationContext(), addaContribution.class);
                    i.putExtra("storyID", sid);
                    startActivity(i);
                }


            }
        });


        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences shp = getSharedPreferences("userInfo", CONTEXT_IGNORE_SECURITY);
                String UID=shp.getString("l_uid", "");
                String UName=shp.getString("l_uname", "");

                String status = helper.addLike(sid, UID);

//                //get likes count
                int likes = helper.likesCount(sid);
                likeCount.setText(String.valueOf(likes));

                Toast pass = Toast.makeText(story.this, status, Toast.LENGTH_SHORT);
                pass.show();
            }
        });
    }

}
