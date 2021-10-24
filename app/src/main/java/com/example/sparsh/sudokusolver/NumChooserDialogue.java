package com.example.sparsh.sudokusolver;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by sparsh on 5/11/18.
 */

public class NumChooserDialogue extends DialogFragment {
    String[] Nums = {"1","2","3","4","5","6","7","8","9","clear"};

    public static NumChooserDialogue newInstance(String title) {

        NumChooserDialogue frag = new NumChooserDialogue();

        Bundle args = new Bundle();

        args.putString("title", title);

        frag.setArguments(args);

        return frag;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose a Number")
                .setItems(Nums, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item


                    }
                });
        return builder.create();
    }
}
