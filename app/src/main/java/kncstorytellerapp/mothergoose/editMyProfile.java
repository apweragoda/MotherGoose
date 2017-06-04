package kncstorytellerapp.mothergoose;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class editMyProfile extends AppCompatActivity {

    Button edut;
    EditText name;
    EditText mail;
    EditText pass;
    EditText checkPass;
    databaseHelper helper = new databaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_profile);
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

        edut = (Button)findViewById(R.id.btnEditPro);
        name = (EditText)findViewById(R.id.editName);
        mail = (EditText)findViewById(R.id.editEmail);
        pass = (EditText)findViewById(R.id.editPassword);
        checkPass = (EditText)findViewById(R.id.CheckEditPassword);

        SharedPreferences shp = getSharedPreferences("userInfo", CONTEXT_IGNORE_SECURITY);
        final String userID=shp.getString("l_uid", "");


        //set my details
        Cursor c = helper.getMyPProfileDetails(userID);

        String theName=null;
        String theMail=null;
        String thePass=null;

        int j=0;
        if(c.moveToFirst()){

            do{

                theName=c.getString(1);
                theMail=c.getString(2);
                thePass=c.getString(3);

                j=j+1;

            }
            while (c.moveToNext());
        }

        name.setText(theName);
        mail.setText(theMail);
        pass.setText(thePass);
        checkPass.setText(thePass);


        edut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sname = name.getText().toString();
                String semail = mail.getText().toString();
                String spass = pass.getText().toString();
                String scpass = checkPass.getText().toString();


                if (!spass.equals(scpass)) {

                    Toast pass = Toast.makeText(editMyProfile.this, "Password and the confermation does not match", Toast.LENGTH_SHORT);
                    pass.show();
                }
                else if(sname.isEmpty() || semail.isEmpty() || spass.isEmpty() || scpass.isEmpty()){
                    Toast pass = Toast.makeText(editMyProfile.this, "Please fill out all details", Toast.LENGTH_SHORT);
                    pass.show();
                }
                else if(!emailValidator(semail)){
                    Toast pass = Toast.makeText(editMyProfile.this, "Email is not valied", Toast.LENGTH_SHORT);
                    pass.show();
                }
                else if(spass.length()<5){
                    Toast pass = Toast.makeText(editMyProfile.this, "Use more than 5 characters to your password", Toast.LENGTH_SHORT);
                    pass.show();
                }
                else {

                    helper.editMyProfile(userID,sname,semail,spass);

                    Toast pass = Toast.makeText(editMyProfile.this, "Make cahanges successfully.", Toast.LENGTH_SHORT);
                    pass.show();

                    finish();



                }
            }
        });
    }

    public boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
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
