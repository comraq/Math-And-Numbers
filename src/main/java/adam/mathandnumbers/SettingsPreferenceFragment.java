package adam.mathandnumbers;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;

/**
 * Created by adam on 2016-01-03.
 */
public class SettingsPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

  private Preference addCarryCheck;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Load the preferences from an XML resource
    addPreferencesFromResource(R.xml.preferences);
    getActivity().setTitle(R.string.settings_pref_frag_title);

    getPreferenceManager().findPreference(getString(R.string.sw_pref_add)).setOnPreferenceChangeListener(this);
    addCarryCheck = getPreferenceManager().findPreference(getString(R.string.check_pref_add_carry));
  }

  @Override
  public boolean onPreferenceChange(Preference preference, Object newValue) {
    switch (preference.getTitleRes()) {
      case R.string.sw_pref_add_title:
        if (((SwitchPreference) preference).isChecked()) {
          getPreferenceScreen().addPreference(addCarryCheck);
        } else {
          getPreferenceScreen().removePreference(addCarryCheck);
        }
      default:
        //Nothing
    }
    return true;
  }
}
