package fi.jamk.imageviewexample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;
    private ProgressBar progressBar;
    private final String PATH = "http://ptm.fi/android/";
    private String[] images = {"image1.jpg", "image2.jpg", "image3.jpg"};
    private int imageIndex;
    private DownloadImageTask task;
    public double x1, x2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        imageIndex = 0;
        showImage();
    }

    public void showImage(){
        task = new DownloadImageTask();
        task.execute(PATH+images[imageIndex]);
    }

    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected void onPreExecute(){
            //show loading progressbar
            progressBar.setVisibility(View.VISIBLE);
        }

        protected Bitmap doInBackground(String... urls){
            URL imageUrl;
            Bitmap bitmap = null;

            try{
                imageUrl = new URL(urls[0]);
                InputStream in = imageUrl.openStream();
                bitmap = BitmapFactory.decodeStream(in);
            }catch(Exception e){
                Log.d("<<LOADIMAGE>>", e.getMessage());
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){
            imageView.setImageBitmap(bitmap);
            textView.setText("Image " + (imageIndex+1) + "/ " + images.length);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public boolean onTouchEvent(MotionEvent event){
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                if(x1 < x2){
                    imageIndex--;
                    if(imageIndex<0)
                        imageIndex = images.length - 1;
                }else if(x2 < x1){
                    imageIndex++;
                    if(imageIndex==images.length)
                        imageIndex = 0;
                }
                showImage();
                break;
        }
        return false;
    }
}
