package fi.jamk.sgky_golfcourses_json_example;

/**
 * Created by Leo on 07.11.2016.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Arthur on 04.11.2016.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context mContext;
    private RecyclerView mRecyclerView;

    public List<GolfCourse> golfcourses;
    public int getItemCount(){
        return golfcourses.size();
    }

    private final View.OnClickListener mOnClickListener = new MyOnClickListener();


    public MyAdapter(List<GolfCourse> golfcourses, RecyclerView recyclerView){
        //this.mContext = context;
        this.golfcourses = golfcourses;
        this.mRecyclerView = recyclerView;

    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.golf_card, parent, false);
        view.setOnClickListener(mOnClickListener);
        return new ViewHolder(view);
    }


    public void onBindViewHolder(ViewHolder holder, int position){
        final GolfCourse golfdata = golfcourses.get(position);
        new DownloadImageTask(holder.golfImage).execute("http://ptm.fi/jamk/android/golfcourses/" + golfdata.image);
        holder.golfName.setText(golfdata.name);
        holder.golfAddress.setText(golfdata.address);
        holder.golfPhone.setText(golfdata.phonenr);
        holder.golfEmail.setText(golfdata.email);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView golfImage;
        public TextView golfName;
        public TextView golfAddress;
        public TextView golfPhone;
        public TextView golfEmail;


        public ViewHolder(View itemView){
            super(itemView);
            mContext = itemView.getContext();
            golfImage = (ImageView) itemView.findViewById(R.id.golfImage);
            golfName = (TextView) itemView.findViewById(R.id.golfName);
            golfAddress = (TextView) itemView.findViewById(R.id.golfAddress);
            golfPhone = (TextView) itemView.findViewById(R.id.golfPhone);
            golfEmail = (TextView) itemView.findViewById(R.id.golfEmail);

        }

    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView imagev;
        public DownloadImageTask(ImageView image) {
            this.imagev = image;
        }
        protected Bitmap doInBackground(String... urls){
            String url = urls[0];
            Bitmap icon = null;
            try{
                InputStream instream = new java.net.URL(url).openStream();
                icon = BitmapFactory.decodeStream(instream);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return icon;
        }
        protected void onPostExecute(Bitmap bitmap) {
            imagev.setImageBitmap(bitmap);
        }
    }


    class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int itemPosition = mRecyclerView.getChildLayoutPosition(view);
            GolfCourse item = golfcourses.get(itemPosition);
            //Toast.makeText(mContext, item.name, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(mContext, MainActivity.class);

            // add data to intent
            intent.putExtra("name", item.name);
            intent.putExtra("image", item.image);
            intent.putExtra("address", item.address);
            intent.putExtra("phonenr", item.phonenr);
            intent.putExtra("email", item.email);
            intent.putExtra("text", item.text);
            intent.putExtra("web", item.web);
            intent.putExtra("lat", item.lat);
            intent.putExtra("lng", item.lng);

            mContext.startActivity(intent);

        }
    }



}
