package com.kocak.kmenuserver.exception;

public class FloorNotFoundException extends RuntimeException {
    public FloorNotFoundException(String id) {
        super("Floor not found with id: " + id);
    }
    public FloorNotFoundException() {
        super("Floor not found ");
    }
}
