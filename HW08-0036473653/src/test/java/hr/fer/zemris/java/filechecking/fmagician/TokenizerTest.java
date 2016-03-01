package hr.fer.zemris.java.filechecking.fmagician;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.java.filechecking.fmagician.lexical.FMagicianToken;
import hr.fer.zemris.java.filechecking.fmagician.lexical.FMagicianTokenType;
import hr.fer.zemris.java.filechecking.fmagician.lexical.FMagicianTokenizer;
import hr.fer.zemris.java.filechecking.fmagician.lexical.FMagicianTokenizerException;

public class TokenizerTest {


			@Test
			public void getTokenizerTestedSubTag(){
				String naj="exist d \"${srcahahaha\"";
				FMagicianTokenizer tokenizer = new FMagicianTokenizer(naj);
				try{
				while(true) {
					FMagicianToken token = tokenizer.getCurrentToken();
					
					System.out.println("Trenutni token: "+token.getTokenType()
							+", vrijednost '"+token.getValue()+"'");
					if(token.getTokenType() == FMagicianTokenType.EOF) break;
					tokenizer.nextToken();
				};
				} catch(FMagicianTokenizerException t){
					Assert.assertTrue(t.getMessage(),t.getMessage().equals("Sub tag never closed!"));
				}
			}

				@Test
				public void getTokenizerTestedVarNameInvalid(){
					String naj="def 4.opsw";
					FMagicianTokenizer tokenizer = new FMagicianTokenizer(naj);
					try{
					while(true) {
						FMagicianToken token = tokenizer.getCurrentToken();
						
						System.out.println("Trenutni token: "+token.getTokenType()
								+", vrijednost '"+token.getValue()+"'");
						if(token.getTokenType() == FMagicianTokenType.EOF) break;
						tokenizer.nextToken();
					};
					} catch(FMagicianTokenizerException t){
						Assert.assertTrue(t.getMessage(),t.getMessage().equals("Invalid variable name! 1. char should not be : 4" ));
					}
					
					
			}
				@Test
				public void getTokenizerTestedVarNameInvalidMiddle(){
					String naj="exist d @\"ovo nije valja{na poruka!\"";
					FMagicianTokenizer tokenizer = new FMagicianTokenizer(naj);
					try{
					while(true) {
						FMagicianToken token = tokenizer.getCurrentToken();
						
						System.out.println("Trenutni token: "+token.getTokenType()
								+", vrijednost '"+token.getValue()+"'");
						if(token.getTokenType() == FMagicianTokenType.EOF) break;
						tokenizer.nextToken();
					};
					} catch(FMagicianTokenizerException t){
						Assert.assertTrue(t.getMessage(),t.getMessage().equals("Error string contains forbidden character : {" ));
					}
					
					
			}
				@Test
				public void getTokenizerTestedStringHas$(){
					String naj="exist d @\"ovo nije valja$na poruka!\"";
					FMagicianTokenizer tokenizer = new FMagicianTokenizer(naj);
					try{
					while(true) {
						FMagicianToken token = tokenizer.getCurrentToken();
						
						System.out.println("Trenutni token: "+token.getTokenType()
								+", vrijednost '"+token.getValue()+"'");
						if(token.getTokenType() == FMagicianTokenType.EOF) break;
						tokenizer.nextToken();
					};
					} catch(FMagicianTokenizerException t){
						Assert.assertTrue(t.getMessage(),t.getMessage().equals("Error: string contains $"));
					}
					
					}
				
				@Test
				public void getTokenizerTestedMissinQuote(){
					String naj="exist d @\"ovo nije valjana poruka";
					FMagicianTokenizer tokenizer = new FMagicianTokenizer(naj);
					try{
					while(true) {
						FMagicianToken token = tokenizer.getCurrentToken();
						
						System.out.println("Trenutni token: "+token.getTokenType()
								+", vrijednost '"+token.getValue()+"'");
						if(token.getTokenType() == FMagicianTokenType.EOF) break;
						tokenizer.nextToken();
					};
					} catch(FMagicianTokenizerException t){
						Assert.assertTrue(t.getMessage(),t.getMessage().equals("Missing closing \" !"));
					}
					
					}

				@Test
				public void getTokenizerTesteInvalidIdentificator(){
					String naj="def op-aska";
					FMagicianTokenizer tokenizer = new FMagicianTokenizer(naj);
					try{
					while(true) {
						FMagicianToken token = tokenizer.getCurrentToken();
						
						System.out.println("Trenutni token: "+token.getTokenType()
								+", vrijednost '"+token.getValue()+"'");
						if(token.getTokenType() == FMagicianTokenType.EOF) break;
						tokenizer.nextToken();
					};
					} catch(FMagicianTokenizerException t){
						Assert.assertTrue(t.getMessage(),t.getMessage().equals("Invalid variable name! 3. char should not be : -" ));
					}
					
					
			}
				@Test
				public void getTokenizerTestEmptyPackage(){
					String naj="exist file \":\"";
					FMagicianTokenizer tokenizer = new FMagicianTokenizer(naj);
					try{
					while(true) {
						FMagicianToken token = tokenizer.getCurrentToken();
						
						System.out.println("Trenutni token: "+token.getTokenType()
								+", vrijednost '"+token.getValue()+"'");
						if(token.getTokenType() == FMagicianTokenType.EOF) break;
						tokenizer.nextToken();
					};
					} catch(FMagicianTokenizerException t){
						Assert.assertTrue(t.getMessage(),t.getMessage().equals("Error: Package should not be empty!"));
					}
				}
					@Test
					public void getTokenizerTesteInvalidTag(){
						String naj="exist file \"$src}\"";
						FMagicianTokenizer tokenizer = new FMagicianTokenizer(naj);
						try{
						while(true) {
							FMagicianToken token = tokenizer.getCurrentToken();
							
							System.out.println("Trenutni token: "+token.getTokenType()
									+", vrijednost '"+token.getValue()+"'");
							if(token.getTokenType() == FMagicianTokenType.EOF) break;
							tokenizer.nextToken();
						};
						} catch(FMagicianTokenizerException t){
							Assert.assertTrue(t.getMessage(),t.getMessage().equals("$ must be with {!"));
						}
					
					
			}
					@Test
					public void getTokenizerTestMissingQuote(){
						String naj="exist file \"${src}:petra.karla";
						FMagicianTokenizer tokenizer = new FMagicianTokenizer(naj);
						try{
						while(true) {
							FMagicianToken token = tokenizer.getCurrentToken();
							
							System.out.println("Trenutni token: "+token.getTokenType()
									+", vrijednost '"+token.getValue()+"'");
							if(token.getTokenType() == FMagicianTokenType.EOF) break;
							tokenizer.nextToken();
						};
						} catch(FMagicianTokenizerException t){
							Assert.assertTrue(t.getMessage(),t.getMessage().equals("One quote is missing!"));
						}
					
					
			}
		
	}

