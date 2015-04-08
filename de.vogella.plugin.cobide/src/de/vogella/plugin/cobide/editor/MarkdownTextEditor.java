package de.vogella.plugin.cobide.editor;

import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

public class MarkdownTextEditor extends TextEditor {
    
    private ColorManager colorManager;
    private MarkdownTextOutlinePage outlinePage;
    
    @Override
    protected void initializeEditor() {
            super.initializeEditor();
            System.out.println("editor initialised");
            colorManager = new ColorManager();
            
            // setup configuration of some graphical presentation aspects of the editor's raw content
            setSourceViewerConfiguration(new MarkdownTextEditorConfiguration(colorManager));
            System.out.println("configuration set");
            // setup document provider
            setDocumentProvider(new MarkdownTextDocumentProvider());
            System.out.println("document provided");
            //MarkdownContentAssistProcessor.loadMnemonicNames();
    }       
            
    @Override
    public void dispose() {
            colorManager.dispose();
            
            super.dispose();
    }
    // page that will show the text structure (headings)
    
    /**
     *  Returns an object of the IContentOutlinePage class that is associated with this editor.
     *  The page is displayed inside the outline window.
     */
    @Override
    public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {          	
            if (IContentOutlinePage.class.equals(adapter)) {
                    if (outlinePage == null) {
                            outlinePage = new MarkdownTextOutlinePage();
                            outlinePage.setInput(getDocumentProvider().getDocument(getEditorInput()));
                            // register a listener for selection of elements in the content outline page
                            outlinePage.addSelectionChangedListener(new ISelectionChangedListener() {
                                    @Override
                                    public void selectionChanged(SelectionChangedEvent event) {
                                            IStructuredSelection selection = (IStructuredSelection) event.getSelection();
                                            if (!selection.isEmpty()) {
                                                    // show and highlight the selected text region
                                                    ITypedRegion region = (ITypedRegion) selection.getFirstElement();
                                                    selectAndReveal(region.getOffset(), region.getLength());
                                                    setHighlightRange(region.getOffset(), region.getLength(), true);
                                            }                                               
                                    }
                            });                            
                    }       
                    
                    return outlinePage;
            }   
            return super.getAdapter(adapter);
    }
}
