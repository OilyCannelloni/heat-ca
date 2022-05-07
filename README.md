# heat-ca

This repo contains a JavaFX framework built to implement cellular automata.

## Defining a CA simulation
A simulation can be implemented in a few easy steps:

 1. Create a class extending Cell: 
```
public class MyCell extends Cell {
	...
}
```
2.  Create an Enum implementing ICellType, which defines types of cells in your simulation:
```
public enum MyCellType implements ICellType {
	RED, GREEN, BLUE;
	...
}
```
3. In your Cell extension, override the `onTick()` method, which defines the behavior of the cell during each iteration:
```
@Override  
public void onTick(int epoch) {
	if (Algorithm.random.nextDouble() < 0.1)
	    this.setType(MyCellType.RED);
	else
	    this.setType(MyCellType.GREEN)
}
```
4. Create a class extending Scenario, where you can define the initial conditions for the simulation. Pass your class `MyCell` to the constructor of parent class:
```
public class MyScenario extends Scenario {  
    public MyScenario() {  
        super(MyCell.class);  
    }  
  
    @Override  
	public CAGrid build(int width, int height) {  
        CAGrid grid = super.build(width, height);  
	grid.get(20, 10).setType(MyCellType.BLUE);  
	return grid;  
    }  
}
```
5. In class App, edit the `buildGrid()` method as follows:
```
private void buildGrid() {  
    MyScenario scenario = new MyScenario();  
    this.grid = scenario.build(60, 30);  
}
```
