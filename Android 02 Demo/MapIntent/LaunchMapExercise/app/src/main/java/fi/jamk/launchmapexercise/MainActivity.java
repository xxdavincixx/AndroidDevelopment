package fi.jamk.launchmapexercise;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showMap(View view) {
        EditText latView = (EditText) findViewById(R.id.latView);
        EditText longView = (EditText) findViewById(R.id.longView);
        String latCoordString = latView.getText().toString();
        String longCoordString = longView.getText().toString();
        *double latCoord = Double.parseDouble(latCoordString);
        double longCoord = Double.parseDouble(longCoordString);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:" + latCoord + "," + longCoord));
        startActivity(intent);
    }
}
