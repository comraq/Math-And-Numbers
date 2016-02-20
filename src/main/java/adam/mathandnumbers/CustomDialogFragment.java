package adam.mathandnumbers;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import java.util.Map;

/**
 * My custom dialog fragment class for generating multipurpose dialogs
 */
public class CustomDialogFragment extends DialogFragment {

  public enum DialogKeys {
    TITLE,
    MESSAGE,
    NEG_BUTTON,
    POS_BUTTON
  }

  private CustomDialogListener dialogListener;

  interface CustomDialogListener { void doNegClick(String negButton); }

  public static CustomDialogFragment newInstance(Map<DialogKeys, String> mapBundle) {
    CustomDialogFragment f = new CustomDialogFragment();
    Bundle b = new Bundle();

    for (DialogKeys key: mapBundle.keySet())
      b.putCharSequence(key.toString(), mapBundle.get(key));

    f.setArguments(b);
    return f;
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

    for (DialogKeys key: DialogKeys.values()) {
      final String keyString = key.toString();
      if (getArguments().containsKey(keyString)) switch (key) {
        case TITLE:
          builder.setTitle(getArguments().getString(keyString));
          break;

        case MESSAGE:
          builder.setMessage(getArguments().getString(keyString));
          break;

        case NEG_BUTTON:
          builder.setNegativeButton(getArguments().getString(keyString), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              dialogListener.doNegClick(getArguments().getString(keyString));
            }
          });
          break;

        case POS_BUTTON:
          builder.setPositiveButton(getArguments().getString(keyString), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              dismiss();
            }
          });
          break;

        default:
          //Do Nothing!
      }
    }
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
