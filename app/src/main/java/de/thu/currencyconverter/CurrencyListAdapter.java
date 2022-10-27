package de.thu.currencyconverter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;

import java.util.List;

public class CurrencyListAdapter extends BaseAdapter {
    private final List<ExchangeRate> rate_data;

    public CurrencyListAdapter(List<ExchangeRate> rate_data) {
        this.rate_data = rate_data;
    }

    @Override
    public int getCount() {
        return rate_data.size();
    }

    @Override
    public Object getItem(int i) {
        return rate_data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private String getFlagId(ExchangeRate rate) {
        return "flag_" + rate.getCurrencyName().toLowerCase();
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();
        ExchangeRate rate = rate_data.get(i);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.cc_list_view_item, null, false);
        }
        //TextView rate_text = view.findViewById(R.id.Rate_text);
        //rate_text.setText(String.valueOf(rate.getRateForOneEuro()));
        TextView currency_text = (TextView)view.findViewById(R.id.Currency_text);
        currency_text.setText(rate.getCurrencyName());
        ImageView flag1 = (ImageView)view.findViewById(R.id.flag1);
        //ImageView flag2 = (ImageView)view.findViewById(R.id.flag2);
        int imageId = context.getResources().getIdentifier(getFlagId(rate),
        "drawable", context.getPackageName());
        flag1.setImageResource(imageId);
        //flag2.setImageResource(imageId);
        return view;
    }
}