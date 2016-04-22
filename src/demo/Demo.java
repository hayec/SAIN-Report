package demo;

import controller.SAINController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Demo extends Application 
{

	@Override
	public void start(Stage primaryStage)
	{
		SAINController controller = new SAINController(primaryStage);
	}
}
