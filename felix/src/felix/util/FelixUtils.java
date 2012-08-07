package felix.util;

import felix.ast.DeclName;
import felix.ast.QualifiedName;
import felix.ast.TVar;
import felix.ast.TVarList;

public class FelixUtils {

	public static String toString(QualifiedName qname) {
		StringBuffer sb = new StringBuffer();
		for(DeclName dname : qname.getParts()) {
			if(sb.length() > 0) sb.append("::");
			sb.append(toString(dname));
		}
		return sb.toString();
	}

	public static String toString(felix.ast.DeclName dname) { 
		if(dname.getTvars() == null || dname.getTvars().getVars().isEmpty()) {
			return dname.getName();
		} else {
			return dname.getName() + "[" + toString(dname.getTvars()) + "]";
		}
	}

	public static String toString(TVarList tvars) {
		StringBuffer sb = new StringBuffer();
		for(TVar tvar : tvars.getVars()) {
			if(sb.length() > 0) sb.append(',');
			sb.append(tvar.getName());
		}
		return sb.toString();
	}

}
