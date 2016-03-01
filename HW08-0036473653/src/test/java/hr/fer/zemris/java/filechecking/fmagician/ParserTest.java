package hr.fer.zemris.java.filechecking.fmagician;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.java.filechecking.fmagician.lexical.FMagicianTokenizer;
import hr.fer.zemris.java.filechecking.fmagician.syntax.FMagicianSyntaxException;
import hr.fer.zemris.java.filechecking.fmagician.syntax.Parser;


public class ParserTest {
@Test

public void getParserTestedMissingQuotes(){
	try{
	String naj="exists d \"homework\" @ec";
	Parser parser = new Parser(new FMagicianTokenizer(naj));
	parser.getProgramNode();
}catch (FMagicianSyntaxException se){
	Assert.assertTrue(se.getMessage().equals("Error: Expected 'i' or ' \" '!"));
}
}
@Test
public void getParserTestedMissingQuotes2(){
	try{
	String naj="exists d \"homework\" @iec";
	Parser parser = new Parser(new FMagicianTokenizer(naj));
	 parser.getProgramNode();
}catch (FMagicianSyntaxException se){
	Assert.assertTrue(se.getMessage().equals("Error: Expected ' \" ' after 'i' !"));
}
}
@Test
public void getParserTestedMissingBranch(){
	try{
	String naj="exists d \"homework\" @\"ec\" { ";
	Parser parser = new Parser(new FMagicianTokenizer(naj));
	  parser.getProgramNode();
}catch (FMagicianSyntaxException se){
	Assert.assertTrue(se.getMessage(),se.getMessage().equals("Found '}' without pair!"));
}
}
@Test
public void getParserTestedKeyword(){
	try{
	String naj="petra d \"homework\" @\"ec\" { ";
	Parser parser = new Parser(new FMagicianTokenizer(naj));
	 parser.getProgramNode();
}catch (FMagicianSyntaxException se){
	Assert.assertTrue(se.getMessage(),se.getMessage().equals("Keyword or negation expected."));
}
}
@Test
public void getParserTestedNegation(){
	try{
	String naj="exists  d \"homework\" @\"ec\" ! \"k\" ";
	Parser parser = new Parser(new FMagicianTokenizer(naj));
 parser.getProgramNode();
}catch (FMagicianSyntaxException se){
	Assert.assertTrue(se.getMessage(),se.getMessage().equals("Negation '!' must be before keyword!"));
}
}


@Test
public void getParserTestedIdentExpectedDef(){
	try{
	String naj="def \"homework\"";
	Parser parser = new Parser(new FMagicianTokenizer(naj));
	 parser.getProgramNode();
}catch (FMagicianSyntaxException se){
	Assert.assertTrue(se.getMessage(),se.getMessage().equals("Identifier was expected."));
}
}

@Test
public void getParserTestedIdentExpectedFormat(){
	try{
	String naj="format \"homework\"";
	Parser parser = new Parser(new FMagicianTokenizer(naj));
	 parser.getProgramNode();
}catch (FMagicianSyntaxException se){
	Assert.assertTrue(se.getMessage(),se.getMessage().equals("Identifier was expected."));
}
}

@Test
public void getParserTestedExist(){
	try{
	String naj="exists  err  \"homework\" @\"ec\" ! \"k\" ";
	Parser parser = new Parser(new FMagicianTokenizer(naj));
	parser.getProgramNode();
}catch (FMagicianSyntaxException se){
	Assert.assertTrue(se.getMessage(),se.getMessage().equals("Error: Expected 'd' , 'di' , 'dir' , 'f' , 'fi' , 'fil' , or 'file' !"));
}
}

}