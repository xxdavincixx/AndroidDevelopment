package fi.jamk.toastandnotification;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity{

    final private int notification_ID = 1;
    public int resultSet = 0;
    public int randomA = 0;
    public int randomB = 0;
    private String resultSetString="";
    private boolean boolNoti=false;
    private boolean boolPop= false;
    private boolean boolToast = false;
    private boolean explanation = true;
    Random random = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_toastMenu:
                if(boolToast){
                    Toast.makeText(getBaseContext(), "Multiply " + randomA + " and " + randomB, Toast.LENGTH_SHORT).show();
                    resultSet = randomA*randomB;
                    resultSetString = Integer.toString(resultSet);
                    TextView randA = (TextView)findViewById(R.id.randomNumberA);
                    randA.setText("" + randomA);
                    TextView randB = (TextView)findViewById(R.id.randomNumberB);
                    randB.setText("" + randomB);
                    return true;
                }
                return false;
            case R.id.action_notificationMenu:
                if(boolNoti) {
                    launchNotification();
                    resultSet = randomA*randomB;
                    resultSetString = Integer.toString(resultSet);
                    return true;
                }
                return false;
            case R.id.action_dialogMenu:
                if(boolPop) {
                    resultSet = randomA*randomB;
                    resultSetString = Integer.toString(resultSet);
                    dialog();
                    TextView randA = (TextView)findViewById(R.id.randomNumberA);
                    randA.setText("" + randomA);
                    TextView randB = (TextView)findViewById(R.id.randomNumberB);
                    randB.setText("" + randomB);
                    return true;
                }
                return false;
            case R.id.action_explanationMenu:
                if(explanation)
                    nextOperation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void dialog(){
        new AlertDialog.Builder(this)
                .setTitle("Your math problem")
                .setMessage("Multiply " + randomA + " with " + randomB)
                .setNegativeButton("I don't understand", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "You didn't understood what to do!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("Got it", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "You understood what to do!", Toast.LENGTH_SHORT).show();
                    }
                }).create().show();
    }

    private boolean nextOperation() {
        int i = random.nextInt(3);
        newNumbers();
        switch(i){
            case 0:
                boolToast = true;
                Toast.makeText(getBaseContext(), "Go on with Toast", Toast.LENGTH_SHORT).show();
                return true;
            case 1:
                boolNoti = true;
                Toast.makeText(getBaseContext(), "Go on with Notification", Toast.LENGTH_SHORT).show();
                return true;
            case 2:
                boolPop = true;
                Toast.makeText(MainActivity.this, "Go on with Dialog", Toast.LENGTH_SHORT).show();
                return true;
            default:
                Toast.makeText(getBaseContext(), "There went something wrong", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    private void newNumbers() {
        randomA = random.nextInt(100)+1;
        randomB = random.nextInt(100)+1;
        resultSet = randomA*randomB;
    }

    public void launchNotification(){
        Intent actionIntent = new Intent(Intent.ACTION_VIEW);
        actionIntent.setData(Uri.parse("http://www.jamk.fi"));

        //create a pending intent
        PendingIntent actionPendingIntent
                = PendingIntent.getActivity(this, 0, actionIntent,0);

        //create a notification
        Notification notification = new Notification.Builder(this)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setContentTitle("Your task")
                .setContentText("Multiply " + randomA + " and " + randomB)
                .setSmallIcon(R.drawable.bug)
                .setAutoCancel(true)
                .setContentIntent(actionPendingIntent)
                .setVisibility(Notification.VISIBILITY_PUBLIC).build();

        //connect notification manager
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(notification_ID, notification);

        TextView randA = (TextView)findViewById(R.id.randomNumberA);
        randA.setText(""+randomA);
        TextView randB = (TextView)findViewById(R.id.randomNumberB);
        randB.setText(""+randomB);
    }
    public void checkResults(View view) {
        TextView resultReceived = (TextView) findViewById(R.id.results);
        String resultString = resultReceived.getText().toString();
        if (resultString.equals(resultSetString)) {
            Toast.makeText(getApplicationContext(), "Well Done!", Toast.LENGTH_SHORT).show();
            boolPop = false;
            boolNoti = false;
            boolToast = false;
            nextOperation();
        }else{
            Toast.makeText(getApplicationContext(), "That's not true :'(", Toast.LENGTH_SHORT).show();
        }
    }

}
