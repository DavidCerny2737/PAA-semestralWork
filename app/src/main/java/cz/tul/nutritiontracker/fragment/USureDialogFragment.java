package cz.tul.nutritiontracker.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import cz.tul.nutritiontracker.R;

public class USureDialogFragment extends DialogFragment {

    public interface NoticeDialogListenerUSure {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    NoticeDialogListenerUSure listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof NoticeDialogListenerUSure)
            this.listener = (USureDialogFragment.NoticeDialogListenerUSure) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(getString(R.string.u_sure_dialog_message))
                .setTitle(getString(R.string.u_sure_dialog_title))
                .setPositiveButton(R.string.u_sure_dialog_confirm, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogPositiveClick(USureDialogFragment.this);
                    }
                })

                .setNegativeButton(R.string.u_sure_dialog_reject, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogNegativeClick(USureDialogFragment.this);
                    }
                });

        return builder.create();

    }

}
