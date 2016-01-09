package adam.mathandnumbers;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by adam on 2016-01-08.
 */
public class Questions {

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

  public static final int INCLUDE = 0;
  public static final int CARRY = 1;
  public static final int REMAINDER = 1;

  private Map<QuestionType, Map<Integer, Boolean>> questions;

  public Questions() { questions = new HashMap<Questions.QuestionType, Map<Integer, Boolean>>(); }

  public void include(QuestionType questionType) { questions.put(questionType, initQuestionOptions()); }

  public boolean check(QuestionType questionType, int option) {
    if (questions.get(questionType).get(option) == null || !questions.get(questionType).get(option)) {
      return false;
    } else {
      return true;
    }
  }

  public void set(QuestionType questionType, int option) { questions.get(questionType).put(option, true); }

  public int numQuestionTypes() { return questions.size(); }

  private Map<Integer, Boolean> initQuestionOptions() {
    Map<Integer, Boolean> questionOptions = new HashMap<Integer, Boolean>();
    questionOptions.put(INCLUDE, true);
    return questionOptions;
  }
}
