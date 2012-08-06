/*
* generated by Xtext
*/
package felix.ui.labeling;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider;

import com.google.inject.Inject;

import felix.ast.CompilationUnit;
import felix.ast.DeclName;
import felix.ast.QualifiedName;
import felix.ast.TVar;
import felix.ast.TVarList;
import felix.ast.VarDecl;

/**
 * Provides labels for a EObjects.
 * 
 * see http://www.eclipse.org/Xtext/documentation/latest/xtext.html#labelProvider
 */
public class FelixLabelProvider extends DefaultEObjectLabelProvider {

	@Inject
	public FelixLabelProvider(AdapterFactoryLabelProvider delegate) {
		super(delegate);
	}

	// String image(CompilationUnit cu) { return "icons/felix.gif"; }
	String image(CompilationUnit x) { return "felix.gif"; }
	String image(felix.ast.Class x) { return "container_obj.gif"; }
	String image(felix.ast.VarDecl x) { return "field_obj.gif"; }
	String image(felix.ast.ConstDecl x) { return "field_obj.gif"; }
	String image(felix.ast.Include x) { return "include_obj.gif"; }
	String image(felix.ast.Open x) { return "imp_obj.png"; }
	String image(felix.ast.Fun x) { return x.getKind()+".png"; }
	String image(felix.ast.Proc x) { return "proc.png"; }
	String image(felix.ast.StructDef x) { return "struct_obj.gif"; }
	String image(felix.ast.UnionDef x) { return "union_obj.gif"; }
	String image(felix.ast.Typedef x) { return "typedef_obj.gif"; }
	String image(felix.ast.RecordMemDecl x) { return "field_obj.gif"; }
	String image(felix.ast.CEnum x) { return "enum_obj.png"; }
	
	
	String text(VarDecl v) { return text(v.getName()); }
	
	String text(QualifiedName qname) {
		StringBuffer sb = new StringBuffer();
		for(DeclName dname : qname.getParts()) {
			if(sb.length() > 0) sb.append("::");
			sb.append(text(dname));
		}
		return sb.toString();
	}
	String text(felix.ast.DeclName dname) { 
		if(dname.getTvars() == null || dname.getTvars().getVars().isEmpty()) {
			return dname.getName();
		} else {
			return dname.getName() + "[" + text(dname.getTvars()) + "]";
		}
	}
	String text(TVarList tvars) {
		StringBuffer sb = new StringBuffer();
		for(TVar tvar : tvars.getVars()) {
			if(sb.length() > 0) sb.append(',');
			sb.append(tvar.getName());
		}
		return sb.toString();
	}
	String text(felix.ast.Class cls) { return text(cls.getName()); }
	String text(felix.ast.Instance inst) { return text(inst.getName()); }
	String text(felix.ast.StructDef x) { return text(x.getName()); }
	String text(felix.ast.Fun x) { return text(x.getName()); }
	String text(felix.ast.Proc x) { return text(x.getName()); }
	String text(felix.ast.Include x) { return "include \'"+x.getModule()+"\'"; }
	String text(felix.ast.Open x) {
		if(x.getTvars() == null || x.getTvars().getVars().isEmpty())
			return text(x.getName());
		return "open["+text(x.getTvars())+"] "+text(x.getName());
	}
	String text(felix.ast.RecordMemDecl x) { return x.getName(); }
}
