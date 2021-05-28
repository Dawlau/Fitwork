package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitwork.R;

import java.util.ArrayList;
import java.util.List;

import Models.Profile;

public class UserSearchAdapter extends
        RecyclerView.Adapter<UserSearchAdapter.ExampleViewHolder> implements Filterable {
    private List<Profile> profileList;
    private List<Profile> profileListFull;

    @Override
    public Filter getFilter() {
        return filter;
    }

    class ExampleViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView2;
        ExampleViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            textView2 = itemView.findViewById(R.id.textView2);
        }
    }
    public UserSearchAdapter(List<Profile> exampleList) {
        this.profileList = exampleList;
        profileListFull = new ArrayList<>(profileList);
    }
    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_item, parent, false);
        return new ExampleViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        Profile currentItem = profileList.get(position);
        //holder.imageView.setImageResource(currentItem.getImageResource());
        holder.textView2.setText(currentItem.getFullName());
    }
    @Override
    public int getItemCount() {
        return profileList.size();
    }

    private Filter filter= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Profile> filterList = new ArrayList<>();
            if(constraint == null || constraint.length()==0){
                filterList.addAll(profileListFull);
            }
            else{
                String pattern = constraint.toString().toLowerCase().trim();
                for(Profile item :profileListFull){
                    if(item.getFullName().toLowerCase().contains(pattern)){
                        filterList.add(item);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;
            return filterResults;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            profileList.clear();
            profileList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}