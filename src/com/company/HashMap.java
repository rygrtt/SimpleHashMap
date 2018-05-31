package com.company;

import java.util.ArrayList;

// a HashMap implementation back with an ArrayList of buckets
class HashMap<K, V> {

    private final double loadFactor = 0.7;
    // an ArrayList of chained nodes
    private ArrayList<HashNode<K, V>> bucketArray;
    private int numBuckets;
    private int numNodes; // number of nodes

    public HashMap() {

        numBuckets = 10;
        numNodes = 0;
        bucketArray = new ArrayList<>();

        for (int i = 0; i < numBuckets; i++) {
            bucketArray.add(null);
        }
    }

    public int getNumNodes() {
        return numNodes;
    }

    public boolean isEmpty() {
        return this.getNumNodes() == 0;
    }

    // gets hash code from key and generates index of our array list
    private int getBucketIndex(K key) {
        int hashCode = key.hashCode();

        // compresses hashcode via modulus, ensures it's within the bounds of our array
        // (remainder will always be smaller than the numNodes of the array)
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
                // node with that key already exists, so we overwrite it and exit
                node.setValue(value);
                return;
            }
            node = node.getNext();
        }

        // Insert key at top of chain
        numNodes++;
        node = bucketArray.get(bucketIndex);
        HashNode<K, V> newNode = new HashNode<K, V>(key, value);

        //our new node becomes the head node in our bucket
        newNode.setNext(node);

        // place the new node in the bucket
        bucketArray.set(bucketIndex, newNode);

        // if the new node puts us over the load factor,
        // we double the numNodes of the ArrayList and rearrange our nodes

        double test = (1.0 * numNodes) / numBuckets;

        if ( ((1.0 * numNodes) / numBuckets) >= loadFactor) {
            ArrayList<HashNode<K, V>> temp = bucketArray;
            bucketArray = new ArrayList();

            numBuckets = 2 * numBuckets;
            numNodes = 0;

            for (int i = 0; i < numBuckets; i++) {
                bucketArray.add(null);
            }

            // cycle through the old ArrayList buckets...
            for (HashNode<K, V> currentNode : temp) {
                //... and each chain in each bucket
                while (currentNode != null) {
                    // recursively call this method to add each node to the proper bucket
                    add(currentNode.getKey(), currentNode.getValue());

                    currentNode = currentNode.getNext();
                }
            }
        }
    }

    public V remove(K key) {

        int bucketIndex = getBucketIndex(key);

        HashNode<K,V> currentNode = bucketArray.get(bucketIndex);

        HashNode<K,V> prevNode = null;

        while (currentNode != null) {
            if (currentNode.getKey().equals(key)) {
                break;
            }

            prevNode = currentNode;
            currentNode = currentNode.getNext();
        }

        // key not found, we exit the method with null
        if (currentNode == null) {
            return null;
        }

        numNodes--;

        if (prevNode != null) {
            prevNode.setNext(currentNode.getNext());
        } else {
            bucketArray.set(bucketIndex, currentNode.getNext());
        }

        return currentNode.getValue();
    }
}

