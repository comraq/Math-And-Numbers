package adam.mathandnumbers;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by adam on 2016-01-09.
 */
public class QuestionFragment extends Fragment {

  private static String OPERANDS = "questionOperands";
  private static String QUESTION_TYPE = "questionType";
  private static String NUM_OPERANDS = "numOperands";
  private static String NUM_DIGITS = "numDigits";

  private static int COLOUR_CORRECT = Color.GREEN;
  private static int COLOUR_INCORRECT = Color.RED;

  private Question question;
  private QuestionFragCommunicator comm;
  private RelativeLayout layoutContainer;

  public interface QuestionFragCommunicator {
    Question getNextQuestion();
    Question restoreQuestion(int ordinal);
    void showCheckDialog(boolean correct, String answer);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_question_long, container, false);
    layoutContainer = (RelativeLayout) v.findViewById(R.id.ques_frag_long_layout_container);
    return v;
  }

  public void showNextQuestion() {
    removePreviousViews();
    question = comm.getNextQuestion();
    getActivity().setTitle(question.getType().title());

    showOperandViews();
    updateOperator();
    showAnswerView();
  }

  public void checkAnswer() {
    EditText answerView = (EditText) getView().findViewById(R.id.ques_frag_answer);
    String answer = String.valueOf(question.getAnswer());
    try {
      if (Integer.parseInt(answerView.getText().toString()) == question.getAnswer()) {
        answerView.setBackgroundColor(COLOUR_CORRECT);
        comm.showCheckDialog(true, answer);
      } else {
        answerView.setBackgroundColor(COLOUR_INCORRECT);
        comm.showCheckDialog(false, answer);
      }
    } catch (NumberFormatException e) {
      answerView.setBackgroundColor(COLOUR_INCORRECT);
      comm.showCheckDialog(false, answer);
    }
  }

  private void showOperandViews() {
    TextView currOperand;
    RelativeLayout.LayoutParams params;
    TypedArray operandIds = getResources().obtainTypedArray(R.array.id_array);
    View prev = getView().findViewById(R.id.ques_frag_long_equation_line);

    int numOperands = question.getNumOperands();
    for (int i = 0; i < numOperands; ++i) {
      params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
      params.addRule(RelativeLayout.ALIGN_RIGHT, prev.getId());
      params.addRule(RelativeLayout.ABOVE, prev.getId());

      currOperand = new TextView(getActivity());
      currOperand.setId(operandIds.getResourceId(i, 0));
      currOperand.setText("" + question.getOperands().get(i));
      currOperand.setTextAppearance(getActivity(), android.R.style.TextAppearance_Large);
      currOperand.setLayoutParams(params);
      layoutContainer.addView(currOperand);
      prev = currOperand;
    }
    operandIds.recycle();
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

  private void showAnswerView() {
    View equationLine = getView().findViewById(R.id.ques_frag_long_equation_line);
    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    params.addRule(RelativeLayout.ALIGN_RIGHT, equationLine.getId());
    params.addRule(RelativeLayout.BELOW, equationLine.getId());

    EditText answerView = new EditText(getActivity());
    answerView.setId(R.id.ques_frag_answer);
    answerView.setHint(R.string.ques_frag_answer_hint);
    answerView.setGravity(Gravity.RIGHT);
    answerView.setInputType(InputType.TYPE_CLASS_NUMBER);
    answerView.setTextAppearance(getActivity(), android.R.style.TextAppearance_Large);
    answerView.setPadding(0,0,0,0);
    answerView.setBackgroundResource(0); //Removes background resource
    answerView.setLayoutParams(params);
    layoutContainer.addView(answerView);
  }

  private void removePreviousViews() {
    if (layoutContainer.getChildCount() > 2) {
      layoutContainer.removeView(getView().findViewById(R.id.ques_frag_answer));

      int numOperands = question.getNumOperands();
      TypedArray operandsId = getResources().obtainTypedArray(R.array.id_array);
      for (int i = 0; i < numOperands; ++i) {
        layoutContainer.removeView(getView().findViewById(operandsId.getResourceId(i, 0)));
      }
      operandsId.recycle();
    }
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    if (savedInstanceState == null) {
      showNextQuestion();
    } else {
      question = comm.restoreQuestion(savedInstanceState.getInt(QUESTION_TYPE));
      question.setOperands(savedInstanceState.getIntegerArrayList(OPERANDS));
      question.setNumOperands(savedInstanceState.getInt(NUM_OPERANDS));
      question.setNumDigits(savedInstanceState.getInt(NUM_DIGITS));

      getActivity().setTitle(question.getType().title());

      removePreviousViews();
      showOperandViews();
      updateOperator();
      showAnswerView();
    }

  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);

    outState.putIntegerArrayList(OPERANDS, question.getOperands());
    outState.putInt(QUESTION_TYPE, question.getType().ordinal());
    outState.putInt(NUM_OPERANDS, question.getNumOperands());
    outState.putInt(NUM_DIGITS, question.getNumDigits());
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
