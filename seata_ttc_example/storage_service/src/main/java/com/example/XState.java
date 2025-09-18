package com.example;

public enum XState {

    TRY(1), CANCEL(2), COMMIT(3);

    XState(int state) {
        this.state = state;
    }

    private final int state;

    public int getState() {
        return state;
    }

}