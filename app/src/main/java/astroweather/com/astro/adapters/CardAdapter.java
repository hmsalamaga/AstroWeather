package astroweather.com.astro.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import astroweather.com.astro.R;
import astroweather.com.astro.fragments.FutureInfoFragment;
import astroweather.com.astro.models.ForecastDayModel;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {

    private ArrayList<ForecastDayModel> dataSet;
    private FutureInfoFragment futureInfoFragment;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView dayInfo;
        TextView dayLowInfo;
        TextView dayHighInfo;
        ImageView dayDescriptionImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.dayInfo = itemView.findViewById(R.id.dayInfo);
            this.dayLowInfo = itemView.findViewById(R.id.dayLowInfo);
            this.dayHighInfo = itemView.findViewById(R.id.dayHighInfo);
            this.dayDescriptionImage = itemView.findViewById(R.id.dayDescriptionImage);
        }
    }

    public CardAdapter(ArrayList<ForecastDayModel> data, FutureInfoFragment fragment) {
        this.dataSet = data;
        futureInfoFragment = fragment;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.future_info_card, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        TextView dayInfo = holder.dayInfo;
        TextView dayLowInfo = holder.dayLowInfo;
        TextView dayHighInfo = holder.dayHighInfo;
        ImageView dayDescriptionImage = holder.dayDescriptionImage;
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM", Locale.UK);

        dayInfo.setText(dateFormat.format(dataSet.get(listPosition).date * 1000L));
        dayLowInfo.setText(dataSet.get(listPosition).low);
        dayHighInfo.setText(dataSet.get(listPosition).high);
        dayDescriptionImage.setImageResource(prepareImageResource(dataSet.get(listPosition).code));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    private int prepareImageResource(int code) {
        String uri = "weather" + code;
        String defaultUri = "undefined";

        int imageResource = futureInfoFragment.getResources().getIdentifier(uri, "drawable", futureInfoFragment.getActivity().getPackageName());
        if (imageResource == 0) {
            return futureInfoFragment.getResources().getIdentifier(defaultUri, "drawable", futureInfoFragment.getActivity().getPackageName());
        }

        return imageResource;
    }
}

