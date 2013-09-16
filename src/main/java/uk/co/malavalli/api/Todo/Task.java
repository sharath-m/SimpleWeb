package uk.co.malavalli.api.Todo;

import java.util.List;

public interface Task {
	public List<TodoItem> getTasks();

	public void addTasks(List<TodoItem> todoList);
}
