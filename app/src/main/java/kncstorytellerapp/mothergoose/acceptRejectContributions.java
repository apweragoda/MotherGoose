package kncstorytellerapp.mothergoose;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;

public class acceptRejectContributions extends AppCompatActivity {

    private ListView listView;
    TextView storyNameText;
    TextView mainStory;
    ImageView imageView;

    databaseHelper helper = new databaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_reject_contributions);
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


        listView=(ListView)findViewById(R.id.storyViewAddReject);
        storyNameText=(TextView)findViewById(R.id.storyNameTextViewAddReject);
        mainStory=(TextView)findViewById(R.id.mainStoryTextViewAddReject);
        imageView=(ImageView)findViewById(R.id.storyImageViewAddReject);

        Intent ii=getIntent();
        Bundle b=ii.getExtras();
        final String sid=(String)b.get("storyID");

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




        //get sub stories
        Cursor cursor2 = helper.getPendingStory(sid);
        String[] stories = new String[cursor2.getCount()];
        String[] authors = new String[cursor2.getCount()];
        final String[] storyId=new String[cursor2.getCount()];
        final String[] ID=new String[cursor2.getCount()];

        int i=0;
        if(cursor2.moveToFirst()){

            do{
                storyId[i]=cursor2.getString(0);
                stories[i]=cursor2.getString(1);
                authors[i]=cursor2.getString(2);
                ID[i]=cursor2.getString(3);
                i=i+1;

            }
            while (cursor2.moveToNext());
        }


        ArrayAdapter<String> adapter = new storyAdapter(this, stories,authors );


        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final String storyID = storyId[position];
                final String STORY = ID[position];

                AlertDialog.Builder builder = new AlertDialog.Builder(acceptRejectContributions.this);
                builder.setMessage("Do you want to add this contribution to the story?").setCancelable(true).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int contributionCount = helper.contributionCount(sid);
                        if(contributionCount<10) {

                            helper.acceptContribution(storyID);
                            helper.increseContributionCount(STORY);

                            Toast pass = Toast.makeText(acceptRejectContributions.this, "Contribution successfully added." , Toast.LENGTH_SHORT);
                            pass.show();

                            finish();
                        }
                        else{
                            Toast pass = Toast.makeText(acceptRejectContributions.this, "Fail To Add Contribution, Story is Complete." , Toast.LENGTH_SHORT);
                            pass.show();
                        }

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        helper.rejectContribution(storyID);

                        Toast pass = Toast.makeText(acceptRejectContributions.this, "Contribution rejected." , Toast.LENGTH_SHORT);
                        pass.show();

                        finish();
                    }
                });

                AlertDialog alert = builder.create();
                alert.setTitle("Alert");
                alert.show();
            }
        });


    }

    public void pageRefresh(){

        Intent refresh = new Intent(this, myShelf.class);
        startActivity(refresh);
        this.finish();
    }
}
