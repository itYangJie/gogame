package com.ityang.gogame.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.ityang.gogame.R;
import com.ityang.gogame.utils.GoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/25.
 */
public class GoView extends View {

    private static int mMaxCountInLine = 5;
    private int mLinesNum = 12;
    private int mheight;
    private float mLineDistance;
    private float radioPieceLineHeight = 0.75f;
    private Bitmap mWhitePiece;
    private Bitmap mBlackPiece;
    private Bitmap mScaleWhitePiece;
    private Bitmap mScaleBlackPiece;
    private Paint mPaint;
    private int mLineColor=Color.BLACK;
    private List<Point> mBlackPoints,mWhitePoints;
    private boolean isWhiteRound=true;
    private static int WINNER_WHITE = 0;
    private static int WINNER_BLACK = 1;
    private static int WINNER_TIE = 2;
    private int whoWinner = WINNER_TIE;

    public GoView(Context context) {
        super(context);
        init();
    }

    public GoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mBlackPoints = new ArrayList<>();
        mWhitePoints = new ArrayList<>();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mLineColor);
        mWhitePiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_w2);
        mBlackPiece = BitmapFactory.decodeResource(getResources(),R.drawable.stone_b1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawLines(canvas);
        drawPoints(canvas);
        super.onDraw(canvas);
    }

    private void drawPoints(Canvas canvas) {
        for(int i = 0, size = mBlackPoints.size(); i < size; i++)
        {
            Point whitePoint = mBlackPoints.get(i);
            canvas.drawBitmap(mScaleBlackPiece,
                    (whitePoint.x + (1 - radioPieceLineHeight) / 2) * mLineDistance,
                    (whitePoint.y + (1 - radioPieceLineHeight) / 2) * mLineDistance, null);

        }

        for(int i = 0, size = mWhitePoints.size(); i < size; i++)
        {
            Point whitePoint = mWhitePoints.get(i);
            canvas.drawBitmap(mScaleWhitePiece,
                    (whitePoint.x + (1 - radioPieceLineHeight) / 2) * mLineDistance,
                    (whitePoint.y + (1 - radioPieceLineHeight) / 2) * mLineDistance, null);

        }
    }

    private void drawLines(Canvas canvas) {
        //画线
        for(int i=0;i<mLinesNum;i++){
            int startX = (int)(mLineDistance / 2);
            int endX = (int)(mheight - mLineDistance / 2);

            int y = (int)((0.5 + i) * mLineDistance);
            canvas.drawLine(startX, y, endX, y, mPaint);
            canvas.drawLine(y, startX, y, endX, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
           /* case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;*/
            case MotionEvent.ACTION_UP:
                 //胜负已经确定
                 if(whoWinner!=WINNER_TIE){
                     return  false;
                 }
                 int x = (int) event.getX();
                 int y = (int) event.getY();
                 Point point = getValidPoint(x,y);
                 if(mWhitePoints.contains(point)||mBlackPoints.contains(point)){
                     return false;
                 }
                if(isWhiteRound){
                    mWhitePoints.add(point);
                }else {
                    mBlackPoints.add(point);
                }
                invalidate();
                whoWinner = checkWinner();
                if(whoWinner!=WINNER_TIE){ //已经有一方获胜
                    if(mGoListener!=null){
                        mGoListener.onGameWin(whoWinner==WINNER_WHITE);
                    }
                    return false; //结束
                }
                //平局
                if(mWhitePoints.size()+mBlackPoints.size()>=(mLinesNum-1)*(mLinesNum-1)){
                    if(mGoListener!=null){
                        mGoListener.onGameTie();
                    }
                }
                isWhiteRound = !isWhiteRound;
                if(mGoListener!=null){
                    mGoListener.onWhoPlay(isWhiteRound);
                }
                break;
        }
        return true;
    }
    public void restart(){
        mBlackPoints.clear();
        mWhitePoints.clear();
        isWhiteRound=true;
        whoWinner = WINNER_TIE;
        invalidate();
    }
    /**
     * 判断胜负
     * @return
     */
    private int checkWinner() {
        if(GoUtils.checkWin(mWhitePoints)){
            //白棋胜利
            return WINNER_WHITE;
        }
        if(GoUtils.checkWin(mBlackPoints)){
            //黑棋胜利
            return WINNER_BLACK;
        }
       return WINNER_TIE;
    }




    private Point getValidPoint(int x, int y)
    {
        return new Point((int)(x / mLineDistance), (int)(y / mLineDistance));
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mheight = w;
        mLineDistance = mheight*1.0f/mLinesNum;
        int goHeight = (int)(mLineDistance*radioPieceLineHeight);
        mScaleBlackPiece = Bitmap.createScaledBitmap(mBlackPiece,goHeight,goHeight,false);
        mScaleWhitePiece = Bitmap.createScaledBitmap(mWhitePiece,goHeight,goHeight,false);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int size = Math.min(widthSize,heightSize);
        if(widthMode==MeasureSpec.UNSPECIFIED){
            size = heightSize;
        }else if(heightMode==MeasureSpec.UNSPECIFIED){
            size = widthSize;
        }
        setMeasuredDimension(size,size);
    }
    private GOListener mGoListener;

    public static int getMaxCountInLine() {
        return mMaxCountInLine;
    }

    public interface GOListener{
        /**
         * 轮到谁下棋了.
         *
         * @param  true白棋下;false 黑棋下
         */
        void onWhoPlay(boolean mIsWhitePlay);
        /**
         * @param win true,白赢; false,黑赢
         */
        void onGameWin(boolean win);
        /**
         * 平局
         */
        void onGameTie();
    }
    //设置回调接口
    public void setGoListener(GOListener mGoListener) {
        this.mGoListener = mGoListener;
    }
}
