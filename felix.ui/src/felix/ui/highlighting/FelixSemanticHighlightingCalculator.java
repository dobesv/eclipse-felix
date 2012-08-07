package felix.ui.highlighting;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.nodemodel.BidiTreeIterator;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.eclipse.xtext.ui.editor.syntaxcoloring.ISemanticHighlightingCalculator;
import org.eclipse.xtext.util.PolymorphicDispatcher;

import felix.ast.Application;
import felix.ast.CEnum;
import felix.ast.CStructDef;
import felix.ast.CompilationUnit;
import felix.ast.EnumDef;
import felix.ast.Fun;
import felix.ast.Proc;
import felix.ast.QualifiedName;
import felix.ast.StructDef;
import felix.ast.TypeDecl;
import felix.ast.TypeExpr;
import felix.ast.Typedef;
import felix.ast.VarDecl;

public class FelixSemanticHighlightingCalculator implements
		ISemanticHighlightingCalculator {
	private final PolymorphicDispatcher<Object> highlightDispatcher = PolymorphicDispatcher.createForSingleTarget("highlight", 1, 3, this);

	@Override
	public void provideHighlightingFor(XtextResource resource, IHighlightedPositionAcceptor acceptor) {
		if(resource == null || resource.getContents() == null)
			return;
		TreeIterator<EObject> iterator = resource.getAllContents();
		while(iterator.hasNext()) {
			doHighlight(iterator.next(), acceptor, iterator);
		}
	}

	private void doHighlight(EObject obj, IHighlightedPositionAcceptor acceptor, TreeIterator<EObject> iterator) {
		highlightDispatcher.invoke(obj, acceptor, iterator);
	}

	public void highlight(EObject x, IHighlightedPositionAcceptor acceptor, TreeIterator<EObject> iterator) {}
	
	public void highlight(TypeExpr x, IHighlightedPositionAcceptor acceptor, TreeIterator<EObject> iterator) {
		highlightSemanticElement(x, FelixHighlightingConfiguration.TYPE_EXPR_ID, acceptor);
		iterator.prune();
	}

	private void highlightSemanticElement(EObject semanticElement,
			String highlightId, IHighlightedPositionAcceptor acceptor) {
		ICompositeNode node = NodeModelUtils.findActualNodeFor(semanticElement);
		highlightNode(node, highlightId, acceptor);
	}
	public void highlight(felix.ast.Assignment a, IHighlightedPositionAcceptor acceptor, TreeIterator<EObject> iterator) {
		if(a.getLeft() instanceof QualifiedName) {
			if(":=".equals(a.getOp())) {
				highlightSemanticElement(a.getLeft(), FelixHighlightingConfiguration.VAR_ID, acceptor);
			} else {
				highlightSemanticElement(a.getLeft(), FelixHighlightingConfiguration.VAR_USE_ID, acceptor);
			}
		}
	}
	public void highlight(Application a, IHighlightedPositionAcceptor acceptor, TreeIterator<EObject> iterator) {
		// TODO: Pick appropriate highlight color based on actual callee type
		highlightFirstFeature(a, "fun", acceptor, FelixHighlightingConfiguration.FUN_REF_ID);
	}
	public void highlight(Fun f, IHighlightedPositionAcceptor acceptor) {
		boolean isGen = "gen".equals(f.getKind());
		String highlightId = isGen?FelixHighlightingConfiguration.GEN_ID:FelixHighlightingConfiguration.FUN_ID;
		highlightName(f, highlightId, acceptor);
	}

	public void highlight(Proc f, IHighlightedPositionAcceptor acceptor, TreeIterator<EObject> iterator) {
		highlightName(f, FelixHighlightingConfiguration.FUN_ID, acceptor);
	}

	public void highlight(Typedef f, IHighlightedPositionAcceptor acceptor, TreeIterator<EObject> iterator) {
		highlightTypeName(f, acceptor);
	}

	private void highlightTypeName(EObject semanticObject, IHighlightedPositionAcceptor acceptor) {
		highlightName(semanticObject, FelixHighlightingConfiguration.TYPE_ID, acceptor);
	}
	public void highlight(StructDef f, IHighlightedPositionAcceptor acceptor, TreeIterator<EObject> iterator) {
		highlightTypeName(f, acceptor);
	}
	public void highlight(EnumDef f, IHighlightedPositionAcceptor acceptor, TreeIterator<EObject> iterator) {
		highlightTypeName(f, acceptor);
	}
	public void highlight(CStructDef f, IHighlightedPositionAcceptor acceptor, TreeIterator<EObject> iterator) {
		highlightTypeName(f, acceptor);
	}
	public void highlight(CEnum f, IHighlightedPositionAcceptor acceptor, TreeIterator<EObject> iterator) {
		highlightTypeName(f, acceptor);
	}
	public void highlight(TypeDecl f, IHighlightedPositionAcceptor acceptor, TreeIterator<EObject> iterator) {
		highlightTypeName(f, acceptor);
	}
	public void highlight(VarDecl f, IHighlightedPositionAcceptor acceptor, TreeIterator<EObject> iterator) {
		highlightName(f, f.getKind(), acceptor);
	}
	
	private void highlightName(EObject o, String highlightId, IHighlightedPositionAcceptor acceptor) {
		highlightFirstFeature(o, "name", acceptor, highlightId);
	}
	
	private void highlightFirstFeature(EObject semanticElement, String featureName,
			IHighlightedPositionAcceptor acceptor, String highlightId) {
		INode node = findFirstFeature(semanticElement, featureName);
		highlightNode(node, highlightId, acceptor);
	}

	private void highlightNode(INode node, String highlightId, IHighlightedPositionAcceptor acceptor) {
		if(node != null) {
			//System.out.println("Highlighting <<"+node.getText()+">> on line "+node.getStartLine()+" with semantic element "+node.getSemanticElement()+" and highlight id "+highlightId);
			acceptor.addPosition(node.getOffset(), node.getLength(), highlightId);
		}
	}
	
	private INode findFirstFeature(EObject semanticElement, String featureName) {
		ICompositeNode node = NodeModelUtils.findActualNodeFor(semanticElement);
		if(node != null) {
			BidiTreeIterator<INode> iterator = node.getAsTreeIterable().iterator();
			while (iterator.hasNext()) {
				INode child = iterator.next();
				EObject grammarElement = child.getGrammarElement();
				if (grammarElement != null) {
					if (grammarElement instanceof Action) {
						Action action = (Action) grammarElement;
						if (child.getSemanticElement() == semanticElement) {
							child = iterator.next();
							if (featureName.equals(action.getFeature())) {
								return child;
							}
						} else {
							// navigate the action's left side (first child) until we find an assignment (a rule call)
							// the assignment will tell us about the feature to which we assigned
							// the semantic object that has been created by the action
							INode firstChild = ((ICompositeNode) child).getFirstChild();
							while (firstChild.getGrammarElement() instanceof Action) {
								firstChild = ((ICompositeNode) firstChild).getFirstChild();
							}
							EObject firstChildGrammarElement = firstChild.getGrammarElement();
							Assignment assignment = GrammarUtil.containingAssignment(firstChildGrammarElement);
							if (assignment != null && featureName.equals(assignment.getFeature())) {
								return child;
							}
						}
						iterator.prune();
					} else if (child != node) {
						Assignment assignment = GrammarUtil.containingAssignment(grammarElement);
						if (assignment != null) {
							if (featureName.equals(assignment.getFeature())) {
								return child;
							}
							iterator.prune();
						}
					}
				}
			}
		}
		return null;
	}
}
