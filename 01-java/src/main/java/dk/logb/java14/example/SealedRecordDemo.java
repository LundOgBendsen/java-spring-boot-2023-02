package dk.logb.java14.example;

sealed interface Expr
        permits ConstantExpr, PlusExpr, TimesExpr, NegExpr {
    int value();
}

record ConstantExpr(int i)       implements Expr {
    @Override
    public int value() {
        return i;
    }
    @Override
    public String toString() {
        return String.valueOf(i);
    }
}
record PlusExpr(Expr a, Expr b)  implements Expr {
    @Override
    public int value() {
        return a.value() + b.value();
    }

    @Override
    public String toString() {
        return String.format("(%s + %s)", a, b);
    }
}
record TimesExpr(Expr a, Expr b) implements Expr {
    @Override
    public int value() {
        return a.value() * b.value();
    }

    @Override
    public String toString() {
        return String.format("(%s * %s)", a, b);
    }
}
record NegExpr(Expr e) implements Expr {
    @Override
    public int value() {
        return -e.value();
    }

    @Override
    public String toString() {
        return String.format("(-%s)", e);
    }
}

public class SealedRecordDemo {

    //return RPN (Reverse Polish Notation) string for expression, e.g. 1+2 -> 1 2 +
    static String asRpn(Expr e) {
        return switch (e) {
            case ConstantExpr ce -> Integer.toString(ce.i());
            case PlusExpr pe -> asRpn(pe.a()) + " " + asRpn(pe.b()) + " +";
            case TimesExpr te -> asRpn(te.a()) + " " + asRpn(te.b()) + " *";
            case NegExpr ne -> asRpn(ne.e()) + " -";
        };
    }

    public static void main(String[] args) {
        //explain: 1 + -2 * 3
        Expr expr = new PlusExpr(new ConstantExpr(1), new NegExpr(new TimesExpr(new ConstantExpr(2), new ConstantExpr(3))));
        System.out.println(expr.toString() + " = " + expr.value());
        System.out.println(asRpn(expr));
    }
}
