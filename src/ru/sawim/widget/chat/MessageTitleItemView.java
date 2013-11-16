package ru.sawim.widget.chat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.TypedValue;
import android.view.View;
import ru.sawim.General;
import ru.sawim.Scheme;
import ru.sawim.widget.Util;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 14.11.13
 * Time: 17:58
 * To change this template use File | Settings | File Templates.
 */
public class MessageTitleItemView extends View {
    private BitmapDrawable msgImage;
    private String msgTimeText;
    private String nickText;
    private int nickColor;
    private int msgTimeColor;
    private Typeface nickTypeface;
    private Typeface msgTimeTypeface;
    private int nickSize;
    private int msgTimeSize;

    private int nickX;
    private float msgTimeX;
    private int msgTimeWidth;
    private int textY;
    private static Paint textPaint;

    public MessageTitleItemView(Context context) {
        super(context);
        int padding = Util.dipToPixels(context, 5);
        setPadding(padding, padding, padding, padding);
        if (textPaint == null) {
            textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            textPaint.setAntiAlias(true);
            textPaint.setTextSize(General.getFontSize());
            textPaint.setColor(Scheme.getColor(Scheme.THEME_TEXT));
        }
    }

    private void setTextSize(int size) {
        textPaint.setTextSize(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, size, General.getResources(getContext()).getDisplayMetrics()));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int ascent = (int) textPaint.ascent();
        int descent = (int) textPaint.descent();
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = msgImage == null
                    ? (-ascent + descent) + getPaddingTop() + getPaddingBottom()
                    : msgImage.getBitmap().getHeight();
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        computeCoordinates(right - left, bottom - top);
    }

    private void computeCoordinates(int viewWidth, int viewHeight) {
        int y = viewHeight / 2;
        nickX = msgImage == null ? getPaddingLeft() : msgImage.getBitmap().getWidth() + getPaddingRight();
        if (msgTimeText != null) {
            msgTimeWidth = (int) textPaint.measureText(msgTimeText);
            msgTimeX = viewWidth - msgTimeWidth - getPaddingRight();
        } else {
            msgTimeX = viewWidth - getPaddingRight();
        }
        textY = (int) (textPaint.getTextSize()) + getPaddingTop();
    }

    public void setMsgImage(BitmapDrawable msgImage) {
        this.msgImage = msgImage;
    }

    public void setNick(int nickColor, int nickSize, Typeface nickTypeface, String nickText) {
        this.nickColor = nickColor;
        this.nickSize = nickSize;
        this.nickTypeface = nickTypeface;
        this.nickText = nickText;
    }

    public void setMsgTime(int msgTimeColor, int msgTimeSize, Typeface msgTimeTypeface, String msgTimeText) {
        this.msgTimeColor = msgTimeColor;
        this.msgTimeSize = msgTimeSize;
        this.msgTimeTypeface = msgTimeTypeface;
        this.msgTimeText = msgTimeText;
    }

    public void repaint() {
        requestLayout();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (msgImage != null)
            canvas.drawBitmap(msgImage.getBitmap(), 0, 0, null);
        if (nickText != null) {
            textPaint.setColor(nickColor);
            setTextSize(nickSize);
            textPaint.setTypeface(nickTypeface);
            if (msgTimeText != null)
                textPaint.setStrokeWidth(getWidth() - msgTimeWidth - getPaddingRight());
            canvas.drawText(nickText, nickX, textY, textPaint);
        }
        if (msgTimeText != null) {
            textPaint.setColor(msgTimeColor);
            setTextSize(msgTimeSize);
            textPaint.setTypeface(msgTimeTypeface);
            canvas.drawText(msgTimeText, msgTimeX, textY, textPaint);
        }
    }
}