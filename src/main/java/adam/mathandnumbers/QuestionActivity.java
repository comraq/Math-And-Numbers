package adam.mathandnumbers;

import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import adam.mathandnumbers.QuestionFragment.QuestionFragCommunicator;
import adam.mathandnumbers.ButtonPanelFragment.ButtonPanelFragCommunicator;

/**
 * Created by adam on 2016-01-08.
 */
public class QuestionActivity extends AppCompatActivity implements QuestionFragCommunicator, ButtonPanelFragCommunicator, CustomDialogFragment.CustomDialogListener {

  //private static final String QUES_FRAG_TAG = "ques_frag_tag";
  //private static final String BUTTON_PANE_TAG = "button_pane_tag";

  private QuestionBank questionBank;
  private String answer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_question);

    SharedPreferences pref = getSharedPreferences(MainActivity.DEFAULT_PREFERENCES, MODE_PRIVATE);
    File f = new File("/data/data/" + getPackageName() + "/shared_prefs/" + MainActivity.DEFAULT_PREFERENCES + ".xml");
    if (!f.exists()) {
      SharedPreferences.Editor editor = pref.edit();
      editor.putBoolean(QuestionBank.QuestionType.ADDITION.toString(), true);
      editor.putBoolean(QuestionBank.QuestionType.SUBTRACTION.toString(), true);
      editor.commit();
    }
    questionBank = new QuestionBank(this, pref);

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
  public void showCheckDialog(boolean correct, String answer) {
    this.answer = answer;
    Map<CustomDialogFragment.DialogKeys, String> mapBundle = new HashMap<>();
    mapBundle.put(CustomDialogFragment.DialogKeys.POS_BUTTON, getString(R.string.dialog_button_close));

    if (correct) {
      mapBundle.put(CustomDialogFragment.DialogKeys.TITLE, getString(R.string.dialog_correct_title));
      mapBundle.put(CustomDialogFragment.DialogKeys.NEG_BUTTON, getString(R.string.dialog_button_next_question));

      CustomDialogFragment dialogFrag = CustomDialogFragment.newInstance(mapBundle);
      dialogFrag.show(getFragmentManager(), "Answer Correct Dialog");
    } else {
      mapBundle.put(CustomDialogFragment.DialogKeys.TITLE, getString(R.string.dialog_incorrect_title));
      mapBundle.put(CustomDialogFragment.DialogKeys.NEG_BUTTON, getString(R.string.dialog_button_show_answer));

      CustomDialogFragment dialogFrag = CustomDialogFragment.newInstance(mapBundle);
      dialogFrag.show(getFragmentManager(), "Answer Incorrect Dialog");
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
  public void doNegClick(String negButton) {
    if (negButton.equals(getString(R.string.dialog_button_next_question)))
      showNextQuestion();
    else if (negButton.equals(getString(R.string.dialog_button_show_answer))) {
      Map<CustomDialogFragment.DialogKeys, String> mapBundle = new HashMap<>();
      mapBundle.put(CustomDialogFragment.DialogKeys.TITLE, getString(R.string.dialog_show_answer_title));
      mapBundle.put(CustomDialogFragment.DialogKeys.MESSAGE, answer);
      mapBundle.put(CustomDialogFragment.DialogKeys.NEG_BUTTON, getString(R.string.dialog_button_next_question));
      mapBundle.put(CustomDialogFragment.DialogKeys.POS_BUTTON, getString(R.string.dialog_button_close));

      CustomDialogFragment dialogFrag = CustomDialogFragment.newInstance(mapBundle);
      dialogFrag.show(getFragmentManager(), "Show Answer Dialog");
    }
  }
}
