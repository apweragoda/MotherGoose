package kncstorytellerapp.mothergoose;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by HP-PC on 3/15/2016.
 */
class storyAdapter extends ArrayAdapter<String> {

    String[] auth;

    public storyAdapter(Context context, String[] story,String[] author) {
        super(context,R.layout.custme_story ,story);

        auth=author;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutI = LayoutInflater.from(getContext());
        View customView= layoutI.inflate(R.layout.custme_story, parent, false);

        String story = getItem(position);
        String aut=auth[position];
        String id=String.valueOf(position+1);

        TextView textV = (TextView) customView.findViewById(R.id.storyText);
        TextView textV1 = (TextView) customView.findViewById(R.id.storyNum);
        TextView textV2 = (TextView) customView.findViewById(R.id.authorName);


        textV.setText(story);
        textV1.setText(id);
        textV2.setText(aut);

        return customView;
    }
}
