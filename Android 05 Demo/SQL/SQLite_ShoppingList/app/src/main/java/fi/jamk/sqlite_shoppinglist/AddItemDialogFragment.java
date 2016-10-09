package fi.jamk.sqlite_shoppinglist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Leo on 09.10.2016.
 */
public class AddItemDialogFragment extends DialogFragment {
    public interface DialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, String name, int score, float price);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    DialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (DialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement DialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.add_item, null);
        builder.setView(dialogView)
                .setTitle("Add a new Item")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText editName = (EditText) dialogView.findViewById(R.id.productName);
                        String name = editName.getText().toString();
                        EditText editScore = (EditText) dialogView.findViewById(R.id.count);
                        int score = Integer.valueOf(editScore.getText().toString());
                        EditText editPrice = (EditText) dialogView.findViewById(R.id.productItemPrice);
                        float price = Float.valueOf(editPrice.getText().toString());
                        mListener.onDialogPositiveClick(AddItemDialogFragment.this,name,score,price);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNegativeClick(AddItemDialogFragment.this);
                    }
                });
        return builder.create();
    }
}
