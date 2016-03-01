package hr.fer.zemris.bool;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class MasksTest {
	
 @Test
	public void fromStringsTest(){
	 List<Mask> maske=Masks.fromStrings("101","100","000");
	 Assert.assertTrue(maske.get(0).equals(Masks.parse("101")));
	 Assert.assertTrue(maske.get(1).equals(Masks.parse("100")));
	 Assert.assertTrue(maske.get(2).equals(Masks.parse("000")));
 }
 

 @Test
	public void fromIndexesTest(){
	 List<Mask> maske=Masks.fromIndexes(3,5,4,0);
	 Assert.assertTrue(maske.get(0).equals(Masks.parse("101")));
	 Assert.assertTrue(maske.get(1).equals(Masks.parse("100")));
	 Assert.assertTrue(maske.get(2).equals(Masks.parse("000")));
 }
 
	


}
