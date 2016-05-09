import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Parse {
	public static boolean isWell(Node n) {
		String input = n.value.trim();
		if (input.startsWith("(") && input.endsWith(")"))
			input = input.substring(1, input.length() - 1).trim();
		// decompose the props
		if (input.contains("(") && input.contains(")")) {
			if (input.startsWith("\\not")) {
				String temp = input.substring(5, input.length());
				n.left = new Node();
				n.left.value = temp;
				return isWell(n.left);
			}
			int leftPara = 0;
			int rightPara = 0;
			int i = 0;
			boolean isLetter = input.startsWith("(");
			for (; i < input.length() - 1; i ++) {// find the first constituent 
				if (isLetter){
					if (input.charAt(i) == '(')
						leftPara ++;
					if (input.charAt(i) == ')')
						rightPara ++;
					if (leftPara == rightPara)// if '(' and ')' are balance
						break;
				} else {
					if (input.charAt(i) == ' ') {
						i--;
						break;
					}
						
				}
			}
			if (leftPara != rightPara)
				return false;
			String temp = input.substring(0, i + 1);
			int j = i + 1;// mark the start of OP
			n.left = new Node();
			n.left.value = temp;
			for (i += 2; i < input.length() - 1; i ++) 
				if (input.charAt(i) == ' ')
					break;				
			temp = input.substring(i + 1, input.length());// find the second constituents
			n.right = new Node();
			n.right.value = temp;
			temp = input.substring(j + 1, i);
			if (!(temp.equals("\\and") || temp.equals("\\or") || temp.equals("\\imply") || temp.equals("\\eq")))// OP is not '\not'
				return false;
			n.left.parent = n;
			n.right.parent = n;
			return  isWell(n.left) && isWell(n.right);
		} else if (!input.contains("(") && !input.contains(")")) {
			return simple(n);
		} else {
			return false;
		}
	}
	
	private static boolean simple(Node n){
		String in = n.value.trim();
		if (in.startsWith("(") && in.endsWith(")"))
			in = in.substring(1, in.length() - 1).trim();
		if (in.matches("[A-Z]+(_\\{[0-9]+})?"))
			return true;

		int start=0, end=0;
		boolean started = false;
		for (int i = 0; i < in.length(); i++) {
			if (in.charAt(i) == '\\') { 
				start = i;
				started = true;
			}
			if (started && end == 0 && in.charAt(i) == ' ')
				end = i;
			if (started && end != 0)
				break;
		}
		String op = in.substring(start, end);
		String first = in.substring(0, start).trim();
		String second = in.substring(end+1, in.length()).trim();
		if (op.equals("\\not")) {
			if ((first.equals("")) && second.matches("[A-Z]+(_\\{[0-9]+})?")) {
				n.left = new Node();
				n.left.value = second;
				return true;
			}
			return false;
		} else if (op.equals("\\and") || op.equals("\\or") || op.equals("\\imply") || op.equals("\\eq")) {
			if (first.matches("[A-Z]+(_\\{[0-9]+})?") && second.matches("[A-Z]+(_\\{[0-9]+})?")) {
				n.left = new Node();
				n.right = new Node();
				n.left.value = first;
				n.right.value = second;
				return true;
			}
			return false;
		} else
			return false;
	}
	
	public static void parse() {
        File file = new File("test.txt");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null) {
            	Tree t = new Tree();
            	t.root.value = line;
            	if (isWell(t.root))
            		System.out.println("Yes~");
            	else
            		System.out.println("No!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }
}
