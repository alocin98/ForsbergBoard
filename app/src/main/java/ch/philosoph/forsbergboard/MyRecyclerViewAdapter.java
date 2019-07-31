package ch.philosoph.forsbergboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.forsbergboard.R;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewAdapter for the RecyclerView, which is used for showing all Per sounds.
 * This Class implements also a Filterable for filtering the Per sounds.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {
    private List<PerSound> mData;
    private List<PerSound> mDataFull;

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    MyRecyclerViewAdapter(Context context, List<PerSound> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        mDataFull = new ArrayList<>(data);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.forsberg_button_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String soundDisplayName = mData.get(position).getDisplayName();
        holder.soundDisplayName.setText(soundDisplayName);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public Filter getFilter() {
        return soundFilter;
    }

    private  Filter soundFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<PerSound> filteredList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(mDataFull);
            } else {
                String filterValue = charSequence.toString().toLowerCase().trim();
                for (PerSound perSound : mDataFull) {
                    if (perSound.getDisplayName().toLowerCase().contains(filterValue)) {
                        filteredList.add(perSound);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mData.clear();
            mData.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView soundDisplayName;

        ViewHolder(View itemView) {
            super(itemView);
            soundDisplayName = itemView.findViewById(R.id.perSoundName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    PerSound getItem(int id) {
        return mData.get(id);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
