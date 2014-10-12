package app.cci.com.bliblistream.Model.CustomClass;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Class qui customise le EditText
 * @author DaRk-_-D0G on 17/06/2014.
 *
 */
public class CustomEditText extends EditText {




    final Drawable imgX = getResources().getDrawable(android.R.drawable.presence_offline ); // X image
    public boolean dejaClic = false;

    /**
     * Contructeur 1
     * @param context Context
     */
    public CustomEditText(Context context) {
        super(context);

        init();
    }

    /**
     * Contructeur 2
     * @param context Context
     * @param attrs AttributeSet
     * @param defStyle int
     */
    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init();
    }

    /**
     * Contructeur
     * @param context Context
     * @param attrs AttributeSet
     */
    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    /**
     * Initilisation et ajout literner
     */
    void init()  {

        /* Ajout bouton x */
        imgX.setBounds(0, 0, imgX.getIntrinsicWidth(), imgX.getIntrinsicHeight());

        /* initilise le textView, et gere le bouton */
        manageClearButton();

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                CustomEditText et = CustomEditText.this;
                /* si un X */
                if (et.getCompoundDrawables()[2] == null) return false;

                /* lors du clic decu active deja clic pour modifier le text */
                if (event.getAction() != MotionEvent.ACTION_UP) {
                    if(!dejaClic) {
                        et.setText("");
                    }
                    dejaClic = true;
                    return false;
                }

                /* si clic sur la croix */
                if (event.getX() > et.getWidth() - et.getPaddingRight() - imgX.getIntrinsicWidth()) {
                    et.setText("");

                    CustomEditText.this.removeClearButton();
                }
                return false;
            }
        });

        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CustomEditText.this.manageClearButton();
            }

            @Override
            public void afterTextChanged(Editable arg0) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        });
    }

    /**
     * manager du bouton x
     */
    void manageClearButton() {
        if (this.getText().toString().equals("") )
            removeClearButton();
        else
            addClearButton();
    }

    /**
     * Add un bouton x
     */
    void addClearButton() {
        this.setCompoundDrawables(this.getCompoundDrawables()[0],
                this.getCompoundDrawables()[1],
                imgX,
                this.getCompoundDrawables()[3]);
    }

    /**
     * Enlever le bouton x
     */
    void removeClearButton() {
        this.setCompoundDrawables(this.getCompoundDrawables()[0],
                this.getCompoundDrawables()[1],
                null,
                this.getCompoundDrawables()[3]);
    }
}

