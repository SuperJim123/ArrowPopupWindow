package cn.wecoder.arrowpopupwindow.library.widgets.popupwindow;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.wecoder.arrowpopupwindow.library.R;
import cn.wecoder.arrowpopupwindow.library.utils.Util;
import cn.wecoder.arrowpopupwindow.library.widgets.drawable.RoundRectDrawable;
import cn.wecoder.arrowpopupwindow.library.widgets.drawable.TintedBitmapDrawable;

/**
 * Create a arrowed popup window.
 */
public class ArrowPopupWindow extends PopupWindow {

    public enum ArrowDirection {
        LEFT, RIGHT, TOP, BOTTOM
    }

    public enum ArrowSize {
        SMALLER(10), SMALL(15), NORMAL(20), BIG(25), BIGGER(30);
        private final int value;
        ArrowSize(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }

    private Context mContext;
    private int mBackgroundColor;
    private float mRadius;
    private ArrowDirection mArrowDirection = ArrowDirection.BOTTOM;
    private int mArrowColor;
    private float mArrowPosition;
    private String mText;
    private int mTextColor;
    private int mTextSize;
    private int xPadding;
    private int yPadding;
    private ArrowSize mArrowSize = ArrowSize.NORMAL;
    private RelativeLayout container;
    private int arrowPointOffset;
    private int mViewWidth, mViewHeight;

    public ArrowPopupWindow(Context context) {
        mContext = context;
    }

    /**
     * Set the background parameters to this popup window
     * @param colorRes the resource of the background color
     * @param radius the radius(dp)
     * @param xPadding the x padding(dp)
     * @param yPadding the y padding(dp)
     */
    public void setBackground(int colorRes, float radius, int xPadding, int yPadding) {
        mBackgroundColor = mContext.getResources().getColor(colorRes);
        mRadius = Util.DpToPx(mContext, radius);
        this.xPadding = xPadding;
        this.yPadding = yPadding;
    }

    /**
     * Set the arrow parameters to this popup window
     * @param direction the direction
     * @param colorRes the resource of this arrow's color
     * @param relativePosition the relative position of this arrow, this must be a float between 0 and 1
     * @param size the size of this arrow
     */
    public void setArrow(ArrowDirection direction, int colorRes, float relativePosition, ArrowSize size) {
        mArrowDirection = direction;
        mArrowColor = mContext.getResources().getColor(colorRes);
        mArrowPosition = relativePosition;
        mArrowSize = size;
    }

    /**
     * Set the arrow parameters to this popup window
     * @param colorRes the resource of this arrow's color
     * @param relativePosition the relative position of this arrow, this must be a float between 0 and 1
     * @param size the size of this arrow
     */
    public void setArrow(int colorRes, float relativePosition, ArrowSize size) {
        mArrowColor = mContext.getResources().getColor(colorRes);
        mArrowPosition = relativePosition;
        mArrowSize = size;
    }

    /**
     * Set the arrow direction of this popup window
     * @param direction direction
     */
    public void setArrowDirection(ArrowDirection direction) {
        mArrowDirection = direction;
    }

    /**
     * Set the text parameters to this popup window.
     * @param text the content of this text
     * @param textColorRes the resource of this text's color.
     * @param textSize the text size(sp)
     */
    public void setText(String text, int textColorRes, int textSize) {
        mText = text;
        mTextColor = mContext.getResources().getColor(textColorRes);
        mTextSize = textSize;
    }

    /**
     * You must user this method before you show this popup window.
     */
    public void preShow() {
        TextView tvContent = new TextView(mContext);
        tvContent.setTextColor(mTextColor);
        tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
        tvContent.setText(mText);

        int xPaddingPx = Util.DpToPx(mContext, xPadding);
        int yPaddingPx = Util.DpToPx(mContext, yPadding);
        tvContent.setPadding(xPaddingPx, yPaddingPx, xPaddingPx, yPaddingPx);

        RelativeLayout textContainer = new RelativeLayout(mContext);
        textContainer.setId(R.id.popup_text_container);
        RelativeLayout.LayoutParams textContainerParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RoundRectDrawable roundRectDrawable = new RoundRectDrawable(mBackgroundColor, mRadius);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            textContainer.setBackgroundDrawable(roundRectDrawable);
        } else {
            textContainer.setBackground(roundRectDrawable);
        }
        textContainer.addView(tvContent);

