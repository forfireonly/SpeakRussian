package com.annandroidapps.speakrussian;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class UpgradeAvailableDialog extends DialogFragment {

    public static final String ownGooglePlayLink="market://details?id=com.annandroidapps.speakrussian";
    public static final String ownWebLink="https://play.google.com/store/apps/details?id=com.annandroidapps.speakrussian";
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.upgrade_dialog)
                .setPositiveButton(R.string.start_download, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // download!
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ownGooglePlayLink)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ownWebLink)));
                        }

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
    public static String TAG = "DownloadConfirmationDialog";
}
