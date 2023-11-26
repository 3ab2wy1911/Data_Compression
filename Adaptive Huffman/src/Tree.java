public class Tree {
    private Node root;

    //----------------------------------------------------------------

    Tree(){
        root = new Node('\0',0);    // represents NYT
    }

    //----------------------------------------------------------------

    public String encodeSymbol(char s){
        String result ="";
        if(search(s,root) == null){
            // send NYT n.c + symbol short code
        }
        else {
            // send symbol node code
        }
        updateTree(s,search(s,root) == null);
        return result;
    };

    //----------------------------------------------------------------

    public void updateTree(char s, boolean isFirst){
        if (isFirst){
            Node node = search('\0',root);
            Node child = new Node(s,1);
            if(node.right == null){
                node.right = child;
            }
            else node.left = child;
            // Not complete because we need to implement another function.
        }

        else {
            // check swapping
        }

    }

    //----------------------------------------------------------------
     public Node search (char s, Node node){    // complete.
         if (node == null) {
             return null;
         }

         if (node.data == s) {
             return node;
         }

         Node result = search(s, node.right);
         if (result != null) {
             return result;
         }

         return search(s, node.left);
     }

    //----------------------------------------------------------------

}
