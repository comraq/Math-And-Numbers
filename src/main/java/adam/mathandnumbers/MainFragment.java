package adam.mathandnumbers;

import android.app.Activity;
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

  public interface MainCommunicator {
    void settingsButtonClicked();
    void exitButtonClick();
  }

  MainCommunicator comm;

  @Override
  public void onClick(View v) {
    switch(v.getId()) {
      case R.id.main_act_button_start:
        break;
      case R.id.main_act_button_settings:
        comm.settingsButtonClicked();
        break;
      case R.id.main_act_button_exit:
        comm.exitButtonClick();
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

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    try {
      comm = (MainCommunicator) activity;
    } catch (ClassCastException e) {
      throw new ClassCastException(comm.toString() + " must implement MainCommunicator");
    }
  }
}
