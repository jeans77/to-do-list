// ToDoItemRepository.java
package com.libertymutual.goforcode.todolist.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;

import com.libertymutual.goforcode.todolist.models.ToDoItem;
import org.springframework.stereotype.Service;

import com.libertymutual.goforcode.todolist.models.ToDoItem;

@Service
public class ToDoItemRepository {

	File inputFile = new File("C://Users//n0176384//dev/to-do-list/toDoList.csv");
	File OutputFile = new File("C://Users//n0176384//dev/to-do-list/toDoList.csv");

	List<ToDoItem> items;
	int itemId;

	/**
	 * Get all the items from the file.
	 * 
	 * @return
	 * @return A list of the items. If no items exist, returns an empty list.
	 */

	public List<ToDoItem> getAll() {
		items = new ArrayList<ToDoItem>();
		itemId = 1;
		ToDoItem toDoItem = null;
		Reader fr = null;
		try {
			fr = new FileReader(inputFile);
			Iterable<CSVRecord> tasks = CSVFormat.RFC4180.parse(fr);
			List<String> input = new ArrayList<String>();
			int i = 0;

			// Read CSV file
			for (CSVRecord task : tasks) {
				itemId++;
				toDoItem = new ToDoItem();
				String id = task.get(0);
				System.out.println("id: " + id);
				int idN = Integer.parseInt(id);
				toDoItem.setId(idN);
				String taskName = task.get(1);
				toDoItem.setText(taskName);
				Boolean isCompleteB = Boolean.valueOf(task.get(2));
				toDoItem.setComplete(isCompleteB);
				items.add(i, toDoItem);
				i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return items;
	}

	/**
	 * Assigns a new id to the ToDoItem and saves it to the file.
	 * 
	 * @param item
	 *            The to-do item to save to the file.
	 */

	public void create(ToDoItem item) {

		try (CSVPrinter printer = new CSVPrinter(new FileWriter(OutputFile, true), CSVFormat.DEFAULT)) {

			int i = 0;
			List itemRecord = new ArrayList();
			itemRecord.add(String.valueOf(itemId));
			itemRecord.add(String.valueOf(item.getText()));
			itemRecord.add(String.valueOf(false));

			printer.printRecord(itemRecord);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Gets a specific ToDoItem by its id.
	 * 
	 * @param id
	 *            The id of the ToDoItem.
	 * @return The ToDoItem with the specified id or null if none is found.
	 */
	public ToDoItem getById(int id) {
		ToDoItem toDoItem = new ToDoItem();
		Reader fr = null;

		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getId() == id) {
				toDoItem = items.get(i);
			}
		}
		System.out.println("toDoItem: " + toDoItem.getText());
		return toDoItem;
	}

	/**
	 * Updates the given to-do item in the file.
	 * 
	 * @param item
	 *            The item to update.
	 */
	public void update(ToDoItem item) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getId() == item.getId()) {
				items.get(i).setComplete(true);
			}
		}
		// System.out.println("itemsitems.size)
		try (CSVPrinter eraseFile = new CSVPrinter(new FileWriter(OutputFile, false), CSVFormat.DEFAULT);) {
			eraseFile.printRecords(new ArrayList());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (ToDoItem toDoItem : items) {
			try (CSVPrinter printer = new CSVPrinter(new FileWriter(OutputFile, true), CSVFormat.DEFAULT)) {
				List itemRecord = new ArrayList();
				itemRecord.add(String.valueOf(toDoItem.getId()));
				itemRecord.add(String.valueOf(toDoItem.getText()));
				itemRecord.add(String.valueOf(toDoItem.isComplete()));
				printer.printRecord(itemRecord);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}