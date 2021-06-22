package trees;

/**
 * Classe criada para o funcionamento de um nó da árvore.
 *
 * @author Emerson
 */
public class BST <Key extends Comparable<Key>, Value>{
    private Node root;
    
    private static int level = 0;
    private static final String identationString = "  ";
    private String localIdentation = "";
    private StringBuilder sb = new StringBuilder();
    
    
    private class Node{
        private Key key;
        private Value val;
        private Node left, right;
        private int count;

        public Node( Key key, Value val, int size ){
            this.key = key;
            this.val = val;
            this.count = size;
        }
    }
    
    public void put( Key key, Value val ){
        root = put( key, val, root );
    }
    
    private Node put( Key key, Value val, Node root ){
        // Se não existir nenhum nó, criamos.
        if( root == null )
            return new Node( key, val, 1 );
        
        // Comparação para ver qual direção iremos caminhar.
        int comp = key.compareTo( root.key );
        
        // Se comp < 0 vamos para a esquerda, comp > 0 vamos para a direita e
        // caso seja igual só atualizamos o valor.
        if( comp < 0 )
            root.left = put( key, val, root.left );
        else if( comp > 0 )
            root.right = put( key, val, root.right );
        else
            root.val = val;
        
        // Quantidade de nós filhos.
        root.count = 1 + size( root.left ) + size( root.right );
        
        return root;
    }
    
    public Value get( Key key ){
        // Criamos um nó auxiliar para buscarmos, estamos fazendo a busca a
        // partir da raiz.
        Node aux = root;
        
        // Retornamos nulo se não encontrarmos, caso contrário buscamos pelo
        // nó através da comparação da chave.
        while( aux != null ){
            int comp = key.compareTo( aux.key );
            
            if( comp < 0 )
                aux = aux.left;
            else if( comp > 0 )
                aux = aux.right;
            else
                return aux.val;
        }
        
        return null;
    }
    
    public void delete( Key key ){
        root = delete( key, root );
    }
    
    private Node delete( Key key, Node root ){
        if( root == null )
            return null;
        
        int comp = key.compareTo( root.key );
        
        // Busca a chave.
        if( comp < 0 ){
            root.left = delete( key, root.left );
        }else if( comp > 0 ){
            root.right = delete( key, root.right );
        }else{
            // Nenhum filho a direita.
            if( root.left == null )
                return root.right;
            // Nenhum filho a esquerda.
            if( root.right == null )
                return root.left;
            
            // Substitui pelo sucessor.
            Node aux = root;
            root.key = min( aux.right );
            root.right = deleteMin( aux.right );
            root.left = aux.left;
        }
        
        // Atualiza o contador.
        root.count = 1 + size( root.left ) + size( root.right );
        return root;
    }
    
    public void deleteMin(){
        root = deleteMin( root );
    }
    
    private Node deleteMin( Node root ){
        if( root.left == null )
            return root.right;
        
        root.left = deleteMin( root.left );
        root.count = 1 + size( root.left ) + size( root.right );
        
        return root;
    }
    
    public Key min(){
        // Retornamos nulo se não existe árvore, caso contrário buscamos.
        if( root == null )
            return null;
        
        return min( root.left );
    }
    
    private Key min( Node root ){
        // Se não existir filho na esquerda retornamos a chave do nó que estamos,
        // caso contrário continuamos buscando pela esquerda.
        if( root.left == null )
            return root.key;
        
        return min( root.left );
    }
    
    public Key floor( Key key ){
        // Criamos um nó auxiliar para encontrar o floor.
        Node aux = floor( key, root );
        
        // Retornamos nulo se não encontramos.
        if( aux == null )
            return null;
        
        return aux.key;
    }
    
    private Node floor( Key key, Node root ){
        if( root == null )
            return null;
        
        // Comparação que irá fazer todo o caminho (esquerda ou direita).
        int comp = key.compareTo( root.key );
        
        // Se comp < 0, vamos para a esquerda. Se comp == 0, encontramos.
        if( comp < 0 )
            return floor( key, root.left );
        else if( comp == 0 )
            return root;
        
        // Caminho para a direita, porém guarda-se o valor do nó origem caso 
        // não encontre um valor menor.
        Node aux = floor( key, root.right );
        if( aux != null )
            return aux;
        else
            return root;
    }
    
    public int size(){
        // Calculamos o tamanho saindo da raiz.
        return size( root );
    }
    
    private int size( Node root ){
        // Tamanho 0 se a árvore não existe, caso contrário pegamos a quantidade.
        if( root == null )
            return 0;
        
        return root.count;
    }
    
    public int rank( Key key ){
        // Calculamos quantas chaves são menores que key saindo da raiz.
        return rank( key, root );
    }
    
    private int rank( Key key, Node root ){
        // Se a árvore não existe retornamos 0.
        if( root == null )
            return 0;
        
        // Comparação
        int comp = key.compareTo( root.key );
        
        if( comp < 0 )
            return rank( key, root.left );
        else if( comp > 0 )
            return 1 + size( root.left ) + rank( key, root.right );
        else
            return size( root.left );
    }
    
    // A partir daqui é uma implementação teste para uma exibição gráfica.
    public Node getRoot(){
        return root;
    }
    
    public void visitRoot(Node x){
        // Se não tiver filhos, criamos um nó folha.
        if( x.left == null && x.right == null ){
            createLeaf( x.key.toString() );
            return;
        }
        
        openBranch( x.key.toString(), "raiz" );
        
        if( x.left != null ){
            visitLeft( x.left );
        }else{
            createLeaf( "null" );
        }
        
        if( x.right != null ){
            visitRight( x.right );
        }else{
            createLeaf( "null" );
        }
        
        closeBranch();
    }
    
    public void visitLeft(Node l){
        if( l.left == null && l.right == null ){
            createLeaf( l.key.toString() );
            return;
        }
        
        openBranch( l.key.toString() );
        
        if( l.left != null ){
            visitLeft( l.left );
        }else{
            createLeaf( "null" );
        }
        
        if( l.right != null ){
            visitRight( l.right );
        }else{
            createLeaf( "null" );
        }
        
        closeBranch();
    }
    
    public void visitRight(Node r){
        if( r.left == null && r.right == null ){
            createLeaf( r.key.toString() );
            return;
        }
        
        openBranch( r.key.toString() );
        
        if( r.left != null ){
            visitLeft( r.left );
        }else{
            createLeaf( "null" );
        }
        
        if( r.right != null ){
            visitRight( r.right );
        }else{
            createLeaf( "null" );
        }
        
        closeBranch();
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
    
    private void createLeaf( String leafValue ) {
        sb.append( localIdentation ).append( "<leaf>" ).append( "\n" );
        sb.append( localIdentation ).append( identationString ).append( "<attribute name=\"nome\" value=\" " ).append( leafValue ).append( " \"/>" ).append( "\n" );
        sb.append( localIdentation ).append( identationString ).append( "<attribute name=\"tipo\" value=\"folha\"/>" ).append( "\n" );
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


















