package de.vogella.plugin.cobide.editor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.SWT;

public class MarkdownTextEditorConfiguration extends SourceViewerConfiguration {

    public MarkdownTextEditorConfiguration(ColorManager colorManager) {
		// TODO Auto-generated constructor stub
	}

	@Override
    public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
            return new String[] { IDocument.DEFAULT_CONTENT_TYPE,
                            MarkdownTextPartitionScanner.MARKDOWN_ID_DIV,
                            MarkdownTextPartitionScanner.MARKDOWN_EN_DIV,
                            MarkdownTextPartitionScanner.MARKDOWN_DD_DIV,
                            MarkdownTextPartitionScanner.MARKDOWN_PD_DIV,
                            MarkdownTextPartitionScanner.MARKDOWN_CN_SEC,
                            MarkdownTextPartitionScanner.MARKDOWN_WS_SEC,
                            MarkdownTextPartitionScanner.MARKDOWN_LS_SEC,
                            MarkdownTextPartitionScanner.MARKDOWN_COMMENT
                    };
    }
	/**
	 * Returns the PresentationReconciler object that is responsible for the proper
	 * style attributes (presentation) of each document partition.
	 * It manages the process of defining correct style attributes (color, etc) for
	 * text partitions that are changed by the user, i.e. correct style for updated
	 * partitions of the document content ("model").
	 * It identifies the "damaged text" (without the proper style) in the document based 
	 * on user's actions (typing, etc) and "repairs" it.
	 */
	@Override
	public IPresentationReconciler getPresentationReconciler (ISourceViewer sourceViewer) {
	        PresentationReconciler reconciler = new PresentationReconciler();
	                
	        DefaultDamagerRepairer dr = null;
	        // creates the damager-repairer for the H1 content type (mark)
	        // the scanner must define the correct style attributes for every possible token
	        // that can appear inside text with the given content type
	        dr = new DefaultDamagerRepairer(getMarkdownCommentScanner());                
	        reconciler.setDamager(dr, MarkdownTextPartitionScanner.MARKDOWN_COMMENT);
	        reconciler.setRepairer(dr, MarkdownTextPartitionScanner.MARKDOWN_COMMENT);

	        DefaultDamagerRepairer cr = null;
	        cr = new DefaultDamagerRepairer(getMarkdownDefaultScanner());                
	        reconciler.setDamager(cr, IDocument.DEFAULT_CONTENT_TYPE);
	        reconciler.setRepairer(cr, IDocument.DEFAULT_CONTENT_TYPE);	        
	        /* ... */
	        return reconciler;
	}

	private RuleBasedScanner markdownCommentScanner;
	private RuleBasedScanner markdownDefaultScanner;

	/**
	 * A scanner that returns tokens that have proper text attributes for H1 content type.
	 * The scanner says what style attributes (color, font) the tokens should have.
	 * 
	 * Here we define the same style for all possible text with the content type H1.
	 */
	private ITokenScanner getMarkdownCommentScanner() {
		    System.out.println("going for getMarkdownCommentScanner");
		    if (markdownCommentScanner == null) {                        
		    	markdownCommentScanner = new CommentScanner();
		    }
		    /*if (markdownCommentScanner == null) {                        
		    	markdownCommentScanner = new RuleBasedScanner() {
	                     {
	                          setDefaultReturnToken(new Token(
	                        		                new TextAttribute(
	                        		                ColorManager.getColor(IMarkdownTextColorConstants.MARKDOWN_COMMENT),
	                        		                ColorManager.getColor(IMarkdownTextColorConstants.MARKDOWN_COMMENT_BG),
	                        		                SWT.NORMAL)));
	                                }
	                        };
	        }
	        System.out.println("passed getMarkdownCommentScanner");*/
	        return markdownCommentScanner;
	}
	
	private ITokenScanner getMarkdownDefaultScanner() {
	    System.out.println("going for getMarkdownDefaultScanner");
	    if (markdownDefaultScanner == null) {                        
	    	markdownDefaultScanner = new RuleBasedScanner() {
                     {
                          setDefaultReturnToken(new Token(
                        		                new TextAttribute(
                        		                ColorManager.getColor(IMarkdownTextColorConstants.DEFAULT),
                        		                ColorManager.getColor(IMarkdownTextColorConstants.DEFAULT_BG),
                        		                SWT.NORMAL)));
                                }
                        };
        }
        System.out.println("passed getMarkdownDefaultScanner");
        return markdownDefaultScanner;
    }
	
	@Override
	public ITextHover getTextHover(ISourceViewer sourceViewer, String contentType) {
	     return new MarkdownTextHover();
	}

	@Override
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
	        ContentAssistant assistant = new ContentAssistant();
	        IContentAssistProcessor sharedProcessor = new MarkdownContentAssistProcessor();
	                
	        // define assist processor for each content type -> we use the same for all types here
	        assistant.setContentAssistProcessor(sharedProcessor, IDocument.DEFAULT_CONTENT_TYPE);
	        assistant.setContentAssistProcessor(sharedProcessor, MarkdownTextPartitionScanner.MARKDOWN_ID_DIV);
	        assistant.setContentAssistProcessor(sharedProcessor, MarkdownTextPartitionScanner.MARKDOWN_EN_DIV);
	        assistant.setContentAssistProcessor(sharedProcessor, MarkdownTextPartitionScanner.MARKDOWN_DD_DIV);
	        assistant.setContentAssistProcessor(sharedProcessor, MarkdownTextPartitionScanner.MARKDOWN_PD_DIV);            
	        assistant.setContentAssistProcessor(sharedProcessor, MarkdownTextPartitionScanner.MARKDOWN_CN_SEC);
	        assistant.setContentAssistProcessor(sharedProcessor, MarkdownTextPartitionScanner.MARKDOWN_WS_SEC);
	        assistant.setContentAssistProcessor(sharedProcessor, MarkdownTextPartitionScanner.MARKDOWN_LS_SEC);
	        
	        assistant.setEmptyMessage("Sorry, no hint for you :-/");
	        assistant.enableAutoActivation(true);
	        assistant.setAutoActivationDelay(500);
	        
	        return assistant;
	}	
}
