package adam.mathandnumbers;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

/**
 * My custom dialog fragment class for generating multipurpose dialogs
 */
public class CustomDialogFragment extends DialogFragment {

  private static final String TITLE = "title";
  private static final String MESSAGE = "message";
  private static final String NEG_BUTTON = "neg";
  private static final String POS_BUTTON = "pos";
  private static final int NULL_ID = 0;

  private CustomDialogListener dialogListener;

  interface CustomDialogListener { void doNegClick(); }

  public static CustomDialogFragment newInstance(Context context, int titleId, int messageId, int negButtonId, int posButtonId) {
    CustomDialogFragment f = new CustomDialogFragment();
    Bundle b = new Bundle();

    if (titleId != NULL_ID) b.putInt(TITLE, titleId);
    if (messageId != NULL_ID) b.putInt(MESSAGE, messageId);
    if (negButtonId != NULL_ID) b.putInt(NEG_BUTTON, negButtonId);
    if (posButtonId != NULL_ID) b.putInt(POS_BUTTON, posButtonId);

    f.setArguments(b);
    return f;
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

    if (getArguments().containsKey(TITLE)) builder.setTitle(getArguments().getInt(TITLE));
    if (getArguments().containsKey(MESSAGE)) builder.setMessage(getArguments().getInt(MESSAGE));
    if (getArguments().containsKey(NEG_BUTTON))
      builder.setNegativeButton(getArguments().getInt(NEG_BUTTON), new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          dialogListener.doNegClick();
        }
      });
    if (getArguments().containsKey(POS_BUTTON))
      builder.setPositiveButton(getArguments().getInt(POS_BUTTON), new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          dismiss();
        }
      });

    //LayoutInflater i = getActivity().getLayoutInflater();
    //View dialogView = i.inflate(R.layout.fragment_generate_dialog, null);
    //builder.setView(dialogView);
    return builder.create();
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    try {
      dialogListener = (CustomDialogListener) activity;
    } catch (ClassCastException e) {
      throw new ClassCastException(dialogListener.toString() + " must implement CustomDialogListener");
    }
  }
}
