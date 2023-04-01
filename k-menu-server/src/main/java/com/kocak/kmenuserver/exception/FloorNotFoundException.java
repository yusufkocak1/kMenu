package com.kocak.kmenuserver.exception;

public class FloorNotFoundException extends RuntimeException {
    public FloorNotFoundException(Long id) {
        super("Floor not found with id: " + id);
    }
}
