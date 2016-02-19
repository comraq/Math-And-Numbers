package adam.mathandnumbers;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import adam.mathandnumbers.QuestionFragment.QuestionFragCommunicator;
import adam.mathandnumbers.ButtonPanelFragment.ButtonPanelFragCommunicator;

/**
 * Created by adam on 2016-01-08.
 */
public class QuestionActivity extends AppCompatActivity implements QuestionFragCommunicator, ButtonPanelFragCommunicator, CustomDialogFragment.CustomDialogListener {

  //private static final String QUES_FRAG_TAG = "ques_frag_tag";
  //private static final String BUTTON_PANE_TAG = "button_pane_tag";

  private QuestionBank questionBank;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_question);

    questionBank = new QuestionBank(this, getSharedPreferences(MainActivity.DEFAULT_PREFERENCES, MODE_PRIVATE));

    if (savedInstanceState == null) {
      FragmentTransaction ft = getFragmentManager().beginTransaction();
      ft.replace(R.id.question_act_ques_frag_container, new QuestionFragment());//, QUES_FRAG_TAG);
      ft.replace(R.id.question_act_button_panel_frag_container, new ButtonPanelFragment());//, BUTTON_PANE_TAG);
      ft.commit();
    }
  }

  @Override
  public Question getNextQuestion() { return questionBank.getNextQuestion(); }

  @Override
  public Question restoreQuestion(int ordinal) {
    return questionBank.restoreQuestion(ordinal);
  }

  @Override
  public void showCheckDialog(boolean correct) {
    if (correct) {
      CustomDialogFragment dialogFrag = CustomDialogFragment.newInstance(this, R.string.dialog_correct_title, CustomDialogFragment.NULL_ID, R.string.dialog_button_next_question, R.string.dialog_button_close);
      dialogFrag.show(getFragmentManager(), "Answer Correct Dialog");
    } else {

    }
  }

  @Override
  public void checkAnswer() {
    QuestionFragment qFrag = (QuestionFragment) getFragmentManager().findFragmentById(R.id.question_act_ques_frag_container);
    qFrag.checkAnswer();
  }

  @Override
  public void showNextQuestion() {
    QuestionFragment qFrag = (QuestionFragment) getFragmentManager().findFragmentById(R.id.question_act_ques_frag_container);
    qFrag.showNextQuestion();
  }

  @Override
  public void doNegClick() { showNextQuestion(); }
}
