package kncstorytellerapp.mothergoose;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class FirebaseTest extends AppCompatActivity {

    Firebase myFirebaseRef;
    TextView t1;
    EditText edt;
    Button b1;

    bookClass book1;
    bookClass bstory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Firebase.setAndroidContext(this);

        setContentView(R.layout.activity_firebase_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        t1 = (TextView)findViewById(R.id.textView13);
        edt=(EditText)findViewById(R.id.editText);
        b1=(Button)findViewById(R.id.button);



        //firebase con
        Firebase.setAndroidContext(this);
        //ref to db
        myFirebaseRef = new Firebase("https://fables.firebaseio.com//story");

        Button bb = (Button)findViewById(R.id.button);
        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // t1.setText(myFirebaseRef);
            }
        });

        ///add listner to
        myFirebaseRef.child("kassa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                t1.setText(snapshot.getValue(String.class));
            }
            @Override public void onCancelled(FirebaseError error) { }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                book1=new bookClass();
//                book1.setOwner("3");
//                book1.setSname("kasstory");
//                book1.setTagone("sdsds");
//                book1.setTagtwo("Aasd");
//
               addBook("ADasda","Asdasdsa","Asdas","Asdasdasd","asdasd","0","0","1","sdds","asda");
            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void addBook(String name,String story,String t1,String t2,String t3,String liks,String concount,String owid,String ownam,String key){
        bstory=new bookClass();

                    bstory.setSname(name);
                    bstory.setStory(story);
                    bstory.setTagone(t1);
                    bstory.setTagtwo(t2);
                    bstory.setTagthree(t3);
                    bstory.setContributionCount(concount);
                    bstory.setLikesCount(liks);
                    bstory.setOwner(owid);
                    bstory.setOwnerName(ownam);
                    bstory.setKey(key);

        myFirebaseRef.push().setValue(bstory);
    }
}
