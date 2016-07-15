package cc.coderchoy.leo.morehorisrcollview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Created by leo
 * on 2016/7/13.
 */
public class EdgeEffectTextView extends TextView {

    private Path path;
    private Paint paint;
    private int edgeEffectWidth;
    private int edgeEffectOriWidth = 10;

    public EdgeEffectTextView(Context context) {
        super(context);
        init();
    }

    public EdgeEffectTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EdgeEffectTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        setPadding(10, 0, 10, 0);
        setWillNotDraw(false);
        path = new Path();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(getContext().getResources().getColor(android.R.color.darker_gray));
        paint.setAlpha(128);
    }

    public void setEdgeEffectWidth(int edgeEffectWidth) {
        this.edgeEffectWidth = edgeEffectWidth;
        invalidate();
    }

    public int getEdgeEffectWidth() {
        return edgeEffectWidth;
    }

    public void setTipText(String text) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            sb.append(text.charAt(i));
            if (i != text.length() - 1) {
                sb.append("\n");
            }
        }
        setText(sb.toString());
    }

    public void setEdgeEffectColor(int color) {
        paint.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int measuredHeight = getMeasuredHeight();
        int measuredWidth = getMeasuredWidth();
        path.reset();
        path.moveTo(measuredWidth, 0);
        path.lineTo(measuredWidth - edgeEffectOriWidth, 0);
        path.cubicTo(measuredWidth - edgeEffectWidth, measuredHeight / 8
                , measuredWidth - edgeEffectWidth, measuredHeight / 8 * 7
                , measuredWidth - edgeEffectOriWidth, measuredHeight);
        path.lineTo(measuredWidth, measuredHeight);

        canvas.drawPath(path, paint);
    }

}
