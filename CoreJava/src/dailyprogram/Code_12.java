package dailyprogram;


class Vehicle {
	public Vehicle(){
		System.out.println("Inside Vehicle class's default constructor");
	}
}

class Car extends Vehicle {
	public Car() {
		System.out.println("Inside Car class's default constructor");
	}
}

public class Code_12 extends Car{
	public Code_12() {
		System.out.println("Inside Audi class's default constructor");
	}
	
	public static void main(String[] args) {
		new Code_12();
	}
}
/* OUTPUT
Inside Vehicle class's default constructor
Inside Car class's default constructor
Inside Audi class's default constructor*/