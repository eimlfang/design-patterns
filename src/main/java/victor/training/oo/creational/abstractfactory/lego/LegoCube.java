package victor.training.oo.creational.abstractfactory.lego;

import victor.training.oo.creational.abstractfactory.spi.Board;
import victor.training.oo.creational.abstractfactory.spi.Cube;

public class LegoCube implements Cube {

	@Override
	public void stackOnto(Cube anotherCube) {
		if (!(anotherCube instanceof LegoCube)) 
			throw new IllegalArgumentException("Not compatible!");
		System.out.println("Stacking perfectly onto " + anotherCube);
	}

	@Override
	public void putOn(Board board) {
		if (!(board instanceof LegoBoard)) 
			throw new IllegalArgumentException("Not compatible!");
		System.out.println("Putting nicely on" + board);
	}

}
