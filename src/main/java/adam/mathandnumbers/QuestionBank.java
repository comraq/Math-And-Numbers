package adam.mathandnumbers;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by adam on 2016-01-08.
 */
public class QuestionBank {

  public enum QuestionType {
    ADDITION("Question: Addition"),
    SUBTRACTION("Question: Subtraction"),
    MULTIPLICATION("Question: Multiplication"),
    DIVISION("Question: Division");

    private String title;

    QuestionType(String m) {
      title = m;
    }

    String title() {
      return title;
    }
  }

  public enum QuestionOptions {
    CARRY,
    REMAINDER;
  }

  private Map<QuestionType, Question> questions;
  private static QuestionType[] QUESTION_TYPES = QuestionType.values();

  public QuestionBank(Context context, SharedPreferences pref) {
    questions = new HashMap<QuestionType, Question>();
    Question q;

    for (QuestionType type : QuestionType.values()) {
      //Log.i("qtype", questionType.toString() + " or " + questionType);
      if (pref.getBoolean(type.toString(), false)) {
        q = new Question(type);
        setQuestionOptions(q, context, pref);
        questions.put(type, q);
      }
    }
  }

  public Question getNextQuestion() {
    Question[] qArray = questions.values().toArray(new Question[0]);
    int randIndex = ThreadLocalRandom.current().nextInt(0, qArray.length);
    return qArray[randIndex].genNextQuestion();
  }

  private void setQuestionOptions(Question q, Context context, SharedPreferences pref) {
    QuestionType type = q.getType();
    switch(type) {
      case ADDITION:
        if (pref.getBoolean(context.getString(R.string.check_pref_add_carry_key), false)) q.includeOption(QuestionOptions.CARRY);
        break;
      case SUBTRACTION:
        if (pref.getBoolean(context.getString(R.string.check_pref_sub_carry_key), false)) q.includeOption(QuestionOptions.CARRY);
        break;
      case MULTIPLICATION:
        if (pref.getBoolean(context.getString(R.string.check_pref_multi_carry_key), false)) q.includeOption(QuestionOptions.CARRY);
        break;
      case DIVISION:
        if (pref.getBoolean(context.getString(R.string.check_pref_div_remainder_key), false)) q.includeOption(QuestionOptions.REMAINDER);
        break;
      default:
        //Nothing
    }
  }
}
