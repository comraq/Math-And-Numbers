package adam.mathandnumbers;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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
public class QuestionFragment extends Fragment implements TextWatcher {

  private static String OPERANDS = "questionOperands";
  private static String QUESTION_TYPE = "questionType";
  private static String NUM_OPERANDS = "numOperands";
  private static String NUM_DIGITS = "numDigits";

  private static int COLOUR_CORRECT = Color.GREEN;
  private static int COLOUR_INCORRECT = Color.RED;

  private Question question;
  private QuestionFragCommunicator comm;
  private RelativeLayout layoutContainer;
  private EditText answerView;

  private int textBeforeLength;

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    textBeforeLength = s.length();
  }

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {
    // Nothing
  }

  @Override
  public void afterTextChanged(Editable s) {
    if (textBeforeLength > s.length() || question.getType() == QuestionBank.QuestionType.DIVISION)
      return;

    if (answerView != null && answerView.hasFocus())
      answerView.setSelection(0);
  }

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
    EditText remainderView = (EditText) getView().findViewById(R.id.ques_frag_remainder_view);
    String answer = String.valueOf(question.getAnswer());
    boolean remainder = question.checkOptions(QuestionBank.QuestionOptions.REMAINDER);
    if (remainder)
      answer +=  " Remainder: " + String.valueOf(question.getAnswerRemainder());

    try {
      if (Integer.parseInt(answerView.getText().toString()) != question.getAnswer()
          || (remainder && Integer.parseInt(remainderView.getText().toString())
                           != question.getAnswerRemainder())) {
        answerView.setBackgroundColor(COLOUR_INCORRECT);
        if (remainder)
          remainderView.setBackgroundColor(COLOUR_INCORRECT);
        comm.showCheckDialog(false, answer);
      } else {
        answerView.setBackgroundColor(COLOUR_CORRECT);
        if (remainder)
          remainderView.setBackgroundColor(COLOUR_CORRECT);
        comm.showCheckDialog(true, answer);
      }
    } catch (NumberFormatException e) {
      answerView.setBackgroundColor(COLOUR_INCORRECT);
      if (remainder)
        remainderView.setBackgroundColor(COLOUR_INCORRECT);
      comm.showCheckDialog(false, answer);
    }
  }

  private void showOperandViews() {
    RelativeLayout.LayoutParams params;
    TypedArray operandIds = getResources().obtainTypedArray(R.array.id_array);
    View prev = getView().findViewById(R.id.ques_frag_long_equation_line);

    int numOperands = question.getNumOperands();
    if (question.getType() != QuestionBank.QuestionType.DIVISION) {
      TextView currOperand;
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
    } else {
      params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
      params.addRule(RelativeLayout.ALIGN_RIGHT, prev.getId());
      params.addRule(RelativeLayout.BELOW, prev.getId());

      TextView dividend = new TextView(getActivity());
      dividend.setId(operandIds.getResourceId(1, 0));
      dividend.setText("" + question.getOperands().get(1));
      dividend.setTextAppearance(getActivity(), android.R.style.TextAppearance_Large);
      dividend.setLayoutParams(params);
      layoutContainer.addView(dividend);

      params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
      params.addRule(RelativeLayout.LEFT_OF, R.id.ques_frag_operator);
      params.addRule(RelativeLayout.BELOW, prev.getId());

      TextView divisor = new TextView(getActivity());
      divisor.setId(operandIds.getResourceId(0, 0));
      divisor.setText("" + question.getOperands().get(0));
      divisor.setTextAppearance(getActivity(), android.R.style.TextAppearance_Large);
      divisor.setLayoutParams(params);
      layoutContainer.addView(divisor);
    }
    operandIds.recycle();
  }

  private void updateOperator() {
    View equationLine = getView().findViewById(R.id.ques_frag_long_equation_line);

    View operatorView = null;
    RelativeLayout.LayoutParams params = null;
    if (question.getType() != QuestionBank.QuestionType.DIVISION) {
      operatorView = new TextView(getActivity());
      params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);
      params.addRule(RelativeLayout.ALIGN_LEFT, equationLine.getId());
      params.addRule(RelativeLayout.ABOVE, equationLine.getId());

      ((TextView) operatorView).setGravity(Gravity.LEFT);
      ((TextView) operatorView).setTextAppearance(getActivity(), android.R.style.TextAppearance_Large);
      switch (question.getType()) {
        case ADDITION:
          ((TextView) operatorView).setText("\u002B"); //Unicode for Addition Symbol
          break;
        case SUBTRACTION:
          ((TextView) operatorView).setText("\u2212"); //Unicode for Subtraction Symbol
          break;
        case MULTIPLICATION:
          ((TextView) operatorView).setText("\u00D7"); //Unicode for Multiplication Symbol
          break;
        default:
          ((TextView) operatorView).setText(""); //Empty String
      }
    } else {
      operatorView = new QuarterCircleView(getActivity());

      float scale = getResources().getDisplayMetrics().density;
      params = new RelativeLayout.LayoutParams((int) (40 * scale), (int) (65 * scale));
      params.addRule(RelativeLayout.LEFT_OF, equationLine.getId());
      params.addRule(RelativeLayout.CENTER_VERTICAL);
    }
    operatorView.setId(R.id.ques_frag_operator);
    operatorView.setLayoutParams(params);
    layoutContainer.addView(operatorView);
  }

  private void showAnswerView() {
    View equationLine = getView().findViewById(R.id.ques_frag_long_equation_line);
    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    params.addRule(RelativeLayout.ALIGN_RIGHT, equationLine.getId());
    if (question.getType() != QuestionBank.QuestionType.DIVISION)
      params.addRule(RelativeLayout.BELOW, equationLine.getId());
    else
      params.addRule(RelativeLayout.ABOVE, equationLine.getId());

    answerView = new EditText(getActivity());
    answerView.setId(R.id.ques_frag_answer);
    answerView.setHint(R.string.ques_frag_answer_hint);
    answerView.setGravity(Gravity.RIGHT);
    answerView.setInputType(InputType.TYPE_CLASS_NUMBER);
    answerView.setTextAppearance(getActivity(), android.R.style.TextAppearance_Large);
    answerView.setPadding(0, 0, 0, 0);
    answerView.setBackgroundResource(0); //Removes background resource
    answerView.setLayoutParams(params);
    layoutContainer.addView(answerView);
    answerView.addTextChangedListener(this);

    if (question != null && question.checkOptions(QuestionBank.QuestionOptions.REMAINDER)) {
      params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);
      params.addRule(RelativeLayout.ALIGN_BOTTOM, answerView.getId());
      params.addRule(RelativeLayout.RIGHT_OF, answerView.getId());

      TextView remainderLabel = new TextView(getActivity());
      remainderLabel.setId(R.id.ques_frag_remainder_label);
      remainderLabel.setTextAppearance(getActivity(), android.R.style.TextAppearance_Large);
      remainderLabel.setText(R.string.ques_frag_remainder_label);
      remainderLabel.setLayoutParams(params);
      layoutContainer.addView(remainderLabel);

      params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);
      params.addRule(RelativeLayout.ALIGN_BOTTOM, remainderLabel.getId());
      params.addRule(RelativeLayout.RIGHT_OF, remainderLabel.getId());

      EditText remainderView = new EditText(getActivity());
      remainderView.setId(R.id.ques_frag_remainder_view);
      remainderView.setHint(R.string.ques_frag_remainder_view_hint);
      remainderView.setGravity(Gravity.LEFT);
      remainderView.setInputType(InputType.TYPE_CLASS_NUMBER);
      remainderView.setTextAppearance(getActivity(), android.R.style.TextAppearance_Large);
      remainderView.setPadding(0, 0, 0, 0);
      remainderView.setBackgroundResource(0); //Removes background resource
      remainderView.setLayoutParams(params);
      layoutContainer.addView(remainderView);

      remainderView.addTextChangedListener(this);
    }
  }

  private void removePreviousViews() {
    if (layoutContainer.getChildCount() > 2) {
      layoutContainer.removeView(getView().findViewById(R.id.ques_frag_answer));
      layoutContainer.removeView(getView().findViewById(R.id.ques_frag_operator));
      if (getView().findViewById(R.id.ques_frag_remainder_label) != null)
        layoutContainer.removeView(getView().findViewById(R.id.ques_frag_remainder_label));
      if (getView().findViewById(R.id.ques_frag_remainder_view) != null)
        layoutContainer.removeView(getView().findViewById(R.id.ques_frag_remainder_view));

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
