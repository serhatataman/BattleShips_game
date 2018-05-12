import java.util.Scanner;

public class BattleShips {

	public static void main(String[] args) 
	{
		char[][] map = new char[10][10];
		short[] ships = {5,5}; // index 0 includes number of ships that belongs to user, index 1 includes number of ships that belongs to computer
		
		intro();
		printMap(map,ships);
		deployShips(map);
		printMap(map,ships);
		battle(map,ships);
		congrats(ships);
	}
	public static void intro()
	{
		/*Introduction and greet the user!*/
		System.out.println("******* Welcome to Batte Ship Game *******");
		System.out.println("Right now, the sea is empty! \n");
	}
	public static void printMap(char[][] map, short[] ships)
	{
		System.out.println("---------------");
		/*Prints first part of the map*/
		System.out.print("  ");
		for (int i = 0; i < map.length; i++) 
			System.out.print(i);
		System.out.println("  ");
		
		/*Prints rows of the map*/
		for (int i = 0; i < map.length; i++) 
		{
			System.out.print(i+"|");
			
			for (int j = 0; j < map[i].length; j++) // map[x][y]=='x' || map[x][y]=='!' || map[x][y]=='-'
			{
				if(map[i][j]==1) //print user's ships
					System.out.print("@");
				/*else if(map[i][j]==2) //do not print computer's ships
					System.out.print("$");	*/
				else if(map[i][j]=='x')
					System.out.print("x");
				else if(map[i][j]=='!') // print killed enemy ships
					System.out.print("!");
				else if(map[i][j]=='-') // print missed shots
					System.out.print("-");
				else
					System.out.print(" ");
			}
			System.out.println("|"+i);
		}
		
		/*Prints last part of the map*/
		System.out.print("  ");
		for (int i = 0; i < map.length; i++) 
			System.out.print(i);
		System.out.println("  ");

		System.out.println("---------------");
		System.out.println("Your ships: "+ships[0]+"   |   Computer's ships: "+ships[1]);
		System.out.println("---------------");
	}
	public static void deployShips(char[][] map)
	{
		
		/*Deploy ships for user*/
		Scanner input = new Scanner(System.in);
		
		System.out.println("Deploy your ships:");
		for (int i = 0; i < 5; i++) // Deploy 5 ships for user
		{
			System.out.print("Enter X coordinate for your "+ (i+1) +". ship: ");
			int x = input.nextInt();
			System.out.print("Enter Y coordinate for your "+ (i+1) +". ship: ");
			int y = input.nextInt();
			
			if(x<0 || x>9 || y<0 || y>9 || map[x][y]==1)/*Checking if user enter valid coordinates or is there already a ship*/
			{
				System.out.println("Error! There is already a ship or you have entered wrong coordinates! (Coordinates must be between 0 and 9)");
				i--;
			}
			else
				map[x][y]=1; /*User's ship assigned to the map. User ships are assigned to "1"*/
		}
		System.out.println("---------------");
		
		/*Deploy ships for computer*/
		System.out.println("Computer is deploying ships...");
		for (int i = 0; i < 5; i++) //Deploy 5 ships for computer
		{	
			int x = (int) (Math.random()*10); // Assign a random value to a ship's x coordinate
			int y = (int) (Math.random()*10); // Assign a random value to a ship's y coordinate
			/*This Random code will return numbers between 0 and 9. So we do not need to put x>0 or x<10 etc. inside if statement*/
			
			if(map[x][y]==1 || map[x][y]==2)/*Checking if there is already a ship (either for user and computer)*/
				i--; // if there is a user's ship, then do calculations again for this particular ship again.
			else {
				map[x][y]=2; /*Computer's ship assigned to the map as "2". User ships are assigned to "1"*/
				System.out.println((i+1)+". ship DEPLOYED");
			}
		}
		
	}
	
	public static void battle(char[][] map, short[] ships) 
	{
		/*User attacks*/
		Scanner input = new Scanner(System.in);
		while(ships[0]>0 && ships[1]>0)
		{
			
		System.out.println("YOUR TURN TO ATTACK!");
		do {
			
			System.out.print("Enter X coordinate: ");
			int x = input.nextInt();
			System.out.print("Enter Y coordinate: ");
			int y = input.nextInt();
			System.out.println("---------------");
		
			if (x>=0 && x<=9 && y>=0 && y<=9) // Check if the user entered valid coordinates
			{
				if(map[x][y]==2) // if user hits one of the enemy's ship then sign computer's ship as "!"
				{
					System.out.println("Boom! You sunk a ship!");
					map[x][y]='!';
					ships[1]--; // reduce number of ships that belong to computer
				}
				else if(map[x][y]==1) // if user hits one of his/her own ship then sign user's ship as "x"
				{
					System.out.println("Oh, no! You sunk your own ship!");
					map[x][y]='x';
					ships[0]--; // reduce number of ships that belong to user
				}
				else if(map[x][y]==0) // if user misses the shot then replace the empty place with "-"
				{
					System.out.println("Sorry! You missed.");
					map[x][y]='-';
				}
				else if(map[x][y]=='x' || map[x][y]=='!' || map[x][y]=='-') // if the user shots in a place where there was already shot, give him/her another chance to shot
				{
					System.out.println("This area has already been shot! Please try again...");
					continue; //continue to loop until shot somewhere that has not been shot yet
				}
				
					System.out.println("---------------");
				break; // Valid coordinates entered, so break the loop
				
			}
			 else 
				System.out.println("Unvalid coordinates! Please enter coordinates between 0 and 9! ");
			System.out.println("---------------");
		}while(true);
		
		/*Computer attacks*/
		System.out.println("COMPUTER'S TURN TO ATTACK!");
		int x = (int) (Math.random()*10); // Take a random X coordinate
		int y = (int) (Math.random()*10); // Take a random Y coordinate
		System.out.println("---------------");
		do {
				if(map[x][y]==2) // if computer hits one of its own ship then sign computer's ship as "!"
				{
					System.out.println("Boom! Computer sunk its own ship!");
					map[x][y]='!';
					ships[1]--; // reduce number of ships that belong to computer
				}
				else if(map[x][y]==1) // if computer hits one of enemy's ship then sign user's ship as "x"
				{
					System.out.println("Boom! Computer sunk your ship!");
					map[x][y]='x';
					ships[0]--; // reduce number of ships that belong to user
				}
				else if(map[x][y]==0) // if computer misses the shot then replace the empty place with "-"
				{
					System.out.println("Computer missed.");
					map[x][y]='-';
				}
				else if(map[x][y]=='x' || map[x][y]=='!' || map[x][y]=='-') // if the computer shots in a place where there was already shot, give it another chance to shot
				{
					continue; //continue to loop until shot somewhere that has not been shot yet
				}
				
				System.out.println("---------------");
			break; // A valid place came, so break the loop
			
		}while(true);
		printMap(map,ships);
		}
	}
	
	public static void congrats(short[] ships)
	{
		if (ships[1]==0) 
			System.out.println("Congtulations! You won!");
		
		else
			System.out.println("Computer won!");
	}
	
}
