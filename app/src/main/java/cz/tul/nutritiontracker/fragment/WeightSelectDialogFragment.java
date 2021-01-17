package cz.tul.nutritiontracker.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import cz.tul.nutritiontracker.R;

public class WeightSelectDialogFragment extends DialogFragment {

    private String foodId;
    private EditText numberPicker;

    public interface NoticeDialogListenerWeightSelect {
        public void onDialogPositiveClick(DialogFragment dialog, int weight);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    NoticeDialogListenerWeightSelect noticeDialogListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof NoticeDialogListenerWeightSelect)
            this.noticeDialogListener = (NoticeDialogListenerWeightSelect) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.weight_dialog, null);
        numberPicker = (EditText) rootView.findViewById(R.id.weightPicker);

        builder.setView(rootView)
                .setTitle(getString(R.string.food_add_dialog_title))
                .setPositiveButton(R.string.food_add_dialog_confirm, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        int weight = Integer.parseInt(numberPicker.getText().toString());
                        noticeDialogListener.onDialogPositiveClick(WeightSelectDialogFragment.this, weight);
                    }
                })

                .setNegativeButton(R.string.food_add_dialog_reject, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        noticeDialogListener.onDialogNegativeClick(WeightSelectDialogFragment.this);
                        WeightSelectDialogFragment.this.getDialog().cancel();
                    }
                });

        return builder.create();

    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }
}
