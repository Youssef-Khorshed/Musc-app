package com.example.newmediaplayer2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class song_adaptor extends ArrayAdapter<song> implements Filterable {
    List<song> all;
    List<song> songs;

    public song_adaptor(@NonNull Context context, List<song> songs) {
        super(context, 0, songs);
        this.songs = songs;
        this.all = new ArrayList<>(songs);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.song, null);
        TextView title = convertView.findViewById(R.id.Song);
        TextView artist = convertView.findViewById(R.id.artist);
        song s = getItem(position);
        title.setText(s.getTitle());
        artist.setText(s.getArtist());

        return convertView;
    }

    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<song> filterlist = new ArrayList<>();
            if (constraint.toString().isEmpty()) {
                filterlist.addAll(all);
            } else {
                for (song s : all) {
                    if (s.toString().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filterlist.add(s);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterlist;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            songs.clear();
            songs.addAll((Collection<? extends song>) results.values);
            notifyDataSetChanged();
        }
    };

}
