package felix.ui.highlighting;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;

public class FelixHighlightingConfiguration extends DefaultHighlightingConfiguration implements
		IHighlightingConfiguration {

	public static final String TYPE_ID = "type";
	public static final String PROC_ID = "proc";
	public static final String FUN_ID = "fun";
	public static final String GEN_ID = "gen";
	public static final String VAR_ID = "var";
	public static final String VAL_ID = "val";
	public static final String TYPE_EXPR_ID = "typeExpr";
	public static final String PROC_REF_ID = "procRef";
	public static final String FUN_REF_ID = "funRef";
	public static final String GEN_REF_ID = "genRef";
	public static final String VAR_USE_ID = "varUse";
	public static final String VAL_USE_ID = "valUse";
	public static final String UNRESOLVED_REF_ID = "unresolvedRef";
	public static final String UNUSED_VAR_ID = "unusedVar";

	@Override
	public void configure(IHighlightingConfigurationAcceptor acceptor) {
		super.configure(acceptor);
		acceptor.acceptDefaultHighlighting(TYPE_ID, "Type Declarations", typeExprTextStyle());
		acceptor.acceptDefaultHighlighting(TYPE_EXPR_ID, "Types", typeTextStyle());
		acceptor.acceptDefaultHighlighting(PROC_ID, "Procedure Declarations", procTextStyle());
		acceptor.acceptDefaultHighlighting(PROC_REF_ID, "Procedures", procTextStyle());
		acceptor.acceptDefaultHighlighting(FUN_ID, "Function declarations", funTextStyle());
		acceptor.acceptDefaultHighlighting(FUN_REF_ID, "Functions", funRefTextStyle());
		acceptor.acceptDefaultHighlighting(GEN_ID, "Generator declarations", genTextStyle());
		acceptor.acceptDefaultHighlighting(GEN_REF_ID, "Generators", genRefTextStyle());
		acceptor.acceptDefaultHighlighting(VAR_ID, "Var declarations", varTextStyle());
		acceptor.acceptDefaultHighlighting(VAR_USE_ID, "Var uses", varUseTextStyle());
		acceptor.acceptDefaultHighlighting(VAL_ID, "Val declarations", valTextStyle());
		acceptor.acceptDefaultHighlighting(VAL_USE_ID, "Val uses", valUseTextStyle());
		acceptor.acceptDefaultHighlighting(UNRESOLVED_REF_ID, "Unresolved Name", unresolvedTextStyle());
		acceptor.acceptDefaultHighlighting(UNUSED_VAR_ID, "Unread var/val", unusedTextStyle());
	}

	public TextStyle typeTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setStyle(SWT.ITALIC);
		return textStyle;
	}

	public TextStyle typeExprTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setStyle(SWT.ITALIC);
		return textStyle;
	}
	
	public TextStyle procTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		return textStyle;
	}
	public TextStyle procRefTextStyle() {
		TextStyle textStyle = procTextStyle().copy();
		return textStyle;
	}
	public TextStyle funTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		return textStyle;
	}
	public TextStyle funRefTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		return textStyle;
	}
	public TextStyle genTextStyle() {
		return funTextStyle().copy();
	}
	public TextStyle genRefTextStyle() {
		return funTextStyle().copy();
	}
	public TextStyle varTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		return textStyle;
	}
	public TextStyle varUseTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		return textStyle;
	}
	public TextStyle valTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		return textStyle;
	}
	public TextStyle valUseTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		return textStyle;
	}
	
	public TextStyle unresolvedTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(127, 127, 127));
		return textStyle;
	}
	
	public TextStyle unusedTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(127, 127, 127));
		return textStyle;
	}
	
}
