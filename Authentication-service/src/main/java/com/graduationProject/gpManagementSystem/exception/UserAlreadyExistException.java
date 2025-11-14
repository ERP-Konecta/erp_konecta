package com.graduationProject.gpManagementSystem.exception;

public class UserAlreadyExistException extends RuntimeException {
        public UserAlreadyExistException(String message) {
            super(message);
        }
}
