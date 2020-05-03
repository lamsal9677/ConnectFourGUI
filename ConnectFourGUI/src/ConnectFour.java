/*	
 * ConnectFour.java
 * Project 3 Honors
 * 
 * This file includes a program for the game Connect four: a 2 player game involving a Yellow:Y player and Red:R player
 * where they compete with each other in order to have a sequence of 4 Y's or R's respectively either horizontally, vertically
 * or diagonally to win the game.
 * 
 * I have made use of JavaFX to create the GUI of the game.
 *  
 * 	Sanskar Lamsal
 * 	EECS 1510 - Intro to Object Oriented
 * 	Dr. Joseph Hobbs
 */

//importing all important essential libraries
import java.util.Optional;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ConnectFour extends Application 
{
	/*
	 * Class ConnectFour is responsible for running the game Connect Four both the Front End and the Backend
	*/
	public char Turn = 'Y'; //initialize the starting player to Yellow
	private Cell[][] cell =  new Cell[6][7];//Calls the cell class to make both the GUI and backend of the game

	@Override
	public void start(Stage primaryStage) 
	{
		GridPane grid = new GridPane();//make a new GridPane called grid to make the Grid design for the game
		final StackPane stack = new StackPane();//Make a new stackPane to create a stack of HBOX to add to the top
		BorderPane borderPane = new BorderPane();//make a new BorderPane to divide the whole PrimaryStage to TOP,CENTER AND BOTTOM
		
		for (int i = 0; i < 6; i++)
		      for (int j = 0; j < 7; j++)
		    	  grid.add(cell[i][j] = new Cell(primaryStage), j, i);//make the ixj number of Cell object
		
		Button button1 = new Button("NEW GAME");//new Button to start a new game
		
		Text t = new Text("CONNECT FOUR");//Header of the Game
		t.setFont(Font.font ("Algerian", 35));//set Header FONT to Algerian
		t.setFill(Color.BLACK);//Set the text color to Black
		
		DropShadow ds = new DropShadow();//Create a new dropShadow for Text to make it stand out
		ds.setOffsetY(4.0f);
		ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
		t.setEffect(ds);//set effects to Text object

		HBox hboxTop = new HBox(t);//Make a new Hbox for the top
		hboxTop.setPadding(new Insets(23, 0, 0,185));//set padding around the box
		
		HBox hboxBottom = new HBox(button1);//Make a new Hbox for the bottom
		hboxBottom.setPadding(new Insets(26, 0, 0, 550));//set padding around the box
			
		stack.getChildren().addAll(hboxTop,hboxBottom);//Add hbox2 and Hbox to the stack
		
		borderPane.setTop(stack);//Set stack to the Top of the borderPane
		borderPane.setBottom(grid);//Set grid i.e. the board to the Bottom of borderPane
		
		button1.setOnMouseClicked(new EventHandler<MouseEvent>() 
		{
			@Override 
	        public void handle(MouseEvent event) //this is the event handler for NEW GAME button
			{        			  
				Alert alert = new Alert(AlertType.CONFIRMATION);//Make a new Confirmation AlertBox
	        	alert.setTitle("Connect Four");//Set the titile to Connect Four
	        	alert.setHeaderText("A NEW GAME HAS BEEN REQUESTED");//Message for when the User wants to start a new game
	        	alert.setContentText("DO YOU WANT TO PLAY A NEW GAME?");

	        	Optional<ButtonType> result = alert.showAndWait();//Show the AlertBox to the user
	        	
	        	if (result.get() == ButtonType.OK)//If user clicks the OK Button
	        	{
	        		for(int i=0;i<7;i++) 
	        		{
	        			for(int j=0;j<6;j++) 
	        			{
	        				cell[j][i].setToken(' ');//Clear the 2D array
	        				cell[j][i].ClearBoard();//Clear all nodes from the board
	        				Turn = 'Y';//Initialize the current player to Y
	        			}
	        		}
	        	} 
	        	
	        	else 
	        	{
	        		alert.close();//Closes the alert box
	        		primaryStage.close();//Closes the primary stage
	        	}
			}
		});
		    
		Scene scene = new Scene(borderPane, 650, 670); //makes a new scene of 650x670 and then adds borderPane to it
	    primaryStage.setTitle("Connect Four"); //Set the Title for the PrimaryStage
	    primaryStage.setScene(scene);	//set whole scene to the PrimaryStage
	    primaryStage.setResizable(false);//To stop the user from changing the scene size because the functioning of the app depends on fixed cordinates
	    primaryStage.show();//show the Primary Stage 	    
	}	
	
	public boolean BoardisFull() 
	{
		/*
		 * Method BoardIsFull checks whether the whole board is full or not and
 		 * Then, it returns the result as a boolean: True representing Full 
		 */
		
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 7; j++)
				if (cell[i][j].getToken() == ' ')
					return false;
	    return true;//returns true if nne of the element in the array is ' '.
	}
	
	public boolean ColumnIsFull(int col) 
	{
		/*
		 * Method ColumnIsFull checks whether the user-input column is full or not
		 * Then, it returns the result as a boolean: True representing Full 
		 */
		for (int i=0;i<6;i++) 
			if (cell[i][col].getToken() == ' ') 
				return false;
		return true;//returns true if the specified column does not have a empty spot for the disk
	}
	
	public boolean isWinner() 
	{
		/*
		 *This method checks if either of the player has won the game: i.e if anyone has achieved either:
		 * a. 4 character Horizontally 
		 * b. 4 character Vertically
		 * c. 4 character Diagonally 
		 *
		 * This method returns true if the 4 tokens align in any order and 
		 * returns false if none of the conditions satisfy
		 *
		 */
		
		for(int j=0;j<4;j++) //This loop checks tokens Horizontally 
		{
			for(int i=0; i<6; i++) 
			{
				if(cell[i][j].getToken()!=' ') 
				{
					if((cell[i][j].getToken()== cell[i][j+1].getToken()&&(cell[i][j].getToken()==cell[i][j+2].getToken())&&cell[i][j].getToken()==cell[i][j+3].getToken())) 
					{
						if (cell[i][j].getToken() == 'R') 
						{		
							cell[i][j].ClearBoard();//Clear the node at cell[i][j] and replace with the flashing one
							cell[i][j].RedFlash();//Add the Red Flashing Disk
							cell[i][j+1].ClearBoard();
							cell[i][j+1].RedFlash();
							cell[i][j+2].ClearBoard();
							cell[i][j+2].RedFlash();
							cell[i][j+3].ClearBoard();
							cell[i][j+3].RedFlash();
						}
						
						if (cell[i][j].getToken() == 'Y') 
						{
							cell[i][j].ClearBoard();
							cell[i][j].YellowFlash();//Add the Yellow Flashing Disk
							cell[i][j+1].ClearBoard();
							cell[i][j+1].YellowFlash();
							cell[i][j+2].ClearBoard();
							cell[i][j+2].YellowFlash();
							cell[i][j+3].ClearBoard();
							cell[i][j+3].YellowFlash();
						}
						return true;
					}
				}
			}
		}
		
		for(int j=0; j<7; j++) //This loop checks tokens Vertically
		{
			for(int i=0;i<3;i++) 
			{
				if(cell[i][j].getToken()!=' ') 
				{
					if((cell[i][j].getToken()== cell[i+1][j].getToken()&&(cell[i][j].getToken()==cell[i+2][j].getToken())&&cell[i][j].getToken()==cell[i+3][j].getToken())) 
					{
						if (cell[i][j].getToken() == 'R') 
						{		
							cell[i][j].ClearBoard();
							cell[i][j].RedFlash();
							cell[i+1][j].ClearBoard();
							cell[i+1][j].RedFlash();
							cell[i+2][j].ClearBoard();
							cell[i+2][j].RedFlash();
							cell[i+3][j].ClearBoard();
							cell[i+3][j].RedFlash();
						}
						
						if (cell[i][j].getToken() == 'Y') 
						{
							cell[i][j].ClearBoard();
							cell[i][j].YellowFlash();
							cell[i+1][j].ClearBoard();
							cell[i+1][j].YellowFlash();
							cell[i+2][j].ClearBoard();
							cell[i+2][j].YellowFlash();
							cell[i+3][j].ClearBoard();
							cell[i+3][j].YellowFlash();
						}	
						return true;
					}
				}
			}
		}
		
		for(int j=0;j<4;j++) //this loop checks for tokens Diagonally from Left to Right up
		{
			for(int i=0; i<3; i++) 
			{
				if(cell[i][j].getToken()!=' ') 
				{
					if((cell[i][j].getToken()== cell[i+1][j+1].getToken()&&(cell[i][j].getToken()==cell[i+2][j+2].getToken())&&cell[i][j].getToken()==cell[i+3][j+3].getToken())) 
					{
						if (cell[i][j].getToken() == 'R') 
						{		
							cell[i][j].ClearBoard();
							cell[i][j].RedFlash();
							cell[i+1][j+1].ClearBoard();
							cell[i+1][j+1].RedFlash();
							cell[i+2][j+2].ClearBoard();
							cell[i+2][j+2].RedFlash();
							cell[i+3][j+3].ClearBoard();
							cell[i+3][j+3].RedFlash();
						}
						
						if (cell[i][j].getToken() == 'Y') 
						{
							cell[i][j].ClearBoard();
							cell[i][j].YellowFlash();
							cell[i+1][j+1].ClearBoard();
							cell[i+1][j+1].YellowFlash();
							cell[i+2][j+2].ClearBoard();
							cell[i+2][j+2].YellowFlash();
							cell[i+3][j+3].ClearBoard();
							cell[i+3][j+3].YellowFlash();
						}
						return true;
					}
				}
			}
		}
	
		for(int i=0;i<3; i++) //this loop checks for tokens Diagonally from Left to Right down
		{
			for(int j=3;j<7;j++) 
			{
				if(cell[i][j].getToken()!=' ') 
				{
					if((cell[i][j].getToken()== cell[i+1][j-1].getToken()&&(cell[i][j].getToken()==cell[i+2][j-2].getToken())&&cell[i][j].getToken()==cell[i+3][j-3].getToken())) 
					{
						if (cell[i][j].getToken() == 'R') 
						{		
							cell[i][j].ClearBoard();
							cell[i][j].RedFlash();
							cell[i+1][j-1].ClearBoard();
							cell[i+1][j-1].RedFlash();
							cell[i+2][j-2].ClearBoard();
							cell[i+2][j-2].RedFlash();
							cell[i+3][j-3].ClearBoard();
							cell[i+3][j-3].RedFlash();
						}
						
						if (cell[i][j].getToken() == 'Y') 
						{
							cell[i][j].ClearBoard();
							cell[i][j].YellowFlash();
							cell[i+1][j-1].ClearBoard();
							cell[i+1][j-1].YellowFlash();
							cell[i+2][j-2].ClearBoard();
							cell[i+2][j-2].YellowFlash();
							cell[i+3][j-3].ClearBoard();
							cell[i+3][j-3].YellowFlash();
						}
						return true;
					}
				}	
			}
		}		
		
		return false; //returns false if none of the conditions earlier in the method satisfy
	}

	public class Cell extends Pane 
	{
		private char token = ' ';//set the initial private token to ' '
		public Cell(Stage primaryStage) 
		{
			DropShadow dropShadow = new DropShadow();//Creating a DropShadow object to be implemented to the circle
		   	dropShadow.setRadius(10.0);
		    dropShadow.setOffsetX(3.0);
		    dropShadow.setOffsetY(3.0);
		    dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
		    	 
			this.setPrefSize(100, 100);//sets the preferred size of a single box
			
			Circle circle = new Circle(50,50,40, Color.WHITE);//making a white circle
			circle.setStroke(Color.BLACK);//Set the border of the circle to Black
			
			Rectangle rectangle = new Rectangle(100,100,Color.BLUE);//making a square in GUI
		 	circle.setEffect(dropShadow);//adding the drop shadow into the circle
		 	
			getChildren().add(rectangle);//This adds the blue Rectangle to the GUI
			getChildren().add(circle);//This adds the White Circle to the GUI where Disk drops
		      
		   		      
			this.setOnMouseMoved(new EventHandler<MouseEvent>() 
			{
				/*
				 * setOnMouseMoved is used to track the movement of the mouse and change the color
				 * of the disk according to the current player so that the GUI updates real time
				 * without having to click any button
				 */
			   	@Override
			   	public void handle(MouseEvent event) 
			   	{
			   		if (Turn == 'Y') 
			   		{
			   			circle.setFill(Color.YELLOW);//Fills the current instance of circle object to Yellow if the player is Y
			   		}
		           
			   		if (Turn == 'R') 
			   		{
			   			circle.setFill(Color.RED);//Fills the current instance of circle object to Red if the player is R
			   		}
			   	  }
			});
		      
			this.setOnMouseExited(new EventHandler<MouseEvent>() 
			{
				/*
				 * setOnMouseMoved is used to track the movement of the mouse and change the color
				 * of the disk to default i.e. 'white' when the mouse exists
				 */
				@Override 
			    public void handle(MouseEvent event) 
			    {
			      	  circle.setFill(Color.WHITE);//fills the space to white
			    }
			});
			      
			this.setOnMouseClicked(new EventHandler<MouseEvent>()
			{
				/*
				 * setOnMouseMoved is used to track the button pressed of the mouse and update the current player
				 * update the Token, check Winner, column is full, board is full and drops the current disk if
				 * the game is still in progress
				 */
		    	@Override
	            public void handle(MouseEvent t) 
		    	{
			         if (Turn == 'Y') 
			         {
			        	 circle.setFill(Color.RED);//Fills the space in RED
			         }
				         
			         if (Turn == 'R')
				     {
				       	 circle.setFill(Color.YELLOW);//FIlls the space in Yellow
			         }
		            	
		           	int column = (((int)t.getSceneX())/100)%100;//getting the current mouse position in  the scene
		            	
		          	if (Turn != ' ') //Turn is set to ' ' if the game is over so this condition is checking the status if the game is still running
		           	{
			    		if (ColumnIsFull(column)) //Runs if the column if full
			    		{	
			    			Alert alert = new Alert(AlertType.INFORMATION);//Create a new INFORMATION AlertBox
				    		alert.setTitle("Connect Four");//Title of the Alert
				    		alert.setHeaderText("THE SELECTED COLUMN IS FULL");
				   			alert.setContentText("PLEASE SELECT ANOTHER COLUMN");
				   			alert.showAndWait();//show the AlertBox
				   			Turn = (Turn == 'Y') ? 'R' : 'Y';//change the current player to avoid error while checking
			     		}
				    		
			    		else 
			    		{
			    			dropDisk(Turn,column);//drop the current disk if the game is still in progress				  		
			    		}
		            }
				    	
		          	if (BoardisFull()) //calls the BoardisFull to check whether the board is full or not
		          	{			
				    	Alert alert = new Alert(AlertType.CONFIRMATION);//make a new CONFIRMATION alert box
		        		alert.setTitle("Connect Four");
		        		alert.setHeaderText("THE BOARD IS FULL");
		        		alert.setContentText("DO YOU WANT TO PLAY A NEW GAME?");

		        		Optional<ButtonType> result = alert.showAndWait();
		        			
		        		if (result.get() == ButtonType.OK)//If user pressed the OK Button
		       			{
		       				  Turn = ' ';
		       				  for(int i=0;i<7;i++) 
		       				  {
		       	        		  for(int j=0;j<6;j++) 
		       	        		  {
		       	        			  cell[j][i].setToken(' ');//set current token to ' '
		       	        			  cell[j][i].ClearBoard();//Clears the board
	        	        			  Turn = 'Y';//set the player to Yellow
	        	        		  }
		       				  }
		        		} 
		        	
		        		else //if user Pressed any button except than OK
		        		{
		        			  Turn = ' ';//Indicates to end the game
		        		      alert.close();//closes the alert
		       			      primaryStage.close();//closes the whole GUI
		       			}
				    } 
				    	
		          	if (isWinner()) //checks if someone has won the game
				    {
				   		Alert alert = new Alert(AlertType.CONFIRMATION);//make a new CONFIRMATION AlertBox
		        		alert.setTitle("Connect Four");
		       			alert.setHeaderText(Turn + " HAS WON THE GAME");
		       			alert.setContentText("DO YOU WANT TO PLAY A NEW GAME?");
		        			  
		       			Optional<ButtonType> result = alert.showAndWait();
		        			  
		       			if (result.get() == ButtonType.OK)//if the player pressed OK
		       			{
		       				  Turn = 'Y';//set the Player to Yellow
		       				  for(int i=0;i<7;i++) 
		       				  {
	        	        		  for(int j=0;j<6;j++) 
	        	        		  {
		       	        			  cell[j][i].setToken(' ');//clears the 2D array to start again
		       	        			  cell[j][i].ClearBoard();//clears the board to start a new game
		       	        		  }
	        				  }		  
		        		}
		        			
		       			else 
		        		{
		        			Turn = ' ';//take Turn to ' ' to end game
		        			alert.close();//close the alert
		       				primaryStage.close();//closes the whole GUI
		       			}
				   }
				    	
		       		else
		       		{
			    		Turn = (Turn == 'Y') ? 'R' : 'Y';//If nothing is satisfied, game is still running so change player
			   		}
		    	}
			}); 
		}
		    
		public char getToken() 
		{
		   	/*
	    	 * Getter method for the private variable token
	    	 */
	    	return token;
		}
		    
		public void setToken(char current) 
		{
		   	/*
		   	 * Setter method for the private variable token which assigns current player to token
		   	 */
	    	token = current;
	   }
		    
		public void dropDisk(char current, int column) 
		{
			/*
			 * Method dropDisk drops the character Y or R into the array after determining the empty space from bottom to
			 * top(Like gravity would do)
			 * 
			 * It also adds Yellow and Red Disk to the GUI when needed
			 */
		   	
		   	int row=5; //this is the bottom row (to simulate the effect of gravity)
	    	if (current=='R') 
	    	{
	    		while(cell[row][column].getToken() != ' ') //if the cell is empty
	    		{
		    		row--;//if the bottom row is full, go one step up and so on and store value in row
		    	}
	    		cell[row][column].setToken('R');//add R in the 2D array
	    		cell[row][column].addRedDisk();//dropping the Red disk to the row and column
	    	}
		   	if (current=='Y') 
		   	{
		   		while(cell[row][column].getToken() != ' ') 
		   		{
		    		row--;//if the bottom row is full, go one step up and so on and store value in row
		    	}		
		   		cell[row][column].setToken('Y');//add Y in the 2D array
		   		cell[row][column].addYellowDisk();//dropping the yellow disk to the row and column
		   	}
		}
			
		Circle RedCircle = new Circle(50,50,40, Color.RED); //Red Disk
		Circle YellowCircle = new Circle(50,50,40, Color.YELLOW); //Yellow Disk
		Circle RedCircleFlash = new Circle(50,50,40, Color.RED); //Flashing Red Disk
		Circle YellowCircleFlash = new Circle(50,50,40, Color.YELLOW); //Flashing Yellow Disk
		    
	    public void addRedDisk() 
		{
	    	/*
	    	 * This method is responsible to adding Red Disk to the selected Node where necessary
	    	*/
			getChildren().add(RedCircle); 
		}
		    
		public void addYellowDisk() 
		{	
			/*
	    	 * This method is responsible to adding Yellow Disk to the selected Node where necessary
	    	*/
			getChildren().add(YellowCircle);
		}
			
		public void ClearBoard() 
		{
			/*
	    	 * This method is responsible to removing all Disks from the selected Node where necessary
	    	*/
			getChildren().remove(YellowCircle);
			getChildren().remove(RedCircle);
			getChildren().remove(YellowCircleFlash);
			getChildren().remove(RedCircleFlash);
		}
			
		public void RedFlash() 
		{
			/*
	    	 * This method is responsible to adding Red Flashing Disk to the selected Node where necessary
	    	*/
			getChildren().add(RedCircleFlash);
			FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.2), RedCircleFlash); //making the animation
		    fadeTransition.setFromValue(1.0); //peak value
		    fadeTransition.setToValue(0.2); //end value
		    fadeTransition.setCycleCount(Animation.INDEFINITE); //animation runs forever
		    fadeTransition.play(); //starts the animation
		}
			
		public void YellowFlash() 
		{
			/*
	    	 * This method is responsible to adding Yellow Flashing Disk to the selected Node where necessary
	    	*/
			getChildren().add(YellowCircleFlash);
			FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.2), YellowCircleFlash); //making the animation
		    fadeTransition.setFromValue(1.0); //peak
		    fadeTransition.setToValue(0.2); //end value
		    fadeTransition.setCycleCount(Animation.INDEFINITE); //animation runs forever
		    fadeTransition.play();//starts the animation
		}
	}
}