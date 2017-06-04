package kncstorytellerapp.mothergoose;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class WriteStory extends AppCompatActivity {

    Button post;
    ImageView imageupload;
    EditText storyname;
    EditText story;
    Spinner tagone;
    Spinner tagtwo;
    Spinner tagthree;

    String sname;
    String sstory;
    String stagone;
    String stagtwo;
    String stagthree;
    byte simage[];

    Firebase myFirebaseRef;

    databaseHelper helper = new databaseHelper(this);
    bookClass bstory;

    TextView wcount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_story);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase("https://fables.firebaseio.com//story");

        storyname = (EditText)findViewById(R.id.storyname);
        story = (EditText)findViewById(R.id.storycontext);
        tagone = (Spinner) findViewById(R.id.tagone);
        tagtwo = (Spinner) findViewById(R.id.tagtwo);
        tagthree = (Spinner) findViewById(R.id.tagthree);
        post = (Button) findViewById(R.id.buttonpost);
        imageupload = (ImageView) findViewById(R.id.uploadimage);
        wcount = (TextView)findViewById(R.id.wordcount);


       story.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

               String [] swords = story.getText().toString().split(" ");
               wcount.setText(swords.length + "/50");
                if(swords.length>=50){
                    story.setFilters(new InputFilter[]{new InputFilter.LengthFilter(0)});
                }
               else {
                    story.setFilters(new InputFilter[]{});
                }
           }

           @Override
           public void afterTextChanged(Editable s) {

           }
       });


        imageupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select story image"), 1);
            }
        });

        post.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                SharedPreferences shp = getSharedPreferences("userInfo",CONTEXT_IGNORE_SECURITY);
                String ownerID=shp.getString("l_uid", "");
                String ownerName=shp.getString("l_uname","");

                sname = storyname.getText().toString();
                sstory = story.getText().toString();
                stagone = tagone.getSelectedItem().toString();
                stagtwo = tagtwo.getSelectedItem().toString();
                stagthree = tagthree.getSelectedItem().toString();
                //simage = imageupload.getContext().toString();
                int owner=Integer.parseInt(ownerID);
                String ownername=ownerName;

                Bitmap bitmap = ((BitmapDrawable)imageupload.getDrawable()).getBitmap();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap .compress(Bitmap.CompressFormat.PNG, 100, bos);
                simage = bos.toByteArray();

                if(sname.isEmpty()){
                    Toast pass = Toast.makeText(WriteStory.this, "Please enter a name " , Toast.LENGTH_SHORT);
                    pass.show();
                }
                else if(sstory.isEmpty()){

                    Toast pass = Toast.makeText(WriteStory.this, "Story cant be empty " , Toast.LENGTH_SHORT);
                    pass.show();
                }
                else {

                    boolean isInserted = helper.insertStory(sname, sstory, stagone, stagtwo, stagthree, simage,owner,ownername);

                    if(isInserted==true){

                        bstory = new bookClass();
                        bstory.setSname(sname);
                        bstory.setStory(sstory);
                        bstory.setTagone(stagone);
                        bstory.setTagtwo(stagtwo);
                        bstory.setTagthree(stagthree);
                        bstory.setContributionCount("0");
                        bstory.setLikesCount("0");
                        bstory.setOwner(ownerID);
                        bstory.setOwnerName(ownerName);
                        bstory.setKey(sname);

                        myFirebaseRef.push().setValue(bstory);

                        Toast pass = Toast.makeText(WriteStory.this, "Story posted " , Toast.LENGTH_SHORT);
                        pass.show();
                        finish();

                    }

                }

            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void onActivityResult(int reqCode, int resCode, Intent data){

        if (resCode == RESULT_OK) {
            if (reqCode == 1) {
                imageupload.setImageURI(data.getData());
            }
        }
    }

}
