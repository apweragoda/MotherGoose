package kncstorytellerapp.mothergoose;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RelativeLayout;

public class navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RelativeLayout relativeLayout;
    Button writeNew;
    Button readStory;
    Button myShelf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        relativeLayout = (RelativeLayout) findViewById(R.id.nav_relative_layout);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        writeNew=(Button)findViewById(R.id.btnwriteNew);
        writeNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), WriteStory.class);
                startActivity(i);
            }
        });


        readStory=(Button)findViewById(R.id.btnreadStory);
        readStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),readStory.class);
                startActivity(i);
            }
        });

        myShelf=(Button)findViewById(R.id.btnMyShelf);
        myShelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), myShelf.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
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


            SharedPreferences shp = getSharedPreferences("userInfo", CONTEXT_IGNORE_SECURITY);

            SharedPreferences.Editor editor = shp.edit();
            editor.putString("l_uid","");
            editor.putString("l_uname","");
            editor.putString("l_email","");
            editor.apply();


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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.nav_aboutUs) {

            Intent i = new Intent(getApplicationContext(), aboutAs.class);
            startActivity(i);

        } else if (id == R.id.nav_editProfile) {

            Intent i = new Intent(getApplicationContext(), aboutApp.class);
            startActivity(i);
        }
//        else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//      }
    else if (id == R.id.nav_share) {
            Intent i = new Intent(getApplicationContext(), logIn.class);
            startActivity(i);

            SharedPreferences shp = getSharedPreferences("userInfo", CONTEXT_IGNORE_SECURITY);

            SharedPreferences.Editor editor = shp.edit();
            editor.putString("l_uid","");
            editor.putString("l_uname","");
            editor.putString("l_email","");
            editor.apply();

            finish();
        }
     else if (id == R.id.nav_send) {

            System.exit(0);
            }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
