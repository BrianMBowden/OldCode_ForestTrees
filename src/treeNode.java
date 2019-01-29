import java.util.Scanner;

public class treeNode {
	
	char data;
	boolean sign;
	treeNode right = null;
	treeNode left = null;
	
	public treeNode(char ch){
		this.data = ch;
		if (ch == '0')
			this.sign = false;
		else
			this.sign = true;
	}
	
	public char getType(){
		return this.data;
	}
	
	public boolean isBool(){
		return ((this.data == '1')||(this.data == '0'));
	}
	
	public int numChildren(){
		int i = 0;
		if (this.right != null)
			i++;
		if (this.left != null)
			i++;
		return i;
	}
	
	public boolean getBool(){
		boolean aha = false;
		int i;
		if (!isBool()){
			System.out.println("please enter a value for " + data + " (either 1 or 0)");
			Scanner sc = new Scanner(System.in);
			i = sc.nextInt();
			if (i == 1)
				aha = true;
			else if (i == 0)
				aha = false;
			else{ 
				System.out.println("please enter a valid value");
				getBool();
			}
			this.sign = aha;
		}
		return aha;
	}
}
