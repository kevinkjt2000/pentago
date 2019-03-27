import java.util.ArrayList;

public class Tree
{
   public class TreeNode
   {
      private Object item;
      private ArrayList<TreeNode> children;
      
      public TreeNode( Object object ) 
      {
         item = object;
         children = new ArrayList<TreeNode>();
      }
      
      public void insertChild( Object object )
      {
         children.add( new TreeNode( object ) );
         size++;
      }
      
      public ArrayList<TreeNode> getChildren()
      {
         return children;
      }
   }
   
   private TreeNode root;
   protected int size;
   
   public Tree()
   {
      root = null;
      size = 0;
   }
   
   public void setRoot( Object object )
   {
      root = new TreeNode( object );
      size = 1;
   }
   
   public TreeNode getRoot()
   {
      return root;
   }
   
   public Object getRootItem()
   {
      return root.item;
   }
   
   public boolean isEmpty()
   {
      return size == 0;
   }
   
   public int getSize()
   {
      return size;
   }
}
