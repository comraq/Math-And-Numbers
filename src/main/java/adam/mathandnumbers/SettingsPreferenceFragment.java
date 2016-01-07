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
 * Created by adam on 2016-01-03.
 */
public class SettingsPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

  private PreferenceScreen mainScreen;

  private PreferenceCategory additionCategory, subtractionCategory, multiplicationCategory, divisionCategory;

  private Preference addCarryCheck, subCarryCheck, mulCarryCheck, divRemainderCheck;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    getActivity().setTitle(R.string.settings_pref_frag_title);
    mainScreen = getPreferenceManager().createPreferenceScreen(getActivity());
    setPreferenceScreen(mainScreen);

    additionCategory = addCategory(R.string.settings_pref_frag_category_addition);
    subtractionCategory = addCategory(R.string.settings_pref_frag_category_subtraction);
    multiplicationCategory = addCategory(R.string.settings_pref_frag_category_multiplication);
    divisionCategory = addCategory(R.string.settings_pref_frag_category_division);

    initCategory(additionCategory, R.string.sw_pref_add);
    initCategory(subtractionCategory, R.string.sw_pref_sub);
    initCategory(multiplicationCategory, R.string.sw_pref_mul);
    initCategory(divisionCategory, R.string.sw_pref_div);

    initCheckPrefs();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = super.onCreateView(inflater, container, savedInstanceState);
    //SwitchPreference addSw, subSw, mulSw, divSw;
    onPreferenceChange(additionCategory.findPreference(getString(R.string.sw_pref_add)), null);
    onPreferenceChange(subtractionCategory.findPreference(getString(R.string.sw_pref_sub)), null);
    onPreferenceChange(multiplicationCategory.findPreference(getString(R.string.sw_pref_mul)), null);
    onPreferenceChange(divisionCategory.findPreference(getString(R.string.sw_pref_div)), null);

    return v;
  }

  @Override
  public boolean onPreferenceChange(Preference preference, Object newValue) {
    int keyResID = getResources().getIdentifier(preference.getKey(), "string", getActivity().getPackageName());

    switch (keyResID) {
      case R.string.sw_pref_add:
        if (!((SwitchPreference) preference).isChecked()) {
          additionCategory.addPreference(addCarryCheck);
        } else {
          additionCategory.removePreference(addCarryCheck);
        }
        break;
      case R.string.sw_pref_sub:
        if (!((SwitchPreference) preference).isChecked()) {
          subtractionCategory.addPreference(subCarryCheck);
        } else {
          subtractionCategory.removePreference(subCarryCheck);
        }
        break;
      case R.string.sw_pref_mul:
        if (!((SwitchPreference) preference).isChecked()) {
          multiplicationCategory.addPreference(mulCarryCheck);
        } else {
          multiplicationCategory.removePreference(mulCarryCheck);
        }
        break;
      case R.string.sw_pref_div:
        if (!((SwitchPreference) preference).isChecked()) {
          divisionCategory.addPreference(divRemainderCheck);
        } else {
          divisionCategory.removePreference(divRemainderCheck);
        }
        break;
      default:
        //Nothing
    }
    return true;
  }

  private PreferenceCategory addCategory(int titleId) {
    PreferenceCategory category = new PreferenceCategory(getActivity());
    category.setTitle(titleId);
    mainScreen.addPreference(category);

    return category;
  }

  private void initCategory(PreferenceCategory category, int keyId) {
    SwitchPreference sw = new SwitchPreference(getActivity());
    sw.setKey(getString(keyId));
    sw.setTitle(R.string.sw_pref_title);
    sw.setOnPreferenceChangeListener(this);
    category.addPreference(sw);
  }

  private void initCheckPrefs() {
    addCarryCheck = new CheckBoxPreference(getActivity());
    addCarryCheck.setKey(getString(R.string.check_pref_add_carry));
    addCarryCheck.setTitle(R.string.check_pref_carry);

    subCarryCheck = new CheckBoxPreference(getActivity());
    subCarryCheck.setKey(getString(R.string.check_pref_sub_carry));
    subCarryCheck.setTitle(R.string.check_pref_carry);

    mulCarryCheck = new CheckBoxPreference(getActivity());
    mulCarryCheck.setKey(getString(R.string.check_pref_multi_carry));
    mulCarryCheck.setTitle(R.string.check_pref_carry);

    divRemainderCheck = new CheckBoxPreference(getActivity());
    divRemainderCheck.setKey(getString(R.string.check_pref_div_remainder));
    divRemainderCheck.setTitle(R.string.check_pref_remainder);
  }
}
