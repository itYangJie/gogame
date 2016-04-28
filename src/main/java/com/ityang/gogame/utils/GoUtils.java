package com.ityang.gogame.utils;

import android.graphics.Point;

import com.ityang.gogame.view.GoView;

import java.util.List;

/**
 * Created by Administrator on 2016/4/26.
 */
public class GoUtils {
    private GoUtils(){

    }
    public static boolean checkWin(List<Point> points)
    {
        for(Point point : points)
        {
            int x = point.x;
            int y = point.y;

            boolean win = checkHorizontal(x, y, points);
            if(win)
                return true;
            win = checkVertical(x, y, points);
            if(win)
                return true;
            win = checkLeftDiagonal(x, y, points);
            if(win)
                return true;
            win = checkRightDiagonal(x, y, points);
            if(win)
                return true;

        }

        return false;
    }
    private static boolean checkHorizontal(int x, int y, List<Point> points)
    {
        int MAX_COUNT_IN_LINE = GoView.getMaxCountInLine();

        int count = 1;
        for(int i = 1; i < MAX_COUNT_IN_LINE; i++)
        {
            if(points.contains(new Point(x - i, y)))
            {
                count++;
            }
            else
            {
                break;
            }

        }
        if(count == MAX_COUNT_IN_LINE)
            return true;

        for(int i = 1; i < MAX_COUNT_IN_LINE; i++)
        {
            if(points.contains(new Point(x + i, y)))
            {
                count++;
            }
            else
            {
                break;
            }
        }
        if(count == MAX_COUNT_IN_LINE)
        {
            return true;
        }
        return false;
    }


    /**
     * 纵向检测WIN
     * @param x
     * @param y
     * @param points
     * @return
     */
    private static boolean checkVertical(int x, int y, List<Point> points)
    {
        int MAX_COUNT_IN_LINE = GoView.getMaxCountInLine();

        int count = 1;
        for(int i = 1; i < MAX_COUNT_IN_LINE; i++)
        {
            if(points.contains(new Point(x, y - i)))
            {
                count++;
            }
            else
            {
                break;
            }

        }
        if(count == MAX_COUNT_IN_LINE)
            return true;

        for(int i = 1; i < MAX_COUNT_IN_LINE; i++)
        {
            if(points.contains(new Point(x, y + i)))
            {
                count++;
            }
            else
            {
                break;
            }
        }
        if(count == MAX_COUNT_IN_LINE)
        {
            return true;
        }
        return false;
    }

    /**
     * 左斜检测WIN
     * @param x
     * @param y
     * @param points
     * @return
     */
    private static boolean checkLeftDiagonal(int x, int y, List<Point> points)
    {
        int MAX_COUNT_IN_LINE = GoView.getMaxCountInLine();
        int count = 1;
        for(int i = 1; i < MAX_COUNT_IN_LINE; i++)
        {
            if(points.contains(new Point(x - i, y + i)))
            {
                count++;
            }
            else
            {
                break;
            }

        }
        if(count == MAX_COUNT_IN_LINE)
            return true;

        for(int i = 1; i < MAX_COUNT_IN_LINE; i++)
        {
            if(points.contains(new Point(x + i, y - i)))
            {
                count++;
            }
            else
            {
                break;
            }
        }
        if(count == MAX_COUNT_IN_LINE)
        {
            return true;
        }
        return false;
    }

    /**
     * 右斜检测WIN
     * @param x
     * @param y
     * @param points
     * @return
     */
    private static boolean checkRightDiagonal(int x, int y, List<Point> points)
    {
        int MAX_COUNT_IN_LINE = GoView.getMaxCountInLine();

        int count = 1;
        for(int i = 1; i < MAX_COUNT_IN_LINE; i++)
        {
            if(points.contains(new Point(x - i, y - i)))
            {
                count++;
            }
            else
            {
                break;
            }

        }
        if(count == MAX_COUNT_IN_LINE)
            return true;

        for(int i = 1; i < MAX_COUNT_IN_LINE; i++)
        {
            if(points.contains(new Point(x + i, y + i)))
            {
                count++;
            }
            else
            {
                break;
            }
        }
        if(count == MAX_COUNT_IN_LINE)
        {
            return true;
        }
        return false;
    }


}
