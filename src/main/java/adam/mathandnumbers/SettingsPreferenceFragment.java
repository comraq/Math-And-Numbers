package adam.mathandnumbers;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by adam on 2016-01-03.
 */
public class SettingsPreferenceFragment extends PreferenceFragment {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Load the preferences from an XML resource
    addPreferencesFromResource(R.xml.preferences);
    getActivity().setTitle(R.string.settings_pref_frag_title);
  }
}
