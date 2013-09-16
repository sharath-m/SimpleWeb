package uk.co.malavalli.Todo;

import java.util.List;

public interface Task {
	public List<TodoItem> getTasks();

	public void addTasks(List<TodoItem> todoList);
}
