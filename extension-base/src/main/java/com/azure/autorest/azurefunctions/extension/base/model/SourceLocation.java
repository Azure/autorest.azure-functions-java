package com.azure.autorest.azurefunctions.extension.base.model;

public class SourceLocation {

    private String document;
    private SmartLocation position;

    public SmartLocation getPosition() {
        return position;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public void setPosition(SmartLocation position) {
        this.position = position;
    }
}
