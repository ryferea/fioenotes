package flogleg.fioe.notes;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import flogleg.fioe.notes.reafer.Ryfer;
import flogleg.fioe.notes.sql.DatabaseHandler;
import flogleg.fioe.notes.sql.usermodels;

public class HomeActivity extends AppCompatActivity {
    ListViewAdapter listviewadapter;
    DatabaseHandler db;
    List<WorldPopulation> worldpopulationlist = new ArrayList<WorldPopulation>();
    static String[] values;
    static String[] fid;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("FIoE Notes");
         db = new DatabaseHandler(getApplicationContext());
        String title = db.getTitle();
        final ListView listview =  findViewById(R.id.mainNotes);
        values = title.split("<<");
        fid = db.getKeyId();

        for (int i = 0; i < values.length; ++i) {
            WorldPopulation worldpopulation = new WorldPopulation(values[i]);
            worldpopulationlist.add(worldpopulation);
        }
        if(fid.length>0&&!fid[0].equals("")) {
            listviewadapter = new ListViewAdapter(this, R.layout.listview_item,
                    worldpopulationlist);
            listview.setAdapter(listviewadapter);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Ryfer.id = Integer.parseInt(fid[i]);
                startActivity(new Intent(HomeActivity.this,EditorActivity.class));
                finish();
                }
            });
            listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            listview.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

                @Override
                public void onItemCheckedStateChanged(ActionMode mode,
                                                      int position, long id, boolean checked) {
                    // Capture total checked items
                    final int checkedCount = listview.getCheckedItemCount();
                    // Set the CAB title according to total checked items
                    mode.setTitle(checkedCount + " Selected");
                    // Calls toggleSelection method from ListViewAdapter Class
                    listviewadapter.toggleSelection(position);
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.delete:
                            // Calls getSelectedIds method from ListViewAdapter Class
                            SparseBooleanArray selected = listviewadapter
                                    .getSelectedIds();
                            // Captures all selected ids with a loop
                            String op = "";
                            for (int i = (selected.size() - 1); i >= 0; i--) {
                                if (selected.valueAt(i)) {
                                    WorldPopulation selecteditem = listviewadapter
                                            .getItem(selected.keyAt(i));
                                    usermodels um = db.getContact(Integer.parseInt(fid[selected.keyAt(i)]));
                                    db.deleteModel(um);
                                    op = " " + selected.keyAt(i) + " ";
                                    listviewadapter.remove(selecteditem);
                                }
                            }
                            // Close CAB

                            mode.finish();
                            return true;
                        default:
                            return false;
                    }
                }

                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    mode.getMenuInflater().inflate(R.menu.menu_home, menu);
                    return true;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                    // TODO Auto-generated method stub
                    listviewadapter.removeSelection();
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    // TODO Auto-generated method stub
                    return false;
                }
            });
            toolbar.setBackgroundColor(getColor(R.color.colorPrimaryDark));
        }
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ryfer.id = 0;
                Intent ot = new Intent(HomeActivity.this,EditorActivity.class);
                startActivity(ot);
                finish();
            }
        });
    }

}
