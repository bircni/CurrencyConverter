package de.thu.currencyconverter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * This class is used to create a list of currencies to edit.
 */
public class CurrencyEditAdapter extends BaseAdapter {
    private final List<ExchangeRate> rate_data;

    /**
     * Constructor for the CurrencyEditAdapter.
     *
     * @param rate_data The list of currencies to edit.
     */
    public CurrencyEditAdapter(List<ExchangeRate> rate_data) {
        this.rate_data = rate_data;
    }

    /**
     * This method returns the number of items in the list.
     *
     * @return The number of items in the list.
     */
    @Override
    public int getCount() {
        return rate_data.size();
    }

    /**
     * This method returns the item at the given position.
     *
     * @param i The position of the item.
     * @return The item at the given position.
     */
    @Override
    public Object getItem(int i) {
        return rate_data.get(i);
    }

    /**
     * This method returns the item id at the given position.
     *
     * @param i The position of the item.
     * @return i.
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * This method returns the flagID of the given currency rate.
     *
     * @return the flagId
     */
    private String getFlagId(ExchangeRate rate) {
        return "flag_" + rate.getCurrencyName().toLowerCase();
    }

    /**
     * This method is called for each item in the list. It is responsible for
     *
     * @param i         The position of the item within the adapter's data set of the item whose view
     *                  we want.
     * @param view      The old view to reuse, if possible. Note: You should check that this view
     *                  is non-null and of an appropriate type before using. If it is not possible to convert
     *                  this view to display the correct data, this method can create a new view.
     *                  Heterogeneous lists can specify their number of view types, so that this View is
     *                  always of the right type (see {@link #getViewTypeCount()} and
     *                  {@link #getItemViewType(int)}).
     * @param viewGroup The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @SuppressLint("InflateParams")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();
        ExchangeRate rate = rate_data.get(i);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.cc_list_view_item, null, false);
        }
        TextView currency_text = view.findViewById(R.id.Currency_text);
        currency_text.setText(rate.getCurrencyName());
        ImageView flag1 = view.findViewById(R.id.flag1);
        @SuppressLint("DiscouragedApi") int imageId = context.getResources().getIdentifier(getFlagId(rate),
                "drawable", context.getPackageName());
        flag1.setImageResource(imageId);
        TextView rate_text = view.findViewById(R.id.rate_text);
        rate_text.setText(String.valueOf(rate.getRateForOneEuro()));
        return view;
    }
}