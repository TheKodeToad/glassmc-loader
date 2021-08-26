package com.github.glassmc.loader.exception;

public class NoSuchInterfaceException extends RuntimeException {

    public NoSuchInterfaceException(Class<?> interfaceClass) {
        super(String.format("Interface of type %s requested, but not present", interfaceClass.getName()));
    }

}
