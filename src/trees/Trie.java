package trees;

/**
 *
 * @author Emerson
 * @param <Value> value type for trie
 */
public class Trie<Value> {
    private static final int R = 256;
    private Node root;
    private int size;
    
    private static int level = 0;
    private static final String identationString = "  ";
    private String localIdentation = "";
    private final StringBuilder sb = new StringBuilder();
    
    private static class Node{
        private Object value;
        private final Node[] next = new Node[R];
    }
    
    public void put(String key, Value val){
        root = put( root, key, val, 0 );
        size++;
    }
    
    private Node put(Node x, String key, Value val, int d){
        if( x == null )
            x = new Node();
        
        if( d == key.length() ){
            x.value = val;
            return x;
        }
        
        char c = key.charAt( d );
        x.next[c] = put( x.next[c], key, val, d+1 );
        return x;
    }
    
    public boolean contains(String key){
        return get( key ) != null;
    }
    
    public Value get(String key){
        Node x = get( root, key, 0 );
        
        if( x == null )
            return null;
        
        return (Value) x.value;
    }
    
    private Node get(Node x, String key, int d){
        if( x == null )
            return null;
        
        if( d == key.length() )
            return x;
        
        char c = key.charAt( d );
        return get( x.next[c], key, d+1 );
    }
    
    public void delete(String key){
        root = delete( root, key, 0 );
    }
    
    private Node delete(Node x, String key, int d){
        if( x == null )
            return null;
        
        if( d == key.length() ){
            x.value = null;
            size--;
        }else{
            char c = key.charAt( d );
            x.next[c] = delete( x.next[c], key, d+1 );
        }
        
        if( x.value != null )
            return x;
        
        for(char c = 0; c < R; c++){
            if( x.next[c] != null )
                return x;
        }
        return null;
    }
    
    public int size(){
        return size;
    }
    
    public Node getRoot(){
        return root;
    }
   
    public void visitRoot(Node x){
        if( x == null ){
            return;
        }
        
        openBranch( " ", "raiz" );
        
        for (int i = 0; i < x.next.length; i++) {
            if( x.next[i] != null ){
                char c = (char) i;
                openBranch( "" + c );
                
                generateChildNode( x.next[i] );
                
                closeBranch();
            }
        }
        
        closeBranch();
    }
    
    private void generateChildNode(Node x){
        for (int i = 0; i < x.next.length; i++) {
            if( x.next[i] != null ){
                char c = (char) i;
                openBranch( "" + c );
                
                generateChildNode( x.next[i] );
                
                closeBranch();
            }
        }
    }
    
    public String getXMLArvore() {
        
        StringBuilder intSb = new StringBuilder();
        
        intSb.append( "<tree>" ).append( "\n" );
        intSb.append( identationString ).append( "<declarations>" ).append( "\n" );
        intSb.append( identationString ).append( identationString ).append( "<attributeDecl name=\"nome\" type=\"String\"/>" ).append( "\n" );
        intSb.append( identationString ).append( identationString ).append( "<attributeDecl name=\"tipo\" type=\"String\"/>" ).append( "\n" );
        intSb.append( identationString ).append( "</declarations>" ).append( "\n" );
        intSb.append( sb );
        intSb.append( "</tree>" );
        
        return intSb.toString();
        
    }
    
    private void openBranch( String branchName, String nodeType ) {
        level++;
        localIdentation = generateIdentation();
        sb.append( localIdentation ).append( "<branch>" ).append( "\n" );
        sb.append( localIdentation ).append( identationString ).append( "<attribute name=\"nome\" value=\" " ).append( branchName ).append( " \"/>" ).append( "\n" );
        sb.append( localIdentation ).append( identationString ).append( "<attribute name=\"tipo\" value=\"" ).append( nodeType ).append( "\"/>" ).append( "\n" );
    }
    
    private void openBranch( String branchName ) {
        openBranch( branchName, "galho" );
    }
    
    private void closeBranch() {
        sb.append( localIdentation ).append( "</branch>" ).append( "\n" );
        level--;
        localIdentation = generateIdentation();
    }
    
    private String generateIdentation() {
        StringBuilder sb = new StringBuilder();
        for ( int i = 0; i < level; i++ ) {
            sb.append( identationString );
        }
        return sb.toString();
    }
    
}
