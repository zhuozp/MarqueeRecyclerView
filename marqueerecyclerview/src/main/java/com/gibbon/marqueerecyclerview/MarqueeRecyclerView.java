package com.gibbon.marqueerecyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.SoftReference;

/**
 * @author zhipeng.zhuo
 * @date 2020-03-18
 */
public class MarqueeRecyclerView extends RecyclerView {

    private static final long TIME_AUTO_POLL = 16;
    AutoPollTask autoPollTask;
    private boolean running; //标示是否正在自动轮询
    private boolean canRun = false;//标示是否可以自动轮询,可在不需要的是否置false

    public MarqueeRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public void setAutoRun(boolean canRun) {
        this.canRun = canRun;
    }

    static class AutoPollTask implements Runnable {
        private final SoftReference<MarqueeRecyclerView> mReference;
        public AutoPollTask(MarqueeRecyclerView reference) {
            this.mReference = new SoftReference<>(reference);
        }
        @Override
        public void run() {
            MarqueeRecyclerView recyclerView = mReference.get();
            if (recyclerView != null && recyclerView.running &&recyclerView.canRun) {
                recyclerView.scrollBy(2, 2);
                recyclerView.postDelayed(recyclerView.autoPollTask,recyclerView.TIME_AUTO_POLL);
            }
        }
    }
    //开启:如果正在运行,先停止->再开启
    public void start() {
        if (running)
            stop();

        if (this.canRun) {
            if (this.autoPollTask == null) {
                autoPollTask = new AutoPollTask(this);
            }

            running = true;
            postDelayed(autoPollTask,TIME_AUTO_POLL);
        }
    }
    public void stop(){
        running = false;
        removeCallbacks(autoPollTask);
    }
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (running)
                    stop();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                if (canRun)
                    start();
                break;
        }
        return super.onTouchEvent(e);
    }
}
