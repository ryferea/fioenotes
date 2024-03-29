package flogleg.fioe.notes;


import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class ListViewAdapter extends ArrayAdapter<WorldPopulation> {
    // Declare Variables
    Context context;
    LayoutInflater inflater;
    List<WorldPopulation> worldpopulationlist;
    private SparseBooleanArray mSelectedItemsIds;

    public ListViewAdapter(Context context, int resourceId,
                           List<WorldPopulation> worldpopulationlists) {
        super(context, resourceId, worldpopulationlists);
        worldpopulationlist = worldpopulationlists;
        mSelectedItemsIds = new SparseBooleanArray();
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    private class ViewHolder {
        TextView country;
    }

    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_item, null);
            holder.country = view.findViewById(R.id.country);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.country.setText(worldpopulationlist.get(position).getCountry());
        return view;
    }

    @Override
    public void remove(WorldPopulation object) {
        worldpopulationlist.remove(object);
        notifyDataSetChanged();
    }

    public List<WorldPopulation> getWorldPopulation() {
        return worldpopulationlist;
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }
}
