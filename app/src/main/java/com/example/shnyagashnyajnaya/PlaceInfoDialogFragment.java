package com.example.shnyagashnyajnaya;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.shnyagashnyajnaya.OTMAPI.ResponseOTM.Feature;
import com.example.shnyagashnyajnaya.OTMAPI.ResponseOTMInf.ResponseOTMInf;

import java.util.Objects;

import retrofit2.Response;

public class PlaceInfoDialogFragment  extends DialogFragment {
    Response<ResponseOTMInf> response;
    String dist;
    String wiki="";
    String src="";
    String tittle="";
    String text="";

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
    return new AlertDialog.Builder(getActivity())
                .setTitle(name)
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
