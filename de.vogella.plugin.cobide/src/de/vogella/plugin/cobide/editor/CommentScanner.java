package de.vogella.plugin.cobide.editor;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.graphics.Color;

public class CommentScanner extends RuleBasedScanner {

	  public static final String EOL_COMMENT_START = "*";
	  public static final String EOL_COMMENT_START_EXTRA = "/";

	  public CommentScanner() {
		System.out.println("commentscanner was executed atleast once");
	    IToken commentToken = createCommentToken();
	    IRule[] rules = new IRule[] {
	      new EndOfLineRule( EOL_COMMENT_START, commentToken ),
	      new EndOfLineRule( EOL_COMMENT_START_EXTRA, commentToken )
	    };
	    setRules( rules );
	  }
	  
	  private IToken createCommentToken() {
		    Color color = ColorManager.getColor( IMarkdownTextColorConstants.MARKDOWN_COMMENT );
		    IToken defaultToken = new Token( new TextAttribute(color));
		    /*IToken defaultToken = new Token( new TextAttribute(
	                ColorManager.getColor(IMarkdownTextColorConstants.MARKDOWN_COMMENT),
	                ColorManager.getColor(IMarkdownTextColorConstants.MARKDOWN_COMMENT_BG),
	                ) );*/
		    return defaultToken;
		  }	  
}
