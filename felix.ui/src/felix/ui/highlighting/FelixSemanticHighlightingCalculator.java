package felix.ui.highlighting;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.eclipse.xtext.ui.editor.syntaxcoloring.ISemanticHighlightingCalculator;

import felix.ast.DeclName;
import felix.ast.Fun;
import felix.ast.Proc;
import felix.ast.TypeExpr;

public class FelixSemanticHighlightingCalculator implements
		ISemanticHighlightingCalculator {

	@Override
	public void provideHighlightingFor(XtextResource resource, IHighlightedPositionAcceptor acceptor) {
		System.out.println("IN provideHighlightingFor !");
		if(resource == null || resource.getParseResult() == null)
			return;
		System.out.println("IN provideHighlightingFor 2 !");
		INode root = resource.getParseResult().getRootNode();
		for(INode node : root.getAsTreeIterable()) {
			EObject elt = node.getGrammarElement();
			System.out.println(elt);
			if(elt instanceof Fun) {
				System.out.println("IN provideHighlightingFor fun !");
				highlightDeclName(node, FelixHighlightingConfiguration.FUN_ID, acceptor);
			} else if(elt instanceof Proc) {
				System.out.println("IN provideHighlightingFor proc !");
				highlightDeclName(node, FelixHighlightingConfiguration.PROC_ID, acceptor);
			} else if(elt instanceof TypeExpr) {
				System.out.println("IN provideHighlightingFor type !");
				highlightNode(node, FelixHighlightingConfiguration.TYPE_ID, acceptor);
			}
		}
	}
	
	
	
	private void highlightDeclName(INode rootNode, String id, IHighlightedPositionAcceptor acceptor) {
		for(INode node : rootNode.getAsTreeIterable()) {
			if(node.getGrammarElement() instanceof DeclName) {
				highlightNode(node, id, acceptor);
				break;
			}
		}
	}



	private void highlightNode(INode node, String id, IHighlightedPositionAcceptor acceptor) {
		if (node == null)
			return;
		if (node instanceof ILeafNode) {
			acceptor.addPosition(node.getOffset(), node.getLength(), id);
		} else {
			for(ILeafNode leaf: node.getLeafNodes()) {
				if (!leaf.isHidden()) {
					acceptor.addPosition(leaf.getOffset(), leaf.getLength(), id);
				}
			}
		}
	}
	

}
