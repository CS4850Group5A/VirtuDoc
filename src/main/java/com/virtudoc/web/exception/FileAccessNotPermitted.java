package com.virtudoc.web.exception;

public class FileAccessNotPermitted extends Exception {
    public FileAccessNotPermitted(String fileName, String accessUser) {
        super("File " + fileName + " access attempt blocked for user " + accessUser);
    }
}
