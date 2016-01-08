package adam.mathandnumbers;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by adam on 2016-01-07.
 */
public class AddSubPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

  private PreferenceScreen mainScreen;
  private PreferenceCategory additionCategory, subtractionCategory;
  private SwitchPreference addSw, subSw;
  private CheckBoxPreference addCarryCheck, subCarryCheck;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = super.onCreateView(inflater, container, savedInstanceState);
    onPreferenceChange(addSw, addSw.isChecked());
    onPreferenceChange(subSw, subSw.isChecked());
    return v;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    getActivity().setTitle(R.string.add_sub_pref_frag_title);

    mainScreen = getPreferenceManager().createPreferenceScreen(getActivity());
    setPreferenceScreen(mainScreen);

    additionCategory = PreferenceFragmentUtilities.addCategory(getActivity(), mainScreen, R.string.settings_pref_frag_category_addition);
    subtractionCategory = PreferenceFragmentUtilities.addCategory(getActivity(), mainScreen, R.string.settings_pref_frag_category_subtraction);

    addSw = PreferenceFragmentUtilities.initCategory(getActivity(), this, additionCategory, R.string.sw_pref_add_key);
    subSw = PreferenceFragmentUtilities.initCategory(getActivity(), this, subtractionCategory, R.string.sw_pref_sub_key);

    addCarryCheck = PreferenceFragmentUtilities.initCheckPref(getActivity(), R.string.check_pref_add_carry_key);
    subCarryCheck = PreferenceFragmentUtilities.initCheckPref(getActivity(), R.string.check_pref_sub_carry_key);
  }

  @Override
  public boolean onPreferenceChange(Preference preference, Object newValue) {
    int keyResID = getResources().getIdentifier(preference.getKey(), "string", getActivity().getPackageName());
    boolean checked = (boolean) newValue;
    PreferenceCategory category;
    CheckBoxPreference checkPref;

    switch (keyResID) {
      case R.string.sw_pref_add_key:
        category = additionCategory;
        checkPref = addCarryCheck;
        break;
      case R.string.sw_pref_sub_key:
        category = subtractionCategory;
        checkPref = subCarryCheck;
        break;
      default:
        return false;
    }
    if (checked) {
      category.addPreference(checkPref);
    } else {
      category.removePreference(checkPref);
    }
    return true;
  }
}
