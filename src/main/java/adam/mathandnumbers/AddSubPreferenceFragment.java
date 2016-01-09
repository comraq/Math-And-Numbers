package adam.mathandnumbers;

import android.content.Context;
import android.content.SharedPreferences;
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

import adam.mathandnumbers.QuestionBank.QuestionType;
import adam.mathandnumbers.QuestionBank.QuestionOptions;

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
    initPreferences();
  }

  private void initPreferences() {
    additionCategory = PreferenceFragmentUtilities.addCategory(getActivity(), mainScreen, R.string.settings_pref_frag_category_addition);
    subtractionCategory = PreferenceFragmentUtilities.addCategory(getActivity(), mainScreen, R.string.settings_pref_frag_category_subtraction);

    addSw = PreferenceFragmentUtilities.initCategory(getActivity(), this, additionCategory, QuestionType.ADDITION.toString());
    subSw = PreferenceFragmentUtilities.initCategory(getActivity(), this, subtractionCategory, QuestionType.SUBTRACTION.toString());

    addCarryCheck = PreferenceFragmentUtilities.initCheckPref(getActivity(), R.string.check_pref_add_carry_key);
    subCarryCheck = PreferenceFragmentUtilities.initCheckPref(getActivity(), R.string.check_pref_sub_carry_key);

    setDefaultValues();
  }

  private void setDefaultValues() {
    SharedPreferences pref = getActivity().getSharedPreferences(MainActivity.DEFAULT_PREFERENCES, Context.MODE_PRIVATE);

    if (!pref.contains(QuestionType.ADDITION.toString())) addSw.setChecked(true);
    if (!pref.contains(QuestionType.SUBTRACTION.toString())) subSw.setChecked(true);
    if (!pref.contains(getActivity().getString(R.string.check_pref_add_carry_key))) addCarryCheck.setChecked(false);
    if (!pref.contains(getActivity().getString(R.string.check_pref_sub_carry_key))) subCarryCheck.setChecked(false);
  }

  @Override
  public boolean onPreferenceChange(Preference preference, Object newValue) {
    String key = preference.getKey();
    boolean checked = (boolean) newValue;
    PreferenceCategory category;
    CheckBoxPreference checkPref;

    if (key == QuestionType.ADDITION.toString()) {
      category = additionCategory;
      checkPref = addCarryCheck;
    } else if (key == QuestionType.SUBTRACTION.toString()) {
      category = subtractionCategory;
      checkPref = subCarryCheck;
    } else {
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
