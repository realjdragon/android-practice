package com.jdragon.library.asymmetricgridview.widget;

import android.view.View;

import java.util.Stack;

class ViewPool<T extends View> {

    static class PoolStats {
        int size = 0;
        int hits = 0;
        int misses = 0;
        int created = 0;

        String getStats(String name) {
            return String.format("%s: size %d, hits %d, misses %d, created %d", name, size, hits,
                    misses, created);
        }
    }

    Stack<T> stack = new Stack<>();
    PoolObjectFactory<T> factory = null;
    PoolStats stats;

    ViewPool() {
        stats = new PoolStats();
    }

    ViewPool(PoolObjectFactory<T> factory) {
        this.factory = factory;
    }

    T get() {
        if (stack.size() > 0) {
            stats.hits++;
            stats.size--;
            return stack.pop();
        }

        stats.misses++;

        T object = factory != null ? factory.createObject() : null;

        if (object != null)
            stats.created++;

        return object;
    }

    void put(T object) {
        stack.push(object);
        stats.size++;
    }

    void clear() {
        stats = new PoolStats();
        stack.clear();
    }

    String getStats(String name) {
        return stats.getStats(name);
    }
}
