package app.cci.com.bliblistream.Model.CustomClass;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import app.cci.com.bliblistream.R;

/**
 * Created by DaRk-_-D0G on 09/10/2014.
 */
public class AnimatingRelativeLayout extends RelativeLayout
{
    Context context;
    Animation inAnimation;
    Animation outAnimation;

    public AnimatingRelativeLayout(Context context)
    {
        super(context);
        this.context = context;
        initAnimations();

    }

    public AnimatingRelativeLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        initAnimations();
    }

    public AnimatingRelativeLayout(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        this.context = context;
        initAnimations();
    }

    private void initAnimations()
    {
        inAnimation = (Animation) AnimationUtils.loadAnimation(context, R.anim.animation_translatevershaut);
        outAnimation = (Animation) AnimationUtils.loadAnimation(context,R.anim.animation_translatevershaut);
    }

    public void show()
    {
        if (isVisible()) return;
        show(true);
    }

    public void show(boolean withAnimation)
    {
        if (withAnimation) this.startAnimation(inAnimation);
        this.setVisibility(View.VISIBLE);
    }

    public void hide()
    {
        if (!isVisible()) return;
        hide(true);
    }

    public void hide(boolean withAnimation)
    {
        if (withAnimation) this.startAnimation(outAnimation);
        this.setVisibility(View.GONE);
    }

    public boolean isVisible()
    {
        return (this.getVisibility() == View.VISIBLE);
    }

    public void overrideDefaultInAnimation(Animation inAnimation)
    {
        this.inAnimation = inAnimation;
    }

    public void overrideDefaultOutAnimation(Animation outAnimation)
    {
        this.outAnimation = outAnimation;
    }
}
