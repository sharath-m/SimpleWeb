package uk.co.malavalli.services.todo;

import java.util.List;

public interface Task {
	public List<TodoItem> getTasks();

	public void addTasks(List<TodoItem> todoList);
}
