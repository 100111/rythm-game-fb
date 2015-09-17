
public class BinSNode {
	String value;
	BinSNode left;
	BinSNode right;
	
	public BinSNode(String value) {
		this.value = value;
	}
	
	void insert(String newString) {
		//Compare
		int comp = this.value.compareTo(newString);
		
		//Node value is greater
		if (comp > 0) {
			if (this.left == null) this.left = new BinSNode(newString);
			else this.left.insert(newString);
		}
		
		//Node value is lesser 
		if (comp < 0) {
			if (this.right == null) this.right = new BinSNode(newString);
			else this.right.insert(newString);
		}

		//If comp == 0, do nothing
	}
	
	boolean searchFor(String string) {
		//Compare
		int comp = this.value.compareTo(string);
		
		if (comp > 0) {
			if (this.left != null) return this.left.searchFor(string); 
		}
		else if (comp < 0) {
			if (this.right != null) return this.right.searchFor(string);
		}
		else return true;
		
		//If nothing got return, meaning the node is not found
		return false;
	}
	
	void deleteAtRoot(String string) {
		int comp = this.value.compareTo(string);
		
		if (comp > 0) this.left.delete(string, this, true);
		else if (comp < 0) this.right.delete(string, this, false);
		else { // if the root need to be delete
			//Move the right node to root
			if (this.left == null && this.right != null) {
				this.value = this.right.value;
				this.left = this.right.left;
				this.right = this.right.right;
			}
			
			//Move the left node to root
			if (this.right == null && this.left != null) {
				this.value = this.left.value;
				this.left = this.left.left;
				this.right = this.left.right;
			}
			
			if (this.right == null && this.left == null) {
				System.out.println("Can not delete root");
			}
		}
	}
	
	/**
	 * 
	 * @param string - string to delete
	 * @param parent - parent node
	 * @param isChildOnLeft - location of the current node, true - left child of parent, false - right child
	 */
	private void delete(String string, BinSNode parent, boolean isChildOnLeft) {
		int comp = this.value.compareTo(string);
		
		if (comp > 0) {
			if (this.left != null) this.left.delete(string, this, true);
			else return;
		}
		else if (comp < 0) {
			if (this.right != null) this.right.delete(string, this, false);
			else return;
			
		}
		else { //When arrive at the node that need to be deleted
			
			//Case 1: both child are null (leaf)
			if (isLeafNode()) {
				if (isChildOnLeft) parent.left = null;
				else parent.right = null;
				return;
			}
			
			//Case 2: only one child is null
			if (this.left != null && this.right == null) {
				if (isChildOnLeft) parent.left = this.left;
				else parent.right = this.left;
			}
			
			if (this.left == null && this.right != null) {
				if (isChildOnLeft) parent.left = this.right;
				else parent.right = this.right;
			}
			
			//Case 3: No null child
			if (this.left != null && this.right != null) {
				String minString = this.right.minString();
				this.value = minString;
				this.right.deleteAtRoot(minString);
			}
		}
	}
	
	boolean isLeafNode() {
		return this.right == null && this.left == null;
	}
	
	String minString() {
		if (this.left == null) return this.value;
		else {
			return this.left.minString();
		}
	}
	
	String maxString() {
		if (this.right == null) return this.value;
		else {
			return this.right.maxString();
		}
	}
	
	int depth() {
		if (isLeafNode()) return 0;
		
		if (this.right == null) return 1 + this.left.depth();
		
		if (this.left == null) return 1 + this.right.depth();
		
		return 1 + Math.max(this.left.depth(), this.right.depth());
	}
	
	int nodeCountOnDepth(int depth) {
		int count = 0;
		int currentDepth = 0;
		
		if(currentDepth == depth) {
			count += 1;
		} else {
			if (this.left != null) {
				count += this.left.nodeCountOnDepth(depth-1);
			}
			
			if (this.right != null) {
				count += this.right.nodeCountOnDepth(depth-1);
			}
		}
		
		return count;
	}
	
	double averageDepth() {
		int depth = this.depth();

		int sumDepth = 0;
		int sumNode = 0;
		
		int nodeCount;
		for (int i = 0; i <= depth; i++) {
			nodeCount = nodeCountOnDepth(i);
			
			sumNode += nodeCount;
			sumDepth += nodeCount * i;
		}
		
		return sumDepth/sumNode;
	}
}
