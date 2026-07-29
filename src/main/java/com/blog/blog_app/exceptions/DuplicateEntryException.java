package com.blog.blog_app.exceptions;

public class DuplicateEntryException extends RuntimeException {
    String resourceName;
    String fieldName;
    String fieldValueStr;


    public DuplicateEntryException(String resourceName, String fieldName, String fieldValueStr) {
        super(String.format("%s already register with this %s : %s", resourceName, fieldName, fieldValueStr));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValueStr = fieldValueStr;
    }


}
