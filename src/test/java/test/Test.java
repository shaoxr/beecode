package test;

public class Test {
	public static void main(String[] args) {
		
		String str="aaaaaaa%#name#%";
		
		String newStr=str.replaceAll("%#name#%", "fuck");
		System.out.println(newStr);
	}

}
