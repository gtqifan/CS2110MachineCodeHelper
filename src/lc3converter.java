import java.util.Scanner;

public class lc3converter {

    public static String binaryStringToInts(String binary)
    {
        int num = 0;
        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == '1') {
                num |= (1 << (binary.length() - 1 - i));
            }
        }
        String res = "" + num;
        if (binary.charAt(0) == '1') {
//            res = binary;
            String reverted = "";
            for (int i = 0; i < binary.length(); i++) {
                if (binary.charAt(i) == '1') {
                    reverted += '0';
                } else {
                    reverted += '1';
                }
            }
            int result = binaryStringToInt(reverted) + 1;
            return "#-" + Integer.toString(result);
        }
        return "#" + res;
    }
    public static int binaryStringToInt(String binary)
    {
        int num = 0;
        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == '1') {
                num |= (1 << (binary.length() - 1 - i));
            }
        }
        return num;
    }
    public static String binaryStringToHexString(String binary)
    {
        String hex = "";
        int biStr = 0;

        for (int j = 0; j < 32; j++) {
            if (binary.charAt(j) == '1') {
                biStr |= (1 << (31 - j));
            }
        }
        int copy = biStr;

        for (int i = 8; i > 0; i--) {
            biStr = copy - ((copy >> 4) << 4);
            if (biStr > 9) {
                char character = (char) (biStr + 55);
                hex = character + hex;
            } else {
                hex = biStr + hex;
            }
            copy >>= 4;
        }
        return hex;
    }

    public static String add(String in) {
        int DR = binaryStringToInt(in.substring(4,7));
        int SR1 = binaryStringToInt(in.substring(7,10));
        String result = "ADD R" + DR + ",R" + SR1 + ",";
        if (Integer.parseInt(in.substring(10, 11)) == 0) {
            result +=  "R" + binaryStringToInt(in.substring(11,16));
        } else {
            result +=  binaryStringToInts(in.substring(11,16));
        }
        return result;
    }
    public static String and(String in) {
        int DR = binaryStringToInt(in.substring(4,7));
        int SR1 = binaryStringToInt(in.substring(7,10));
        String result = "AND R" + DR + ",R" + SR1 + ", " + binaryStringToInts(in.substring(11,16));
        if (Integer.parseInt(in.substring(10, 11)) == 0) {
            result +=  "R" + binaryStringToInt(in.substring(11,16));
        } else {
            result +=  binaryStringToInts(in.substring(11,16));
        }
        return result;
    }

    public static String jmp(String in) {
        int SR1 = binaryStringToInt(in.substring(7,10));
        String result = "JMP R" + SR1;
        return result;
    }

    public static String jsr(String in) {
        String off11 = binaryStringToInts(in.substring(5,16));
        String result = "JSR " + off11;
        if (in.charAt(4) == '0') {
            result = jsrr(in);
        }
        return result;
    }

    public static String jsrr(String in) {
        int SR1 = binaryStringToInt(in.substring(7,10));
        String result = "JSRR R" +  SR1;
        return result;
    }

    //LD, LDI, LEA, ST, STI
    public static String ld(String in) {
        int SR = binaryStringToInt(in.substring(4,7));
        String offset = binaryStringToInts(in.substring(7,16));
        String result = "R" + SR + ", " + offset;
        return result;
    }

    //LDR, STR
    public static String ldr(String in) {
        int SR = binaryStringToInt(in.substring(4,7));
        int BaseR = binaryStringToInt(in.substring(7,10));
        String offset = binaryStringToInts(in.substring(10,16));
        String result = "R" + SR + ", R" + BaseR + ", " + offset;
        return result;
    }

    public static String not(String in) {
        int SR = binaryStringToInt(in.substring(4,7));
        int BaseR = binaryStringToInt(in.substring(7,10));
        String result = "NOT R" + SR + ", R" + BaseR;
        return result;
    }

    public static String brhelp(String sub) {
        String result = "";
        if(sub.charAt(0) == '1') {
            result += "n";
        }
        if(sub.charAt(1) == '1') {
            result += "z";
        }
        if(sub.charAt(2) == '1') {
            result += "p";
        }
        return result;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Please enter the binary code:");
            String input = scanner.nextLine();
            System.out.println("Output:");

            if (input.substring(0, 4).equals("0001")) {
                //ADD
                System.out.println(add(input));
            } else if (input.substring(0, 4).equals("0101")) {
                //AND
                System.out.println(and(input));
            } else if (input.substring(0, 4).equals("0000")) {
                //BR
                System.out.println("BR" + brhelp(input.substring(4, 7)) + " ,off " + binaryStringToInts(input.substring(7, 16)));
            } else if (input.substring(0, 4).equals("0100")) {
                //JSR JSRR
                System.out.println(jsr(input));//and jsrr
            } else if (input.substring(0, 4).equals("0010")) {
                //LD
                System.out.println("LD" + ld(input));
            } else if (input.substring(0, 4).equals("1010")) {
                //LDI
                System.out.println("LDI " + ld(input));
            } else if (input.substring(0, 4).equals("0110")) {
                //LDR
                System.out.println("LDR " + ldr(input));
            } else if (input.substring(0, 4).equals("1110")) {
                //LEA
                System.out.println("LEA " + ld(input));
            } else if (input.substring(0, 4).equals("1001")) {
                //NOT
                System.out.println(not(input));
            } else if (input.substring(0, 4).equals("0011")) {
                //ST
                System.out.println("ST " + ld(input));
            } else if (input.substring(0, 4).equals("1011")) {
                //STI
                System.out.println("STI " + ld(input));
            } else if (input.substring(0, 4).equals("0111")) {
                //STR
                System.out.println("STR " + ldr(input));
            } else if (input.substring(0, 4).equals("1100")) {
                //JMP
                System.out.println(jmp(input));
            } else if (input.substring(0, 4).equals("1111")) {
                //TRAP
                System.out.println("TRAP " + binaryStringToInts(input.substring(8,16)));
            }

            System.out.println("----------------------------------");
        }
    }
}
