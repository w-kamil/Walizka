package com.github.w_kamil.walizka.dao;


import com.github.w_kamil.walizka.R;

public enum Category {
    CLOTHES(R.string.clothes, R.drawable.ic_clothes),
    COSMETICS(R.string.cosmetics, R.drawable.ic_cosmetics),
    DOCUMENTS(R.string.documents, R.drawable.ic_documents),
    ELECTRONIC(R.string.electronic, R.drawable.ic_electronic),
    SPORT(R.string.sports, R.drawable.ic_sport),
    PLAY(R.string.play, R.drawable.ic_play),
    WORK(R.string.work, R.drawable.ic_work),
    FOOD(R.string.food, R.drawable.ic_food),
    OTHER(R.string.other, R.drawable.ic_other);

    private int drawingResource;
    private int categoryName;

    Category(int categoryName, int drawingRexource) {
        this.drawingResource = drawingRexource;
        this.categoryName = categoryName;
    }

    public int getDrawingResource() {
        return drawingResource;
    }
    public int getCategoryName() {
        return categoryName;
    }
}
