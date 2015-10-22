package cn.wecoder.arrowedpopupwindow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import cn.wecoder.arrowedpopupwindow.widgets.popupwindow.ArrowPopupWindow;
import cn.wecoder.arrowedpopupwindow.widgets.popupwindow.ArrowTiedFollowPopupWindow;
import cn.wecoder.arrowedpopupwindow.widgets.popupwindow.ArrowTiedPopupWindow;

public class MainActivity extends AppCompatActivity {

    private Button mLeftButton;
    private Button mRightButton;
    private Button mTopButton;
    private Button mBottomButton;
    private Button mPositionButton;
    private Button mOffsetButton;

    private ArrowTiedFollowPopupWindow leftPop, rightPop, topPop, bottomPop, offsetPop, positionPop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initListeners();
    }

    private void initListeners() {
        mLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(leftPop == null) {
                    leftPop = showPopup(mLeftButton, ArrowTiedPopupWindow.TiedDirection.LEFT, 0.5f, 0, 0);
                }else {
                    if(!leftPop.isShowing()) {
                        leftPop.show();
                    }
                }
            }
        });
        mRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rightPop == null) {
                    rightPop = showPopup(mRightButton, ArrowTiedPopupWindow.TiedDirection.RIGHT, 0.5f, 0, 0);
                }else {
                    if(!rightPop.isShowing()) {
                        rightPop.show();
                    }
                }
            }
        });
        mTopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(topPop == null) {
                    topPop = showPopup(mTopButton, ArrowTiedPopupWindow.TiedDirection.TOP, 0.5f, 0, 0);
                }else {
                    if(!topPop.isShowing()) {
                        topPop.show();
                    }
                }
            }
        });
        mBottomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bottomPop == null) {
                    bottomPop = showPopup(mBottomButton, ArrowTiedPopupWindow.TiedDirection.BOTTOM, 0.5f, 0, 0);
                }else {
                    if(!bottomPop.isShowing()) {
                        bottomPop.show();
                    }
                }
            }
        });
        mPositionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(positionPop == null) {
                    positionPop = showPopup(mPositionButton, ArrowTiedPopupWindow.TiedDirection.RIGHT, 0.7f, 0, 0);
                }else {
                    if(!positionPop.isShowing()) {
                        positionPop.show();
                    }
                }
            }
        });
        mOffsetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(offsetPop == null) {
                    offsetPop = showPopup(mOffsetButton, ArrowTiedPopupWindow.TiedDirection.LEFT, 0.7f, -10, -10);
                }else {
                    if(!offsetPop.isShowing()) {
                        offsetPop.show();
                    }
                }
            }
        });
    }

    private void initViews() {
        mLeftButton = (Button) findViewById(R.id.left);
        mRightButton = (Button) findViewById(R.id.right);
        mTopButton = (Button) findViewById(R.id.top);
        mBottomButton = (Button) findViewById(R.id.bottom);
        mOffsetButton = (Button) findViewById(R.id.offset);
        mPositionButton = (Button) findViewById(R.id.position);
    }

    private ArrowTiedFollowPopupWindow showPopup(View view, ArrowTiedPopupWindow.TiedDirection direction, float position, int xoffset, int yoffset) {
        ArrowTiedFollowPopupWindow popupWindow = new ArrowTiedFollowPopupWindow(MainActivity.this);
        popupWindow.setBackground(R.color.transparent_70, 5, 20, 10);
        popupWindow.setArrow(android.R.color.holo_red_light, position, ArrowPopupWindow.ArrowSize.SMALL);
        popupWindow.setText("hello world\nvery nice\ngood", R.color.white, 12);
        popupWindow.setTiedView(view, direction);
        popupWindow.setOffset(xoffset, yoffset);
        popupWindow.setEdge(80,0,10,0);
        popupWindow.preShow();
        popupWindow.setAnimationStyle(R.style.cardPopupAnimation);
        popupWindow.show();
        return popupWindow;
    }

    @Override
    protected void onDestroy() {
        if(leftPop != null && leftPop.isShowing()){
            leftPop.dismiss();
        }
        if(rightPop != null && rightPop.isShowing()){
            rightPop.dismiss();
        }
        if(topPop != null && topPop.isShowing()){
            topPop.dismiss();
        }
        if(bottomPop != null && bottomPop.isShowing()){
            bottomPop.dismiss();
        }
        if(offsetPop != null && offsetPop.isShowing()){
            offsetPop.dismiss();
        }
        if(positionPop != null && positionPop.isShowing()){
            positionPop.dismiss();
        }
        super.onDestroy();
    }

}
