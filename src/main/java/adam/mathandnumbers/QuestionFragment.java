package adam.mathandnumbers;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import adam.mathandnumbers.QuestionBank.QuestionType;
import adam.mathandnumbers.QuestionBank.QuestionOptions;

/**
 * Created by adam on 2016-01-09.
 */
public class QuestionFragment extends Fragment implements View.OnClickListener {

  private Question question;
  private QuestionCommunicator comm;
  private TextView questionTextView;
  private Button nextButton;

  @Override
  public void onClick(View v) {
    switch(v.getId()) {
      case R.id.ques_frag_button_next_question:
        showNextQuestion();
        break;
      default:
        //Do nothing
    }
  }

  public interface QuestionCommunicator {
    Question getNextQuestion();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_question, container, false);
    questionTextView = (TextView) v.findViewById(R.id.ques_frag_answer);
    nextButton = (Button) v.findViewById(R.id.ques_frag_button_next_question);
    nextButton.setOnClickListener(this);
    return v;
  }

  private void showNextQuestion() {
    question = comm.getNextQuestion();
    getActivity().setTitle(question.getType().title());
    if (question.getType() == QuestionType.DIVISION) {
      questionTextView.setText("" + question.checkOptions(QuestionOptions.REMAINDER));
    } else {
      questionTextView.setText("" + question.checkOptions(QuestionOptions.CARRY));
    }
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    try {
      comm = (QuestionCommunicator) activity;
    } catch (ClassCastException e) {
      throw new ClassCastException(comm.toString() + " must implement QuestionCommunicator");
    }
  }
}
