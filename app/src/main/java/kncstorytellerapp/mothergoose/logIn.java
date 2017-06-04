package kncstorytellerapp.mothergoose;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class logIn extends AppCompatActivity {

    Button goToMenu;
    databaseHelper helper = new databaseHelper(this);
    EditText uname,pword;
    String log_id,log_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        uname=(EditText)findViewById(R.id.usernameText);
        pword=(EditText)findViewById(R.id.passwordText);

        //remove this
        SharedPreferences shp = getSharedPreferences("userInfo",CONTEXT_IGNORE_SECURITY);
        String uemail=shp.getString("l_email", "");
    //    String contributorName=shp.getString("l_uname","");

        uname.setText(uemail);

        goToMenu=(Button)findViewById(R.id.goToMenu);
        goToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = uname.getText().toString();
                String password = pword.getText().toString();


                if(!emailValidator(username)){
                    Toast pass = Toast.makeText(logIn.this, "Username is not valied", Toast.LENGTH_SHORT);
                    pass.show();
                }
                else {
                    String passwordFromDb = helper.searchPassword(username);

                    if (passwordFromDb.equals(password)) {

                        //get logged user id and name
                        Cursor cursor1 = helper.getUserDate(username);
                        int j=0;
                        if(cursor1.moveToFirst()){
                            do{
                                log_id=cursor1.getString(0);
                                log_name=cursor1.getString(1);
                                j=j+1;
                            }
                            while (cursor1.moveToNext());
                        }

                        SharedPreferences shp = getSharedPreferences("userInfo", CONTEXT_IGNORE_SECURITY);

                        SharedPreferences.Editor editor = shp.edit();
                        editor.putString("l_uid",log_id);
                        editor.putString("l_uname",log_name);
                        editor.putString("l_email",username);
                        editor.apply();

                        Toast pass = Toast.makeText(logIn.this, "Welcome "+log_name, Toast.LENGTH_SHORT);
                        pass.show();

                        Intent i = new Intent(getApplicationContext(), navigation.class);
                        startActivity(i);

                        finish();
                    }
                    else {
                        Toast pass = Toast.makeText(logIn.this, "Username and password are does not match", Toast.LENGTH_SHORT);
                        pass.show();
                    }



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

}
