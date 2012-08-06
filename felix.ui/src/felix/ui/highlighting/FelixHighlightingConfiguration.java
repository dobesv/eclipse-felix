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

	@Override
	public void configure(IHighlightingConfigurationAcceptor acceptor) {
		super.configure(acceptor);
		acceptor.acceptDefaultHighlighting(TYPE_ID, "Type", typeTextStyle());
		acceptor.acceptDefaultHighlighting(PROC_ID, "Procedure", keywordTextStyle());
		acceptor.acceptDefaultHighlighting(FUN_ID, "Function", keywordTextStyle());
		acceptor.acceptDefaultHighlighting(GEN_ID, "Generator", keywordTextStyle());
	}

	public TextStyle typeTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(63, 63, 63));
		return textStyle;
	}

	public TextStyle procTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(127, 127, 127));
		textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}
	public TextStyle funTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(127, 127, 127));
		textStyle.setStyle(SWT.ITALIC);
		return textStyle;
	}
	public TextStyle genTextStyle() {
		return funTextStyle();
	}
	
	
}
