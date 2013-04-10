import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.WindowManager;

public class LiveWallpaper extends WallpaperService {

	@Override
	public Engine onCreateEngine() {
		return new WallpaperEngine();
	}

	// Context Accessor
	protected Context ctx = this;

	public Context getContext() {
		return ctx;
	}

	// Engine
	public class WallpaperEngine extends Engine {

		// General Canvas values
		private int width;
		private int height;
		private boolean visible = true;
		int FRAMERATE = 33;

		// Draw Loop
		private final Handler handler = new Handler();
		private final Runnable drawRunner = new Runnable() {
			@Override
			public void run() {
				draw();
			}
		};

		private Point getDisplaySize(){
			Point size = new Point();
			WindowManager w = ((WindowManager) ctx.getSystemService("window"));
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
				w.getDefaultDisplay().getSize(size);
			} else {
				Display d = w.getDefaultDisplay();
				size.set(d.getWidth(), d.getHeight());
			}
			return size;
		}

		@Override
		public void onVisibilityChanged(boolean visible) {
			this.visible = visible;
			if (visible) {
				handler.post(drawRunner);
			} else {
				handler.removeCallbacks(drawRunner);
			}
		}

		@Override
		public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			super.onSurfaceChanged(holder, format, width, height);
		}

		/*
		 * ---------------------- Draw ---------------------------
		 */

		private void draw() {
			SurfaceHolder holder = getSurfaceHolder();
			Canvas canvas = null;
			try {
				canvas = holder.lockCanvas();
				if (canvas != null) {
				
				}
			} finally {
				if (canvas != null)
					holder.unlockCanvasAndPost(canvas);
			}
			handler.removeCallbacks(drawRunner);
			if (visible) {
				handler.postDelayed(drawRunner, 1000 / FRAMERATE);
			}
		}

		/*
		 * --------------------- Touch Handling ---------------------
		 */

		@Override
		public Bundle onCommand(final String action, final int x, final int y, final int z, final Bundle extras,
				final boolean resultRequested) {
			if (action.equals("android.wallpaper.tap")) {
			}
			return extras;
		}

		/*
		 * --------------------- Scroll Handling ---------------------
		 */
		@Override
		public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep,
				int xPixelOffset, int yPixelOffset) {
			super.onOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep, xPixelOffset, yPixelOffset);
		}

	}
}
