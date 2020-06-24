package victor.training.oo.behavioral.visitor;

import victor.training.oo.behavioral.visitor.model.Circle;
import victor.training.oo.behavioral.visitor.model.Square;

public interface ShapeVisitor {
	void visit(Square square);
	void visit(Circle circle);

//	void visit(Rectangle rectangle);
}
