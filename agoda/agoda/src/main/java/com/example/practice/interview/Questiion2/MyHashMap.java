package com.example.practice.interview.Questiion2;

import java.util.ArrayList;
import java.util.LinkedList;

public class MyHashMap<Key,Value> {

    private final int DEFAULT_CAPACITY = 10;
    private final double LOAD_FACTOR = 0.75;

    class KeyValuePair {

        Key key;
        Value value;
        KeyValuePair next;

        public KeyValuePair(){}

        public KeyValuePair(Key key, Value value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }

        public Key getKey() {
            return this.key;
        }

        public Value getValue() {
            return this.value;
        }
    }

    private int size;
    private ArrayList<LinkedList<KeyValuePair>> buckets;

    public MyHashMap() {
        this.size = 0;
        buckets =  new ArrayList<>(DEFAULT_CAPACITY);
        for(int i = 0;i<DEFAULT_CAPACITY;i++) {
            LinkedList<KeyValuePair> bucket = new LinkedList<>();
            buckets.add(bucket);
        }
    }

    public void put(Key key, Value value ) {
        int bucketIndex = getIndex(key);
        addElementInBucket(bucketIndex,key,value);
    }

    private void addElementInBucket(int bucketIndex, Key key, Value value) {
        LinkedList<KeyValuePair> bucket = buckets.get(bucketIndex);
        KeyValuePair pair = new KeyValuePair(key, value);
        bucket.add(pair);
        size++;

        if((double) size/buckets.size() > LOAD_FACTOR) {
            resize();
        }

    }

    public Value get(Key key ) {
        int bucketIndex = getIndex(key);
        return getValueFromKey(bucketIndex,key);
    }

    private Value getValueFromKey(int bucketIndex, Key key) {
        LinkedList<KeyValuePair> bucket = buckets.get(bucketIndex);
        for(KeyValuePair pair : bucket) {
            if(pair.getKey().equals(key)) {
                return pair.getValue();
            }
        }
        return null;
    }

    public boolean containsKey(Key key) {
        int bucketIndex =  getIndex(key);
        return isContainsKey(bucketIndex, key);
    }

    private boolean isContainsKey(int bucketIndex, Key key) {
        LinkedList<KeyValuePair> bucket = buckets.get(bucketIndex);
        for(KeyValuePair pair : bucket) {
            if(pair.getKey().equals(key)) {
                return true;
            }
        }
        return false;   
    }

    public boolean removeKey(Key key) {
        int bucketIndex =  getIndex(key);
        return removeElementFromBucket(bucketIndex, key);
    }

    private boolean removeElementFromBucket(int bucketIndex, Key key) {
        LinkedList<KeyValuePair> bucket = buckets.get(bucketIndex);
        if(bucket.size() != 0) {
            for(KeyValuePair pair : bucket) {
                if(pair.getKey().equals(key)) {
                    bucket.remove(key);
                    size--;
                    return true;
                }
            }
        }
        return false;
    }

    private int getIndex(Key key) {
        return Math.abs(key.hashCode()) % buckets.size();
    }

    private void resize() {
        // resize logic
    }
}