        int ms = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        textContainer.setLayoutParams(textContainerParams);
        textContainer.measure(ms, ms);

        int textContainerWidth = textContainer.getMeasuredWidth();
        int textContainerHeight = textContainer.getMeasuredHeight();

        ImageView arrowImage = new ImageView(mContext);
        arrowImage.setId(R.id.popup_arraw);
        RelativeLayout.LayoutParams arrowParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        int r = 0;
        switch (mArrowDirection) {
            case LEFT:
                r = 180;
                textContainerParams.addRule(RelativeLayout.END_OF, R.id.popup_arraw);
                break;
            case TOP:
                r = 270;
                textContainerParams.addRule(RelativeLayout.BELOW, R.id.popup_arraw);
                break;
            case BOTTOM:
                r = 90;
                arrowParams.addRule(RelativeLayout.BELOW, R.id.popup_text_container);
                break;
            case RIGHT:
                r = 0;
                arrowParams.addRule(RelativeLayout.END_OF, R.id.popup_text_container);
                break;
        }

        int arrowRes = R.drawable.bubble_arrow_light;
        Bitmap source = BitmapFactory.decodeResource(mContext.getResources(), arrowRes);
        Bitmap rotateBitmap = Util.rotateBitmap(source, r);

        int rotateWidth = rotateBitmap.getWidth();
        int rotateHeight = rotateBitmap.getHeight();
        int arrowSize = mArrowSize.getValue();
        int arrowSizePx = Util.DpToPx(mContext, arrowSize);
        float scale = 1;
        switch (mArrowDirection) {
            case TOP:
            case BOTTOM:
                int width = textContainerWidth/3;
                if(arrowSizePx > width) {
                    arrowSizePx = width;
                }
                scale = ((float) arrowSizePx)/rotateWidth;
                break;
            case LEFT:
            case RIGHT:
                int height = textContainerHeight/3;
                if(arrowSizePx > height) {
                    arrowSizePx = height;
                }
                scale = ((float) arrowSizePx)/rotateHeight;
                break;
        }
        Bitmap scaleBitmap = Util.scaleBitmap(rotateBitmap, scale);

        TintedBitmapDrawable arrowDrawable = new TintedBitmapDrawable(mContext.getResources(),scaleBitmap, mArrowColor);
        arrowImage.setImageDrawable(arrowDrawable);

        switch (mArrowDirection) {
            case TOP:
            case BOTTOM:
                int marginLeft = (int) (textContainerWidth * mArrowPosition) - (scaleBitmap.getWidth() / 2);
                if(marginLeft < mRadius) {
                    marginLeft = (int)mRadius;
                }
                if(marginLeft > (textContainerWidth - mRadius - scaleBitmap.getWidth())) {
                    marginLeft = textContainerWidth - (int)mRadius - scaleBitmap.getWidth();
                }
                arrowPointOffset = marginLeft + (scaleBitmap.getWidth() / 2);
                arrowParams.setMargins(marginLeft, 0, 0, 0);
                break;
            case LEFT:
            case RIGHT:
                int marginTop = (int) (textContainerHeight * mArrowPosition) - (scaleBitmap.getHeight() / 2);
                if(marginTop < mRadius) {
                    marginTop = (int)mRadius;
                }
                if(marginTop > (textContainerHeight - mRadius - scaleBitmap.getHeight())) {
                    marginTop = textContainerHeight - (int)mRadius - scaleBitmap.getHeight();
                }
                arrowPointOffset = marginTop + (scaleBitmap.getHeight() / 2);
                arrowParams.setMargins(0, marginTop, 0, 0);
                break;
        }

        container = new RelativeLayout(mContext);
        container.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        container.addView(arrowImage, arrowParams);
        container.addView(textContainer, textContainerParams);

        setBackgroundDrawable(new BitmapDrawable());
        setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setContentView(container);
        container.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        int ms2 = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        container.measure(ms2, ms2);

        mViewWidth = container.getMeasuredWidth();
        mViewHeight = container.getMeasuredHeight();
    }

    protected int getViewHeight() {
        return mViewHeight;
    }

    protected int getViewWidth() {
        return mViewWidth;
    }

    protected int getArrowPointOffset() {
        return arrowPointOffset;
    }
}
