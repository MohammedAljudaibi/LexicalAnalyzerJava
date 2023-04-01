//Group Project Part 1 Lexical Analyser
package pkg302groupprojectpart1lexical;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Main {

    public static char lookahead;
//Sorted Keywords in order to apply Binary Search
    static final String KEYWORDS[] = {"abstract", "assert", "boolean",
        "break", "byte", "case", "catch", "char", "class", "const",
        "continue", "default", "do", "double", "else", "extends", "false",
        "final", "finally", "float", "for", "goto", "if", "implements",
        "import", "instanceof", "int", "interface", "long", "native",
        "new", "null", "package", "private", "protected", "public",
        "return", "short", "static", "strictfp", "super", "switch",
        "synchronized", "this", "throw", "throws", "transient", "true",
        "try", "void", "volatile", "while"};

    public static void main(String[] args) throws FileNotFoundException, IOException {
        File in = new File("input.txt");     //Creation of File Descriptor for input file
        if (!in.exists()) {
            System.out.println("input.txt file does not exist!");
            System.exit(0);
        }
        FileReader fr = new FileReader(in);   //Creation of File Reader object
        BufferedReader br = new BufferedReader(fr);  //Creation of BufferedReader object
        //Call tokenizer
        tokenizer(in,br);

    }

    public static void tokenizer(File in, BufferedReader br) throws IOException {
        int c = br.read(); //Reads first Char in file (as integer)
        lookahead = (char) c; //converting integer to char
        int state = 0;
        char[] lexeme = new char[69];
        int i = 0;
        boolean flag = true;
        do//Read char by Char
        {
            if ((c) == -1) {
                flag = false;
            }
            switch (state) {
                case 0:				// case for white spaces
                    if (lookahead == '\r' || lookahead == '\t' || lookahead == ' ' || lookahead == '\n') {
                        state = 0;
                        c = br.read();
                        lookahead = (char) c;
                    } else if (lookahead == '_' || Character.isAlphabetic(lookahead) || lookahead == '$') // underscore and Letter for Identifiers
                    {
                        state = 1;
                        lexeme[i++] = lookahead;
                    } else if (Character.isDigit(lookahead)) {
                        state = 3;
                        lexeme[i++] = lookahead;
                    } else if (lookahead == '+') {
                        state = 7;
                        lexeme[i++] = lookahead;
                    } else if (lookahead == '-') {
                        state = 11;
                        lexeme[i++] = lookahead;
                    } else if (lookahead == '*') {
                        state = 15;
                        lexeme[i++] = lookahead;
                    } else if (lookahead == '/') {
                        state = 18;
                        lexeme[i++] = lookahead;
                    } else if (lookahead == '%') {
                        state = 21;
                        lexeme[i++] = lookahead;
                    } else if (lookahead == '=') {
                        state = 24;
                        lexeme[i++] = lookahead;
                    } else if (lookahead == '!') {
                        state = 27;
                        lexeme[i++] = lookahead;
                    } else if (lookahead == '<') {
                        state = 30;
                        lexeme[i++] = lookahead;
                    } else if (lookahead == '>') {
                        state = 33;
                        lexeme[i++] = lookahead;
                    } else if (lookahead == '|') {
                        state = 36;
                        lexeme[i++] = lookahead;
                    } else if (lookahead == '&') {
                        state = 39;
                        lexeme[i++] = lookahead;
                    } else if (lookahead == ';') {
                        state = 42;
                        lexeme[i++] = lookahead;
                    } else if (lookahead == '[') {
                        state = 43;
                        lexeme[i++] = lookahead;
                    } else if (lookahead == ']') {
                        state = 44;
                        lexeme[i++] = lookahead;
                    } else if (lookahead == ',') {
                        state = 45;
                        lexeme[i++] = lookahead;
                    } else if (lookahead == '{') {
                        state = 46;
                        lexeme[i++] = lookahead;
                    } else if (lookahead == '}') {
                        state = 47;
                        lexeme[i++] = lookahead;
                    } else if (lookahead == '(') {
                        state = 48;
                        lexeme[i++] = lookahead;
                    } else if (lookahead == ')') {
                        state = 49;
                        lexeme[i++] = lookahead;
                    } else if (lookahead == ':') {
                        state = 50;
                        lexeme[i++] = lookahead;
                    } else if (lookahead == '.') {
                        state = 51;
                        lexeme[i++] = lookahead;
                    } else if (lookahead == '"') {
                        state = 56;
                        lexeme[i++] = lookahead;
                    }//charecter change state
                    else if (lookahead == '\'') {
                        state = 59;
                        lexeme[i++] = lookahead;
                    } else if (lookahead == '\uffff') {
                        break;
                    } else {
                        error(lookahead);
                        state = 0;
                    }
                    break;

                case 1: //ID
                    c = br.read();
                    lookahead = (char) c;
                    if (Character.isDigit(lookahead) || Character.isAlphabetic(lookahead) || lookahead == '$') {
                        state = 1;
                        lexeme[i++] = lookahead;
                    } else {
                        state = 2;
                    }
                    break;
                case 2:
                    state = 0;
                    lexeme[i] = '\0'; //Storing null character at the end
                    String ident = toString(lexeme);
                    i = 0;
                    //If java keyword, print as such, otherwise print ID
                    if (isJavaKeyword(ident)) {
                        System.out.println(ident + "\t\t" + ident);
                    } else {
                        System.out.println(ident + "\t\t" + "ID");
                    }
                    break;
                case 3:  //int/double
                    c = br.read();
                    lookahead = (char) c;
                    if (Character.isDigit(lookahead)) {
                        state = 3;
                        lexeme[i++] = lookahead;
                    } else if (lookahead == '.') {
                        state = 4;
                        lexeme[i++] = lookahead;
                    } else {
                        state = 5;
                    }
                    break;
                case 4:
                    c = br.read();
                    lookahead = (char) c;
                    if (Character.isDigit(lookahead)) {
                        state = 4;
                        lexeme[i++] = lookahead;
                    } else {
                        state = 6;
                    }
                    break;
                case 5:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    System.out.println(toString(lexeme) + "\t\t" + "Int_Literal");
                    break;
                case 6:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    System.out.println(toString(lexeme) + "\t\t" + "Double_Literal");
                    break;
                case 7: //Operators
                    c = br.read();
                    lookahead = (char) c;
                    if (lookahead == '+') {
                        lexeme[i++] = lookahead;
                        state = 8;
                    } else if (lookahead == '=') {
                        lexeme[i++] = lookahead;
                        state = 9;
                    } else {
                        state = 10;
                    }
                    break;
                case 8:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    c = br.read();
                    lookahead = (char) c;
                    System.out.println(toString(lexeme) + "\t\tIncrement Op");
                    break;
                case 9:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    c = br.read();
                    lookahead = (char) c;
                    System.out.println(toString(lexeme) + "\t\tPlus Equals");
                    break;
                case 10:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    System.out.println(toString(lexeme) + "\t\tAddition Op");
                    break;
                case 11:
                    c = br.read();
                    lookahead = (char) c;
                    if (lookahead == '-') {
                        lexeme[i++] = lookahead;
                        state = 12;
                    } else if (lookahead == '=') {
                        lexeme[i++] = lookahead;
                        state = 13;
                    } else {
                        state = 14;
                    }
                    break;
                case 12:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    c = br.read();
                    lookahead = (char) c;
                    System.out.println(toString(lexeme) + "\t\tDecrement Op");
                    break;
                case 13:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    c = br.read();
                    lookahead = (char) c;
                    System.out.println(toString(lexeme) + "\t\tMinus Equals");
                    break;
                case 14:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    System.out.println(toString(lexeme) + "\t\tSubstraction Op");
                    break;
                case 15:
                    c = br.read();
                    lookahead = (char) c;
                    if (lookahead == '=') {
                        lexeme[i++] = lookahead;
                        state = 16;
                    } else {
                        state = 17;
                    }
                    break;
                case 16:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    c = br.read();
                    lookahead = (char) c;
                    System.out.println(toString(lexeme) + "\t\tMultiplication Assign");
                    break;
                case 17:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    System.out.println(toString(lexeme) + "\t\tMultiplication Op");
                    break;
                case 18:
                    c = br.read();
                    lookahead = (char) c;
                    if (lookahead == '=') {
                        lexeme[i++] = lookahead;
                        state = 19;
                    } else if (lookahead == '/') {
                        lexeme[i++] = lookahead;
                        state = 55;
                    } else if (lookahead == '*') {
                        lexeme[i++] = lookahead;
                        state = 62;
                    } else {
                        state = 20;
                    }
                    break;
                case 19:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    c = br.read();
                    lookahead = (char) c;
                    System.out.println(toString(lexeme) + "\t\tDivision Assign");
                    break;
                case 20:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    System.out.println(toString(lexeme) + "\t\tDivision Op");
                    break;
                case 21:
                    c = br.read();
                    lookahead = (char) c;
                    if (lookahead == '=') {
                        lexeme[i++] = lookahead;
                        state = 22;
                    } else {
                        state = 23;
                    }
                    break;
                case 22:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    c = br.read();
                    lookahead = (char) c;
                    System.out.println(toString(lexeme) + "\t\tModus Assign");
                    break;
                case 23:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    System.out.println(toString(lexeme) + "\t\tModus Op");
                    break;
                case 24:
                    c = br.read();
                    lookahead = (char) c;
                    if (lookahead == '=') {
                        lexeme[i++] = lookahead;
                        state = 25;
                    } else {
                        state = 26;
                    }
                    break;
                case 25:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    c = br.read();
                    lookahead = (char) c;
                    System.out.println(toString(lexeme) + "\t\tComparision");
                    break;
                case 26:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    System.out.println(toString(lexeme) + "\t\tAssign");
                    break;
                case 27:
                    c = br.read();
                    lookahead = (char) c;
                    if (lookahead == '=') {
                        lexeme[i++] = lookahead;
                        state = 28;
                    } else {
                        state = 29;
                    }
                    break;
                case 28:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    c = br.read();
                    lookahead = (char) c;
                    System.out.println(toString(lexeme) + "\t\tNot Equal");
                    break;
                case 29:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    System.out.println(toString(lexeme) + "\t\tNot Op");
                    break;
                case 30:
                    c = br.read();
                    lookahead = (char) c;
                    if (lookahead == '=') {
                        lexeme[i++] = lookahead;
                        state = 31;
                    } else {
                        state = 32;
                    }
                    break;
                case 31:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    c = br.read();
                    lookahead = (char) c;
                    System.out.println(toString(lexeme) + "\t\tLess Than or Equals");
                    break;
                case 32:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    System.out.println(toString(lexeme) + "\t\tLess Than");
                    break;
                case 33:
                    c = br.read();
                    lookahead = (char) c;
                    if (lookahead == '=') {
                        lexeme[i++] = lookahead;
                        state = 34;
                    } else {
                        state = 35;
                    }
                    break;
                case 34:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    c = br.read();
                    lookahead = (char) c;
                    System.out.println(toString(lexeme) + "\t\tGreater Than or Equals");
                    break;
                case 35:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    System.out.println(toString(lexeme) + "\t\tGreater Than");
                    break;
                case 36:
                    c = br.read();
                    lookahead = (char) c;
                    if (lookahead == '|') {
                        lexeme[i++] = lookahead;
                        state = 37;
                    } else {
                        state = 38;
                    }
                    break;
                case 37:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    c = br.read();
                    lookahead = (char) c;
                    System.out.println(toString(lexeme) + "\t\tComparision OR");
                    break;
                case 38:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    System.out.println(toString(lexeme) + "\t\tBitwise OR");
                    break;
                case 39:
                    c = br.read();
                    lookahead = (char) c;
                    if (lookahead == '&') {
                        lexeme[i++] = lookahead;
                        state = 40;
                    } else {
                        state = 41;
                    }
                    break;
                case 40:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    c = br.read();
                    lookahead = (char) c;
                    System.out.println(toString(lexeme) + "\t\tComparision AND");
                    break;
                case 41:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    System.out.println(toString(lexeme) + "\t\tBitwise AND");
                    break;
                case 42: //Punctiation
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    c = br.read();
                    lookahead = (char) c;
                    System.out.println(toString(lexeme) + "\t\tSemi-Colon");
                    break;
                case 43:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    c = br.read();
                    lookahead = (char) c;
                    System.out.println(toString(lexeme) + "\t\tLeft Bracket");
                    break;
                case 44:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    c = br.read();
                    lookahead = (char) c;
                    System.out.println(toString(lexeme) + "\t\tRight Bracket");
                    break;
                case 45:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    c = br.read();
                    lookahead = (char) c;
                    System.out.println(toString(lexeme) + "\t\tComma");
                    break;
                case 46:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    c = br.read();
                    lookahead = (char) c;
                    System.out.println(toString(lexeme) + "\t\tLeft Curly");
                    break;
                case 47:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    c = br.read();
                    lookahead = (char) c;
                    System.out.println(toString(lexeme) + "\t\tRight Curly");
                    break;
                case 48:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    c = br.read();
                    lookahead = (char) c;
                    System.out.println(toString(lexeme) + "\t\tLeft Parenthesis");
                    break;
                case 49:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    c = br.read();
                    lookahead = (char) c;
                    System.out.println(toString(lexeme) + "\t\tRight Parenthesis");
                    break;
                case 50:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    c = br.read();
                    lookahead = (char) c;
                    System.out.println(toString(lexeme) + "\t\tColon");
                    break;
                case 51:
                    c = br.read();
                    lookahead = (char) c;
                    if (Character.isDigit(lookahead)) {
                        state = 52;
                        lexeme[i++] = lookahead;
                    } else {
                        state = 54;
                    }
                    break;
                case 52:
                    c = br.read();
                    lookahead = (char) c;
                    if (Character.isDigit(lookahead)) {
                        state = 52;
                        lexeme[i++] = lookahead;
                    } else {
                        state = 53;
                    }
                    break;
                case 53:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    System.out.println(toString(lexeme) + "\t\t" + "Double");
                    break;
                case 54:
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    System.out.println(toString(lexeme) + "\t\tPeriod");
                    break;
                case 55: //Single-Line comments will be ignored
                    c = br.read();
                    lookahead = (char) c;

                    if (lookahead == '\n') {
                        state = 0;
                        lexeme[i] = '\0';
                        i = 0;
                    } else {
                        state = 55;
                    }
                    break;
                case 56: //Double Quotes check if string or not
                    if (!isString(br)) {
                        state = 57;
                    } else {
                        state = 58;
                    }
                    break;
                case 57: //Only Double Quotes
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    System.out.println(toString(lexeme) + "\t\tDouble Quotes");
                    c = br.read();
                    lookahead = (char) c;
                    break;
                case 58: //String-Literal
                    System.out.print(lookahead);
                    c = br.read();
                    lookahead = (char) c;
                    while (lookahead != '"') {
                        if (lookahead == '\\') {
                            c = br.read();
                            lookahead = (char) c;
                            switch (lookahead) { //Switch case for \n and others
                                case '\\':
                                    System.out.print("\\");
                                    break;
                                case '\"':
                                    System.out.print("\"");
                                    break;
                                case 't':
                                    System.out.print("\t");
                                    break;
                                case 'n':
                                    System.out.print("\n");
                                    break;
                            }
                        } else {
                            System.out.print(lookahead);
                        }
                        c = br.read();
                        lookahead = (char) c;
                    }
                    System.out.println(lookahead + "\t\tString Literal");
                    state = 0;
                    i = 0;
                    c = br.read();
                    lookahead = (char) c;
                    break;
                case 59: //Chars, check for chars or not
                    if (isChar(br)) {
                        state = 60;
                    } else {
                        state = 61;
                    }
                    break;
                case 60: //Charecter Literal
                    System.out.print(lookahead);
                    c = br.read();
                    lookahead = (char) c;
                    while (lookahead != '\'') {
                        if (lookahead == '\\') {
                            c = br.read();
                            lookahead = (char) c;
                            if (lookahead == '\'') {
                                System.out.print(lookahead);
                            } else {
                                System.out.print("\\" + lookahead);
                            }
                            c = br.read();
                            lookahead = (char) c;
                        } else {
                            System.out.print(lookahead);
                            c = br.read();
                            lookahead = (char) c;
                        }
                    }
                    System.out.println(lookahead + "\t\tCharecter Literal");
                    state = 0;
                    i = 0;
                    c = br.read();
                    lookahead = (char) c;
                    break;
                case 61: //Only Single Quote
                    state = 0;
                    lexeme[i] = '\0';
                    i = 0;
                    System.out.println(toString(lexeme) + "\t\tSingle Quote");
                    c = br.read();
                    lookahead = (char) c;
                    break;
                case 62: //MULTI-LINE COMMENTS
                    if (isMultiLine(br)) {
                        c = br.read();
                        lookahead = (char) c;
                        state = 63;
                    } else {
                        state = 65;
                    }
                    break;
                case 63:
                    if (lookahead != '*') {
                        state = 63;
                        c = br.read();
                        lookahead = (char) c;
                    } else {
                        state = 64;
                    }
                    break;
                case 64:
                    if (lookahead == '*') {
                        state = 64;
                        c = br.read();
                        lookahead = (char) c;
                    } else if (lookahead == '/') {
                        state = 0;
                        lexeme[i] = '\0';
                        i = 0;
                        c = br.read();
                        lookahead = (char) c;
                    } else {
                        state = 63;
                    }
                    break;

            }
        } while (flag);
    }

    //Keyword compare
    public static boolean isJavaKeyword(String keyword) {
        return (Arrays.binarySearch(KEYWORDS, keyword) >= 0);
    }

    public static boolean isChar(BufferedReader br) throws IOException {
        br.mark(8); //Makes a mark in the reader
        int c = br.read();
        char la = (char) c;
        if (la == '\\') {
            c = br.read();
            c = br.read();
            la = (char) c;
            if (la == '\'') {
                br.reset();
                return true;
            }
        } else {
            c = br.read();
            la = (char) c;
            if (la == '\'') {
                br.reset();
                return true;
            }
        }
        br.reset(); //Returns reader to marked spot
        return false;
    }

    public static boolean isString(BufferedReader br) throws IOException {
        br.mark(1024); //Makes a mark in the reader
        int c = br.read();
        char la = (char) c;
        while (la != '\uffff' && la != '\n') {
            if (la == '"') {
                br.reset(); //Returns reader to marked spot 
                return true;
            }
            c = br.read();
            la = (char) c;
        }
        br.reset(); //Returns reader to marked spot
        return false;
    }

    public static boolean isMultiLine(BufferedReader br) throws IOException {
        br.mark(2048); //Makes a mark in the reader
        int c = br.read();
        char la = (char) c;
        while (la != '\uffff') {
            if (la == '*') {
                c = br.read();
                la = (char) c;
                if (la == '/') {
                    br.reset(); //Returns reader to marked spot 
                    return true;
                }
            }
            if (la == '*') {
                continue;
            }
            c = br.read();
            la = (char) c;
        }
        br.reset(); //Returns reader to marked spot
        return false;
    }

    public static String toString(char[] a) {
        String string = "";
        int j = 0;
        while (a[j] != '\0') {
            string = string + a[j];
            j++;
        }
        return string;
    }

    private static void error(char lookahead) {
        System.out.println("ERROR: UNRECOGNIZED CHARECTER: " + lookahead);
    }

}
