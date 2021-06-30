package trees;

/**
 *
 * @author Emerson
 */
public class BlackRedBST<Key extends Comparable<Key>, Value> {

    private Node root;
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {

        private Key key;
        private Value value;
        private Node left, right;
        boolean color;

        public Node( Key k, Value v, boolean color ) {
            this.key = k;
            this.value = v;
            this.color = color;
        }

    }

    // Verifica se o nó está marcado como um 3-node.
    private boolean isRed(Node h) {
        if (h == null) {
            return false;
        }

        return h.color == RED;
    }
    
    // Retorna a cor.
    public boolean redOrBlack( Key k ){
        Node aux = root;

        while (aux != null) {
            int cmp = k.compareTo(aux.key);

            if (cmp < 0) {
                aux = aux.left;
            } else if (cmp > 0) {
                aux = aux.right;
            } else {
                return aux.color;
            }

        }

        return false;
    }

    // Faz a rotação para a esquerda.
    private Node rotateLeft(Node h) {
        Node aux = h.right;
        h.right = aux.left;
        aux.left = h;
        aux.color = h.color;
        h.color = RED;

        return aux;
    }

    // Faz a rotação para a direita.
    private Node rotateRight(Node h) {
        Node aux = h.left;
        h.left = aux.right;
        aux.right = h;
        aux.color = h.color;
        h.color = RED;

        return aux;
    }

    // Faz a troca de cor dos nós.
    private void flipColors(Node h) {
        h.left.color = BLACK;
        h.right.color = BLACK;
        h.color = RED;
    }

    public Value get( Key k ) {
        Node aux = root;

        while (aux != null) {
            int cmp = k.compareTo(aux.key);

            if (cmp < 0) {
                aux = aux.left;
            } else if (cmp > 0) {
                aux = aux.right;
            } else {
                return aux.value;
            }

        }

        return null;
    }

    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    private Node put(Node h, Key key, Value value) {
        if (h == null) {
            return new Node(key, value, RED);
        }

        int cmp = key.compareTo( h.key );

        if (cmp < 0) {
            h.left = put( h.left, key, value );
        } else if (cmp > 0) {
            h.right = put( h.right, key, value );
        } else {
            h.value = value;
        }
        
        if( !isRed( h.left ) && isRed( h.right ) ){
            h = rotateLeft( h );
        }
        if( isRed( h.left ) && isRed( h.left.left ) ){
            h = rotateRight( h );
        }
        if( isRed( h.left ) && isRed( h.right ) ){
            flipColors( h );
        }
        
        return h;
    }

}
