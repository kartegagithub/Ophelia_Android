package com.kartega.eaframework.object;


import com.kartega.ophelia.ea_recycler.interfaces.EATypeInterface;

import java.io.Serializable;

public class TempObject implements Serializable,EATypeInterface {

    private String text;
    private int translationX;

    public TempObject() {
    }

    public TempObject(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTranslationX(int translationX) {
        this.translationX = translationX;
    }

    public int getTranslationX() {
        return translationX;
    }

    @Override
    public String toString() {
        return getText();
    }

    @Override
    public int getRecyclerType() {
        return 10;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TempObject)
            return text.equals(((TempObject) obj).getText());
        return false;
    }
}
