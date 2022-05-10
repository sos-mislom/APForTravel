package com.example.shnyagashnyajnaya;

import static com.example.shnyagashnyajnaya.MainActivity.ArrOfFavorite;
import static com.example.shnyagashnyajnaya.MainActivity.ma;
import static com.example.shnyagashnyajnaya.MainActivity.serialize;
import static com.example.shnyagashnyajnaya.OTMAPI.APIConfig.LANGUAGE;
import static com.yandex.runtime.Runtime.getApplicationContext;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.shnyagashnyajnaya.OTMAPI.ResponseOTMInf.ResponseOTMInf;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Response;

public class PlaceInfoDialogFragment  extends DialogFragment {
    private Response<ResponseOTMInf> response;
    private String dist;
    private String address;
    private String url;
    private String tittle = "Описание ";
    private String text = "отсутствует \n";
    int str_to_fav = R.string.addToFavorite;
    int str_alr_in = R.string.alreadyInFavorite;
    private TableLayout tableLayout;

    private String checkIfNull(String str){ if (str==null){ return ""; }else { return str; } }
    public PlaceInfoDialogFragment (Response<ResponseOTMInf> card, String distance) {
        this.response = card;
        this.dist = distance;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment, null);
        tableLayout = view.findViewById(R.id.contentDialogFragment);

        String name = response.body().getName();

        if (!LANGUAGE.equals("ru")){
            if (Objects.equals(name, "")) {name = "Unnamed";}
            tittle = "Description ";
            text = "is missing \n";
            dist = "To place(m): " + dist;
            str_to_fav = R.string.addToFavorite_eng;
            str_alr_in = R.string.alreadyInFavorite_eng;
        } else {
            if (Objects.equals(name, "")) {name = "Без названия";}
            dist = "До места(м): " + dist;
        }
        url = checkIfNull(response.body().getWikipedia());
        address = checkIfNull(response.body().getAddress().getRoad()) + ", " +
                checkIfNull(response.body().getAddress().getHouse()) + " " +
                checkIfNull(response.body().getAddress().getHouseNumber())+ "\n";
        try {
            tittle = response.body().getWikipediaExtracts().getTitle();
            text = response.body().getWikipediaExtracts().getText()+ "\n";
        } catch (NullPointerException e){}

        TextView tx_title = new TextView(ma);
        tx_title.setText(tittle);
        tx_title.setTextColor(Color.BLACK);
        tx_title.setTextSize(20);
        tx_title.setPadding(14,7,14,7);

        TextView tx_text = new TextView(ma);
        tx_text.setTextSize(19);
        tx_text.setTextColor(Color.BLACK);
        tx_text.setText(text);
        tx_text.setPadding(14,7,14,7);

        TextView tx_dist = new TextView(ma);
        tx_dist.setText(dist);
        tx_dist.setTextSize(17);
        tx_dist.setPadding(14,7,14,7);

        TextView tx_address = new TextView(ma);
        if (address.equals(",")){address = "...";}
        tx_address.setText(address);
        tx_address.setTextSize(17);
        tx_address.setPadding(14,7,14,7);

        TextView tx_url = new TextView(ma);
        tx_url.setText(url);
        tx_url.setPadding(14,7,14,7);
        Linkify.addLinks(tx_url, Linkify.ALL);

        tableLayout.addView(tx_title);
        tableLayout.addView(tx_text);
        tableLayout.addView(tx_dist);
        tableLayout.addView(tx_address);
        tableLayout.addView(tx_url);

        String kinds = response.body().getKinds();
        int bit;
        if (kinds.contains("historic")){
                bit = R.drawable.monument;
        }else if (kinds.contains("cultural")){
                bit = R.drawable.historical;
        }else if (kinds.contains("industrial_facilities")){
                bit = R.drawable.industrial;
        }else if (response.body().getName().length() == 0){
                bit = R.drawable.unknown;
        } else if (kinds.contains("natural")){
                bit = R.drawable.nature;
        } else if (kinds.contains("architecture")){
                bit = R.drawable.buildings;
        }else if (kinds.contains("other")){
                bit = R.drawable.forphoto;
        }else{
                bit = R.drawable.unknown;
        }
        AlertDialog.Builder placeInfo = new AlertDialog.Builder(getActivity());
        placeInfo.setTitle(name);
        placeInfo.setIcon(bit);
        placeInfo.setView(view);
        if (!ArrOfFavorite.keySet().contains(response.body().getXid())){
            if (name.length() > 23){name = name.substring(0, 20) + "...";}
            String finalName = name;
            placeInfo.setNegativeButton(str_to_fav, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ArrayList<String> target = new ArrayList<>();
                    target.add(response.body().getPoint().getLat() + " " + response.body().getPoint().getLon());
                    target.add(String.valueOf(bit));
                    target.add(finalName);
                    target.add(address);
                    ArrOfFavorite.put(response.body().getXid(), target);
                    try {
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit()
                                .putString("favs", serialize(ArrOfFavorite).toString())
                                .apply();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                }
            });
        } else {
            placeInfo.setNegativeButton(str_alr_in, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        }
        return placeInfo.create();
    }

}
