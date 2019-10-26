package flogleg.fioe.notes;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.List;

import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.Toast;

import flogleg.fioe.notes.reafer.Ryfer;

public class TrialActivity extends AppCompatActivity {

    // Declare Variables
    ListView list;
    ListViewAdapter listviewadapter;
    List<WorldPopulation> worldpopulationlist = new ArrayList<WorldPopulation>();
    String[] rank;
    String[] country;
    String[] population;
    String buff = "China,India,United States,Indonesia,Brazil,Pakistan,Nigeria,Bangladesh,Russia,Japan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from listview_main.xml
        setContentView(R.layout.activity_trial);

        // Generate sample data into string arrays
        rank = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        country = buff.split(",");
        population = new String[]{"1,354,040,000", "1,210,193,422",
                "315,761,000", "237,641,326", "193,946,886", "182,912,000",
                "170,901,000", "152,518,015", "143,369,806", "127,360,000"};

        for (int i = 0; i < rank.length; i++) {
            WorldPopulation worldpopulation = new WorldPopulation(country[i]);
            worldpopulationlist.add(worldpopulation);
        }

        // Locate the ListView in listview_main.xml
        list = findViewById(R.id.listview);

        // Pass results to ListViewAdapter Class
        listviewadapter = new ListViewAdapter(this, R.layout.listview_item,
                worldpopulationlist);

        // Binds the Adapter to the ListView
        list.setAdapter(listviewadapter);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        // Capture ListView item click
        list.setMultiChoiceModeListener(new MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode mode,
                                                  int position, long id, boolean checked) {
                // Capture total checked items
                final int checkedCount = list.getCheckedItemCount();
                // Set the CAB title according to total checked items
                mode.setTitle(checkedCount + " Selected");
                // Calls toggleSelection method from ListViewAdapter Class
                listviewadapter.toggleSelection(position);
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.save:
                        // Calls getSelectedIds method from ListViewAdapter Class
                        SparseBooleanArray selected = listviewadapter
                                .getSelectedIds();
                        // Captures all selected ids with a loop
                        String op = "";
                        for (int i = (selected.size() - 1); i >= 0; i--) {
                            if (selected.valueAt(i)) {
                                WorldPopulation selecteditem = listviewadapter
                                        .getItem(selected.keyAt(i));
                                // Remove selected items following the ids
                                op = " " +selected.keyAt(i) + " ";
                                Toast.makeText(TrialActivity.this,op+country[selected.keyAt(i)],Toast.LENGTH_SHORT).show();
                                buff = Ryfer.parsing(buff,country[selected.keyAt(i)],",");
                                country = buff.split(",");
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
                mode.getMenuInflater().inflate(R.menu.menu_main, menu);
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

    }
}