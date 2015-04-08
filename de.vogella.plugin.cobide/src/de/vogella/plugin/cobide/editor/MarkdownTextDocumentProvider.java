package de.vogella.plugin.cobide.editor;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.ui.editors.text.FileDocumentProvider;

public class MarkdownTextDocumentProvider extends FileDocumentProvider {
    /**
     * Return an IDocument object that allows to work with the actual text representation
     * of the document shown in the editor widget (control) - to access lines, chars, 
     * update the current position, replace text fragments, and so on.
     */
    @Override
    protected IDocument createDocument(Object element) throws CoreException {               
            IDocument document = super.createDocument(element);
            
            System.out.println("should be returning something");
            // register the document partitioner
            if (document != null) {
                    IDocumentPartitioner partitioner = new FastPartitioner(
                                    new MarkdownTextPartitionScanner(), 
                                    MarkdownTextPartitionScanner.LEGAL_CONTENT_TYPES
                            );
                    System.out.println("trying to connect");
                    // connect the partitioner with the document
                    partitioner.connect(document);
                    System.out.println("connected...setting partitioner");                    
                    document.setDocumentPartitioner(partitioner);
                    System.out.println("partitioner set");                      
            }
            System.out.println("returning something");
            return document;
    }
}
