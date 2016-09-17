package fi.jamk.basicuicontrols2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, new String[]{"Pasi","Juha","Kari", "Jouni", "Esa", "Hannu"});
        AutoCompleteTextView actv = (AutoCompleteTextView)
                findViewById(R.id.loginAutoComplete);
        // add strings to control
        actv.setAdapter(aa);
    }

    public void selectButtonClicked(View view){
        TextView loginView = (TextView) findViewById(R.id.loginAutoComplete);
        TextView passwordView = (TextView) findViewById(R.id.passwordEditText);
        String loginText = loginView.getText().toString();
        String passwordText = passwordView.getText().toString();
        Toast.makeText(getApplicationContext(), loginText +" "+passwordText, Toast.LENGTH_LONG).show();
    }
}
