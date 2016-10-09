package fi.jamk.layoutchanges2;

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
 * Created by Leo on 05.10.2016.
 */
public class TextEntryDialogFragment extends DialogFragment {

    public interface TextEntryDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, String text);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    TextEntryDialogListener mListener;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mListener = (TextEntryDialogListener) activity;
        }catch(ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement TextEntryDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.textentry_dialog, null);
        builder.setView(dialogView)
                .setTitle("Give a new text")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText editText = (EditText)dialogView.findViewById(R.id.editText);
                        String text = editText.getText().toString();
                        mListener.onDialogPositiveClick(TextEntryDialogFragment.this,text);
                    }
                })
                .setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNegativeClick(TextEntryDialogFragment.this);
                    }
                });
        return builder.create();
    }
}