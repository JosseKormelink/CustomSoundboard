package org.kormelink.josse.customsoundboard.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.kormelink.josse.customsoundboard.R;
import org.kormelink.josse.customsoundboard.entities.LocalSound;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josse on 12/09/2016.
 */
public class SoundBoardArrayAdapter extends BaseAdapter {

    private Context context;
    private List<LocalSound> localSounds;

    public SoundBoardArrayAdapter(Context context, List<LocalSound> localSounds) {
        this.context = context;
        this.localSounds = localSounds;
    }

    @Override
    public int getCount() {
        return localSounds.size();
    }

    @Override
    public LocalSound getItem(int i) {
        return localSounds.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder soundboardItemViewHolder;
        if (view != null) {
            soundboardItemViewHolder = (ViewHolder) view.getTag();
        } else {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.soundboard_item, viewGroup, false);
            soundboardItemViewHolder = new ViewHolder(view);
            view.setTag(soundboardItemViewHolder);
        }

        LocalSound localSound = localSounds.get(i);

        soundboardItemViewHolder.tvTitle.setText(localSound.getTitle());

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.soundboard_item_title)
        public TextView tvTitle;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
