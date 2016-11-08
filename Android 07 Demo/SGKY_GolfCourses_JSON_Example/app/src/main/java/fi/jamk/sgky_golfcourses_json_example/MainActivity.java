package fi.jamk.sgky_golfcourses_json_example;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
public class MainActivity extends AppCompatActivity {
public double lat;
public double lng;
public String gName;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String name = bundle.getString("name");
        String image = bundle.getString("image");
        String address = bundle.getString("address");
        String phonenr = bundle.getString("phonenr");
        String email = bundle.getString("email");
        String text = bundle.getString("text");
        String web = bundle.getString("web");
        lat = bundle.getDouble("lat");
        lng = bundle.getDouble("lng");
        gName = name;

        setTitle("SGKY: " + name);

        ImageView imageView = (ImageView) findViewById(R.id.golfImage);
        new DownloadImageTask(imageView).execute("http://ptm.fi/jamk/android/golfcourses/" + image);

        TextView addressText = (TextView) findViewById(R.id.golfAddress);
        addressText.setText(address);

        TextView phonenrText = (TextView) findViewById(R.id.golfPhone);
        phonenrText.setText(phonenr);

        TextView emailText = (TextView) findViewById(R.id.golfEmail);
        emailText.setText(email);

        TextView textText = (TextView) findViewById(R.id.golfText);
        textText.setText(text);

        TextView webText = (TextView) findViewById(R.id.golfWebLink);
        webText.setText(web);



        }

public void openMap(View view){

        double latitude = lat;
        double longitude = lng;
        String label = gName;
        String uriBegin = "geo:" + latitude + "," + longitude;
        String query = latitude + "," + longitude + "(" + label + ")";
        String encodedQuery = Uri.encode(query);
        String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
        Uri uri = Uri.parse(uriString);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
        startActivity(intent);

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

}
