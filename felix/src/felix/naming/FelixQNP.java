package felix.naming;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

import felix.ast.CEnum;
import felix.ast.CStructDef;
import felix.ast.Class;
import felix.ast.DeclName;
import felix.ast.Fun;
import felix.ast.Instance;
import felix.ast.Proc;
import felix.ast.TypeDecl;
import felix.ast.Typedef;
import felix.ast.UnionDef;
import felix.util.FelixUtils;

public class FelixQNP extends DefaultDeclarativeQualifiedNameProvider {

	protected QualifiedName qualifiedName(Class ele) {
		return QualifiedName.create(FelixUtils.toString(ele.getName()));
	}
	protected QualifiedName qualifiedName(Fun ele) {
		return QualifiedName.create(FelixUtils.toString(ele.getName()));
	}
	protected QualifiedName qualifiedName(Proc ele) {
		return QualifiedName.create(FelixUtils.toString(ele.getName()));
	}
	protected QualifiedName qualifiedName(Instance ele) {
		return QualifiedName.create(FelixUtils.toString(ele.getName()));
	}
	protected QualifiedName qualifiedName(CStructDef ele) {
		return QualifiedName.create(FelixUtils.toString(ele.getName()));
	}
	protected QualifiedName qualifiedName(CEnum ele) {
		return QualifiedName.create(ele.getName());
	}
	protected QualifiedName qualifiedName(UnionDef ele) {
		return QualifiedName.create(FelixUtils.toString(ele.getName()));
	}
	protected QualifiedName qualifiedName(Typedef ele) {
		return QualifiedName.create(FelixUtils.toString(ele.getName()));
	}
	protected QualifiedName qualifiedName(TypeDecl ele) {
		return QualifiedName.create(FelixUtils.toString(ele.getName()));
	}
	protected QualifiedName qualifiedName(felix.ast.QualifiedName ele) {
		List<String> parts = new ArrayList<String>(ele.getParts().size());
		for(DeclName d : ele.getParts()) {
			parts.add(FelixUtils.toString(d));
		}
		return QualifiedName.create(parts);
	}
	
}
