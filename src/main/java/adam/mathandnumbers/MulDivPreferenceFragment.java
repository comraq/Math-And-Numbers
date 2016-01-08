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
public class MulDivPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

  private PreferenceScreen mainScreen;
  private PreferenceCategory multiplicationCategory, divisionCategory;
  private SwitchPreference mulSw, divSw;
  private CheckBoxPreference mulCarryCheck, divRemainderCheck;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = super.onCreateView(inflater, container, savedInstanceState);
    onPreferenceChange(mulSw, mulSw.isChecked());
    onPreferenceChange(divSw, divSw.isChecked());
    return v;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    getActivity().setTitle(R.string.mul_div_pref_frag_title);

    mainScreen = getPreferenceManager().createPreferenceScreen(getActivity());
    setPreferenceScreen(mainScreen);

    multiplicationCategory = PreferenceFragmentUtilities.addCategory(getActivity(), mainScreen, R.string.settings_pref_frag_category_multiplication);
    divisionCategory = PreferenceFragmentUtilities.addCategory(getActivity(), mainScreen, R.string.settings_pref_frag_category_division);

    mulSw = PreferenceFragmentUtilities.initCategory(getActivity(), this, multiplicationCategory, R.string.sw_pref_mul_key);
    divSw = PreferenceFragmentUtilities.initCategory(getActivity(), this, divisionCategory, R.string.sw_pref_div_key);

    mulCarryCheck = PreferenceFragmentUtilities.initCheckPref(getActivity(), R.string.check_pref_multi_carry_key);
    divRemainderCheck = PreferenceFragmentUtilities.initCheckPref(getActivity(), R.string.check_pref_div_remainder_key);
  }

  @Override
  public boolean onPreferenceChange(Preference preference, Object newValue) {
    int keyResID = getResources().getIdentifier(preference.getKey(), "string", getActivity().getPackageName());
    boolean checked = (boolean) newValue;
    PreferenceCategory category;
    CheckBoxPreference checkPref;

    switch (keyResID) {
      case R.string.sw_pref_mul_key:
        category = multiplicationCategory;
        checkPref = mulCarryCheck;
        break;
      case R.string.sw_pref_div_key:
        category = divisionCategory;
        checkPref = divRemainderCheck;
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
