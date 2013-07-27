package ru.sawim.view;

import android.text.*;
import android.content.Context;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import ru.sawim.models.MessagesAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: Gerc
 * Date: 11.07.13
 * Time: 22:27
 * To change this template use File | Settings | File Templates.
 */

public class MyTextView extends TextView {

    TextLinkClickListener mListener;

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean hasFocusable() {
        return false;
    }

    public boolean onTouchEvent(MotionEvent event) {
        Object text = getText();
        if (text instanceof Spannable) {
            Spannable buffer = (Spannable) text;
            int action = event.getAction();
            if ((action == MotionEvent.ACTION_UP)
                    || (action == MotionEvent.ACTION_DOWN)) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                x -= getTotalPaddingLeft();
                y -= getTotalPaddingTop();

                x += getScrollX();
                y += getScrollY();

                Layout layout = getLayout();
                int line = layout.getLineForVertical(y);
                int off = layout.getOffsetForHorizontal(line, x);

                URLSpan[] link = buffer.getSpans(off, off, URLSpan.class);
                if (link.length != 0) {
                    if (action == MotionEvent.ACTION_DOWN) {
                        Selection.setSelection(buffer,
                                buffer.getSpanStart(link[0]),
                                buffer.getSpanEnd(link[0]));
                    }
                    if (action == MotionEvent.ACTION_UP) {
                        if (mListener != null)
                            mListener.onTextLinkClick(this, link[0].getURL());
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public void setOnTextLinkClickListener(MessagesAdapter onTextLinkClickListener) {
        this.mListener = onTextLinkClickListener;
    }

    public interface TextLinkClickListener {
        public void onTextLinkClick(View textView, String clickedString);
    }
}