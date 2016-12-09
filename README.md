# arrowpopupwindow
It's easy to create a arrowed popup window and tie it to another view. Once the tied view moves, this arrowed popup window will move too.

# How to use

## 1. Download
```
compile 'com.jimfengfly:arrow-popup-window:1.0.1'
```
## 2. Code
```
ArrowTiedFollowPopupWindow popupWindow = new ArrowTiedFollowPopupWindow(MainActivity.this);
popupWindow.setBackground(R.color.transparent_70, 5, 20, 10);  //Set the background color, radius and padding.
popupWindow.setArrow(R.color.transparent_70, position, ArrowPopupWindow.ArrowSize.SMALL);  //Set the arrow color, relative posotion and size.
popupWindow.setPopupView(mTextView);  //Set the content view.
popupWindow.setTiedView(view, direction);  //Set the tied view and the tied direction.
popupWindow.setOffset(xoffset, yoffset);  //Set the offset
popupWindow.setEdge(80,0,10,0);  //Set the edge, when this popup window touch the edge, it will dismiss.
popupWindow.preShow();  //Create the views, you should use this method before you show this popup window
popupWindow.show();
```


# Snapshot

 ![image](https://github.com/SuperJim123/arrowpopupwindow/raw/master/snapshot.jpg)
