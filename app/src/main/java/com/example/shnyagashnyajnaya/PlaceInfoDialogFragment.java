package com.example.shnyagashnyajnaya;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.shnyagashnyajnaya.OTMAPI.ResponseOTM.Feature;
import com.example.shnyagashnyajnaya.OTMAPI.ResponseOTMInf.ResponseOTMInf;
import com.yandex.runtime.image.ImageProvider;

import java.util.Objects;

import retrofit2.Response;

public class PlaceInfoDialogFragment  extends DialogFragment {
    Response<ResponseOTMInf> response;
    String dist;
    String tittle;
    String text;

    public PlaceInfoDialogFragment (Response<ResponseOTMInf> card, String distance) {
        this.response = card;

        this.dist = "До места(м): " + distance + "\n";
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    String str = "";
    String name = response.body().getName();
    if (Objects.equals(name, "")) {name = "Что-то непонятное";}
    String address = response.body().getAddress().getRoad() + ", " +
            response.body().getAddress().getHouse() + " " +
            response.body().getAddress().getHouseNumber()+ "\n";
    try {
        tittle = response.body().getWikipediaExtracts().getTitle()+ "\n";
        text = response.body().getWikipediaExtracts().getText()+ "\n";
    } catch (NullPointerException e){}
    str = tittle + text + dist + address;
    String kinds = response.body().getKinds();
        int bit;
        if (kinds.contains("historic")){
            bit = R.drawable.historical;
        }else if (kinds.contains("cultural")){
            bit = R.drawable.historical;
        }else if (kinds.contains("industrial_facilities")){
            bit = R.drawable.industrial;
        }else if (name.length() == 0){
            bit = R.drawable.unknown;
        } else if (kinds.contains("natural")){
            bit = R.drawable.nature;
        } else if (kinds.contains("architecture")){
            bit = R.drawable.buildings;
        }else{
            bit = R.drawable.unknown;
        }
    return new AlertDialog.Builder(getActivity())
                .setTitle(name)
                .setIcon(bit)
                .setMessage(str)
                .setNegativeButton(R.string.addtofavorite, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
    }
}
