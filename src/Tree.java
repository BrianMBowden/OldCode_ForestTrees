import java.util.HashMap;
import java.util.Stack;

public class Tree {

	treeNode root = null;
	HashMap <Character, Boolean> weed = new HashMap<Character, Boolean>();
	
	public Tree(String expression){
		Stack<treeNode> temp = new Stack<treeNode>();
		char[] ch = {};
		treeNode tr;
		
		ch = expression.toCharArray();
		
		for (int i = 0; i < ch.length; i++){
			if (isLiteral(ch[i])){
				tr = new treeNode(ch[i]);
				temp.push(tr);
			}
			else if (isUnary(ch[i])){
				tr = new treeNode(ch[i]);
				tr.right = temp.pop();
				temp.push(tr);
			}
			else if (isBinary(ch[i])){
				tr = new treeNode(ch[i]);
				tr.right = temp.pop();
				tr.left = temp.pop();
				temp.push(tr);
				}
			}
		this.root = temp.pop();
		}
	
	public boolean evaluation(treeNode tn){

 		if (tn == null)
			return false;
		if (tn != null){
			if (tn.left != null){
				if (isLiteral(tn.left.data)){
					if (!tn.left.isBool()){
						if (!weed.containsKey(tn.left.data))
							weed.put(tn.left.data, tn.left.getBool());
						else 
							tn.left.sign = weed.get(tn.left.data);
					}
					if (!tn.right.isBool()){
						if (!weed.containsKey(tn.right.data))
							weed.put(tn.right.data, tn.right.getBool());
						else 
							tn.right.sign = weed.get(tn.right.data);
					}
					if ((tn.data == '&')&&(tn.left.sign == false))
						tn.sign = false;
					else if ((tn.data == '&')&&(tn.left.sign == true))
						if (tn.right.sign == false)
							tn.sign = false;
						else 
							tn.sign = true;
					else if ((tn.data == '|')&&(tn.left.sign == true))
						tn.sign = true;
					else if ((tn.data  == '|')&&(tn.left.sign == false))
						if (tn.right.sign == true)
							tn.sign = true;
						else 
							tn.sign = false;
					else 
						logic(tn.left.sign, tn.right.sign, tn.data);
				}
				else{
					evaluation(tn.left);
					evaluation(tn.right);
				}
				if (isBinary(tn.left.data)||isBinary(tn.right.data)){
					tn.sign = logic(tn.left.sign, tn.right.sign, tn.data);
				}
			}
		}
		
		return tn.sign;
	}
	private boolean isLiteral(char quest){
		boolean type = true;
		if ((quest >= 'A' && quest <= 'Z')||
			(quest >= 'a' && quest <= 'z')||
			(quest >= '0' && quest <='9')){
			type = true;
		}
		else 
			type = false;
		return type;
	}
	
	private boolean isUnary(char quest){
		boolean type = true;
		if (quest == '!'){
			type = true;
		}
		else 
			type = false;
		return type;
	}
	
	private boolean isBinary(char quest){
		boolean type = true;
		if ((quest == '&')||
			(quest == '|')||
			(quest == '^')){
			type = true;
		}
		else 
			type = false;
		return type;
	}
	
	public void preorderPrint(treeNode tn){
		if (tn == null)
			return;
		System.out.print(tn.data);
		preorderPrint(tn.left);
		preorderPrint(tn.right);
	}
	
	public void inorderPrint(treeNode tn){
		
		if (tn == null)
			return;
		if (isBinary(tn.data)||isUnary(tn.data))
			System.out.print('(');
		inorderPrint(tn.left);
		System.out.print(tn.data);
		inorderPrint(tn.right);
		if (isBinary(tn.data)||isUnary(tn.data))
			System.out.print(')');
	}

	public void postorderPrint(treeNode tn){
		if (tn == null)
			return;
		postorderPrint(tn.left);
		postorderPrint(tn.right);
		System.out.print(tn.data);
	}
	
	private boolean logic(boolean op1, boolean op2, char lop){
		boolean lg = false;
		
		if (lop == '&'){
			return (op1 && op2);
		}
		else if (lop == '|'){
			return (op1 || op2);
		}
		else if (lop == '^'){
			return ((op1 || op2) && (!op1 || !op2));
		}
		else if (lop == '!') {
			return !op1;
		}
		else 
			return lg;
	}
	
}
