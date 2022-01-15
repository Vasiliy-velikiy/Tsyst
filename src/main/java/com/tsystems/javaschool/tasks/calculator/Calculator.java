package com.tsystems.javaschool.tasks.calculator;





import java.util.ArrayList;
import java.util.List;

public class Calculator {
//
    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        // TODO: Implement the logic here

        List<Lexeme>  lexemes;  //list for all lexemes
        LexemeBuffer lexemeBuffer;
        double result;       // four operations of computing return double
        String resultToString;  //double must convert to string

        try {    //wrong chars from statement throws exeption. In this case in Tests  method "evaluate" must returns null

            lexemes = lexAnalyze(statement);
            lexemeBuffer = new LexemeBuffer(lexemes);

            result = expr(lexemeBuffer);

            if (result == Double.POSITIVE_INFINITY) return null;//this result  come in if we divide by zero
            if(statement=="")return null;
            if (result == Math.floor(result)) {  // for example if double result = 11.0, we must to round
                resultToString = String.valueOf((int) result);      //in this case result=11, and we convert to string
            } else {
                resultToString = String.valueOf(result);
            }
            return resultToString;
        }
        catch (RuntimeException e){
            return null;
        }


    }


/**This class is needed for parsing expression on different parts: numbers, operations, brackets */
    static class Lexeme {

        LexemeType type; //enum
        String value; //How lexeme presents in text (string statement)

        public Lexeme(LexemeType type, String value) {
            this.type = type;
            this.value = value;
        }

        public Lexeme(LexemeType type, Character value) {
            this.type = type;
            this.value = value.toString();
        }

        @Override
        public String toString() {
            return "Lexeme{" +
                    "type=" + type +
                    ", value='" + value + '\'' +
                    '}';
        }
    }


    /** This class is needed to manage iteration array of lexemes, control position lexeme, etc*/
    static class LexemeBuffer{
        private int pos;

        public List<Lexeme> lexemes;

        public LexemeBuffer(List<Lexeme> lexemes) {
            this.lexemes = lexemes;
        }

        /*return  get current lexeme and shifts on next  position*/
        public Lexeme next(){
            return lexemes.get(pos++);
        }
        /*mark of position shifts back*/
        public  void back(){
            pos--;
        }
        /* return current position*/
        public int getPos(){
            return pos;
        }
    }


    /*param  expText string for computing
     * return array of lexeme
     * throws  if current lexeme wrong*/
    public static List<Lexeme>lexAnalyze(String expText) throws RuntimeException {
        ArrayList<Lexeme> lexemes=new ArrayList<>();
                    //iterating elements of string and analyze chars
        int pos=0;
        while (pos<expText.length()){  // cycle for iterate chars
            char c=expText.charAt(pos);//get current char from string
            switch (c){                 //if char= brackets or operations of computing
                case '(':
                    lexemes.add(new Lexeme(LexemeType.LEFT_BRACKET,c));
                    pos++;
                    continue;
                case ')':
                    lexemes.add(new Lexeme(LexemeType.RIGHT_BRACKET,c));
                    pos++;
                    continue;
                case '+':
                    lexemes.add(new Lexeme(LexemeType.OP_PLUS,c));
                    pos++;
                    continue;
                case '-':
                    lexemes.add(new Lexeme(LexemeType.OP_MINUS,c));
                    pos++;
                    continue;
                case '*':
                    lexemes.add(new Lexeme(LexemeType.OP_MUL,c));
                    pos++;
                    continue;
                case '/':
                    lexemes.add(new Lexeme(LexemeType.PO_DIV,c));
                    pos++;
                    continue;
                default:   //if char!= brackets or operations of computing
                    if(c<='9'&&c >= '0'){   //if char=number, we will read full number
                        StringBuilder sb=new StringBuilder(); //object for reading full number
                        do {                    // if current char=number we continue read full number
                            sb.append(c);     //append char in  full number
                            pos++;       //increase position ( next element)
                            if (pos >= expText.length()) {//if we have end of string
                                break;    //stop cycle
                            }
                            c=expText.charAt(pos);   //get next char from string

                        }while (c<='9'&&c >= '0' || c=='.');    //cycle works if current symble =number or . for example =3.048
                        lexemes.add(new Lexeme(LexemeType.NUMBER,sb.toString())) ;   //add in list of lexemes
                    }else {          //if char!=number and !=brackets and operations of computing
                        if(c!=' '){//if char != space it means we have wrong symbol
                            throw new RuntimeException("Unexpected character: "+c);
                        }
                        pos++;//increase position ( next element)
                    }
            }
        }
        lexemes.add(new Lexeme(LexemeType.EOF,""));//  add mark in end of string
        return lexemes;
    }



//static analizator corresponds each of four rules: which rule is executed first
    //expr: plusminus *EOF;
    //plusminus: multdiv (('+' | '-') multdiv)*;
    //multdiv:factor (( '*' | '/' ) factor )* ;
    //factor: NUMBER | '(' expr ')' ; //factor- expresion in brackets or number


    public static double expr(LexemeBuffer lexemes){ //object (LexemeBuffer) were save list of lexemes
        Lexeme lexeme=lexemes.next();
        if(lexeme.type== LexemeType.EOF){ //check on end of string
            return 0;
        }
        else {
            lexemes.back();//иначе вернемся назад
            return plusminus(lexemes); //запустим вычисление +- подвыражения
        }
    }

    /* object (LexemeBuffer) were save list of lexemes
     * @return double result*/
    public static double multdiv(LexemeBuffer lexemes){  //handling multiplication or division operation
        double value=factor(lexemes);   //computing first expresion
        while(true){     //we can have a lot of multiplication or division operations
            Lexeme lexeme=lexemes.next();  //get next element from lost of lexemes
            switch (lexeme.type){  //check type lexeme(check operation)
                case OP_MUL:
                    value *=factor(lexemes);  //multiply number(or expresion) on other number(or expresion)
                    break;
                case PO_DIV:
                    value /=factor(lexemes);//such as multiplication
                    break;
                default:   //if we find other lexeme
                    lexemes.back();//shifts mark to back
                    return value;//return first value
            }
        }
    }

    /* object (LexemeBuffer) were save list of lexemes
     *return double result*/
    public static double plusminus(LexemeBuffer lexemes){// such as multdiv method

        double value=multdiv(lexemes);
        while(true){
            Lexeme lexeme=lexemes.next();
            switch (lexeme.type){
                case OP_PLUS:
                    value +=multdiv(lexemes);
                    break;
                case OP_MINUS:
                    value -=multdiv(lexemes);
                    break;
                default:
                    lexemes.back();
                    return value;
            }
        }
    }
    /*param object (LexemeBuffer) were save list of lexemes
     * return double result*/
    public static double factor(LexemeBuffer lexemes) throws RuntimeException{
        Lexeme lexeme=lexemes.next();//read next lexeme
        switch (lexeme.type) {     //check type
            case NUMBER:
                return Double.parseDouble(lexeme.value); // if current lexeme=number, convert to double
            case LEFT_BRACKET:
                double value=expr(lexemes); //expr-reads all lexemes that corresponds in current expression
                lexeme=lexemes.next(); //next lexeme should be right bracket
                if (lexeme.type!= LexemeType.RIGHT_BRACKET){ //if next lexeme !=')'  this expression is wrong
                    throw new RuntimeException("Unexpected token: "+lexeme.value+" at position: "+lexemes.getPos());
                }
                return value;// return result from brackets
            default:  //if we find other lexeme -it is wrong
                throw new RuntimeException("Unexpected token: "+lexeme.value+" at position: "+lexemes.getPos());

        }
    }
}
