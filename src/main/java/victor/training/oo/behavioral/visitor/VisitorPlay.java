package victor.training.oo.behavioral.visitor;

import victor.training.oo.behavioral.visitor.model.Circle;
import victor.training.oo.behavioral.visitor.model.Rectangle;
import victor.training.oo.behavioral.visitor.model.Shape;
import victor.training.oo.behavioral.visitor.model.Square;

import java.util.Arrays;
import java.util.List;

public class VisitorPlay {

	public static void main(String[] args) {
		List<Shape> shapes = Arrays.asList(
				new Square(10), 
				new Circle(5), 
				new Rectangle(5,3),
				new Square(5));

		PerimeterCalculatorVisitor perimeterCalculator = new PerimeterCalculatorVisitor();
		for (Shape shape : shapes) {
			shape.accept(perimeterCalculator);
		}
		System.out.println("Total perimeter: " + perimeterCalculator.getTotal());

		AreaVisitor areaVisitor = new AreaVisitor();
		for (Shape shape : shapes) {
			shape.accept(areaVisitor);
		}


		System.out.println("Total area: " + areaVisitor.getSum()); // TODO

	}

}
