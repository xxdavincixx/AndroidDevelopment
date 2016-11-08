package fi.jamk.sgky_golfcourses_json_example;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Leo on 07.11.2016.
 */

public class ScrollingActivity extends AppCompatActivity {

    public List<GolfCourse> golfcourses;
    public RecyclerView rview;
    public RecyclerView.Adapter rviewadapter;
    public RecyclerView.LayoutManager rviewlayoutmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FetchDataTask task = new FetchDataTask();
        task.execute("http://ptm.fi/jamk/android/golfcourses/golf_courses.json");

    }



    protected class FetchDataTask extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... urls){
            HttpURLConnection urlConnection = null;
            JSONObject jsonobj = null;
            try{
                URL url = new URL(urls[0]);
                urlConnection = (HttpURLConnection)url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                jsonobj = new JSONObject(stringBuilder.toString());
            } catch (IOException e){
                e.printStackTrace();
            } catch (JSONException e){
                e.printStackTrace();
            } finally{
                if(urlConnection != null) urlConnection.disconnect();
            }
            return jsonobj;
        }


        protected void onPostExecute(JSONObject jsonobj){
            try{
                JSONArray courses = jsonobj.getJSONArray("kentat");
                JSONObject course;

                final GolfCourse golfplaces = new GolfCourse();
                for(int i = 0;i < courses.length();i++){
                    course = courses.getJSONObject(i);
                    golfplaces.addGolfplace(new GolfCourse(
                            course.getString("Kentta"),
                            course.getString("Osoite"),
                            course.getString("Puhelin"),
                            course.getString("Sahkoposti"),
                            course.getString("Kuva"),
                            course.getString("Kuvaus"),
                            course.getString("Webbi"),
                            course.getDouble("lat"),
                            course.getDouble("lng")
                    ));
                }

                golfcourses = golfplaces.getGolfplaces();
                rview = (RecyclerView) findViewById(R.id.my_recycler_view);
                rviewlayoutmanager = new LinearLayoutManager(getApplicationContext());
                rviewadapter = new MyAdapter(golfcourses, rview);
                rview.setLayoutManager(rviewlayoutmanager);
                rview.setAdapter(rviewadapter);



            } catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

}
