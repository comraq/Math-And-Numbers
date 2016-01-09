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
    initPreferences();
  }

  private void initPreferences() {
    multiplicationCategory = PreferenceFragmentUtilities.addCategory(getActivity(), mainScreen, R.string.settings_pref_frag_category_multiplication);
    divisionCategory = PreferenceFragmentUtilities.addCategory(getActivity(), mainScreen, R.string.settings_pref_frag_category_division);

    mulSw = PreferenceFragmentUtilities.initCategory(getActivity(), this, multiplicationCategory, QuestionType.MULTIPLICATION.toString());
    divSw = PreferenceFragmentUtilities.initCategory(getActivity(), this, divisionCategory, QuestionType.DIVISION.toString());

    mulCarryCheck = PreferenceFragmentUtilities.initCheckPref(getActivity(), R.string.check_pref_multi_carry_key);
    divRemainderCheck = PreferenceFragmentUtilities.initCheckPref(getActivity(), R.string.check_pref_div_remainder_key);

    setDefaultValues();
  }

  private void setDefaultValues() {
    SharedPreferences pref = getActivity().getSharedPreferences(MainActivity.DEFAULT_PREFERENCES, Context.MODE_PRIVATE);

    if (!pref.contains(QuestionType.MULTIPLICATION.toString())) mulSw.setChecked(false);
    if (!pref.contains(QuestionType.DIVISION.toString())) divSw.setChecked(false);
    if (!pref.contains(getActivity().getString(R.string.check_pref_multi_carry_key))) mulCarryCheck.setChecked(false);
    if (!pref.contains(getActivity().getString(R.string.check_pref_div_remainder_key))) divRemainderCheck.setChecked(false);
  }

  @Override
  public boolean onPreferenceChange(Preference preference, Object newValue) {
    String key = preference.getKey();
    boolean checked = (boolean) newValue;
    PreferenceCategory category;
    CheckBoxPreference checkPref;

    if (key == QuestionType.MULTIPLICATION.toString()) {
      category = multiplicationCategory;
      checkPref = mulCarryCheck;
    } else if (key == QuestionType.DIVISION.toString()) {
      category = divisionCategory;
      checkPref = divRemainderCheck;
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
