package adam.mathandnumbers;

import android.content.Context;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;

/**
 * Created by adam on 2016-01-07.
 */
public class PreferenceFragmentUtilities {

  public static PreferenceCategory addCategory(Context context, PreferenceScreen prSc, int titleId) {
    PreferenceCategory category = new PreferenceCategory(context);
    category.setTitle(titleId);
    prSc.addPreference(category);
    return category;
  }

  public static SwitchPreference initCategory(Context context, Preference.OnPreferenceChangeListener listener, PreferenceCategory category, int keyId) {
    SwitchPreference sw = new SwitchPreference(context);
    sw.setKey(context.getString(keyId));
    sw.setTitle(R.string.sw_pref_title);
    sw.setOnPreferenceChangeListener(listener);
    category.addPreference(sw);
    return sw;
  }

  public static CheckBoxPreference initCheckPref(Context context, int keyId) {
    CheckBoxPreference checkPref = new CheckBoxPreference(context);
    checkPref.setKey(context.getString(keyId));
    if (keyId == R.string.check_pref_div_remainder_key) {
      checkPref.setTitle(R.string.check_pref_remainder_title);
    } else {
      checkPref.setTitle(R.string.check_pref_carry_title);
    }
    return checkPref;
  }
}
