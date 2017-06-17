package com.github.w_kamil.walizka.dao;


import com.github.w_kamil.walizka.R;

public enum Category {
    CLOTHES(R.drawable.ic_clothes),
    COSMETICS(R.drawable.ic_cosmetics),
    DOCUMENTS(R.drawable.ic_documents),
    ELECTRONIC(R.drawable.ic_electronic),
    SPORT(R.drawable.ic_sport),
    PLAY(R.drawable.ic_play),
    WORK(R.drawable.ic_work),
    FOOD(R.drawable.ic_food),
    OTHER(R.drawable.ic_other);

    private int drawingResource;

    Category(int drawingRexource) {
        this.drawingResource = drawingRexource;
    }

    public int getDrawingResource() {
        return drawingResource;
    }
}
