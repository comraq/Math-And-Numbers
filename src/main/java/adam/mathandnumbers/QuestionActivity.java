package adam.mathandnumbers;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import adam.mathandnumbers.QuestionFragment.QuestionFragCommunicator;
import adam.mathandnumbers.ButtonPanelFragment.ButtonPanelFragCommunicator;

/**
 * Created by adam on 2016-01-08.
 */
public class QuestionActivity extends AppCompatActivity implements QuestionFragCommunicator, ButtonPanelFragCommunicator {

  //private static final String QUES_FRAG_TAG = "ques_frag_tag";
  //private static final String BUTTON_PANE_TAG = "button_pane_tag";

  private QuestionBank questionBank;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_question);
    questionBank = new QuestionBank(this, getSharedPreferences(MainActivity.DEFAULT_PREFERENCES, MODE_PRIVATE));

    FragmentTransaction ft = getFragmentManager().beginTransaction();
    ft.replace(R.id.question_act_ques_frag_container, new QuestionFragment());//, QUES_FRAG_TAG);
    ft.replace(R.id.question_act_button_panel_frag_container, new ButtonPanelFragment());//, BUTTON_PANE_TAG);
    ft.commit();
  }

  @Override
  public Question getNextQuestion() { return questionBank.getNextQuestion(); }

  @Override
  public void checkAnswer() {

  }

  @Override
  public void showNextQuestion() {
    QuestionFragment qFrag = (QuestionFragment) getFragmentManager().findFragmentById(R.id.question_act_ques_frag_container);
    qFrag.showNextQuestion();
  }
}
