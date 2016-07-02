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

"-"                            { return symbol("-", MINUS); }
"+"                            { return symbol("+", PLUS); }
"*"                            { return symbol("*", MULT); }
"/"                            { return symbol("/", DIV); }
"%"                            { return symbol("mod", MOD); }
"and"                          { return symbol("and", AND); }
"or"                           { return symbol("or", OR); }
"<"                            { return symbol("<", LT); }
"<="                           { return symbol("<=", LTEQ); }
">"                            { return symbol(">", GT); }
">="                           { return symbol(">=", GTEQ); }
"=="                           { return symbol("==", EQEQ); }
"!="                           { return symbol("!=", NOTEQ); }
"="                            { return symbol("=", EQ); }

/* separators */
"("                            { return symbol("(", LPAREN); }
")"                            { return symbol(")", RPAREN); }
";"                            { return symbol(";", SEMICOLON); }
","                            { return symbol(",", COMMA); }

":"                            { return symbol(":", COLON); }

/* keywords */
"int"                          { return symbol("int", INT); }
"str"                          { return symbol("str", STR); }
"void"                         { return symbol("void", VOID); }
"return"                       { return symbol("return", RETURN); }
{LineComment}                  { /* ignore :) */ }
{WhiteSpace}                   { /* ignore :) */ }
{LineTerminator}               { /* ignore :) */ }
{String}            		   { return symbol("str", STRING, yytext().substring(1, yytext().length() - 1)); } // remove the quotes
{Integer}           		   { return symbol("int", INTEGER, Integer.valueOf(yytext())); }
"def"                          { return symbol("def", DEF); }
"end"                          { return symbol("end", END); }
"if"                           { return symbol("if", IF); }
"else"                         { return symbol("else", ELSE); }
"while"                        { return symbol("while", WHILE); }
{Identifier}                   { return symbol(yytext(), IDENTIFIER, yytext()); }

/* error fallback */
[^]                            { /* +++xcheck */ }
