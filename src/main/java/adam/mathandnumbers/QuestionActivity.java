package adam.mathandnumbers;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by adam on 2016-01-08.
 */
public class QuestionActivity extends AppCompatActivity implements QuestionFragment.QuestionCommunicator {

  private QuestionBank questionBank;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_question);
    questionBank = new QuestionBank(this, getSharedPreferences(MainActivity.DEFAULT_PREFERENCES, MODE_PRIVATE));

    FragmentTransaction ft = getFragmentManager().beginTransaction();
    ft.replace(R.id.question_act_layout_container, new QuestionFragment());
    ft.commit();
  }

  @Override
  public Question getNextQuestion() { return questionBank.getNextQuestion(); }
}
