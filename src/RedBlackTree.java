public class RedBlackTree<K extends Comparable<K>, V> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    public class Node {
        public K key;
        public V value;
        public Node left, right, parent;
        public boolean color;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.color = RED;
        }
    }

    private Node root;
    private int size;

    public RedBlackTree() {
        this.root = null;
        this.size = 0;
    }

    public void put(K key, V value) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        
        if (root == null) {
            root = new Node(key, value);
            root.color = BLACK;
            size++;
            return;
        }

        Node parent = null;
        Node current = root;

        while (current != null) {
            parent = current;
            int cmp = key.compareTo(current.key);
            if (cmp < 0) {
                current = current.left;
            } else if (cmp > 0) {
                current = current.right;
            } else {
                current.value = value;
                return;
            }
        }

        Node newNode = new Node(key, value);
        newNode.parent = parent;

        int cmp = key.compareTo(parent.key);
        if (cmp < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }

        size++;
        fixInsert(newNode);
    }

    private void fixInsert(Node node) {
        while (node != root && node.parent.color == RED) {
            if (node.parent == node.parent.parent.left) {
                Node uncle = node.parent.parent.right;
                if (uncle != null && uncle.color == RED) {
                    node.parent.color = BLACK;
                    uncle.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.right) {
                        node = node.parent;
                        rotateLeft(node);
                    }
                    node.parent.color = BLACK;
                    node.parent.parent.color = RED;
                    rotateRight(node.parent.parent);
                }
            } else {
                Node uncle = node.parent.parent.left;
                if (uncle != null && uncle.color == RED) {
                    node.parent.color = BLACK;
                    uncle.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.left) {
                        node = node.parent;
                        rotateRight(node);
                    }
                    node.parent.color = BLACK;
                    node.parent.parent.color = RED;
                    rotateLeft(node.parent.parent);
                }
            }
        }
        root.color = BLACK;
    }

    private void rotateLeft(Node node) {
        Node rightChild = node.right;
        node.right = rightChild.left;

        if (rightChild.left != null) {
            rightChild.left.parent = node;
        }

        rightChild.parent = node.parent;

        if (node.parent == null) {
            root = rightChild;
        } else if (node == node.parent.left) {
            node.parent.left = rightChild;
        } else {
            node.parent.right = rightChild;
        }

        rightChild.left = node;
        node.parent = rightChild;
    }

    private void rotateRight(Node node) {
        Node leftChild = node.left;
        node.left = leftChild.right;

        if (leftChild.right != null) {
            leftChild.right.parent = node;
        }

        leftChild.parent = node.parent;

        if (node.parent == null) {
            root = leftChild;
        } else if (node == node.parent.right) {
            node.parent.right = leftChild;
        } else {
            node.parent.left = leftChild;
        }

        leftChild.right = node;
        node.parent = leftChild;
    }

    public V get(K key) {
        Node node = findNode(key);
        return node != null ? node.value : null;
    }

    public boolean containsKey(K key) {
        return findNode(key) != null;
    }

    private Node findNode(K key) {
        if (key == null) return null;
        
        Node current = root;
        while (current != null) {
            int cmp = key.compareTo(current.key);
            if (cmp < 0) {
                current = current.left;
            } else if (cmp > 0) {
                current = current.right;
            } else {
                return current;
            }
        }
        return null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void forEach(java.util.function.BiConsumer<K, V> action) {
        inOrderTraversal(root, action);
    }

    private void inOrderTraversal(Node node, java.util.function.BiConsumer<K, V> action) {
        if (node != null) {
            inOrderTraversal(node.left, action);
            action.accept(node.key, node.value);
            inOrderTraversal(node.right, action);
        }
    }

    public K firstKey() {
        if (root == null) return null;
        Node current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.key;
    }

    public K lastKey() {
        if (root == null) return null;
        Node current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.key;
    }

    public Node getRoot() {
        return root;
    }

    public java.util.List<K> prefixSearch(K prefix) {
        java.util.List<K> results = new java.util.ArrayList<>();
        if (prefix == null) return results;
        prefixSearchHelper(root, prefix.toString().toLowerCase(), results);
        return results;
    }

    private void prefixSearchHelper(Node node, String prefix, java.util.List<K> results) {
        if (node == null) return;
        
        String nodeKey = node.key.toString().toLowerCase();
        int cmp = nodeKey.compareTo(prefix);
        if (cmp < 0) {
            prefixSearchHelper(node.right, prefix, results);
        }
        else if (cmp > 0) {
            if (nodeKey.startsWith(prefix)) {
                results.add(node.key);
            }
            prefixSearchHelper(node.left, prefix, results);
        } 
        else {
            if (nodeKey.startsWith(prefix)) {
                results.add(node.key);
            }
            prefixSearchHelper(node.left, prefix, results);
            prefixSearchHelper(node.right, prefix, results);
        }
    }
}
