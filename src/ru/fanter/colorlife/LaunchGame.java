package ru.fanter.colorlife;

import javax.swing.*;

public class LaunchGame {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GameWindow();
			}
		});
	}
}