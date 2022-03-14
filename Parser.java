import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Parser{
    static Parser te; 
    static Scanner reader = new Scanner(System.in);
    static LexToken lookahead = null; 
    public static HashMap<String, LexToken> syTable = new HashMap<>(); 
    public static HashMap<String, LexToken> currentTable = new HashMap<>(); 
    public static HashMap<String, String> int2Lexeme = new HashMap<>(); 
    public static HashMap<String, HashMap<String, LexToken>> allTables = new HashMap<>(); 
    public static String funcName = ""; 
    public static boolean skip = false;
    public static String returnChar = ""; 
    static int id= 1; 
    public static boolean dot = true;
    static int tabNum = 0; 
    static String tabString = "\t"; 
    class LexToken{
        String value; 
        String token; 
        String attributes; 

        
        LexToken(String lexeme , String token , String attributes){
            LexToken temp = findEntry(lexeme); 
            if (temp != null ){
                this.value = temp.value;
                this.token = temp.token; 
                this.attributes = temp.attributes; 

            }else {
                if (token == "ID"){
                    this.value = "" + id; 
                }else {
                    this.value = lexeme; 
                }
                
                this.token = token;
                this.attributes = attributes;
                addValues(this, lexeme);

            }
        }
        @Override
        public String toString(){
            String formattedOutput = "<" + this.token;
            if (this.attributes.contains("Variable")  || this.attributes.contains("Constant") || this.attributes.contains("Operators")|| this.attributes.contains("ERROR")) {
                formattedOutput = formattedOutput +" , "+  this.value; 
            }
            return formattedOutput + ">"; 
        }
        public boolean valueEquals(String temp){
            if (this.value.equals( temp) ){
                return true; 
            }
            return false; 

        }
    }

    public static LexToken CreateError(String word, Scanner reader){
        String errorWord = word; 
        return  te.new LexToken(errorWord, "ERROR", "ERROR");
        /*
        while (reader.hasNext()){
            String value = reader.next();
            switch(value){
                case "0": 
                case "1": 
                case "2": 
                case "3": 
                case "4": 
                case "5": 
                case "6": 
                case "7": 
                case "8": 
                case "9": 
                case "a":
                case "b":
                case "c":
                case "d":
                case "e":
                case "f":
                case "g":
                case "h":
                case "i":
                case "j":
                case "k":
                case "l":
                case "m":
                case "n":
                case "o":
                case "p":
                case "q":
                case "r":
                case "s":
                case "t":
                case "u":
                case "v":
                case "w":
                case "x":
                case "y":
                case "z": 
                    errorWord = errorWord + value; 
                    break; 
                default: 
                    returnChar = value;
                    return te.new LexToken(errorWord, "ERROR", "ERROR"); 
            }
        }
        return null;*/

    }

    public static void addValues(LexToken tok , String lexeme){
        currentTable.put(lexeme, tok); 
        int2Lexeme.put("" + id ,lexeme);
        id ++; 
    }

    public static String getLexeme(String ID){
        return int2Lexeme.get(ID);
    }
    public static LexToken findEntry(String lexeme){
        LexToken temp = syTable.get(lexeme); 
        return temp;  
    }

    public static LexToken isId(String firstChar, Scanner reader){
        String word = firstChar; 
        while (reader.hasNext()){
            String value = reader.next(); 
            switch(value.toLowerCase()){
                case "0": 
                case "1": 
                case "2": 
                case "3": 
                case "4": 
                case "5": 
                case "6": 
                case "7": 
                case "8": 
                case "9": 
                case "a":
                case "b":
                case "c":
                case "d":
                case "e":
                case "f":
                case "g":
                case "h":
                case "i":
                case "j":
                case "k":
                case "l":
                case "m":
                case "n":
                case "o":
                case "p":
                case "q":
                case "r":
                case "s":
                case "t":
                case "u":
                case "v":
                case "w":
                case "x":
                case "y":
                case "z": 
                    word = word + value; 
                    break; 
                case "<": 
                case ">":
                case "=":
                case "-":
                case "+":
                case "%":
                case "*":
                case "/":
                case "(": 
                case ")":
                case " ":
                case "\t":
                case "\n": 
                case ".": 
                case "\r": 
                case ",":
                case ";":    
                    returnChar = value; 
                    return te.new LexToken(word, "ID", "Variable");
                default : 
                    word = word + value; 
                    return CreateError(word, reader); 
            }


        }
        return null; 
    }

    public static LexToken IsRelop(String firstChar, Scanner reader){
        String newToken = firstChar; 
        String value; 
        if (firstChar.equals("<")){
            if (reader.hasNext()){
                value = reader.next(); 
            
                switch(value.toLowerCase()){
                    case "=":
                        newToken = newToken + value; 
                        return te.new LexToken(newToken,"LE", ""); 
                        
                    case ">":
                        newToken = newToken + value; 
                        return te.new LexToken(newToken,"NE", ""); 
                        
                    default: 
                        returnChar = value; 
                        return te.new LexToken(newToken,"LT", ""); 
                        
    
                }
            }else {
                return te.new LexToken(newToken,"LT", "");                     
            }

        }else if(firstChar.equals("=")){
            if (reader.hasNext()){
                value = reader.next();
                switch(value.toLowerCase()){
                    case "=":
                        newToken = newToken + value; 
                        return te.new LexToken(newToken,"EQ", ""); 
                    default: 
                        returnChar = value; 
                        return te.new LexToken(newToken,"ASSIGN", ""); 
                }
            }else{
                return te.new LexToken(newToken,"ASSIGN", ""); 
            }

        }else if (firstChar.equals(">")){    
            if (reader.hasNext()){
                value = reader.next(); 
                switch(value.toLowerCase()){
                    case "=":
                        newToken = newToken + value; 
                        return te.new LexToken(newToken,"GE", ""); 
                        
                    default : 
                        returnChar = value; 
                        return te.new LexToken(newToken,"GT", ""); 
                                          
                }
            }else {
                return te.new LexToken(newToken,"GT", ""); 
            }
        }
        return null;
    }
    public static LexToken IsInterger( String firstChar, Scanner reader){
        
        boolean makeInt = false; 
        boolean digit = true; 
        boolean skip = false; 
        String newVar = firstChar;
        String value = ""; 
        while(digit && reader.hasNext()){
            value = reader.next();
            switch(value){
                case "0": 
                case "1": 
                case "2": 
                case "3": 
                case "4": 
                case "5": 
                case "6": 
                case "7": 
                case "8": 
                case "9": 
                    newVar = newVar + value ; 
                    break; 
                case ".": 
                    newVar = newVar + value ; 
                    digit = false;
                    break; 
                case "E":
                case "e":  
                    newVar = newVar + value ;
                    skip = true ;
                    digit = false;
                    break;
                case " ": 
                case "\n": 
                case "\t":
                case "": 
                case "\r":
                case "-":
                case "+":
                case "%":
                case "*":
                case "/":    
                case ")":
                case ">":
                case "=":
                case "<":
                case ";":
                
                case ",": 
                    returnChar = value; 
                    return te.new LexToken(newVar, "Integer", "Constant"); 
                
                default : 
                    newVar = newVar + value; 
                    return CreateError(newVar, reader); 
            }
        }

        if ( !skip){
            if (reader.hasNext()){ 
               value = reader.next();
                switch(value){
                    case "0": 
                    case "1": 
                    case "2": 
                    case "3": 
                    case "4": 
                    case "5": 
                    case "6": 
                    case "7": 
                    case "8": 
                    case "9": 
                        newVar = newVar + value ;
                        break; 
                    default: 
                        newVar = newVar + value; 
                        return CreateError(newVar, reader); 

                
            }
                
                
            }else {
                return CreateError(newVar, reader); 
            }
            digit = true; 
            while(digit && reader.hasNext()){
                value = reader.next();
                switch(value){
                    case "0": 
                    case "1": 
                    case "2": 
                    case "3": 
                    case "4": 
                    case "5": 
                    case "6": 
                    case "7": 
                    case "8": 
                    case "9": 
                        newVar = newVar + value ; 
                        break; 
                    case "E": 
                    case "e": 
                        newVar = newVar + value ;
                        digit = false;    
                        break;
                        
                    case " ": 
                    case "\n": 
                    case "\t":
                    case "": 
                    case "\r":
                    case "-":
                    case "+":
                    case "%":
                    case "*":
                    case "/":    
                    case ")":
                    case ">":
                    case "=":
                    case "<": 
                    case ";": 
                    case ",":   
                        returnChar = value;
                        return te.new LexToken(newVar, "DOUBLE", "Constant"); 
                    
                    default : 
                        newVar = newVar + value; 
                        return CreateError(newVar, reader); 
                }
            }
                        
        }// E arrows going in at this point 
        if (reader.hasNext()){        
            value = reader.next(); 
            skip = false; 
            switch(value){
                case "0": 
                case "1": 
                case "2": 
                case "3": 
                case "4": 
                case "5": 
                case "6": 
                case "7": 
                case "8": 
                case "9": 
                    skip = true; 
                    newVar = newVar + value ;
                    break; 
                case "+":
                    makeInt = true; 
                case "-":
                    newVar = newVar + value ;
                    break;
    
                default : 
                    newVar = newVar + value; 
                    return CreateError(newVar, reader); 
            }
        
        }else {
            return CreateError(newVar, reader); 
        }
        if (!skip ){
            if(reader.hasNext()){
                            value = reader.next();
                switch(value){
                    case "0": 
                    case "1": 
                    case "2": 
                    case "3": 
                    case "4": 
                    case "5": 
                    case "6": 
                    case "7": 
                    case "8": 
                    case "9": 
                        newVar = newVar + value ;
                        break; 
    
                    default : 
                        newVar = newVar + value; 
                        return CreateError(newVar, reader); 
                }
            }else {
                return CreateError(newVar, reader);  
            }

        }
        while(reader.hasNext()){
            value = reader.next();
            switch(value){
                case "0": 
                case "1": 
                case "2": 
                case "3": 
                case "4": 
                case "5": 
                case "6": 
                case "7": 
                case "8": 
                case "9": 
                    newVar = newVar + value ; 
                    break; 
                    
                case " ": 
                case "\n": 
                case "\t":
                case "": 
                case "\r":
                case "-":
                case "+":
                case "%":
                case "*":
                case "/":    
                case ")":
                case ">":
                case "=":
                case "<":
                case ";":
                case ",": 
                    returnChar = value; 
                        return te.new LexToken(newVar, "DOUBLE", "Constant"); 
                    
                    
                
                default : 
                    newVar = newVar + value; 
                    return CreateError(newVar, reader); 
            }
        }

        return null; 

    }
    public static LexToken getNextToken(Scanner reader)throws Exception{
        LexToken token; 
         

        
        token = null;
        while (token == null ){
            String value = ""; 
            if (returnChar != "" ){
                value = returnChar; 
                returnChar = "" ;
            }else {
                value = reader.next();
            }
            switch(value.toLowerCase()){
                case "0": 
                case "1": 
                case "2": 
                case "3": 
                case "4": 
                case "5": 
                case "6": 
                case "7": 
                case "8": 
                case "9": 
                    token = IsInterger(value,reader);
                    break; 
                case "a":
                case "b":
                case "c":
                case "d":
                case "e":
                case "f":
                case "g":
                case "h":
                case "i":
                case "j":
                case "k":
                case "l":
                case "m":
                case "n":
                case "o":
                case "p":
                case "q":
                case "r":
                case "s":
                case "t":
                case "u":
                case "v":
                case "w":
                case "x":
                case "y":
                case "z":
                    token = isId(value, reader);
                    break; 
    
                case "<": 
                case ">":
                case "=":
                    token = IsRelop(value, reader);
                    break; 
                case "-":
                case "+":
                case "%":
                case "*":
                case "/":
                    token = te.new LexToken(value, "", "");
                    break; 
                case "(":
                    token = te.new LexToken("(", "", "");
                    break; 
                case ")":
                    token = te.new LexToken(")", "", "");
                    break; 
                
                case " ":
                    //token = te.new LexToken(" ", "", "");
                    break; 
                case "\t":
                    //token = te.new LexToken("\t", "", "");
                    break; 
                case "\n": 
                    //token = te.new LexToken("\n", "", "");
                    break; 
                case ".": 
                    token = te.new LexToken(".", "", "");
                    dot = false;
                    break;
                case "\r": 
                    skip = true; 
                    break;
                case ",":
                    token  = te.new LexToken(",","",""); 
                    break; 
                case ";": 
                    token = te.new LexToken(";", "", "");
                    break;
                default:
                    
                    te.new LexToken(value,"ERROR", "ERROR"); 
                    break; 
    
    
    
    
            }
        }
        if (token.token.equals("ERROR")){
            throw new Exception("Error Token encounter.'" + token.value + "'is not a recognizable token. "); 
        }
        return token;
    }

    public static void main(String[] args) {
        //ADD keywords to symbol table 
        te = new Parser();
        currentTable = syTable; 
        te.new LexToken("def", "DEF", "Keyword");
        te.new LexToken("def", "DEF", "Keyword");
        te.new LexToken("fed", "FED", "Keyword");
        te.new LexToken("int", "INT", "Keyword");
        te.new LexToken("double", "DOUBLE", "Keyword");
        te.new LexToken("if", "IF", "Keyword");
        te.new LexToken("fi", "FI", "Keyword");
        te.new LexToken("then", "THEN", "Keyword");
        te.new LexToken("else", "ELSE", "Keyword");
        te.new LexToken("while", "WHILE", "Keyword");
        te.new LexToken("do", "DO", "Keyword");
        te.new LexToken("od", "OD", "Keyword");
        te.new LexToken("print", "PRINT", "Keyword");
        te.new LexToken("return", "RETURN", "Keyword");
        te.new LexToken("or", "OR", "Keyword");
        te.new LexToken("and", "AND", "Keyword");
        te.new LexToken("not", "NOT", "Keyword");

        //syntax
        te.new LexToken(";", "SEMICOLON", "Syntax");
        te.new LexToken(",", "COMMA", "Syntax");
        te.new LexToken(")", "RIGHTBRACK", "Syntax");
        te.new LexToken("(", "LEFTBRACK", "Syntax");
        te.new LexToken(".", "DOT", "Syntax");
        te.new LexToken("=", "ASSIGN", "Relop");
        //relop
        te.new LexToken("<", "LT", "Relop");
        te.new LexToken(">", "GT", "Relop");
        te.new LexToken("==", "EQ", "Relop");
        te.new LexToken("<=", "LE", "Relop");
        te.new LexToken(">=", "GE", "Relop");
        te.new LexToken("<>", "NE", "Relop");

        //Operations
        te.new LexToken("+", "Operator", "Operators");
        te.new LexToken("-", "Operator", "Operators");
        te.new LexToken("*", "Operator", "Operators");
        te.new LexToken("/", "Operator", "Operators");
        te.new LexToken("%", "Operator", "Operators");

        
        ArrayList<LexToken> TokenList = new ArrayList<>(); 

        
        
        
        reader.useDelimiter("");
        LexToken token;
        try {
            lookahead = getNextToken(reader); 
            parse(); 
            System.out.println("\n\n******NOTE: Variables appear in order they appear in hashtable. NOT order of appearance.");
            System.out.println("================Symbol Tables================");
            System.out.println("MAIN Symbol Table");
            String tok = "token";
            String vals = "Values";  
            String paddingString = "                         |                         "; 
            System.out.printf("\n%s" + paddingString.substring(tok.length(), paddingString.length() - vals.length()) +"%s\n", tok,   vals);
            System.out.printf(new String(new char[paddingString.length()]).replace("\0", "-"));
            syTable.entrySet().forEach(entry ->{
                LexToken temp  = entry.getValue(); 
                if (!temp.attributes.contains("Operators") && !temp.attributes.contains("Relop") && !temp.attributes.contains("Syntax")  && !temp.attributes.contains("Keyword") ){
                    if (temp.attributes.contains("Variable")){
                        String ts = getLexeme(temp.value); 
                        System.out.printf("\n%s" + paddingString.substring(temp.toString().length(), paddingString.length()- ts.length()) +"%s", temp, ts   );
                    }else {
                        String ts = entry.getValue().value; 
                        System.out.printf("\n%s" + paddingString.substring(temp.toString().length(), paddingString.length()- ts.length()) +"%s", temp,  ts); 
                    }
                }
                
            });
            ArrayList<HashMap<String, LexToken>> hashList = new ArrayList<>();
            ArrayList<String> funcList = new ArrayList<>(); 
            allTables.entrySet().forEach(entry-> {
                funcList.add(entry.getKey()); 
                hashList.add(entry.getValue());
            });
            
            int n = funcList.size(); 
            for (int i = 0 ; i < n ; i ++ ){
                System.out.println();
                System.out.printf("\n\n================Symbol Table================\n");
                System.out.printf("%s Symbol Table\n", funcList.get(i));
                System.out.printf("\n%s" + paddingString.substring(tok.length(), paddingString.length() - vals.length()) +"%s\n", tok,   vals);
                System.out.printf(new String(new char[paddingString.length()]).replace("\0", "-"));
                HashMap<String, LexToken> thisMap = hashList.get(i); 
                thisMap.entrySet().forEach(entry ->{
                    LexToken temp  = entry.getValue(); 
                    if (!temp.attributes.contains("Operators") && !temp.attributes.contains("Relop") && !temp.attributes.contains("Syntax")  && !temp.attributes.contains("Keyword") ){
                        if (temp.attributes.contains("Variable")){
                            String ts = getLexeme(temp.value); 
                            System.out.printf("\n%s" + paddingString.substring(temp.toString().length(), paddingString.length()- ts.length()) +"%s", temp, ts   );
                        }else {
                            String ts = entry.getValue().value; 
                            System.out.printf("\n%s" + paddingString.substring(temp.toString().length(), paddingString.length()- ts.length()) +"%s", temp,  ts); 
                        }
                    }
                    
                }); 
            }
        }catch(Exception e ){
            System.out.printf("\nError: %s\n %s", e.getMessage() );
            e.printStackTrace();
        
        }

        /*  
        while ((reader.hasNext()|| returnChar != "")&& dot){
            token = getNextToken(reader); 
 
            if (!skip && token != null ){
                TokenList.add(token);
                System.out.print(token);//change this to having it added to the LexToken String
            }else {
                skip = false; 
            }

        } 
        */            
        reader.close();
        //HTML Closing body
    }
    
    public static void parse()throws Exception{
        
        program();

    }
    public static void program() throws Exception{

        fdecls(); 
        declarations(); 
        statement_seq(); 

        if (!match(".") && !reader.hasNext()){
            throw new Exception("Missing '.'  at end of program"); 
        } else if (reader.hasNext()){
            throw new Exception("program ends after <statements> Expected '.' at the end of program, instead received '" + lookahead.value + "'"); 
        }
    }
    public static void fdecls() throws Exception{
        if (fdec() && match(";") ){
            fdecls(); 
             
        }
         
    }

    public static boolean fdec() throws Exception{
        if(match("def")){
            if (!type() ){//not valid type
                throw new Exception("Expected <type>"); 
            }
            if (!fname()){//not valid fname
                throw new Exception("Expected <fname>"); 
            }
            if (!match("(")){//not valid match
                throw new Exception("Expected '('"); 
            }

            if (!params()){//not valid params
                throw new Exception("Expected '('"); 
            }
            if (!match(")")){//not valid match
                throw new Exception("Expected ')'"); 
            }
            System.out.println();
            tabNum ++ ; 
            System.out.print(new String(new char[tabNum]).replace("\0", tabString)); 
            
            if (!declarations()){//not valid declaration
                throw new Exception("Expected <declarations>"); 
            }
            if (!statement_seq()){//not valid declaration
                throw new Exception("Expected <declarations>"); 
            }
            if (!match("fed")){//not valid declaration
                throw new Exception("Expected <declarations>"); 
            }
            currentTable = syTable; 
            
            return true; 
        }
        return false; 
        
    }
    public static boolean type() throws Exception{
        if(match("int")|| match("double") ){
            return true; 
        }
        return false; 
    }
    public static boolean fname() throws Exception{
        if (id()){
            HashMap<String, LexToken> temp = new HashMap<>(); 
            allTables.put(funcName, temp); 
            currentTable = temp; 
            return true; 
        }
        return false; 
    }
    public static boolean id() throws Exception{
        LexToken temp = lookahead; 
        if (lookahead.token.equals("ID")){
            funcName = int2Lexeme.get(lookahead.value); 
            System.out.printf("%s", int2Lexeme.get(lookahead.value));
            if (reader.hasNext()){
                lookahead = getNextToken(reader);
            }else {
                throw new Exception("Program unexpectedly ended");
            } 
            return true; 
        }
        return false; 
        
    }
    public static boolean params()throws Exception{
        if (type() && var()){
            params_rest(); 
        }
        return true; 
    }
    public static void params_rest() throws Exception{
        if(match(",")){
            params(); 
        }
    }
    public static boolean var()throws Exception{
        if(id()){
            var_rest(); 
            return true;
        }
         return false; 
    }
    public static void var_rest()throws Exception{
        if(match("[")){
            if(!expr()){
                throw new Exception("Expected  <expr>"); 
            }
            if (!match("]")){
                throw new Exception("Expected ']' after <expr>"); 
            }
        }
    }
    public static boolean expr()throws Exception{
        boolean val = false; 
        if(term()){
            val = true; 
        } 
        expr_rest(); 
        return val; 
    }
    public static boolean expr_rest()throws Exception {
        if (match("+") || match("-")){
            term(); 
            expr_rest(); 
        }
        return true; 
    }
    public static boolean term()throws Exception{
        boolean val = false; 
        if (factor()){
            val = true;
        }
        
        term_rest();
        return val; 
    }
    public static boolean term_rest() throws Exception{
        if (match("*") || match("/") || match("%")){
            factor();
            term_rest(); 
        }
        return true; 
    }   
    public static boolean factor() throws Exception{
        if (id()){
            if(!factor_rest()){
                throw new Exception("Expected type <factor>"); 
            }
            return true; 
        }
        if (number()){
            return true; 
        }
        if(match("(")){
            if (expr()){
                if (match(")")){
                    return true; 
                }else {
                    throw new Exception("Expecting ')'"); 
                }
            }


        }
        throw new Exception("Expected type <factor>"); 
        
    }
    public static boolean factor_rest() throws Exception{
        if(match("(")){
            if(!exprseq()){
                throw new Exception("Expecting <exprseq> after '('"); 
            }
            if(!match(")")){
                throw new Exception("Expecting <exprseq> after ')'");
            }
        }
        if(varlist_rest()){
            return true; 
        }

        return false ;
    }
    public static boolean exprseq()throws Exception{
        if(expr()){
            exprseq_rest(); 
        }
        return true; 
    }
    public static boolean exprseq_rest() throws Exception{
        if(match(",")){
            exprseq();
        }
        return true;
    }
    public static boolean number() throws Exception{
        LexToken f = lookahead; 
        if (lookahead.token.equals("Integer") || lookahead.token.equals("DOUBLE")){
            System.out.printf("%s", lookahead.value );
            if (reader.hasNext()){
                lookahead = getNextToken(reader); 
            }else{
                throw new Exception("Program Unexpectedly ended");
            }
            
            return true; 
        }
        return false; 
    }
    public static boolean declarations() throws Exception{
        if (decl() ){
            if (!match(";")){
                throw new Exception("Expected ';' "); 
            }
            declarations(); 
        }
        return true; 
    }
    public static boolean decl() throws Exception{
        if (!type()  ){
            return false; 
        }
        if (!varlist()){
            throw new Exception("Expected <varlist> ");
        }
        return true; 
    }
    public static boolean varlist()throws Exception{
        if (var()){
            
            varlist_rest(); 
            
            return true; 
        }
        throw new Exception("Expected type <var> in <varlist>");  
    }
    public static boolean varlist_rest()throws Exception{
        if (match(",")){
            varlist(); 
        }
        return true; 
    }
    public static boolean statement_seq() throws Exception{
        if (statement()){
            statement_seq_rest(); 
            
        }
        return true; 
    }
    public static boolean statement() throws Exception{
        if (var()){
            if (!match("=")){
                throw new Exception("Expected '=' after type <var>");  
            }
            if (!expr()){
                throw new Exception("Expected <expr> after '=' ");
            }
        
        }else if(match("if")){
            if (!bexpr()){
                throw new Exception("Expected <bexpr> after if ");
            }else if(!match("then")){
                throw new Exception("Expected 'then' after <bexpr>"); 

            }
            
            statement_seq(); 
            statement_rest(); 
            
            if (!match("fi")){
                throw new Exception("Expected fi at end of if");
            }
            
        }else if(match("while")){

            if (!bexpr()){
                throw new Exception("Expected <bexpr> after while ");
            }else if(!match("do")){
                throw new Exception("Expected 'do' after <bexpr>"); 

            }
             
            statement_seq(); 
            
            if (!match("od")){
                throw new Exception("Expected od at end of do");
            }
            
        }else if(match("print")){
            if (!expr()){
                throw new Exception("Expected <Expr> at the end of 'print'");
            }
        }else if(match("return")){
            if(!expr()){
                throw new Exception("Expected <Expr> at the end of 'return'");
            }
        }
        return true; 
    }
    public static boolean statement_rest()throws Exception{
        if (match("else")){
            statement_seq(); 
        }
        return true; 
    }
    public static boolean statement_seq_rest()throws Exception{
        if (match(";")){
            statement_seq(); 
        }
        return true; 
    }
    public static boolean bexpr()throws Exception{
        boolean val = false; 
        if (bterm()){
            val = true;
        }
        bexpr_rest();
        return val;  
    }
    public static boolean bexpr_rest()throws Exception{
        if (match("or")){
            bterm();
            bexpr_rest();
        }
        return true; 
    }
    public static boolean bterm()throws Exception{
        boolean val = false; 
        if (bfactor()){
            val = true;
        }
        bterm_rest(); 
        return val; 
    }
    public static boolean bterm_rest()throws Exception{
        if(match("and")){
            bfactor();
            bterm_rest(); 
        }
        return true; 
    }
    public static boolean bfactor()throws Exception{
        if (match("(")){
            bfactor_rest();
            if(!match(")")){
                throw new Exception("Expected ')' after <bfactor-rest>");
            }
            return true; 
        }else if(match("not")){
            bfactor();
            return true;  
        }
        return false; 
    }
    public static boolean bfactor_rest() throws Exception{
        if(bexpr()){
            return true; 
        }
        if(expr()){
            if (lookahead.attributes.equals("Relop")){
                
                System.out.printf("%s",lookahead.value); 
                if (reader.hasNext()){
                    lookahead = getNextToken(reader); 
                }else {
                    throw new Exception("Program Unexpectedly ended");
                }

            }else {
                throw new Exception("Expected <comp> after <expr>"); 
            }
            if(!expr()){
                throw new Exception("Expected <expr> after <comp>"); 
            }
            return true; 
        }
        
        throw new Exception("Expected <bfactor-rest>"); 
    }
    public static boolean match(String value) throws Exception{
        //System.out.println("LINE 969");
        if(lookahead.valueEquals(value)){
            //System.out.println("LINE 971");


            if (value.equals("fi") || value.equals("od")||value.equals("fed")|| value.equals("else")){
                System.out.println(); 
                tabNum--; 
                System.out.print(new String(new char[tabNum]).replace("\0", tabString)); 
            } 
            if (lookahead.token != "ID"){
                //System.out.println("LINE 973");
                System.out.printf("%s", lookahead.value );

            }else{
                //System.out.println("LINE 974");
                System.out.printf("%s", int2Lexeme.get(lookahead.value));
            }
            if (lookahead.attributes.contains("Keyword")){
                System.out.print(" ");
            }

            //printing
            if (value.equals(";")){
                System.out.println();
                System.out.print(new String(new char[tabNum]).replace("\0", tabString)); 
            }else if ( value.equals("then")||value.equals("do")||value.equals("else") ){
                System.out.println();
                tabNum ++; 
                System.out.print(new String(new char[tabNum]).replace("\0", tabString)); 
            }



            if (reader.hasNext()){
                lookahead = getNextToken(reader); 
            }else if (!value.equals(".")){
                throw new Exception("Program Unexpectedly ended");
            }
            return true; 
        }
        return false; 
    
    }
    
}
