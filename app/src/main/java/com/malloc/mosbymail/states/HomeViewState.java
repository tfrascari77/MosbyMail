package com.malloc.mosbymail.states;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;
import com.malloc.mosbymail.views.HomeView;

public class HomeViewState implements ViewState<HomeView> {

    private final static int STATE_FEED = 1;

    private int mState = STATE_FEED;

    @Override
    public void apply(HomeView view, boolean retained) {
        switch (mState) {
            case STATE_FEED:
                view.showFeed();
                break;
        }
    }

    public void setStateFeed() {
        mState = STATE_FEED;
    }
}
