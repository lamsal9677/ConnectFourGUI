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

//importing all the needed libraries to run the application
import java.util.Optional;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
/*
 * Class ConnectFour is responsible for running the game Connect Four both the Front End and the Backend
 */
public class ConnectFour extends Application 
{
	public char Turn = 'Y'; //initialize the starting player to Yellow
	private Cell[][] cell =  new Cell[6][7];

	@Override
	public void start(Stage primaryStage) 
	{
		GridPane grid = new GridPane();
		
		for (int i = 0; i < 6; i++)
		      for (int j = 0; j < 7; j++)
		    	  grid.add(cell[i][j] = new Cell(primaryStage), j, i);
		
		BorderPane borderPane = new BorderPane();
	    Button button1 = new Button("NEW GAME");
		HBox hbox = new HBox(button1);
		borderPane.setTop(hbox);
		borderPane.setBottom(grid);		
		
		button1.setOnMouseClicked(new EventHandler<MouseEvent>() 
		{
			@Override 
	        public void handle(MouseEvent event) 
			{        			  
				Alert alert = new Alert(AlertType.CONFIRMATION);
	        	alert.setTitle("Connect Four");
	        	alert.setHeaderText("A NEW GAME HAS BEEN REQUESTED");
	        	alert.setContentText("DO YOU WANT TO PLAY A NEW GAME?");

	        	Optional<ButtonType> result = alert.showAndWait();
	        	
	        	if (result.get() == ButtonType.OK)
	        	{
	        		for(int i=0;i<7;i++) 
	        		{
	        			for(int j=0;j<6;j++) 
	        			{
	        				cell[j][i].setToken(' ');
	        				cell[j][i].ClearBoard();
	        				Turn = 'Y';
	        			}
	        		}
	        	} 
	        	
	        	else 
	        	{
	        		alert.close();   
	        		primaryStage.close();
	        	}
			}
		});
		    
		Scene scene = new Scene(borderPane, 650, 670);
	    primaryStage.setTitle("Connect Four"); 
	    primaryStage.setScene(scene);
	    primaryStage.setResizable(false);
	    primaryStage.show();    	    
	}	
	
	public boolean BoardisFull() 
	{
		/*
		 * Method BoardIsFull checks whether the whole board is full or not
 		 * Then, it returns the result as a boolean: True representing Full 
		 */
		
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 7; j++)
				if (cell[i][j].getToken() == ' ')
					return false;
	    return true;
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
		return true;
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
							cell[i][j].ClearBoard();//clear the node at cell[i][j] and replace with the flashing one
							cell[i][j].RedFlash();
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
							cell[i][j].YellowFlash();
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
		private char token = ' ';
		public Cell(Stage primaryStage) 
		{
			DropShadow dropShadow = new DropShadow();
		   	dropShadow.setRadius(10.0);
		    dropShadow.setOffsetX(3.0);
		    dropShadow.setOffsetY(3.0);
		    dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
		    	 
			setStyle("-fx-border-color: black"); 
			this.setPrefSize(100, 100);
			Circle circle = new Circle(50,50,40, Color.WHITE);
			circle.setStroke(Color.BLACK);
			Rectangle rectangle = new Rectangle(100,100,Color.BLUE);
		 	circle.setEffect(dropShadow);
			getChildren().add(rectangle);
			getChildren().add(circle);
		      
		   		      
			this.setOnMouseMoved(new EventHandler<MouseEvent>() 
			{
			   	@Override
			   	public void handle(MouseEvent event) 
			   	{
			   		if (Turn == 'Y') 
			   		{
			   			circle.setFill(Color.YELLOW);
			   		}
		           
			   		if (Turn == 'R') 
			   		{
			   			circle.setFill(Color.RED);
			   		}
			   	  }
			});
		      
			this.setOnMouseExited(new EventHandler<MouseEvent>() 
			{
				@Override 
			    public void handle(MouseEvent event) 
			    {
			      	  circle.setFill(Color.WHITE);
			    }
			});
			      
			this.setOnMouseClicked(new EventHandler<MouseEvent>()
			{
		    	@Override
	            public void handle(MouseEvent t) 
		    	{
			         if (Turn == 'Y') 
			         {
			        	 circle.setFill(Color.RED);
			         }
				         
			         if (Turn == 'R')
				     {
				       	 circle.setFill(Color.YELLOW);
			         }
		            	
		           	int column = (((int)t.getSceneX())/100)%100;
		            	
		          	if (Turn != ' ') 
		           	{
			    		if (ColumnIsFull(column)) 
			    		{	
			    			Alert alert = new Alert(AlertType.INFORMATION);
				    		alert.setTitle("Connect Four");
				    		alert.setHeaderText("THE SELECTED COLUMN IS FULL");
				   			alert.setContentText("PLEASE SELECT ANOTHER COLUMN");
				   			alert.showAndWait();
				   			Turn = (Turn == 'Y') ? 'R' : 'Y';
			     		}
				    		
			    		else 
			    		{
			    			dropDisk(Turn,column);				    		}
		            	}
				    	
		          	if (BoardisFull()) 
		          	{			
				    	Alert alert = new Alert(AlertType.CONFIRMATION);
		        		alert.setTitle("Connect Four");
		        		alert.setHeaderText("THE BOARD IS FULL");
		        		alert.setContentText("DO YOU WANT TO PLAY A NEW GAME?");

		        		Optional<ButtonType> result = alert.showAndWait();
		        			
		        		if (result.get() == ButtonType.OK)
		       			{
		       				  Turn = ' ';
		       				  for(int i=0;i<7;i++) 
		       				  {
		       	        		  for(int j=0;j<6;j++) 
		       	        		  {
		       	        			  cell[j][i].setToken(' ');
		       	        			  cell[j][i].ClearBoard();
	        	        			  Turn = 'Y';
	        	        		  }
		       				  }
		        		} 
		        	
		        		else 
		        		{
		        			  Turn = ' ';
		        		      alert.close();
		       			      primaryStage.close();
		       			}
				    } 
				    	
		          	if (isWinner()) 
				    {
				   		Alert alert = new Alert(AlertType.CONFIRMATION);
		        		alert.setTitle("Connect Four");
		       			alert.setHeaderText(Turn + " HAS WON THE GAME");
		       			alert.setContentText("DO YOU WANT TO PLAY A NEW GAME?");
		        			  
		       			Optional<ButtonType> result = alert.showAndWait();
		        			  
		       			if (result.get() == ButtonType.OK)
		       			{
		       				  Turn = 'Y';
		       				  for(int i=0;i<7;i++) 
		       				  {
	        	        		  for(int j=0;j<6;j++) 
	        	        		  {
		       	        			  cell[j][i].setToken(' ');
		       	        			  cell[j][i].ClearBoard();
		       	        		  }
	        				  }		  
		        		}
		        			
		       			else 
		        		{
		        			Turn = ' ';
		        			alert.close();
		       				primaryStage.close();
		       			}
				   }
				    	
		       		else
		       		{
			    		Turn = (Turn == 'Y') ? 'R' : 'Y';
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