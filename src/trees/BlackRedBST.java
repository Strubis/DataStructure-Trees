package trees;

/**
 *
 * @author Emerson
 */
public class BlackRedBST<Key extends Comparable<Key>, Value> {

    private static int level = 0;
    private static final String identationString = "  ";
    private String localIdentation = "";
    private StringBuilder sb = new StringBuilder();
    
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
    
    // A partir daqui é uma implementação teste para uma exibição gráfica.
    public Node getRoot(){
        return root;
    }
    
    public void visitRoot(Node x){
        // Se não tiver filhos, criamos um nó folha.
        if( x.left == null && x.right == null ){
            createLeaf( x.key.toString(), x.color );
            return;
        }
        
        openBranch( x.key.toString(), "raiz", x.color );
        
        if( x.left != null ){
            visitLeft( x.left );
        }else{
            createLeaf( "null", x.color );
        }
        
        if( x.right != null ){
            visitRight( x.right );
        }else{
            createLeaf( "null", x.color );
        }
        
        closeBranch();
    }
    
    public void visitLeft(Node l){
        if( l.left == null && l.right == null ){
            createLeaf( l.key.toString(), l.color );
            return;
        }
        
        openBranch( l.key.toString(), l.color );
        
        if( l.left != null ){
            visitLeft( l.left );
        }else{
            createLeaf( "null", l.color );
        }
        
        if( l.right != null ){
            visitRight( l.right );
        }else{
            createLeaf( "null", l.color );
        }
        
        closeBranch();
    }
    
    public void visitRight(Node r){
        if( r.left == null && r.right == null ){
            createLeaf( r.key.toString(), r.color );
            return;
        }
        
        openBranch( r.key.toString(), r.color );
        
        if( r.left != null ){
            visitLeft( r.left );
        }else{
            createLeaf( "null", r.color );
        }
        
        if( r.right != null ){
            visitRight( r.right );
        }else{
            createLeaf( "null", r.color );
        }
        
        closeBranch();
    }
    
    public String getXMLArvore() {
        
        StringBuilder intSb = new StringBuilder();
        
        intSb.append( "<tree>" ).append( "\n" );
        intSb.append( identationString ).append( "<declarations>" ).append( "\n" );
        intSb.append( identationString ).append( identationString ).append( "<attributeDecl name=\"nome\" type=\"String\"/>" ).append( "\n" );
        intSb.append( identationString ).append( identationString ).append( "<attributeDecl name=\"tipo\" type=\"String\"/>" ).append( "\n" );
        intSb.append( identationString ).append( identationString ).append( "<attributeDecl name=\"cor\" type=\"String\"/>" ).append( "\n" );
        intSb.append( identationString ).append( "</declarations>" ).append( "\n" );
        intSb.append( sb );
        intSb.append( "</tree>" );
        
        return intSb.toString();
        
    }
    
    private void openBranch( String branchName, String nodeType, Boolean color ) {
        level++;
        localIdentation = generateIdentation();
        sb.append( localIdentation ).append( "<branch>" ).append( "\n" );
        sb.append( localIdentation ).append( identationString ).append( "<attribute name=\"nome\" value=\" " ).append( branchName ).append( " \"/>" ).append( "\n" );
        sb.append( localIdentation ).append( identationString ).append( "<attribute name=\"tipo\" value=\"" ).append( nodeType ).append( "\"/>" ).append( "\n" );
        sb.append( localIdentation ).append( identationString ).append( "<attribute name=\"cor\" value=\" " ).append( color ).append( " \"/>" ).append( "\n" );
    }
    
    private void openBranch( String branchName, Boolean color ) {
        openBranch( branchName, "galho", color );
    }
    
    private void closeBranch() {
        sb.append( localIdentation ).append( "</branch>" ).append( "\n" );
        level--;
        localIdentation = generateIdentation();
    }
    
    private void createLeaf( String leafValue, Boolean color ) {
        sb.append( localIdentation ).append( "<leaf>" ).append( "\n" );
        sb.append( localIdentation ).append( identationString ).append( "<attribute name=\"nome\" value=\" " ).append( leafValue ).append( " \"/>" ).append( "\n" );
        sb.append( localIdentation ).append( identationString ).append( "<attribute name=\"tipo\" value=\"folha\"/>" ).append( "\n" );
        sb.append( localIdentation ).append( identationString ).append( "<attribute name=\"cor\" value=\" " ).append( color ).append( " \"/>" ).append( "\n" );
        sb.append( localIdentation ).append( "</leaf>" ).append( "\n" );
    }
    
    private String generateIdentation() {
        StringBuilder sb = new StringBuilder();
        for ( int i = 0; i < level; i++ ) {
            sb.append( identationString );
        }
        return sb.toString();
    }
    
}
