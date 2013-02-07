package com.example.package;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

public class SingleFlipGallery extends Gallery {

  /*
   * As per
   * http://stackoverflow.com/questions/2373617/how-to-stop-scrolling-in-a-gallery-widget
   *
   * Override fling event, translate to DPAD
   * Prevents loose scrolling of gallery.
   *
   */



	public SlowGallery(Context context) {
		super(context);
	}

	public SlowGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		setAnimationDuration(600);
		return super.onScroll(e1, e2, distanceX, distanceY);
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		float velMax = 2500f;
		float velMin = 1000f;
		float velX = Math.abs(velocityX);
		if (velX > velMax) {
			velX = velMax;
		} else if (velX < velMin) {
			velX = velMin;
		}
		velX -= 600;
		int k = 500000;
		int speed = (int) Math.floor(1f / velX * k);
		setAnimationDuration(speed);

		int kEvent;
		if (isScrollingLeft(e1, e2)) {
			kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
		} else {
			kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
		}
		onKeyDown(kEvent, null);

		return true;
	}
	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
		return e2.getX() > e1.getX();
	}
}
