package adam.mathandnumbers;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

/**
 * Created by adam on 2016-01-03.
 */
public class CustomDialogPreference extends DialogPreference {
  public CustomDialogPreference(Context context, AttributeSet attrs) {
    super(context, attrs);

    //setDialogLayoutResource(R.xml.diag_pref);
  }
}
