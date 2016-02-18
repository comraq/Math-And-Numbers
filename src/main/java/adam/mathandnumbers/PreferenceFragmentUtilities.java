package adam.mathandnumbers;

import android.content.Context;
import android.preference.CheckBoxPreference;
import android.preference.DialogPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

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

  public static DialogPreference addDiagPref(Context context, PreferenceScreen prSc, int titleId) {
    XmlPullParser parser = context.getResources().getXml(R.xml.diag_pref);
    DialogPreference diagPref = new CustomDialogPreference(context, Xml.asAttributeSet(parser));
    diagPref.setTitle(titleId);
    prSc.addPreference(diagPref);
    return diagPref;
  }

  public static ListPreference addListPref(Context context, int titleId, int summaryId, int diagTitleId, int keyId, int entriesId) {
    ListPreference listPref = new ListPreference(context);
    listPref.setTitle(titleId);
    listPref.setSummary(summaryId);
    listPref.setDialogTitle(diagTitleId);
    listPref.setKey(context.getResources().getString(keyId));
    listPref.setEntries(entriesId);
    listPref.setEntryValues(entriesId);
    return listPref;
  }

  public static SwitchPreference initCategory(Context context, Preference.OnPreferenceChangeListener listener, PreferenceCategory category, String key) {
    SwitchPreference sw = new SwitchPreference(context);
    sw.setKey(key);
    sw.setTitle(R.string.sw_pref_title);
    sw.setPersistent(true);
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
    checkPref.setPersistent(true);
    return checkPref;
  }
}
