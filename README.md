
# Lexical Analyser for Java

This is a Java-based lexical analyzer for the Java programming language, created for a university Compiler course (CPCS-302). A lexical analyzer is a program that breaks down source code into a sequence of tokens, or meaningful units of code, which are then used in further analysis of the program. The lexical analyzer implemented in this project uses a finite automaton we designed to tokenize Java source code, producing a list of tokens as output. This program can be used in conjunction with a syntax and semantic analyzer to create a full Java compiler.

## Usage

To use the lexer, simply call the `tokenizer` method in the `Main` class and pass in a `File` object that contains the Java source code to be analyzed. The method will read the file and tokenize its contents.

Simply replace the text in the input.txt file with your own java code and run the program

The `tokenizer` method will output the resulting tokens to the console.

Sample input:
```
abc int 12 34.5 + +
= ++ - -= -- * *=
 / /= % %= = == ! //thisIsInvisible
 != < <= > >= | || & 
&& ;[],{}(): . .3
"String" '\'' 'T'
/* MultiLine */
```
Sample output:
```
abc		ID
int		int
12		Int_Literal
34.5		Double_Literal
+		Addition Op
+		Addition Op
=		Assign
++		Increment Op
-		Substraction Op
-=		Minus Equals
--		Decrement Op
*		Multiplication Op
*=		Multiplication Assign
/		Division Op
/=		Division Assign
%		Modus Op
%=		Modus Assign
=		Assign
==		Comparision
!		Not Op
!=		Not Equal
<		Less Than
<=		Less Than or Equals
>		Greater Than
>=		Greater Than or Equals
|		Bitwise OR
||		Comparision OR
&		Bitwise AND
&&		Comparision AND
;		Semi-Colon
[		Left Bracket
]		Right Bracket
,		Comma
{		Left Curly
}		Right Curly
(		Left Parenthesis
)		Right Parenthesis
:		Colon
.		Period
.3		Double
"String"		String Literal
'''		Charecter Literal
'T'		Charecter Literal
```
## Notation
We used a slightly modified version of RE and followed this notation
| Notation | Definition |
| :---: | :---: |
|*|0 or more|
|+|1 or more|
|L|a,b,c...z|
|D|1,2,3...9|
|E|[UTF-16 Chars](https://www.fileformat.info/info/charset/UTF-16/list.htm)|
|(  )|Used for grouping|

## Token Types

The lexer recognizes the following token types:

- Identifiers: `(L,$,_)(L,$,_,D)*`

- White Spaces: `Enter, Tab, Space`

- Operators:
	1. Arithmetic Op: `+ , -, *, /, %`
	2. Arithmetic Assign Op: `+=, -=, *=, /=, %=`
	3. Increment Op: `++, --`
	4. Relational Op: `==, !=, <, >, <=, >=`
	5. Assignment Op: `=`
	6. Logical Op: `&&, ||, !`

- Comments: 
	- Single Line: `// E*`
	 - Multi Line: `/* (E-())+ */` (for confusion * (0 or more) will be replaced with + here)

- Numbers: 
	- Integers: `D+`
	- Floats: `D+` **.** `D+`

- Literals: 
	- String: `"E*"`
	- Character: `'E+'`

- Punctuation: `; : , . { } ( ) [ ]`

Certain identifiers will be classified as Java Keywords if they are one of the following:
```java
KEYWORDS[] = {"abstract", "assert", "boolean",
        "break", "byte", "case", "catch", "char", "class", "const",
        "continue", "default", "do", "double", "else", "extends", "false",
        "final", "finally", "float", "for", "goto", "if", "implements",
        "import", "instanceof", "int", "interface", "long", "native",
        "new", "null", "package", "private", "protected", "public",
        "return", "short", "static", "strictfp", "super", "switch",
        "synchronized", "this", "throw", "throws", "transient", "true",
        "try", "void", "volatile", "while"};
```

## Finite Automaton

The lexer uses a finite automaton to tokenize Java source code. The finite automaton is defined in the `tokenizer` method in the `Main` class, which is responsible for recognizing tokens in the input.

The finite automaton works by reading a single character, LookAhead, from the input and transitioning between states based on the current character and the current state. When the automaton reaches an accepting state, it emits a token and resets to the start state.

The lexer uses a binary search to identify Java keywords in the input, improving performance for large inputs.

## Functions Description
1.  `isChar(BufferedReader br)` - ToDo

2.  `isString(BufferedReader br)` - ToDo
    
3.  `isMultiLine(BufferedReader br)` - ToDo
