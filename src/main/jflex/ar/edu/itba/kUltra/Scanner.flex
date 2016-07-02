package ar.edu.itba.kUltra;

import java_cup.runtime.*;
import java_cup.runtime.ComplexSymbolFactory.Location;
import java.io.Reader;

import static ar.edu.itba.kUltra.Symbols.*;

%%
%cupsym Symbols
%public
%line
%column
%cup
%unicode
%class Scanner

%{
private ComplexSymbolFactory symbolFactory;
private int csline, cscolumn;
private String filename = "unknown";

public Scanner(java.io.InputStream reader, ComplexSymbolFactory sf) {
    this(reader);
    symbolFactory = sf;
}

public void setFilename(String filename) {
    this.filename = filename;
}

public Symbol symbol(String name, int code) {
    Location left = new Location(filename, yyline + 1, yycolumn + 1 - yylength());
    Location right = new Location(filename, yyline + 1, yycolumn + 1);
    return symbolFactory.newSymbol(name, code, left, right);
}

public Symbol symbol(String name, int code, Object lexem) {
    Location left = new Location(filename, yyline+1, yycolumn +1);
    Location right = new Location(filename, yyline+1, yycolumn+yylength());
    return symbolFactory.newSymbol(name, code, left, right, lexem);
}
%}


LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator} | [ \n\t]

Underscore = "_"
Digit = [0-9]
Letter = [A-Za-z]
Alphanumeric = {Letter} | {Digit}
Identifier = {Letter} ( {Alphanumeric} | {Underscore} )*
Variable = {Identifier}

Literal = {Integer} | {String}
Integer = [0] | [1-9] {Digit}*
String = "\"" [^\"]* "\""

CommentText = [^\r\n]
CommentBody = {CommentText}* \n
LineComment = "#" {CommentBody}

%%

/* Binary operators */

"-"                            { System.out.println("-"); return symbol("-", MINUS); }
"+"                            { System.out.println("+"); return symbol("+", PLUS); }
"*"                            { System.out.println("*"); return symbol("*", MULT); }
"/"                            { System.out.println("/"); return symbol("/", DIV); }
"%"                            { System.out.println("mod"); return symbol("mod", MOD); }
"and"                          { System.out.println("and"); return symbol("and", AND); }
"or"                           { System.out.println("or"); return symbol("or", OR); }
"<"                            { System.out.println("<"); return symbol("<", LT); }
"<="                           { System.out.println("<="); return symbol("<=", LTEQ); }
">"                            { System.out.println(">"); return symbol(">", GT); }
">="                           { System.out.println(">="); return symbol(">=", GTEQ); }
"=="                           { System.out.println("=="); return symbol("==", EQEQ); }
"!="                           { System.out.println("!="); return symbol("!=", NOTEQ); }
"="                            { System.out.println("="); return symbol("=", EQ); }

/* separators */
"("                            { System.out.println("("); return symbol("(", LPAREN); }
")"                            { System.out.println(")"); return symbol(")", RPAREN); }
";"                            { System.out.println(";"); return symbol(";", SEMICOLON); }
","                            { System.out.println(","); return symbol(",", COMMA); }

":"                            { System.out.println(":"); return symbol(":", COLON); }

/* keywords */
"int"                          { System.out.println("int"); return symbol("int", INT); }
"str"                          { System.out.println("str"); return symbol("str", STR); }
"void"                         { System.out.println("void"); return symbol("void", VOID); }
"return"                       { System.out.println("return"); return symbol("return", RETURN); }
{LineComment}                  { System.out.println("COMMENT> " + yytext()); /* ignore :) */ }
{WhiteSpace}                   { /* ignore :) */ }
{LineTerminator}               { /* ignore :) */ }
{String}            		   { System.out.println("UN STRING"); return symbol("str", STRING, yytext().substring(1, yytext().length() - 1)); } // remove the quotes
{Integer}           		   { System.out.println("IN ENTERO"); return symbol("int", INTEGER, Integer.valueOf(yytext())); }
"def"                          { System.out.println("def"); return symbol("def", DEF); }
"end"                          { System.out.println("end"); return symbol("end", END); }
"if"                           { System.out.println("if"); return symbol("if", IF); }
"else"                         { System.out.println("else"); return symbol("else", ELSE); }
"while"                        { System.out.println("while"); return symbol("while", WHILE); }
{Identifier}                   { System.out.println("identifier"); return symbol(yytext(), IDENTIFIER, yytext()); }

/* error fallback */
[^]                            { System.out.println("Illegal character <"+yytext()+">"); }
