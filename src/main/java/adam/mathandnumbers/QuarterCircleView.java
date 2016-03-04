package adam.mathandnumbers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

/**
 * Created by adam on 02/03/16.
 */
public class QuarterCircleView extends View {

  public QuarterCircleView(Context context) {
    super(context);
    invalidate();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    float scale = getResources().getDisplayMetrics().density;

    Paint paint = new Paint();
    paint.setStyle(Paint.Style.STROKE);
    paint.setColor(Color.BLACK);
    paint.setStrokeWidth((int) (2 * scale));

    int w = getWidth();
    int h = getHeight();
    RectF rect = new RectF(0f, 0f, (float) w, (float) h);
    canvas.drawArc(rect, 0f, 90f, false, paint);
  }
}
