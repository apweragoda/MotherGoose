package kncstorytellerapp.mothergoose;

/**
 * Created by HP-PC on 3/23/2016.
 */
public class getSetRegistrationValues {

    String name,email,password;

    public void setEmail(String emails){
        this.email=emails;
    }

    public String getEmail(){
        return this.email;
    }

    public void setName(String names){
        this.name=names;
    }

    public String getName(){
        return this.name;
    }

    public void setPassword(String pword){
        this.password=pword;
    }

    public String getPassword(){
        return this.password;
    }
}
