package kncstorytellerapp.mothergoose;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class listRowAdapter extends ArrayAdapter<String>{

    String[] arrx;
    Bitmap[] images;
    String[] likes;
    public listRowAdapter(Context context, String[] stories,String[] x,String[] likecount,Bitmap[] im) {
        super(context,R.layout.custom_row ,stories);

            arrx=x;
            images=im;
            likes=likecount;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutI = LayoutInflater.from(getContext());
        View customView= layoutI.inflate(R.layout.custom_row, parent, false);

        String story = getItem(position);
        String x = arrx[position];
        Bitmap img=images[position];
        String l = likes[position];

        TextView textV = (TextView) customView.findViewById(R.id.rowText);
        TextView textV1 = (TextView) customView.findViewById(R.id.custom_row_text1);
        TextView textV2 = (TextView) customView.findViewById(R.id.likesCusRow);
        ImageView imageV = (ImageView)customView.findViewById(R.id.imgViewCustomRow);

        textV.setText(story);
        textV1.setText(x);
        imageV.setImageBitmap(img);
        textV2.setText(l);
        return customView;
    }
}
