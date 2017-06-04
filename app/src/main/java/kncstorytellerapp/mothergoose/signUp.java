package kncstorytellerapp.mothergoose;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class signUp extends AppCompatActivity {

    Button mainMenu;
    EditText name;
    EditText email;
    EditText password;
    EditText passwordConferm;
    String nameg;
    String mailg;
    String passwg;
    String passwconfg;

    usersClass user;

    Firebase myFirebaseRef;

    databaseHelper helper = new databaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase("https://fables.firebaseio.com//users");

         name =(EditText)findViewById(R.id.textName);
         email =(EditText)findViewById(R.id.textemail);
         password =(EditText)findViewById(R.id.textpassword);
         passwordConferm =(EditText)findViewById(R.id.textpasswordconfer);

        mainMenu=(Button)findViewById(R.id.btnMenu);
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                nameg = name.getText().toString();
                mailg = email.getText().toString();
                passwg = password.getText().toString();
                passwconfg = passwordConferm.getText().toString();

                if (!passwg.equals(passwconfg)) {

                    Toast pass = Toast.makeText(signUp.this, "Password and the confermation does not match", Toast.LENGTH_SHORT);
                    pass.show();
                }
                else if(nameg.isEmpty() || mailg.isEmpty() || passwg.isEmpty() || passwconfg.isEmpty()){
                    Toast pass = Toast.makeText(signUp.this, "Please fill out all details", Toast.LENGTH_SHORT);
                    pass.show();
                }
                else if(!emailValidator(mailg)){
                    Toast pass = Toast.makeText(signUp.this, "Email is not valied", Toast.LENGTH_SHORT);
                    pass.show();
                }
                else if(passwg.length()<5){
                    Toast pass = Toast.makeText(signUp.this, "Use more than 5 characters to your password", Toast.LENGTH_SHORT);
                    pass.show();
                }
                else {

                    user = new usersClass();

                    user.setName(nameg);
                    user.setEmail(mailg);
                    user.setPassword(passwg);

                    myFirebaseRef.push().setValue(user);

                    //getters and setters to signUp
                    getSetRegistrationValues setval = new getSetRegistrationValues();
                    setval.setName(nameg);
                    setval.setEmail(mailg);
                    setval.setPassword(passwg);

                    boolean isInserted = helper.insertUser(setval);


                    if (isInserted = true) {



                        Toast pass = Toast.makeText(signUp.this, "Welcome to Fabels! Please Log in." , Toast.LENGTH_SHORT);
                        pass.show();

                        Intent i = new Intent(getApplicationContext(), logIn.class);
                        startActivity(i);

                        finish();
                    } else {
                        Toast.makeText(signUp.this, "Registration Fail", Toast.LENGTH_LONG).show();
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
