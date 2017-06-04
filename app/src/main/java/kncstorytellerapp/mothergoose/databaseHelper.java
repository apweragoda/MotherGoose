package kncstorytellerapp.mothergoose;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HP-PC on 3/23/2016.
 */
public class databaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "fables.db";
    static final String TABLE_NAME = "users";
    static final String COLUMN_ID = "uid";
    static final String COLUMN_NAME = "name";
    static final String COLUMN_EMAIL = "email";
    static final String COLUMN_PASSWORD  = "password";
    SQLiteDatabase db;

    static final String TABLE_NAME_STORY = "story";
    static final String COLUMN_STORYNAME  = "sname";
    static final String COLUMN_STORY  = "story";
    static final String COLUMN_TAGONE  = "tagOne";
    static final String COLUMN_TAGTWO  = "tagTwo";
    static final String COLUMN_TAGTHREE  = "tagThree";
    static final String COLUMN_STORYIMAGE  = "image";
    static final String COLUMN_OWNER  = "owner";
    static final String COLUMN_OWNERNAME  = "ownerName";
    static final String COLUMN_likes  = "likes";
    static final String COLUMN_ContributionCount  = "contributionCount";

    static final String TABLE_NAME_CONTRIBUTION = "contributions";
    static final String COLUMN_ContributorName="contributorName";
    static final String COLUMN_STORYID="sid";
    static final String COLUMN_CONTRIBUTOR="contributorId";
    static final String COLUMN_CONTEXT="context";
    static final String COLUMN_STATUS="status";

    static final String TABLE_NAME_Likes = "likes";
    static final String COLUMN_StoryIDLiked="sid";
    static final String COLUMN_UserLiked="uid";


    public databaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static String TABLE_CREATE = "create table users (uid integer primary key AUTOINCREMENT, name text, email text, password text);";
    private static String TABLE_STORY_CREATE = "create table story (sid integer primary key AUTOINCREMENT, owner integer,ownerName text, sname text, tagOne text, tagTwo text, tagThree text,story text, image blob,contributionCount integer,likes integer);";
    private static String TABLE_CONTRI_CREATE = "create table contributions (cid integer primary key AUTOINCREMENT, sid integer,contributorId integer,contributorName text , context text, status text, selected text);";
    private static String TABLE_LIKES_CREATE = "CREATE TABLE likes (\n" +
            "  uid INTEGER NOT NULL,\n" +
            "  sid INTEGER NOT NULL,\n" +
            "  PRIMARY KEY ( uid, sid)\n" +
            ");";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL(TABLE_STORY_CREATE);
        db.execSQL(TABLE_CONTRI_CREATE);
        db.execSQL(TABLE_LIKES_CREATE);
        this.db=db;

    }

    public boolean insertUser(getSetRegistrationValues gsv){
        db = this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(COLUMN_NAME, gsv.getName());
        values.put(COLUMN_EMAIL, gsv.getEmail());
        values.put(COLUMN_PASSWORD, gsv.getPassword());


        long result = db.insert(TABLE_NAME,null,values);
        if(result == -1){
            db.close();
            return false;
        }
        else {
            db.close();
            return true;
        }

    }

    public Cursor getUserDate(String uname){
        db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select uid,name from users where email =? " , new String[] { uname});

        return cursor;
    }

    public String searchPassword(String uname){

        db = this.getReadableDatabase();
        String query = "select email,password from "+TABLE_NAME ;

        Cursor cursor= db.rawQuery(query, null);
        String password,username;

        password = "not found";

        if(cursor.moveToFirst()){

            do{
                username=cursor.getString(0);
                if(username.equals(uname)){
                    password=cursor.getString(1);
                    break;
                }
            }
            while (cursor.moveToNext());
        }
        db.close();
        return password;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String query = "DROP TABLE IF EXISTS"+TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);

    }

    public Cursor getAllStories(){
        db = this.getReadableDatabase();

        String query = "select s.sid,s.owner,s.sname,s.image,u.name,s.likes from story s INNER JOIN users u WHERE u.uid=s.owner" ;

        Cursor cursor= db.rawQuery(query, null);

        return cursor;
    }
    public Cursor getSearchedStories(String cat){
        db = this.getReadableDatabase();

    //    String query = "select s.sid,s.owner,s.sname,s.image,u.name from story s INNER JOIN users u WHERE u.uid=s.owner AND tagOne ="+cat ;
        Cursor cursor = db.rawQuery("select s.sid,s.owner,s.sname,s.image,u.name,s.likes from story s INNER JOIN users u  where  u.uid =s.owner AND (tagOne=? OR tagTwo=? OR tagThree=?)"  , new String[] { cat,cat,cat});
       // Cursor cursor= db.rawQuery(query, null);

        return cursor;
    }

    public Cursor getMyStories(String id){
        db = this.getReadableDatabase();

        String query = "select s.sid,s.owner,s.sname,s.image,u.name,s.likes from story s INNER JOIN users u WHERE u.uid=s.owner AND s.owner ="+id ;

        Cursor cursor= db.rawQuery(query, null);

        return cursor;
    }

    public Cursor getStoriesIContributed(String id){
        db = this.getReadableDatabase();

        String query = "select s.sid,s.owner,s.sname,s.image,s.ownerName,s.likes from story s INNER JOIN contributions c WHERE s.sid=c.sid AND c.contributorId = "+id+" GROUP BY s.sid " ;

        Cursor cursor= db.rawQuery(query, null);

        return cursor;
    }

    public Cursor getSelectedStory(String id){
       // String status="accepted";

        db = this.getReadableDatabase();

        //status="+status+" and
        String query = "select cid,context,contributorid from contributions WHERE  sid="+id ;

        Cursor cursor= db.rawQuery(query, null);

        return cursor;
    }
    public Cursor getCorrectStory(String id){
        String status="accepted";

        db = this.getReadableDatabase();


        Cursor cursor= db.rawQuery("select c.cid,c.context,c.contributorName from contributions c WHERE c.status = ? AND c.sid= ?", new String[]{"accepted",id});

        return cursor;
    }

    public Cursor getMyPProfileDetails(String uid){
        db = this.getReadableDatabase();

        String query = "select * from users WHERE  uid="+uid ;

        Cursor cursor= db.rawQuery(query, null);

        return cursor;

    }

    public Cursor getPendingStory(String id){

        db = this.getReadableDatabase();


        Cursor cursor= db.rawQuery("select c.cid,c.context,c.contributorid,c.sid from contributions c WHERE c.status = ? AND c.sid= ?", new String[]{"pending",id});

        return cursor;
    }

    public  Cursor getSelectedStoryDetails(String id){
        db = this.getReadableDatabase();

        String query = "select ownerName,sname,story,image from story WHERE  sid="+id ;

        Cursor cursor= db.rawQuery(query, null);

        return cursor;

    }

    public void acceptContribution(String cid){

        db = this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(COLUMN_STATUS,"accepted");

        db.update("contributions",values,"cid = ?",new String[]{cid});
    }

    public void rejectContribution(String cid){

        db = this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(COLUMN_STATUS,"rejected");

        db.update("contributions",values,"cid = ?",new String[]{cid});
    }

    public boolean insertStory(String sname, String story, String tagone, String tagtwo, String tagthree, byte simage[],int owner,String ownername){
        db = this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(COLUMN_STORYNAME, sname);
        values.put(COLUMN_STORY, story);
        values.put(COLUMN_TAGONE, tagone );
        values.put(COLUMN_TAGTWO, tagtwo );
        values.put(COLUMN_TAGTHREE, tagthree );
        values.put(COLUMN_STORYIMAGE, simage);
        values.put(COLUMN_OWNER, owner);
        values.put(COLUMN_OWNERNAME, ownername);
        values.put(COLUMN_likes, 0);
        values.put(COLUMN_ContributionCount, 0);

        long result = db.insert(TABLE_NAME_STORY, null, values);
        if(result == -1){
            db.close();
            return false;
        }
        else {
            db.close();
            return true;
        }

    }

    public int contributionCount(String sid){

        db = this.getWritableDatabase();

        String query = "select contributionCount from story WHERE  sid="+sid ;

        Cursor cursor1= db.rawQuery(query, null);

        String count=null;

        int j=0;
        if(cursor1.moveToFirst()){
            do{
                count=cursor1.getString(0);
                j=j+1;
            }
            while (cursor1.moveToNext());
        }
        int cunt = Integer.parseInt(count);


        return cunt;
    }

    public int likesCount(String sid){

        db = this.getWritableDatabase();

        String query = "select likes from story WHERE  sid="+sid ;

        Cursor cursor1= db.rawQuery(query, null);

        String count=null;

        int j=0;
        if(cursor1.moveToFirst()){
            do{
                count=cursor1.getString(0);
                j=j+1;
            }
            while (cursor1.moveToNext());
        }
        int cunt = Integer.parseInt(count);


        return cunt;
    }

    public void increseContributionCount(String sid){

        db = this.getWritableDatabase();

        String query = "select contributionCount from story WHERE  sid="+sid ;

        Cursor cursor1= db.rawQuery(query, null);

        String count=null;

        int j=0;
        if(cursor1.moveToFirst()){
            do{
                count=cursor1.getString(0);
                j=j+1;
            }
            while (cursor1.moveToNext());
        }
        int cunt = Integer.parseInt(count);
        cunt = cunt+1;

        ContentValues values= new ContentValues();
        values.put(COLUMN_ContributionCount,cunt);

        db.update("story",values,"sid = ?",new String[]{sid});

    }

    public void editMyProfile(String uid,String name,String email, String pass){

        ContentValues values= new ContentValues();
        values.put("name",name);
        values.put("email",email);
        values.put("password",pass);

        db.update("users",values,"uid = ?",new String[]{uid});

    }
    public String addLike(String sid,String uid){

        String status = "Error occoured";
        int point = 0;

        String query1 = "select uid,sid from likes" ;

        Cursor cursor= db.rawQuery(query1, null);
        String the_uid,the_sid;

        if(cursor.moveToFirst()){

            do{
                the_uid=cursor.getString(0);
                the_sid=cursor.getString(1);
                if(the_uid.equals(uid) && the_sid.equals(sid)){
                    status="You have already liked this story";
                    point = 0;
                    break;
                }
                else{

                    status="You have liked the story";
                    point = 1;

                    ContentValues values= new ContentValues();
                    values.put(COLUMN_StoryIDLiked, sid);
                    values.put(COLUMN_UserLiked, uid);
                    db.insert("likes", null, values);
                }
            }
            while (cursor.moveToNext());
        }
        else if (cursor.getCount()==0){

            status="You have liked the story";
            point = 1;

            ContentValues values= new ContentValues();
            values.put(COLUMN_StoryIDLiked, sid);
            values.put(COLUMN_UserLiked, uid);
            db.insert("likes", null, values);

        }


        if(point == 1){

            db = this.getWritableDatabase();

            String query = "select likes from story WHERE  sid="+sid ;

            Cursor cursor1= db.rawQuery(query, null);

            String count=null;

            int j=0;
            if(cursor1.moveToFirst()){
                do{
                    count=cursor1.getString(0);
                    j=j+1;
                }
                while (cursor1.moveToNext());
            }
            int cunt = Integer.parseInt(count);
            cunt = cunt+1;

            ContentValues values= new ContentValues();
            values.put(COLUMN_likes, cunt);

            db.update("story",values,"sid = ?",new String[]{sid});

        }


        return status;
    }

    public boolean insertContribution(String story,String name ,String contributor,String sid){
        db = this.getWritableDatabase();
        ContentValues values= new ContentValues();
        String stts="pending";


        values.put(COLUMN_STORYID, sid);
        values.put(COLUMN_CONTRIBUTOR,contributor);
        values.put(COLUMN_CONTEXT, story );
        values.put(COLUMN_STATUS, stts );
        values.put(COLUMN_ContributorName, name );

        long result = db.insert(TABLE_NAME_CONTRIBUTION,null,values);
        if(result == -1){
            db.close();
            return false;
        }
        else {
            db.close();
            return true;
        }

    }
}
