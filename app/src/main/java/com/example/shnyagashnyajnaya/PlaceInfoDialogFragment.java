package com.example.shnyagashnyajnaya;

import static com.example.shnyagashnyajnaya.MainActivity.ArrOfFavorite;
import static com.example.shnyagashnyajnaya.OTMAPI.APIConfig.LANGUAGE;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.example.shnyagashnyajnaya.OTMAPI.ResponseOTMInf.ResponseOTMInf;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Response;

public class PlaceInfoDialogFragment  extends DialogFragment {
    private Response<ResponseOTMInf> response;
    private String dist;
    private String address;
    private String tittle = "Описание ";
    private String text = "отсутствует \n";
    int str_to_fav = R.string.addToFavorite;
    int str_alr_in = R.string.alreadyInFavorite;

    private String checkIfNull(String str){ if (str==null){ return ""; }else { return str; } }
    public PlaceInfoDialogFragment (Response<ResponseOTMInf> card, String distance) {
        this.response = card;
        this.dist = distance + "\n";
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    String str = "";
    String name = response.body().getName();
    if (Objects.equals(name, "")) {name = "Без названия";}
    if (!LANGUAGE.equals("ru")){
        if (Objects.equals(name, "")) {name = "Unnamed";}
        tittle = "Description ";
        text = "is missing \n";
        dist = "To place(m): " + dist;
        str_to_fav = R.string.addToFavorite_eng;
        str_alr_in = R.string.alreadyInFavorite_eng;
    } else {
        dist = "До места(м): " + dist;
    }
    address = checkIfNull(response.body().getAddress().getRoad()) + ", " +
            checkIfNull(response.body().getAddress().getHouse()) + " " +
            checkIfNull(response.body().getAddress().getHouseNumber())+ "\n";
    try {
        tittle = response.body().getWikipediaExtracts().getTitle()+ "\n";
        text = response.body().getWikipediaExtracts().getText()+ "\n";
    } catch (NullPointerException e){}
    str = tittle + text + dist + address;
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
        placeInfo.setMessage(str);
        if (!ArrOfFavorite.keySet().contains(response.body().getName())){
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
                    ArrOfFavorite.put(response.body().getName(), target);
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
