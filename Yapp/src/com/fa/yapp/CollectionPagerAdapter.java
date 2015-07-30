package com.fa.yapp;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class CollectionPagerAdapter extends FragmentStatePagerAdapter {
	ObjectFragment[] pages;
    public CollectionPagerAdapter(FragmentManager fm ,ObjectFragment[] pages) {
        super(fm);
        this.pages = pages;   
        System.out.println("pageadapter started");
    }

    @Override
    public Fragment getItem(int i) {        
        return pages[i];
    }

    @Override
    public int getCount() {
        return pages.length;
    }

    
}