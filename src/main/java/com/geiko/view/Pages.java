package com.geiko.view;


public enum Pages {
    REGISTER("isRegisterPage"),
    ACTIVATION("isActivationPage");
    private String viewCode;

    Pages(String viewCode) {
        this.viewCode = viewCode;
    }

    public String getViewCode(){
        return viewCode;
    }

}
