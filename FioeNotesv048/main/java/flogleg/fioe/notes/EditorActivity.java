package flogleg.fioe.notes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import flogleg.fioe.notes.reafer.Ryfer;
import flogleg.fioe.notes.sql.DatabaseHandler;
import flogleg.fioe.notes.sql.usermodels;

public class EditorActivity extends AppCompatActivity {
    EditText content,title;
    String titlef = "";
    String contentf = "";
    DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
         title = findViewById(R.id.editF2);
        content = findViewById(R.id.editF1);
        if(Ryfer.id!=0){
            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
            usermodels um = db.getContact(Ryfer.id);
            titlef = um.getName();
            contentf = um.getTall();
            title.setText(titlef);
            setTitle(titlef);
            content.setText(contentf);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }
    @Override
    public void onBackPressed() {
        finish();
        Intent io = new Intent(EditorActivity.this,HomeActivity.class);
        startActivity(io);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.save){
            String ol = content.getText().toString().replace("\n","");
        if(title.getText().toString().equals("")||ol.equals("")){
            Toast.makeText(this,"No You Must Write Something :(",Toast.LENGTH_SHORT).show();
        }
        else{
            if(Ryfer.id == 0){
                DatabaseHandler db = new DatabaseHandler(EditorActivity.this);
                usermodels um = new usermodels();
                um.setTall(title.getText().toString());
                um.setName(content.getText().toString());
                setTitle(title.getText().toString());
                db.addRecord(um);
                int rec = db.getKeyId().length;
                Ryfer.id = rec;
                Toast.makeText(EditorActivity.this,"Saved",Toast.LENGTH_SHORT).show();
            }
            else{
                DatabaseHandler db = new DatabaseHandler(EditorActivity.this);
                usermodels un = new usermodels(Ryfer.id,title.getText().toString(),content.getText().toString());
                setTitle(title.getText().toString());
                db.update(un);
                Toast.makeText(EditorActivity.this,"Updated",Toast.LENGTH_SHORT).show();
            }

        }
        }

        return true;
    }
}
