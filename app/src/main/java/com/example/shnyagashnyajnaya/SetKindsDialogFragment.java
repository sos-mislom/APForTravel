package com.example.shnyagashnyajnaya;

import static com.example.shnyagashnyajnaya.MainActivity.ma;
import static com.example.shnyagashnyajnaya.MainActivity.mapObjectTapListeners;
import static com.example.shnyagashnyajnaya.MainActivity.mapView;
import static com.example.shnyagashnyajnaya.MainActivity.myPosition;

import static com.example.shnyagashnyajnaya.OTMAPI.APIConfig.ALLOWED_KINDS_OF_PLACES;
import static com.example.shnyagashnyajnaya.OTMAPI.APIConfig.CENSORED_KINDS_OF_PLACES;
import static com.example.shnyagashnyajnaya.OTMAPI.APIConfig.LANGUAGE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class SetKindsDialogFragment extends DialogFragment{
    int indx_str_apply;
    String title;
    ArrayList<String> CENSORED_KINDS_OF_PLACES_P;
    String LANGUAGE_P = LANGUAGE.toString();
    String tx_lang_str;
    TableLayout tableLayout;
    TableRow tableRowKinds;
    Switch switc;
    TextView tx_lang;

    @SuppressLint("ResourceType")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment, null);
        tableLayout = view.findViewById(R.id.contentDialogFragment);
        CENSORED_KINDS_OF_PLACES_P = new ArrayList<>();

        tableRowKinds = new TableRow(ma);


        switc = new Switch(ma);
        tx_lang = new TextView(ma);

        if (!LANGUAGE.equals("ru")){
            indx_str_apply = R.string.ok_eng;
            title = "Settings";
            tx_lang_str = "Change language";
            switc.setChecked(false);
        } else{
            title = "Настройки";
            tx_lang_str = "Сменить язык";
            indx_str_apply = R.string.ok;
            switc.setChecked(true);
        }

        AlertDialog.Builder placeInfo = new AlertDialog.Builder(getActivity());
        placeInfo.setTitle(title);
        placeInfo.setIcon(R.drawable.wing);

        switc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    LANGUAGE_P = "en";
                } else {
                    LANGUAGE_P = "ru";
                }
            }
        });
        switc.setShowText(true);
        switc.setTextOn("РУС");
        switc.setTextOff("ENG");
        switc.setText(tx_lang_str);
        switc.setPadding(14,7,7,7);
        tableLayout.addView(switc);

        for (String str: ALLOWED_KINDS_OF_PLACES){
            @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switc = new Switch(ma);
            switc.setShowText(true);
            switc.setText(str);
            switc.setPadding(14,7,7,7);
            switc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        CENSORED_KINDS_OF_PLACES_P.add(str);
                    } else {
                        if (CENSORED_KINDS_OF_PLACES_P.contains(str)){
                            CENSORED_KINDS_OF_PLACES_P.remove(str);
                        }
                    }
                }
            });
            tableLayout.addView(switc);
        }

        tableLayout.addView(tableRowKinds);



        placeInfo.setView(view);
        placeInfo.setNegativeButton(indx_str_apply, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CENSORED_KINDS_OF_PLACES = CENSORED_KINDS_OF_PLACES_P;
                LANGUAGE = LANGUAGE_P;
                if (myPosition.getLatitude() != 0.0){
                    mapObjectTapListeners.clear();
                    mapView.getMap().getMapObjects().clear();
                    MainActivity.SetPlacesInMap(myPosition);
                    new asyncTaskGetGeoLocation(ma, myPosition, MainActivity.tf).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
                dialog.dismiss();
            }
        });
        return placeInfo.create();
    }
}
