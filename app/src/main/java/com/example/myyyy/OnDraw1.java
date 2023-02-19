package com.example.myyyy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.view.MotionEvent;
import android.view.View;

public class OnDraw1 extends View {
    Bitmap image,image1,image2,image3;
    volatile int x = 300;//начальная координата шарика по х
    volatile int y = 900;//начальная координата шарика по у
    volatile int ry1 = 0;//начальная координата первой полоски по у
    volatile int ryy1 = ry1 + 200;
    volatile int ry2 = 0;//начальная координата второй полоски по у
    volatile int ryy2 = ry2 + 200;
    volatile int vx = 5 + (int) (Math.random() * 10);//скорость шарика по х
    volatile int vy = 5 + (int) (Math.random() * 10);//скорость шарика по у
    int r = 50;//радиус кружочка
    volatile int Tx = -1,Ty = -1;//создание переменной для определения куда нажал пользователь по х,y
    static int counter = 0;
    static int count_result1 = 0;
    static int count_result2 = 0;
    Paint fontPaint;//создание объекта класса пэинт
    MediaPlayer mp;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w==0 || h==0) return;
        fontPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fontPaint.setTextSize(75);
        fontPaint.setStyle(Paint.Style.FILL);
        fontPaint.setTypeface(Typeface.create("Arial", Typeface.ITALIC));

        image1 = BitmapFactory.decodeResource(getResources(), R.drawable.soccerball);
        image1 = Bitmap.createScaledBitmap(image1, 150, 150, false);
        image2 = BitmapFactory.decodeResource(getResources(), R.drawable.grasssmall);
        image2 = Bitmap.createScaledBitmap(image2, getWidth(), getHeight(), false);
        image3 = BitmapFactory.decodeResource(getResources(), R.drawable.see);
        image3 = Bitmap.createScaledBitmap(image3, getWidth(), getHeight(), false);
        MainLoop m = new MainLoop();
        m.execute();
    }

    public OnDraw1(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        Tx = (int) event.getX();
        Ty = (int) event.getY();
        // Двигаем ракетки | Правая
        if ((Tx > getWidth() / 2) && (Ty > ryy2)) { // изменил принцип управления !
            if (ryy2 < getHeight()) {
                ry2 += 100;
                ryy2 += 100;
            }
        }
        if ((Tx > getWidth() / 2) && (Ty<ry2)) {
            if (ryy2 > 200) {
                ry2 -= 100;
                ryy2 -= 100;
            }
        }
        //  Двигаем ракетки | Левая.
        if ((Tx < getWidth() / 2) && (Ty > ryy1)) {
            if (ryy1 < getHeight()) {
                ry1 += 100;
                ryy1 += 100;
            }
        }
        if ((Tx < getWidth() / 2) && (Ty < ry1)) {
            if (ryy1 > 200) {
                ry1 -= 100;
                ryy1 -= 100;
            }
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        if (counter ==20) {
            image = image2;
        }
        if (counter >= 40) {
            counter=0;
        }
        if (counter == 0) {
            image = image3;
        }

        canvas.drawBitmap(image, 0, 0, p); // рисуем фон
        canvas.drawBitmap(image1, x, y, p); // рисуем шарик
        p.setColor(Color.BLUE);
        canvas.drawRect(0, ry1, 50, ryy1, p);//переделать
        canvas.drawRect(getWidth() - 50, ry2, getWidth(), ryy2, p);
        //fontPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setTextSize(75);
        p.setStyle(Paint.Style.FILL);
        p.setTypeface(Typeface.create("Arial", Typeface.ITALIC));
        canvas.drawText("Счет первого игрока "+count_result1,20, 55, p);
        canvas.drawText("Счет второго игрока "+count_result2, 20, 110, p);
    }

    class MainLoop extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            boolean b = true;
            while (b) {
                int x_= x+image1.getWidth()/2;
                int y_= y+image1.getHeight()/2;
                if (y_ + image1.getWidth()/2 > getHeight()) { //отталкивается от нижней стенки
                    vy = -vy;
                    counter++;
                }
                if (y_ - image1.getWidth()/2 < 0) { //отталкивется верхней стенки
                    vy = -vy;
                    counter++;
                }
                if (x_ + image1.getWidth()/2 > getWidth()) {
                    if (y_ >= ry2 && y_ <= ryy2) { //отталкивается от правой стенки
                        vx = -vx;
                        counter++;
                        count_result2++;
                    } else {
                        x = 500;
                        y = 900;
                        vx = -vx;
                        count_result2 = 0;
                    }
                }
                if (x_ - image1.getWidth()/2 < 0) {//отталкивается от левой стенки
                    if (y_ >= ry1 && y_ <= ryy1) {
                        vx = -vx;
                        counter++;
                        count_result1++;
                    }else {
                        x = 300;
                        y = 700;
                        vx = -vx;
                        count_result1 = 0;
                    }
                }
                x += vx;
                y += vy;
                invalidate();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
