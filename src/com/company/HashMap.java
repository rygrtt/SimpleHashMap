package com.company;

import java.util.ArrayList;

// a HashMap implementation back with an ArrayList of buckets
class HashMap<K, V> {

    private final double loadFactor = 0.7;
    // an ArrayList of chained nodes
    private ArrayList<HashNode<K, V>> bucketArray;
    private int numBuckets;
    private int size;

    public HashMap() {

        numBuckets = 10;
        size = 0;

        for (int i = 0; i < numBuckets; i++) {
            bucketArray.add(null);
        }
    }


    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return this.getSize() == 0;
    }

    // gets hash code from key and generates index of our array list
    private int getBucketIndex(K key) {
        int hashCode = key.hashCode();

        // compresses hashcode via modulus, ensures it's within the bounds of our array
        // (remainder will always be smaller than the size of the array)
        // lots of collisions from our function, but it's just a simple example!
        int index = hashCode % numBuckets;

        return index;
    }

    public V get(K key) {

        //retrieving the index of the bucket in which our K, V are found
        int bucketIndex = getBucketIndex(key);

        // gets first node in our bucket
        HashNode<K, V> node = bucketArray.get(bucketIndex);

        // now search the chain within our bucket
        while (node != null) {
            if (node.getKey().equals(key)) {
                return node.getValue();
            }
            node = node.getNext();
        }

        return null;
    }

    public void add(K key, V value) {
        int bucketIndex = getBucketIndex(key);

        // gets first node in bucket
        HashNode<K, V> node = bucketArray.get(bucketIndex);

        while (node != null) {
            if (node.getKey().equals(key)) {
                // node with that key already exists, so we're done!
                return;
            }
            node = node.getNext();
        }

        //Insert key at top of chain
        size++;
        node = bucketArray.get(bucketIndex);
        HashNode<K, V> newNode = new HashNode<K, V>(key, value);

        //our new node becomes the head node in our bucket
        newNode.setNext(node);

        // place the new node in the bucket
        bucketArray.set(bucketIndex, newNode);

        // if the addition put us over the load factor,
        // we double the size of the ArrayList and rearrange our nodes

        if ((1.0 * size) >= loadFactor) {
            ArrayList<HashNode<K, V>> temp = bucketArray;
            bucketArray = new ArrayList();

            numBuckets = 2 * numBuckets;

            size = 0;

            for (int i = 0; i < numBuckets; i++) {
                bucketArray.add(null);
            }

            // cycle through the old ArrayList buckets...
            for (HashNode<K, V> headNode : temp) {
                //... and each chain in the buckets
                while (headNode != null) {
                    // recursively call this method to add each node to the proper bucket
                    add(headNode.getKey(), headNode.getValue());
                    headNode.getNext();
                }
            }
        }
    }

    public void remove(K key) {

    }
}

