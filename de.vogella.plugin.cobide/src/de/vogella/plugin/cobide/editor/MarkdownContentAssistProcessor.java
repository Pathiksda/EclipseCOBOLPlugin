package de.vogella.plugin.cobide.editor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

public class MarkdownContentAssistProcessor implements IContentAssistProcessor {

        private static String[] PROPOSALS = { "=", "==", "*", "_" };

        /**
         * Return completion hints for the given offset.
         * Here we always return all supported markup symbols.
         */
        @Override
        public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
                List<ICompletionProposal> result = new ArrayList<ICompletionProposal>();
                
                for (int i = 0; i < PROPOSALS.length; i++) {
                        result.add(new CompletionProposal(PROPOSALS[i], offset, 0, PROPOSALS[i].length()));                     
                }
                
                return result.toArray(new ICompletionProposal[result.size()]);
        }

        @Override
        public IContextInformation[] computeContextInformation(ITextViewer viewer, int offset) {
                return null;
        }

        // completion hints triggered when the user types '='
        @Override
        public char[] getCompletionProposalAutoActivationCharacters() {
                return new char[] { '=' };
        }

        @Override
        public char[] getContextInformationAutoActivationCharacters() {
                return null;
        }

        @Override
        public String getErrorMessage() {
                return null;
        }

        @Override
        public IContextInformationValidator getContextInformationValidator() {
                return null;
        }

        static void loadMnemonicNames () throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(MarkdownContentAssistProcessor.class.getResourceAsStream("keywords.txt")));
            ArrayList<String> mnemonic = new ArrayList<String> ();
            String nextLine = null;
            while (null != (nextLine = br.readLine())) {
              // if keyword line
              if (nextLine.trim().length() > 0 && nextLine.toUpperCase().equals(nextLine)) {
                StringTokenizer st = new StringTokenizer(nextLine," \t", false);
                final String word = st.nextToken();
                mnemonic.add(word);
              }
            }
            String [] mnemonicArray = new String [mnemonic.size()];
            mnemonicArray = mnemonic.toArray(mnemonicArray);
            MarkdownContentAssistProcessor.PROPOSALS = mnemonicArray;
            System.out.println("loaded");
          }        
}
