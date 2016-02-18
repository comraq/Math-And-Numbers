package adam.mathandnumbers;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by adam on 2016-01-10.
 */
public class ButtonPanelFragment extends DialogFragment implements View.OnClickListener {

  private Button checkButton, nextButton;
  private ButtonPanelFragCommunicator comm;

  public interface ButtonPanelFragCommunicator {
    void checkAnswer();
    void showNextQuestion();
  }

  @Override
  public void onClick(View v) {
    switch(v.getId()) {
      case R.id.button_panel_frag_button_check:
        comm.checkAnswer();
        break;
      case R.id.button_panel_frag_button_next:
        comm.showNextQuestion();
        Log.i("qBank", "nextButton clicked");
        break;
      default:
        //Do nothing
    }
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_button_panel, container, false);

    checkButton = (Button) v.findViewById(R.id.button_panel_frag_button_check);
    nextButton = (Button) v.findViewById(R.id.button_panel_frag_button_next);
    checkButton.setOnClickListener(this);
    nextButton.setOnClickListener(this);
    return v;
  }


  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    try {
      comm = (ButtonPanelFragCommunicator) activity;
    } catch (ClassCastException e) {
      throw new ClassCastException(comm.toString() + " must implement ButtonPanelFragCommunicator");
    }
  }
}
