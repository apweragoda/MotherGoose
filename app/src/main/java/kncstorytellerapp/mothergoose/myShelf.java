package kncstorytellerapp.mothergoose;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class myShelf extends AppCompatActivity {

    Button myStories;
    Button myContribution;
    Button editp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shelf);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myStories=(Button)findViewById(R.id.btnMyStroies);
        editp=(Button)findViewById(R.id.btnEditProfile);

        myStories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), getMyStories.class);
                startActivity(i);
            }
        });

        myContribution=(Button)findViewById(R.id.btnMyContribution);
        myContribution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),storiesIContributed.class);
                startActivity(i);
            }
        });

        editp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),editMyProfile.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_logout) {
            Intent i = new Intent(getApplicationContext(), logIn.class);
            startActivity(i);

            finish();
        }
        if (id == R.id.nav_exit) {
            System.exit(0);
        }
        if (id == R.id.nav_home) {
            Intent i = new Intent(getApplicationContext(), navigation.class);
            startActivity(i);

            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}


