package cn.wecoder.arrowedpopupwindow.widgets.popupwindow;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Create a arrowed popup window, it can be tied to a view, it can follow a view.
 * Once the tied view moves, it will move.
 */
public class ArrowTiedFollowPopupWindow extends ArrowTiedPopupWindow  implements ViewTreeObserver.OnScrollChangedListener, ViewTreeObserver.OnGlobalLayoutListener{

    private Context mContext;
    private View mTiedView;

    public ArrowTiedFollowPopupWindow(Context context) {
        super(context);
        mContext = context;

    }

    /**
     * Start follow.
     */
    protected void startFollow() {
        mTiedView = getTiedView();
        mTiedView.getViewTreeObserver().addOnScrollChangedListener(this);
        mTiedView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void show() {
        startFollow();
        super.show();
    }

    @Override
    public void onScrollChanged() {
        boolean result = updatePosition();
        if(!result) {
            removeListenerAndDismiss();
        }
    }

    @Override
    public void onGlobalLayout() {
        if (!mTiedView.isShown()) {
            removeListenerAndDismiss();
        }
    }

    private void removeListenerAndDismiss(){
        if(mTiedView != null) {
            ViewTreeObserver vto = mTiedView.getViewTreeObserver();
            vto.removeOnScrollChangedListener(this);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                vto.removeGlobalOnLayoutListener(this);
            } else {
                vto.removeOnGlobalLayoutListener(this);
            }
        }
        if(isShowing()){
            dismiss();
        }
    }
}
