package de.vogella.plugin.cobide.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.PatternRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;

public class MarkdownTextPartitionScanner extends RuleBasedPartitionScanner {
    public static final String MARKDOWN_ID_DIV = "__markdown_id_div";
    public static final String MARKDOWN_EN_DIV = "__markdown_en_div";
    public static final String MARKDOWN_DD_DIV = "__markdown_dd_div";
    public static final String MARKDOWN_PD_DIV = "__markdown_pd_div";
    public static final String MARKDOWN_IO_SEC = "__markdown_io_sec";
    public static final String MARKDOWN_CN_SEC = "__markdown_cn_sec";
    public static final String MARKDOWN_FL_SEC = "__markdown_fl_sec";
    public static final String MARKDOWN_WS_SEC = "__markdown_ws_sec";
    public static final String MARKDOWN_LS_SEC = "__markdown_ls_sec";
    public static final String MARKDOWN_COMMENT = "__markdown_cmt";
    
    public static final String[] LEGAL_CONTENT_TYPES = {MARKDOWN_ID_DIV,
    	                                                MARKDOWN_EN_DIV,
    	                                                MARKDOWN_DD_DIV,
    	                                                MARKDOWN_PD_DIV,
    	                                                MARKDOWN_IO_SEC,
    	                                                MARKDOWN_CN_SEC,
    	                                                MARKDOWN_FL_SEC,
    	                                                MARKDOWN_WS_SEC,
    	                                                MARKDOWN_LS_SEC,
    	                                                MARKDOWN_COMMENT};
    
    public MarkdownTextPartitionScanner() {
            
	    IToken iddiv =new Token(MARKDOWN_ID_DIV);
	    IToken endiv =new Token(MARKDOWN_EN_DIV);
	    IToken dddiv =new Token(MARKDOWN_DD_DIV);
	    IToken pddiv =new Token(MARKDOWN_PD_DIV);
	    IToken iosec =new Token(MARKDOWN_IO_SEC);	    
	    IToken cnsec =new Token(MARKDOWN_CN_SEC);
	    IToken flsec =new Token(MARKDOWN_FL_SEC);
	    IToken wssec =new Token(MARKDOWN_WS_SEC);
	    IToken lssec =new Token(MARKDOWN_LS_SEC);
	    IToken coment =new Token(MARKDOWN_COMMENT);
    	
    	    List<PatternRule> rules = new ArrayList<PatternRule>();
    	    System.out.println("came this far");
            //rules.add(new CommentRule("*",coment));
            //rules.add(new CommentRule("/",coment));
            rules.add(new SingleLineRule("*", "\n", coment, ' '));
            rules.add(new MultiLineRule("IDENTIFICATION", "DIVISION", iddiv));
            rules.add(new MultiLineRule("ENVIRONMENT", "DIVISION", endiv));
            rules.add(new MultiLineRule("DATA", "DIVISION", dddiv));                
            rules.add(new MultiLineRule("PROCEDURE", "DIVISION", pddiv));
            rules.add(new MultiLineRule("INPUT-OUTPUT", "SECTION", iosec));            
            rules.add(new MultiLineRule("CONFIGURATION", "SECTION", cnsec));
            rules.add(new MultiLineRule("FILE ", "SECTION", flsec)); 
            rules.add(new MultiLineRule("WORKING-STORAGE", "SECTION", wssec)); 
            rules.add(new MultiLineRule("LINKAGE", "SECTION", lssec));           
        	IPredicateRule[] result= new IPredicateRule[rules.size()];
        	rules.toArray(result);
    	    System.out.println("setting rules");             	
            setPredicateRules( result );
    	    System.out.println("set rules");            
    }

}
