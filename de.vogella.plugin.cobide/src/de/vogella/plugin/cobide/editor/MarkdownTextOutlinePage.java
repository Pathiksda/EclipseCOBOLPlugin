package de.vogella.plugin.cobide.editor;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

public class MarkdownTextOutlinePage extends ContentOutlinePage  {

    private IDocument input;
    
    private class SimpleLabelProvider extends LabelProvider {
            @Override
            public String getText(Object element) {                 
                    if (element instanceof ITypedRegion) {
                            ITypedRegion region = (ITypedRegion) element;
                            
                            try {
                                    return input.get(region.getOffset(), region.getLength());
                            } 
                            catch (BadLocationException e) {                                        
                            }
                    }
                    return super.getText(element);                  
            }
    }
    
    public void createControl(Composite parent) {
            super.createControl(parent);
            
            // get tree viewer of this page
            TreeViewer treeViewer = getTreeViewer();
            
            treeViewer.setContentProvider(new MarkdownSyntaxContentProvider());
            treeViewer.setLabelProvider(new SimpleLabelProvider());
            
            treeViewer.setInput(this.input);
            
            // we must look for selections in the tree viewer so that we can show properties
            // of the selected node
            getSite().setSelectionProvider(treeViewer);
    }

	public void setInput(IDocument element) {
            this.input = element;
            if (getTreeViewer()!=null) {
                    getTreeViewer().setInput(element);              
            }
    }
}