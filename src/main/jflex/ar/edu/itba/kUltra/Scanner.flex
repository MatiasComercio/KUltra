/*
    String code and ideas were extracted from:
    https://github.com/moy/JFlex/blob/master/jflex/examples/java/java.flex
*/

package ar.edu.itba.kUltra;

import java_cup.runtime.*;
import java_cup.runtime.ComplexSymbolFactory.Location;
import java.io.Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
private final static Logger LOGGER = LoggerFactory.getLogger(Scanner.class);
private final StringBuilder string = new StringBuilder();

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

public Symbol symbol(int code, Object lexem) {
    Location left = new Location(filename, yyline+1, yycolumn + 1 - yylength());
    Location right = new Location(filename, yyline+1, yycolumn + 1);
    final Symbol s = symbolFactory.newSymbol(lexem.toString(), code, left, right, lexem);
    return s;
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

// Literal = {Integer} | {String}
Integer = [0] | [1-9] {Digit}*
// String = "\"" [^\"]* "\""

CommentText = [^\r\n]
CommentBody = {CommentText}* \n
LineComment = "#" {CommentBody}



/* string and character literals */
StringCharacter = [^\r\n(\"|\')\\]
OctDigit          = [0-7]

%state STATE_STRING

%%

<YYINITIAL> {
    /* Binary operators */

    "-"                            { LOGGER.debug("-"); return symbol("-", MINUS); }
    "+"                            { LOGGER.debug("+"); return symbol("+", PLUS); }
    "*"                            { LOGGER.debug("/"); return symbol("*", MULT); }
    "/"                            { LOGGER.debug("/"); return symbol("/", DIV); }
    "%"                            { LOGGER.debug("%"); return symbol("mod", MOD); }
    "and"                          { LOGGER.debug("and"); return symbol("and", AND); }
    "or"                           { LOGGER.debug("or"); return symbol("or", OR); }
    "<"                            { LOGGER.debug("<"); return symbol("<", LT); }
    "<="                           { LOGGER.debug("<="); return symbol("<=", LTEQ); }
    ">"                            { LOGGER.debug(">"); return symbol(">", GT); }
    ">="                           { LOGGER.debug(">="); return symbol(">=", GTEQ); }
    "=="                           { LOGGER.debug("=="); return symbol("==", EQEQ); }
    "!="                           { LOGGER.debug("!="); return symbol("!=", NOTEQ); }
    "="                            { LOGGER.debug("="); return symbol("=", EQ); }

    /* separators */
    "("                            { LOGGER.debug("("); return symbol("(", LPAREN); }
    ")"                            { LOGGER.debug(")"); return symbol(")", RPAREN); }
    ";"                            { LOGGER.debug(";"); return symbol(";", SEMICOLON); }
    ","                            { LOGGER.debug(","); return symbol(",", COMMA); }

    ":"                            { LOGGER.debug(":"); return symbol(":", COLON); }

    /* string literal */
    \"                             { LOGGER.debug("\""); yybegin(STATE_STRING); string.setLength(0); }

    /* keywords */
    "int"                          { LOGGER.debug("int"); return symbol("int", INT); }
    "str"                          { LOGGER.debug("str"); return symbol("str", STR); }
    "void"                         { LOGGER.debug("void"); return symbol("void", VOID); }
    "return"                       { LOGGER.debug("return"); return symbol("return", RETURN); }
    {LineComment}                  { /* ignore :) */ }
    {WhiteSpace}                   { /* ignore :) */ }
    {LineTerminator}               { /* ignore :) */ }
    //{String}            		   { return symbol("str", STRING, yytext().substring(1, yytext().length() - 1)); } // remove the quotes
    {Integer}           		   { LOGGER.debug("{}", Integer.valueOf(yytext())); return symbol("int", INTEGER, Integer.valueOf(yytext())); }
    "def"                          { LOGGER.debug("def"); return symbol("def", DEF); }
    "end"                          { LOGGER.debug("end"); return symbol("end", END); }
    "if"                           { LOGGER.debug("if"); return symbol("if", IF); }
    "else"                         { LOGGER.debug("else"); return symbol("else", ELSE); }
    "while"                        { LOGGER.debug("while"); return symbol("while", WHILE); }
    {Identifier}                   { LOGGER.debug("{}", yytext()); return symbol(yytext(), IDENTIFIER, yytext()); }

    /* error fallback */
    [^]                            { /* +++xcheck */ }
}

<STATE_STRING> {
    \"|\'                          { LOGGER.debug("\"");  yybegin(YYINITIAL); return symbol(STRING, string.toString()); }

    {StringCharacter}+             { string.append( yytext() ); }

    /* escape sequences */
    "\\b"                          { LOGGER.debug("\\b"); string.append( '\b' ); }
    "\\t"                          { LOGGER.debug("\\t"); string.append( '\t' ); }
    "\\n"                          { LOGGER.debug("\\n"); string.append( '\n' ); }
    "\\f"                          { LOGGER.debug("\\f"); string.append( '\f' ); }
    "\\r"                          { LOGGER.debug("\\r"); string.append( '\r' ); }
    "\\\""                         { LOGGER.debug("\\\""); string.append( '\"' ); }
    "\\'"                          { LOGGER.debug("\\'"); string.append( '\'' ); }
    "\\\\"                         { LOGGER.debug("\\\\"); string.append( '\\' ); }
    \\[0-3]?{OctDigit}?{OctDigit}  { char val = (char) Integer.parseInt(yytext().substring(1),8);
                                           string.append( val ); LOGGER.debug("{}", val); }

    /* error cases */
    \\.                            { throw new RuntimeException("Illegal escape sequence \""+yytext()+"\""); }
    {LineTerminator}               { throw new RuntimeException("Unterminated string at end of line"); }
}