package felix

import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.InjectWith
import org.junit.runner.RunWith
import org.eclipse.xtext.junit4.util.ParseHelper
import com.google.inject.Inject
import org.junit.Test
import felix.ast.*
import felix.ast.BinaryOp
import felix.ast.Expr

import static org.junit.Assert.*

@InjectWith(typeof(FelixInjectorProvider))
@RunWith(typeof(XtextRunner))
class ParserTest {
    @Inject
    ParseHelper<CompilationUnit> parser
 
    def Expr parseSimplePrintStmt(String s) {
        val ast = parser.parse(s)
        assertEquals(1, ast.stmts.size());
        val apply = ast.stmts.get(0) as Application;
        val fname = (apply.fun as QualifiedName);
        val parts = fname.parts;
        assertEquals(1, parts.size());
        val name = parts.get(0).name;
        assertEquals("println", name);
        return apply.args.get(0);
    } 
    @Test 
    def void parseHelloWorld() {
        val arg = parseSimplePrintStmt("println$ \"Hello, world!\";") as StringLiteral;
        assertEquals("Hello, world!", arg.value);
    }
    
    @Test
    def void parseArithMixed() {
        val plus = parseSimplePrintStmt("println$ 3 + 4 * 5 / 7 ^ 13;") as BinaryOp;
        assertEquals(plus.op, "+");
        val product = plus.right as BinaryOp;
        assertEquals(product.op, "*");
        val productLeft = product.left as IntegerLiteral;
        assertEquals(4, productLeft.value);
        val divide = product.right as BinaryOp;
        assertEquals(divide.op, "/");
        val superscript = divide.right as BinaryOp;
        assertEquals(superscript.op, "^");
    }
    
    @Test
    def void parseArithSame() {
        val plus = parseSimplePrintStmt("println$ 3 + 4 + 5 / 7 / 13 / 17;") as BinaryOp;
        assertEquals(3, ((plus.left as BinaryOp).left as IntegerLiteral).value);
        assertEquals(4, ((plus.left as BinaryOp).right as IntegerLiteral).value);
        val divide = plus.right as BinaryOp;
        
        assertEquals(17, (divide.right as IntegerLiteral).value);
        assertEquals(13, ((divide.left as BinaryOp).right as IntegerLiteral).value);
        assertEquals(7, (((divide.left as BinaryOp).left as BinaryOp).right as IntegerLiteral).value);
        assertEquals(5, (((divide.left as BinaryOp).left as BinaryOp).left as IntegerLiteral).value);
    }
    
    @Test
    def void parseSimpleFun() {
        val fun = parser.parse("fun add(a:int, b:int) => a+b;").stmts.get(0) as Fun;
        val name = fun.name.name;
        assertEquals("add", name);
        val arg = fun.args.get(0) as FunArg;
        val arg1 = arg.params.get(0);
        assertEquals("a", arg1.name);
        val arg2 = arg.params.get(1);
        assertEquals("b", arg2.name);
        val body = fun.expr as BinaryOp;
        assertEquals("+", body.op);
        assertEquals("a", (body.left as QualifiedName).parts.get(0).name);
        assertEquals("b", (body.right as QualifiedName).parts.get(0).name);
    }    
    
    @Test
    def void parseSimpleProc() {
        val proc = parser.parse("proc add_print(a:int, b:int) = { print$ a+b; }").stmts.get(0) as Proc;
        val name = proc.name.name;
        assertEquals("add_print", name);
        val arg = proc.args.get(0) as FunArg;
        val arg1 = arg.params.get(0);
        assertEquals("a", arg1.name);
        val arg2 = arg.params.get(1);
        assertEquals("b", arg2.name);
        val printStmt = proc.stmts.get(0) as Application;
        val printFun = printStmt.fun as QualifiedName;
        assertEquals("print", printFun.parts.get(0).name);
        val printArg = printStmt.args.get(0) as BinaryOp;
        assertEquals("+", printArg.op);
        assertEquals("a", (printArg.left as QualifiedName).parts.get(0).name);
        assertEquals("b", (printArg.right as QualifiedName).parts.get(0).name);
    }
    
    @Test
    def void parseSimpleOpen() {
        val open = parser.parse("open Foo;").stmts.get(0) as Open;
        assertEquals(1, open.name.parts.size());
        val what = open.name.parts.get(0) as DeclName;        
        assertEquals("Foo", what.name);
    }
}