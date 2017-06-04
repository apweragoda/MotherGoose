package kncstorytellerapp.mothergoose;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class addaContribution extends AppCompatActivity {

    Button addContribution;
    Button cancel;
    EditText story;
    databaseHelper helper = new databaseHelper(this);

    Firebase myFirebaseRef;

    contributionClass contribution;

    TextView contwordcount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adda_contribution);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase("https://fables.firebaseio.com//contributions");

        contwordcount = (TextView)findViewById(R.id.cwcount);

        Intent ii=getIntent();
        Bundle b=ii.getExtras();
        final String sid=(String)b.get("storyID");

        addContribution=(Button)findViewById(R.id.btnAddContribution);
        story=(EditText)findViewById(R.id.contributionText);
        cancel=(Button)findViewById(R.id.cancelButton);


        story.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String [] swords = story.getText().toString().split(" ");
                contwordcount.setText(swords.length + "/50");
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


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addContribution.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {


                    String storyyy=story.getText().toString();

                    SharedPreferences shp = getSharedPreferences("userInfo", CONTEXT_IGNORE_SECURITY);
                    String contributorID=shp.getString("l_uid", "");
                    String contributorName=shp.getString("l_uname", "");

              if(storyyy.isEmpty()){
                  Toast pass = Toast.makeText(addaContribution.this, "Contribution can't be null " , Toast.LENGTH_SHORT);
                  pass.show();
              }
              else{

                  boolean isInserted = helper.insertContribution(storyyy, contributorName, contributorID, sid);

                  if(isInserted==true){

                      contribution = new contributionClass();

                      contribution.setContext(storyyy);
                      contribution.setContributorID(contributorID);
                      contribution.setGetContributorName(contributorName);
                      contribution.setStatus("pending");
                      contribution.setStoryID(sid);

                      myFirebaseRef.push().setValue(contribution);

                      Toast pass = Toast.makeText(addaContribution.this, "Contribution added. Owner has to accept your contribution" , Toast.LENGTH_SHORT);
                      pass.show();
                      finish();
                  }
                  else {

                      Toast pass = Toast.makeText(addaContribution.this, "Fail To Add Contribution", Toast.LENGTH_SHORT);
                      pass.show();
                  }

              }



            }
        });
    }

}
