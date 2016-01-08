package adam.mathandnumbers;

import android.app.Activity;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by adam on 2016-01-03.
 */
public class SettingsPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

  private PreferenceScreen mainScreen;
  private PreferenceCategory wholeNumbersCategory;

  SettingsCommunicator comm;

  public interface SettingsCommunicator {
    void addSubClicked();
    void mulDivClicked();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    getActivity().setTitle(R.string.settings_pref_frag_title);

    mainScreen = getPreferenceManager().createPreferenceScreen(getActivity());
    setPreferenceScreen(mainScreen);

    wholeNumbersCategory = PreferenceFragmentUtilities.addCategory(getActivity(), mainScreen, R.string.settings_pref_frag_screen_whole_number_title);

    Preference addSubPref = new Preference(getActivity());
    addSubPref.setKey(getString(R.string.add_sub_pref_key));
    addSubPref.setTitle(R.string.add_sub_pref_title);
    addSubPref.setSummary(R.string.add_sub_pref_summary);
    addSubPref.setOnPreferenceClickListener(this);
    wholeNumbersCategory.addPreference(addSubPref);

    Preference mulDivPref = new Preference(getActivity());
    mulDivPref.setKey(getString(R.string.mul_div_pref_key));
    mulDivPref.setTitle(R.string.mul_div_pref_title);
    mulDivPref.setSummary(R.string.mul_div_pref_summary);
    mulDivPref.setOnPreferenceClickListener(this);
    wholeNumbersCategory.addPreference(mulDivPref);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = super.onCreateView(inflater, container, savedInstanceState);
    return v;
  }

  @Override
  public boolean onPreferenceClick(Preference preference) {
    switch (preference.getTitleRes()) {
      case R.string.add_sub_pref_title:
        comm.addSubClicked();
        break;
      case R.string.mul_div_pref_title:
        comm.mulDivClicked();
        break;
      default:
        return false;
    }
    return true;
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    try {
      comm = (SettingsCommunicator) activity;
    } catch (ClassCastException e) {
      throw new ClassCastException(comm.toString() + " must implement SettingsCommunicator");
    }
  }
}
