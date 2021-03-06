package fi.jamk.musicplayer;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listview;
    private String mediaPath;
    private List<String> songs = new ArrayList<String>();
    private MediaPlayer mediaplayer = new MediaPlayer();
    private LoadSongsTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = (ListView) findViewById(R.id.listView);
        mediaPath = Environment.getExternalStorageDirectory().getPath() + "/Music/";

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    mediaplayer.reset();
                    mediaplayer.setDataSource(songs.get(position));
                    mediaplayer.prepare();
                    mediaplayer.start();
                }catch(IOException e){
                    Toast.makeText(MainActivity.this, "Cannot start audio!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        task = new LoadSongsTask();
        task.execute();
    }

    private class LoadSongsTask extends AsyncTask<Void, String, Void> {

        private List<String> loadedSongs = new ArrayList<String>();

        @Override
        protected void onPreExecute(){
            Toast.makeText(getApplicationContext(), "Loading ...", Toast.LENGTH_SHORT).show();
        }

        @Override
        public Void doInBackground(Void... url){
            updateSongListRecursive (new File(mediaPath));
            return null;
        }

        public void updateSongListRecursive(File path){
            if(path.isDirectory()){
                for(int i=0;i<path.listFiles().length;i++) {
                    File file = path.listFiles()[i];
                    updateSongListRecursive(file);
                }
            }else{
                String name = path.getAbsolutePath();
                publishProgress(name);
                if(name.endsWith(".mp3"))
                    loadedSongs.add(name);
            }
        }

        protected void onPostExecute(Void args){

            ArrayAdapter<String> songList = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, loadedSongs);
            listview.setAdapter(songList);
            songs = loadedSongs;

            Toast.makeText(getApplicationContext(), "Songs"+songs.size(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStop(){
        super.onStop();
        if(mediaplayer.isPlaying())
            mediaplayer.reset();
    }
}
