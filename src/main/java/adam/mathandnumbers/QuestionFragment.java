package adam.mathandnumbers;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import adam.mathandnumbers.QuestionBank.QuestionType;
import adam.mathandnumbers.QuestionBank.QuestionOptions;

/**
 * Created by adam on 2016-01-09.
 */
public class QuestionFragment extends Fragment {

  private Question question;
  private QuestionFragCommunicator comm;
  private EditText answerEditText;

  public interface QuestionFragCommunicator {
    Question getNextQuestion();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_question_long, container, false);
    //View v = inflater.inflate(R.layout.fragment_question_expression, container, false);
    answerEditText = (EditText) v.findViewById(R.id.ques_frag_answer);
    return v;
  }

  public void showNextQuestion() {
    question = comm.getNextQuestion();
    getActivity().setTitle(question.getType().title());

    //TODO: Need to update number of operand textviews as according to numOperands in the question
    showOperands();
    TextView operandOne = (TextView) getView().findViewById(R.id.ques_frag_operand_one);
    TextView operandTwo = (TextView) getView().findViewById(R.id.ques_frag_operand_two);
    operandOne.setText("" + question.getOperands().get(0));
    operandTwo.setText("" + question.getOperands().get(1));

    updateOperator();
  }

  private void showOperands() {
    int numOperands = question.getNumOperands();
    TextView currOperand;
    for (int i = 0; i < numOperands; ++i) {
      //currOperand = new TextView(getActivity());
    }
  }

  private void updateOperator() {
    TextView operator = (TextView) getActivity().findViewById(R.id.ques_frag_operator);
    switch (question.getType()) {
      case ADDITION:
        operator.setText("\u002B"); //Unicode for Addition Symbol
        break;
      case SUBTRACTION:
        operator.setText("\u2212"); //Unicode for Subtraction Symbol
        break;
      case MULTIPLICATION:
        operator.setText("\u00D7"); //Unicode for Multiplication Symbol
        break;
      case DIVISION:
        operator.setText("\u00F7"); //Unicode for Division Symbol
        break;
      default:
        //Do nothing
    }
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    showNextQuestion();
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    try {
      comm = (QuestionFragCommunicator) activity;
    } catch (ClassCastException e) {
      throw new ClassCastException(comm.toString() + " must implement QuestionFragCommunicator");
    }
  }
}
