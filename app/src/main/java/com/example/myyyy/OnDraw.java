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

public class OnDraw extends View {
    Bitmap image;
    int x = 300;//начальная координата шарика по х
    int y = 900;//начальная координата шарика по у
    int xx = 400;//начальная координата картинки по х
    int yy = 400;//начальная координата картинки по у
    //int vxx = 23 +(int)(Math.random() * 10);//скорость картинки по х
    //int vyy = 23 +(int)(Math.random() * 10);//скорость картинки по у
    //int rx1 = 0;//начальная координата первой полоски по х
    int ry1 = 0;//начальная координата первой полоски по у
    int ryy1 = ry1 + 200;
    int ry2 = 0;//начальная координата второй полоски по у
    int ryy2 = ry2 + 200;
    static int vx = 18 +(int)(Math.random() * 10);//скорость шарика по х
    static int vy = 18 +(int)(Math.random() * 10);//скорость шарика по у
    int r = 50;//радиус кружочка
    int Tx = -1;//создание переменной для определения куда нажал пользователь по х
    int Ty = -1;//создание переменной для определения куда нажал пользователь по у
    static int score = 0;//создание счётчика
    static int recordscore = 0;//создание счетчика рекордного значения
    static int count = 0;
    Paint fontPaint;//создание объекта класса пэинт
    MediaPlayer mp;

    public OnDraw(Context context) {
        super(context);
        fontPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fontPaint.setTextSize(75);
        fontPaint.setStyle(Paint.Style.FILL);
        fontPaint.setTypeface(Typeface.create("Arial", Typeface.ITALIC));
        MainLoop m = new MainLoop();
        m.execute();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Tx = (int) event.getX();
        Ty = (int) event.getY();
        // Двигаем ракетки | Правая
        if ((Tx > getWidth() / 2) && (Ty > getHeight() / 2)) {
            if (ryy2 < getHeight()) {
                ry2 += 100;
                ryy2 += 100;
            }
        }
        if ((Tx > getWidth() / 2) && (Ty < getHeight() / 2)) {
            if (ryy2 > 200) {
                ry2 -= 100;
                ryy2 -= 100;
            }
        }
        //  Двигаем ракетки | Левая.
        if ((Tx < getWidth() / 2) && (Ty > getHeight() / 2)) {
            if (ryy1 < getHeight()) {
                ry1 += 100;
                ryy1 += 100;
            }
        }
        if ((Tx < getWidth() / 2) && (Ty < getHeight() / 2)) {
            if (ryy1 > 200) {
                ry1 -= 100;
                ryy1 -= 100;
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        //задний фон
        //Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.grasssmall);
        //Bitmap image3 = BitmapFactory.decodeResource(getResources(), R.drawable.see);
        if (count >= 10 && count <= 20) {
            Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.grasssmall);
            image = image.createScaledBitmap(image, getWidth(), getHeight(), false);
            canvas.drawBitmap(image, 0, 0, p);
        }
        if (count > 20) {
            count = 0;
        }
        if (count >= 0 && count < 10) {
            Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.see);
            image = image.createScaledBitmap(image, getWidth(), getHeight(), false);
            canvas.drawBitmap(image, 0, 0, p);
        }

//        Bitmap image2 = BitmapFactory.decodeResource(getResources(), R.drawable.grasssmall);
//        image2 = image.createScaledBitmap(image, getWidth(), getHeight(), false);
//        //canvas.drawBitmap(image2, 0, 0, p);
//        Bitmap image3 = BitmapFactory.decodeResource(getResources(), R.drawable.see);
//        image3 = image3.createScaledBitmap(image3, getWidth(), getHeight(), false);
//        //canvas.drawBitmap(image3, 0, 0, p);

        Bitmap image1 = BitmapFactory.decodeResource(getResources(), R.drawable.soccerball);
        image1 = Bitmap.createScaledBitmap(image1, 150, 150, false);
        canvas.drawBitmap(image1, x, y, p);
        //p.setColor(0xFFAA0000);//делает шарик красным
        //canvas.drawColor(Color.GRAY);//делает фон серым
        //fontPaint.setColor(Color.BLUE);//делает текст синим
        //canvas.drawCircle(x, y, r, p);
        //canvas.rotate(90);
        //canvas.drawText("Score " + score,50,100,fontPaint);
        //canvas.drawText("Record score " + recordscore,50,160,fontPaint);

        //полоски
        p.setColor(Color.WHITE);
        canvas.drawRect(0, ry1, 75, ryy1, p);//переделать
        canvas.drawRect(getWidth() - 75, ry2, getWidth(), ryy2, p);
        //canvas.drawBitmap(image, xx - image.getWidth(), yy - image.getHeight(), p);
    }

    class MainLoop extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            boolean b = true;

            while (b) {
                if (y + 75 > getHeight()) { //отталкивается от нижней стенки
                    vy = -vy;
                    count += 1;
                }
                if (y - 75 < 0) { //отталкивется верхней стенки
                    vy = -vy;
                    count += 1;
                }
                if (x + 150 + 85 > getWidth()) {
                    if (y >= ry2 && y <= ryy2) { //отталкивается от правой стенки
                        vx = -vx;
                        count += 1;
                    }

                }
                if (x + 75 > getWidth()) {
                    x = 500;
                    y = 900;
                    vx = -vx;
                    count += 1;
                }

                if (x - 150 < 0) {//отталкивается от левой стенки
                    if (y >= ry1 && y <= ryy1) {
                        vx = -vx;
                        count += 1;
                    }
                }
                if (x < 0) {
                    x = 300;
                    y = 700;
                    vx = -vx;
                    count += 1;
                }
//                if ((y > ry2) && (y < ryy2)) {
//                    vx = -vx;
//                }

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
