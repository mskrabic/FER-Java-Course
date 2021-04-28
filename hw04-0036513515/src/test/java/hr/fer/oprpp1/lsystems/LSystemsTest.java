package hr.fer.oprpp1.lsystems;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.collections.EmptyStackException;
import hr.fer.oprpp1.lsystems.impl.Context;
import hr.fer.oprpp1.lsystems.impl.LSystemBuilderImpl;
import hr.fer.oprpp1.lsystems.impl.TurtleState;
import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.impl.commands.*;

public class LSystemsTest {

	@Test
	public void generateTest() {
		LSystem s = new LSystemBuilderImpl()
			.registerProduction('F', "F+F--F+F")
			.setAxiom("F")
			.build();
		assertEquals("F", s.generate(0));
		assertEquals("F+F--F+F", s.generate(1));
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", s.generate(2));
		}
	
	@Test
	public void generateTest2() {
		LSystem s = new LSystemBuilderImpl()
			.registerProduction('A', "MS")
			.registerProduction('M', "Matej")
			.registerProduction('S', "Skrabic")
			.setAxiom("A")
			.build();
		assertEquals("A", s.generate(0));
		assertEquals("MS", s.generate(1));
		assertEquals("MatejSkrabic", s.generate(2));
		}
	
	@Test
	public void buildFromStringTest() {
		String[] text = {
		                 "origin                 0.05 0.05",
		                 "angle                  0",
		                 "unitLength             0.9",
		                 "unitLengthDegreeScaler 1.0 / 2.0",
		                 "",
		                 "command F draw 1",
		                 "command + rotate 90",
		                 "command - rotate -90",
		                 "",
		                 "",
		                 "axiom L",
		                 "",
		                 "production L +RF-LFL-FR+",
		                 "production R -LF+RFR+FL-"
						};
		LSystem s = new LSystemBuilderImpl().configureFromText(text).build();
		assertEquals("L", s.generate(0));
		assertEquals("+RF-LFL-FR+", s.generate(1));
		assertEquals("+-LF+RFR+FL-F-+RF-LFL-FR+F+RF-LFL-FR+-F-LF+RFR+FL-+", s.generate(2));
	}
	
	@Test
	public void colorCommandTest() {
		Context ctx = new Context();
		ctx.pushState(new TurtleState(new Vector2D(0, 0), new Vector2D(1, 0), Color.BLACK, 1));
		
		new ColorCommand(Color.RED).execute(ctx, null);
		
		assertEquals(Color.RED, ctx.getCurrentState().getColor());
	}
	
	@Test
	public void popCommandTest() {
		Context ctx = new Context();
		ctx.pushState(new TurtleState(new Vector2D(0, 0), new Vector2D(1, 0), Color.BLACK, 1));
		
		new PopCommand().execute(ctx, null);
		
		assertThrows(EmptyStackException.class, () -> ctx.getCurrentState());
	}
	
	@Test
	public void PushCommandTest() {
		Context ctx = new Context();
		ctx.pushState(new TurtleState(new Vector2D(0, 0), new Vector2D(1, 0), Color.BLACK, 1));
		
		new PushCommand().execute(ctx, null);
		TurtleState state = ctx.getCurrentState();
		ctx.popState();
		assertEquals(state.getColor(), ctx.getCurrentState().getColor());
		assertEquals(state.getDirection().getX(), ctx.getCurrentState().getDirection().getX());
		assertEquals(state.getDirection().getY(), ctx.getCurrentState().getDirection().getY());
		assertEquals(state.getPosition().getX(), ctx.getCurrentState().getPosition().getX());
		assertEquals(state.getPosition().getY(), ctx.getCurrentState().getPosition().getY());
		assertEquals(state.getUnitLength(), ctx.getCurrentState().getUnitLength());
	}
	
	@Test
	public void rotateCommandTest() {
		Context ctx = new Context();
		ctx.pushState(new TurtleState(new Vector2D(0, 0), new Vector2D(1, 0), Color.BLACK, 1));
		
		new RotateCommand(90).execute(ctx, null);
		
		assertEquals(new Vector2D(1, 0).rotated(Math.PI/2).getX(), ctx.getCurrentState().getDirection().getX());
		assertEquals(new Vector2D(1, 0).rotated(Math.PI/2).getY(), ctx.getCurrentState().getDirection().getY());
	}
	
	@Test
	public void scaleCommandtest() {
		Context ctx = new Context();
		ctx.pushState(new TurtleState(new Vector2D(0, 0), new Vector2D(1, 0), Color.BLACK, 1));
		
		new ScaleCommand(0.5).execute(ctx, null);
		
		assertEquals(0.5, ctx.getCurrentState().getUnitLength());
	}
	
	@Test
	public void skipCommandTest() {
		Context ctx = new Context();
		ctx.pushState(new TurtleState(new Vector2D(0, 0), new Vector2D(1, 0), Color.BLACK, 1));
		
		new SkipCommand(1).execute(ctx, null);
		
		assertEquals(1, ctx.getCurrentState().getPosition().getX());
		assertEquals(0, ctx.getCurrentState().getPosition().getY());
	}
}
