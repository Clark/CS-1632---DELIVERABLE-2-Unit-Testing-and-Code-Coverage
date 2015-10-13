import java.util.Random;

public class CitySim9000 {

	public static void main(String[] args) {

		if(args.length != 1) {
			System.out.println("Please enter one integer as an argument.");
			System.exit(0);
		}
		try {
			int test = Integer.parseInt(args[0]);
		} catch(NumberFormatException e) {
			System.out.println("Please enter one integer as an argument.");
			System.exit(0);
		}
		
		// Generates the map
		// 		Contains four unique city locations
		//		Uses four Nodes for Outside City, but all are called "Outside City"
		Node outTopLeft = new Node("Outside City");
		Node mall = new Node("Mall");
		Node book = new Node("Bookstore");
		Node outTopRight = new Node("Outside City");
		Node outBottomLeft = new Node("Outside City");
		Node coff = new Node("Coffee");
		Node univ = new Node("University");
		Node outBottomRight = new Node("Outside City");

		// Connects places with streets
		//		Both Fourth and Fifth Avenues are present
		//		Both Meow and Chirp Streets are present
		outTopLeft.setEast(mall, "Fourth Ave.");
		mall.setEast(book, "Fourth Ave.");
		mall.setSouth(coff, "Meow St.");
		book.setEast(outTopRight, "Fourth Ave.");
		book.setSouth(univ, "Chirp St.");
		outBottomRight.setWest(univ, "Fifth Ave.");
		univ.setNorth(book, "Chirp St.");
		univ.setWest(coff, "Fifth Ave.");
		coff.setNorth(mall, "Meow St.");
		coff.setWest(outBottomLeft, "Fifth Ave.");

		Node current = null;

		int rando = Integer.parseInt(args[0]);
		Random generator = new Random(rando);

		// Loops five times, once for each driver
		for(int i = 0; i < 5; i++) {

			// Generate random int for initial location
			int spawn = generator.nextInt(Integer.SIZE - 1) % 5;

			// Driver pseudorandomly chooses to go to University or mall when starting at Outside City
			if(spawn == 0) {
				int which = generator.nextInt(Integer.SIZE - 1) % 1;
				if(which == 0) current = outTopLeft;
				else if(which == 1) current = outBottomRight;
			}
			else if(spawn == 1) current = mall;
			else if(spawn == 2) current = book;
			else if(spawn == 3) current = univ;
			else if(spawn == 4) current = coff;

			while(current != outTopRight && current != outBottomLeft) {
				int where = generator.nextInt(Integer.SIZE - 1) % current.getAdjacent();
				where = current.whereToGo(where);
				if(where == 0) {
					System.out.println("Driver " + i + " heading from " + current.retName() +  " to " + current.getNorth().retName() + " via " + current.getNorthStreet());
					current = current.getNorth();
				} else if(where == 1) {
					System.out.println("Driver " + i + " heading from " + current.retName() +  " to " + current.getSouth().retName()+  " via " + current.getSouthStreet());
					current = current.getSouth();
				} else if(where == 2) {
					System.out.println("Driver " + i + " heading from " + current.retName() +  " to " + current.getEast().retName()+  " via " + current.getEastStreet());
					current = current.getEast();
				} else if(where == 3) {
					System.out.println("Driver " + i + " heading from " + current.retName() +  " to " + current.getWest().retName()+  " via " + current.getWestStreet());
					current = current.getWest();
				} else if(where == 9) {

				}

			}
			System.out.println("- - - - -");
		}


	}

	public static class Node {

		Node north;
		Node south;
		Node east;
		Node west;

		boolean car;
		int adjacent;

		String name;
		String streetNorth;
		String streetSouth;
		String streetEast;
		String streetWest;

		boolean has[] = {false, false, false, false};

		private Node(String place) {

			north = null;
			south = null;
			east = null;
			west = null;

			name = place;

			adjacent = 0;

		}

		public String retName() {
			return name;
		}

		public void setNorth(Node n, String st) {
			north = n;
			adjacent++;
			streetNorth = st;
			has[0] = true;
		}
		public void setSouth(Node s, String st) {
			south = s;
			adjacent++;
			streetSouth = st;
			has[1] = true;
		}
		public void setEast(Node e, String st) {
			east = e;
			adjacent++;
			streetEast = st;
			has[2] = true;
		}
		public void setWest(Node w, String st) {
			west = w;
			adjacent++;
			streetWest = st;
			has[3] = true;
		}

		public Node getNorth() {
			return north;
		}
		public Node getSouth() {
			return south;
		}
		public Node getEast() {
			return east;
		}
		public Node getWest() {
			return west;
		}

		public String getNorthStreet() {
			return streetNorth;
		}
		public String getSouthStreet() {
			return streetSouth;
		}
		public String getEastStreet() {
			return streetEast;
		}
		public String getWestStreet() {
			return streetWest;
		}

		public int getAdjacent() {
			return adjacent;
		}

		// Determines which direction to travel to
		public int whereToGo(int here) {
			int check = 0;

			for(int l = 0; l < 4; l++) {
				if(has[l]) {
					if(check == here) {
						return l;
					} else {
						check++;
					}
				}
			}
			// Just to set a return, doesn't mean anything
			return 9;
		}

	}
}