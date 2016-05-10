package demo;

import controller.SAINController;
import javafx.application.Application;
import javafx.stage.Stage;
/**
 * @author 'Christopher Hayes'
 * @version 1.00
 * Date Created : May 8th 2016 05/08/2016
 * Copyright ©2016 All rights reserved 
 ******************************************************************************************************************
 */
public class Demo extends Application {
	@Override
	public void start(Stage primaryStage) {
		SAINController controller = new SAINController(primaryStage);
	}
}
