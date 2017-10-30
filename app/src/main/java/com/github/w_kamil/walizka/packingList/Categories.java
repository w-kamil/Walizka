package com.github.w_kamil.walizka.packingList;

import com.github.w_kamil.walizka.dao.Category;

/**
 * Created by kamil on 30.10.17.
 */
public class Categories {

    private final Category[] categories = new Category[]{Category.CLOTHES, Category.COSMETICS,
        Category.DOCUMENTS, Category.ELECTRONIC, Category.FOOD, Category.PLAY, Category.SPORT,
        Category.WORK, Category.OTHER};

    public Category[] getCategories(){return categories;}
}
