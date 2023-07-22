package de.bitkorn.aes.entity;

public abstract class AbstractEntity {
    public abstract void init(int index, String id, String manufactureCode, int medium, String aes, int building, int apartment);
    public abstract boolean isValid();
}
