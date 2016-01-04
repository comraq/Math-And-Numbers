package adam.mathandnumbers;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by adam on 2016-01-03.
 */
public class MainFragment extends Fragment implements View.OnClickListener {

  @Override
  public void onClick(View v) {
    switch(v.getId()) {
      case R.id.main_act_button_start:
        break;
      case R.id.main_act_button_settings:
        ((MainActivity) getActivity()).showSettingsPreferenceFragment();
        break;
      case R.id.main_act_button_exit:
        ((MainActivity) getActivity()).promptQuit();
        break;
      default:
        //Nothing
    }
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_main, container, false);
    initialize(v);
    getActivity().setTitle(R.string.app_name);
    return v;
  }

  private void initialize(View v) {
    v.findViewById(R.id.main_act_button_start).setOnClickListener(this);
    v.findViewById(R.id.main_act_button_settings).setOnClickListener(this);
    v.findViewById(R.id.main_act_button_exit).setOnClickListener(this);
  }
}
