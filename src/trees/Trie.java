package trees;

import edu.princeton.cs.algs4.StdRandom;

/**
 *
 * @author Emerson
 */
public class Trie<Value> {
    private static final int R = 256;
    private Node root;
    private int size;
    
    private static class Node{
        private Object value;
        private Node[] next = new Node[R];
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
    
    /**
     * Adaptar para gerar a árvore gráfica.
     * 
     * @param x -> node root
     */
    public void visitRoot(Node x){
        for (Node node : x.next) {
            if( node != null ){
                aux(node);
                System.out.println();
            }
                
        }
    }
    
    /**
     * Adaptar para gerar a árvore gráfica.
     * 
     * @param x -> next node
     */
    public void aux(Node x){
        for (Node node : x.next) {
            
            if( node != null ){
                aux(node);
                System.out.println( node.value );
            }
            
        }
    }
    
    // O main foi só para testes, lembrar de excluir na versão final da classe.
    public static void main(String[] args) {
        Trie trie = new Trie();
        
        String[] str = "is th ti fo al go pe to co to th ai of th pa".split(" ");
        for( String s : str )
            trie.put( s, StdRandom.uniform( 0, 100 ) );
        
        Node x = trie.getRoot();
        trie.visitRoot(x);
        for( String s : str )
            System.out.println( "\t" + s + " -> " + trie.get(s) );
        
        
        
        //System.out.println(x.next['t'].next['h'].value);
        
    }
}
