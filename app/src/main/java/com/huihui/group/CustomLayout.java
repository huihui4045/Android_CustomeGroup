package com.huihui.group;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gavin
 * Time 2017/6/14  17:34
 * Email:molu_clown@163.com
 */

public class CustomLayout extends ViewGroup {

    private final String TAG = this.getClass().getSimpleName();

    public CustomLayout(Context context) {
        super(context);
    }

    public CustomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        final int count = getChildCount();
        int childMeasureWidth = 0;
        int childMeasureHeight = 0;
        CustomLayoutParams params = null;
        for ( int i = 0; i < count; i++) {
            View child = getChildAt(i);
            // 注意此处不能使用getWidth和getHeight，这两个方法必须在onLayout执行完，才能正确获取宽高
            childMeasureWidth = child.getMeasuredWidth();
            childMeasureHeight = child.getMeasuredHeight();

            params = (CustomLayoutParams) child.getLayoutParams();
         /*   switch (params.position) {
                case CustomLayoutParams.POSITION_MIDDLE:    // 中间
                    left = (getWidth() - childMeasureWidth) / 2;
                    top = (getHeight() - childMeasureHeight) / 2;
                    break;
                case CustomLayoutParams.POSITION_LEFT:      // 左上方
                    left = 0;
                    top = 0;
                    break;
                case CustomLayoutParams.POSITION_RIGHT:     // 右上方
                    left = getWidth() - childMeasureWidth;
                    top = 0;
                    break;
                case CustomLayoutParams.POSITION_BOTTOM:    // 左下角
                    left = 0;
                    top = getHeight() - childMeasureHeight;
                    break;
                case CustomLayoutParams.POSITION_RIGHTANDBOTTOM:// 右下角
                    left = getWidth() - childMeasureWidth;
                    top = getHeight() - childMeasureHeight;
                    break;
                default:
                    break;
            }*/

            switch (params. position) {
                case CustomLayoutParams. POSITION_MIDDLE:    // 中间
                    left = (getWidth()-childMeasureWidth)/2 - params.rightMargin + params.leftMargin ;
                    top = (getHeight()-childMeasureHeight)/2 + params.topMargin - params.bottomMargin ;
                    break;
                case CustomLayoutParams. POSITION_LEFT:      // 左上方
                    left = 0 + params. leftMargin;
                    top = 0 + params. topMargin;
                    break;
                case CustomLayoutParams. POSITION_RIGHT:     // 右上方
                    left = getWidth()-childMeasureWidth - params.rightMargin;
                    top = 0 + params. topMargin;
                    break;
                case CustomLayoutParams. POSITION_BOTTOM:    // 左下角
                    left = 0 + params. leftMargin;
                    top = getHeight()-childMeasureHeight-params.bottomMargin ;
                    break;
                case CustomLayoutParams. POSITION_RIGHTANDBOTTOM:// 右下角
                    left = getWidth()-childMeasureWidth - params.rightMargin;
                    top = getHeight()-childMeasureHeight-params.bottomMargin ;
                    break;
                default:
                    break;
            }

            // 确定子控件的位置，四个参数分别代表（左上右下）点的坐标值
            child.layout(left, top, left + childMeasureWidth, top + childMeasureHeight);

        }

        /*final int count = getChildCount();
        int childMeasureWidth = 0;

        int nextChildMeasureWidth = 0;
        int childMeasureHeight = 0;
        int layoutWidth = 0;    // 容器已经占据的宽度
        int layoutHeight = 0;   // 容器已经占据的宽度
        int maxChildHeight = 0; //一行中子控件最高的高度，用于决定下一行高度应该在目前基础上累加多少


        for (int i = 0; i < count; i++) {

            View child = getChildAt(i);



            if (i+1<count-1){


                nextChildMeasureWidth=getChildAt(i+1).getMeasuredWidth();
            }else {
                nextChildMeasureWidth=0;
            }


            childMeasureHeight = child.getMeasuredHeight();

            childMeasureWidth = child.getMeasuredWidth();


            int width=getWidth();

            if (layoutWidth+nextChildMeasureWidth < width) {

                //如果一行没有排满，继续往右排列
                left = layoutWidth;
                right = left + childMeasureWidth;
                top = layoutHeight;
                bottom = top + childMeasureHeight;
            } else {

                //排满后换行
                layoutWidth = 0;
                layoutHeight += maxChildHeight;
                maxChildHeight = 0;

                left = layoutWidth;
                right = left + childMeasureWidth;
                top = layoutHeight;
                bottom = top + childMeasureHeight;
            }


            layoutWidth += childMeasureWidth;  //宽度累加
            if (childMeasureHeight > maxChildHeight) {
                maxChildHeight = childMeasureHeight;
            }

            //确定子控件的位置，四个参数分别代表（左上右下）点的坐标值
            child.layout(left, top, right, bottom);
        }*/


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
        int widthMode = MeasureSpec. getMode(widthMeasureSpec);
        int heightMode = MeasureSpec. getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec. getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec. getSize(heightMeasureSpec);
        int layoutWidth = 0;
        int layoutHeight = 0;
        // 计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int cWidth = 0;
        int cHeight = 0;
        int count = getChildCount();

        CustomLayoutParams params = null;

        if (widthMode==MeasureSpec.EXACTLY){

            layoutWidth=sizeWidth;
        }else {

            //如果是未指定或者wrap_content，我们都按照包裹内容做，宽度方向上只需要拿到所有子控件中宽度做大的作为布局宽度
            for ( int i = 0; i < count; i++)  {
                View child = getChildAt(i);



                cWidth = child.getMeasuredWidth();

                params= (CustomLayoutParams) child.getLayoutParams();

                //获取子控件宽度和左右边距之和，作为这个控件需要占据的宽度
                int marginWidth = cWidth+params.leftMargin+params.rightMargin ;

                //获取子控件最大宽度
                layoutWidth = cWidth > layoutWidth ? marginWidth : layoutWidth;
            }
        }


        //高度很宽度处理思想一样
        if(heightMode == MeasureSpec. EXACTLY){
            layoutHeight = sizeHeight;
        } else{
            for ( int i = 0; i < count; i++)  {
                View child = getChildAt(i);
                cHeight = child.getMeasuredHeight();
                params= (CustomLayoutParams) child.getLayoutParams();

                int marginHeight = cHeight+params.topMargin+params.bottomMargin ;

                layoutHeight = cHeight > layoutHeight ? marginHeight : layoutHeight;
            }
        }

        // 测量并保存layout的宽高
        setMeasuredDimension(layoutWidth, layoutHeight);


    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new CustomLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new CustomLayoutParams(getContext(),attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new CustomLayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof CustomLayoutParams;
    }
}
