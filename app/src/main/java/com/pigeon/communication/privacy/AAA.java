package com.pigeon.communication.privacy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

@SuppressLint("AppCompatCustomView")
public class AAA extends ImageView {
    private int imgHeight;

    private int imgWidth;

    private int intrinsicHeight;

    private int intrinsicWidth;

    private float mMaxScale = 5.0f;

    private float mMinScale = 0.5f;

    private Matrix matrix = new Matrix();
    private Matrix currentMatrix = new Matrix();
    private Matrix mMatrix = new Matrix();
    private long firstTouchTime = 0;
    private int intervalTime = 250;
    private PointF firstPointF;


    public AAA(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.setScaleType(ScaleType.FIT_CENTER);

        this.setScaleType(ScaleType.FIT_CENTER);
        this.setOnTouchListener(new TouchListener());
        getImageViewWidthHeight();

    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        mMatrix.set(getImageMatrix());
        float[] values = new float[9];
        mMatrix.getValues(values);
        intrinsicWidth = bm.getWidth();
        intrinsicHeight = bm.getHeight();

    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return super.onSingleTapUp(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {

            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public void onShowPress(MotionEvent e) {
            super.onShowPress(e);
        }


        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return super.onDoubleTapEvent(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return super.onSingleTapConfirmed(e);
        }

    }


    private final class TouchListener implements OnTouchListener {
        private int mode = 0;
        private static final int MODE_DRAG = 1;
        private static final int MODE_ZOOM = 2;
        private PointF startPoint = new PointF();
        private float startDis;
        private PointF midPoint;

        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    mode = MODE_DRAG;
                    currentMatrix.set(getImageMatrix());
                    startPoint.set(event.getX(), event.getY());
                    matrix.set(currentMatrix);
                    makeImageViewFit();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mode == MODE_DRAG) {
                        float dx = event.getX() - startPoint.x;
                        float dy = event.getY() - startPoint.y;
                        matrix.set(currentMatrix);
                        float[] values = new float[9];
                        matrix.getValues(values);
                        dx = checkDxBound(values, dx);
                        dy = checkDyBound(values, dy);
                        matrix.postTranslate(dx, dy);

                    } else if (mode == MODE_ZOOM) {
                        float endDis = distance(event);//
                        if (endDis > 10f) {
                            float scale = endDis / startDis;
                            matrix.set(currentMatrix);
                            float[] values = new float[9];
                            matrix.getValues(values);
                            scale = checkFitScale(scale, values);
                            matrix.postScale(scale, scale, midPoint.x, midPoint.y);

                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    setDoubleTouchEvent(event);

                case MotionEvent.ACTION_POINTER_UP:
                    mode = 0;
                    float[] values = new float[9];
                    matrix.getValues(values);
                    makeImgCenter(values);
                    break;

                case MotionEvent.ACTION_POINTER_DOWN:
                    mode = MODE_ZOOM;
                    startDis = distance(event);
                    if (startDis > 10f) {
                        midPoint = mid(event);
                        currentMatrix.set(getImageMatrix());
                    }
                    break;
            }
            setImageMatrix(matrix);
            return true;
        }

        private float distance(MotionEvent event) {
            float dx = event.getX(1) - event.getX(0);
            float dy = event.getY(1) - event.getY(0);
            return (float) Math.sqrt(dx * dx + dy * dy);
        }

        private float getDistance(MotionEvent event) {
            float x = event.getX(1) - event.getX(0);
            float y = event.getY(1) - event.getY(0);
            float distance = (float) Math.sqrt(x * x + y * y);
            return distance;
        }

        private PointF mid(MotionEvent event) {
            float midX = (event.getX(1) + event.getX(0)) / 2;
            float midY = (event.getY(1) + event.getY(0)) / 2;
            return new PointF(midX, midY);
        }


        private float checkDyBound(float[] values, float dy) {

            float height = imgHeight;
            if (intrinsicHeight * values[Matrix.MSCALE_Y] < height)
                return 0;
            if (values[Matrix.MTRANS_Y] + dy > 0)
                dy = -values[Matrix.MTRANS_Y];
            else if (values[Matrix.MTRANS_Y] + dy < -(intrinsicHeight
                    * values[Matrix.MSCALE_Y] - height))
                dy = -(intrinsicHeight * values[Matrix.MSCALE_Y] - height)
                        - values[Matrix.MTRANS_Y];
            return dy;
        }


        private float checkDxBound(float[] values, float dx) {

            float width = imgWidth;
            if (intrinsicWidth * values[Matrix.MSCALE_X] < width)
                return 0;
            if (values[Matrix.MTRANS_X] + dx > 0)
                dx = -values[Matrix.MTRANS_X];
            else if (values[Matrix.MTRANS_X] + dx < -(intrinsicWidth
                    * values[Matrix.MSCALE_X] - width))
                dx = -(intrinsicWidth * values[Matrix.MSCALE_X] - width)
                        - values[Matrix.MTRANS_X];
            return dx;
        }

        private float checkFitScale(float scale, float[] values) {
            if (scale * values[Matrix.MSCALE_X] > mMaxScale)
                scale = mMaxScale / values[Matrix.MSCALE_X];
            if (scale * values[Matrix.MSCALE_X] < mMinScale)
                scale = mMinScale / values[Matrix.MSCALE_X];
            return scale;
        }


        private void makeImgCenter(float[] values) {

            float zoomY = intrinsicHeight * values[Matrix.MSCALE_Y];
            float zoomX = intrinsicWidth * values[Matrix.MSCALE_X];
            float leftY = values[Matrix.MTRANS_Y];
            float leftX = values[Matrix.MTRANS_X];
            float rightY = leftY + zoomY;
            float rightX = leftX + zoomX;

            if (zoomY < imgHeight) {
                float marY = (imgHeight - zoomY) / 2.0f;
                matrix.postTranslate(0, marY - leftY);
            }

            if (zoomX < imgWidth) {

                float marX = (imgWidth - zoomX) / 2.0f;
                matrix.postTranslate(marX - leftX, 0);

            }

            if (zoomY >= imgHeight) {
                if (leftY > 0) {
                    matrix.postTranslate(0, -leftY);
                }
                if (rightY < imgHeight) {
                    matrix.postTranslate(0, imgHeight - rightY);
                }
            }


            if (zoomX >= imgWidth) {
                if (leftX > 0) {
                    matrix.postTranslate(-leftX, 0);
                }
                if (rightX < imgWidth) {
                    matrix.postTranslate(imgWidth - rightX, 0);
                }
            }
        }

    }

    private void getImageViewWidthHeight() {
        ViewTreeObserver vto2 = getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            public void onGlobalLayout() {
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
                imgWidth = getWidth();
                imgHeight = getHeight();

            }
        });
    }

    private void makeImageViewFit() {
        if (getScaleType() != ScaleType.MATRIX) {
            setScaleType(ScaleType.MATRIX);

            matrix.postScale(1.0f, 1.0f, imgWidth / 2, imgHeight / 2);
        }
    }

    private void setDoubleTouchEvent(MotionEvent event) {

        float values[] = new float[9];
        matrix.getValues(values);
        long currentTime = System.currentTimeMillis();
        if (currentTime - firstTouchTime >= intervalTime) {
            firstTouchTime = currentTime;
            firstPointF = new PointF(event.getX(), event.getY());
        } else {
            if (Math.abs(event.getX() - firstPointF.x) < 30f
                    && Math.abs(event.getY() - firstPointF.y) < 30f) {
                if (values[Matrix.MSCALE_X] < mMaxScale) {
                    matrix.postScale(mMaxScale / values[Matrix.MSCALE_X],
                            mMaxScale / values[Matrix.MSCALE_X], event.getX(),
                            event.getY());
                } else {
                    matrix.postScale(mMinScale / values[Matrix.MSCALE_X],
                            mMinScale / values[Matrix.MSCALE_X], event.getX(),
                            event.getY());
                }
            }

        }
    }
}