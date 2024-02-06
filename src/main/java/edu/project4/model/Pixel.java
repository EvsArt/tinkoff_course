package edu.project4.model;

import java.awt.Color;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Pixel {

    private final ReadWriteLock hitsLock = new ReentrantReadWriteLock();
    private final ReadWriteLock colorLock = new ReentrantReadWriteLock();
    private int hitsCount = 0;
    private Color color = Color.BLACK;
    private double normal;

    public int getHitsCount() {
        try {
            hitsLock.readLock().lock();
            return hitsCount;
        } finally {
            hitsLock.readLock().unlock();
        }

    }

    public int incHitsCount() {
        try {
            hitsLock.writeLock().lock();
            return ++hitsCount;
        } finally {
            hitsLock.writeLock().unlock();
        }
    }

    public Color getColor() {
        try {
            colorLock.readLock().lock();
            return color;
        } finally {
            colorLock.readLock().unlock();
        }
    }

    public void setColor(Color color) {
        try {
            colorLock.writeLock().lock();
            this.color = color;
        } finally {
            colorLock.writeLock().unlock();
        }
    }

    public double getNormal() {
        return normal;
    }

    public void setNormal(double normal) {
        this.normal = normal;
    }
}
