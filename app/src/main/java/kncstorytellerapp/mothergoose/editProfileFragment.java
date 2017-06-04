package kncstorytellerapp.mothergoose;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by HP-PC on 3/13/2016.
 */
public class editProfileFragment extends Fragment {

    View myView;
    Button edit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.editprofilefragment, container, false);
        return myView;
    }
}
