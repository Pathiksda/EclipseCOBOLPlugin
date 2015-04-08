package de.vogella.plugin.cobide.editor;

import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;

public class CommentRule extends EndOfLineRule {

	public CommentRule (String startSequence, IToken token) {
		super(startSequence, token);
		System.out.println("this called");
		// TODO Auto-generated constructor stub
	}
	
	protected boolean sequenceDetected(
			ICharacterScanner scanner,
			char[] sequence,
			boolean eofAllowed) {
		    int pos=0;
		    scanner.unread();
		    scanner.unread();
		    int a = scanner.read();
		    int b = scanner.read();
		    scanner.read();
		    if ( a == '\n' || a == '*' )
		    {
		    	pos=1;
		    }
		    System.out.println("sequenceDetected "+sequence[0]+" read last two chars["+a+":"+(char)a+":"+b+":"+(char)b+"]pos is:"+pos);		    
		    if ( pos == 1 )
			{
				//System.out.println("returning true");		
		    	//return super.sequenceDetected(scanner, sequence, eofAllowed);
				return true;
			}
		    else
		    {
		    	//System.out.println("returning false");
				return false;		    
		    }
			//System.out.println("returning true");	
			//
	}
}
/*
	protected boolean sequenceDetected(
			ICharacterScanner scanner,
			char[] sequence,
			boolean eofAllowed) {
			int c = scanner.read();
			int pos=0;
			if (sequence[0] == '*') {
				//return false if the position is not 1
				if ( pos > 1 ) {
					// processing instruction - abort
					scanner.unread();
					System.out.println("sequenceDetected:"+c+" "+(int)sequence[0]+" return false");					
					return false;
				}
				if ( c == '!' ) {
					scanner.unread();
					// comment - abort
					System.out.println("sequenceDetected:"+c+" "+(int)sequence[0]+" return false");					
					return false;
				}
				System.out.println("sequenceDetected:"+c+" "+(int)sequence[0]+" continue");				
			}
			else if ( sequence[0] == '\n' || c == ICharacterScanner.EOF )
			{
				System.out.println("sequenceDetected:"+c+" "+(int)sequence[0]+" continue endsequence read");				
				scanner.unread();
			}
			System.out.println("recursing");			
			return super.sequenceDetected(scanner, sequence, eofAllowed);
	}
 */
