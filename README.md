# ArrowedPopupWindow
It's easy to create a arrowed popup window and tie it to another view. Once the tied view moves, this arrowed popup window will move too.

# How to use

    ArrowTiedFollowPopupWindow popupWindow = new ArrowTiedFollowPopupWindow(MainActivity.this);
    popupWindow.setBackground(R.color.transparent_70, 5, 20, 10);  //Set the background color, radius and padding.
    popupWindow.setArrow(R.color.transparent_70, position, ArrowPopupWindow.ArrowSize.SMALL);  //Set the arrow color, relative posotion and size.
    popupWindow.setText("hello world\nvery nice\ngood", R.color.white, 12);  //Set the text content and color.
    popupWindow.setTiedView(view, direction);  //Set the tied view and the tied direction.
    popupWindow.setOffset(xoffset, yoffset);  //Set the offset
    popupWindow.setEdge(80,0,10,0);  //Set the edge, when this popup window touch the edge, it will dismiss.
    popupWindow.preShow();  //Create the views, you should use this method before you show this popup window
    popupWindow.show();


# Snapshot

 ![image](https://github.com/SuperJim123/ArrowedPopupWindow/raw/master/snapshot.jpg)
