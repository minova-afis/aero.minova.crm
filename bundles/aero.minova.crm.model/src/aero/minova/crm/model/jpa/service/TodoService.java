package aero.minova.crm.model.jpa.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import aero.minova.crm.model.jpa.Todo;

public interface TodoService {

	void getTodos(Consumer<List<Todo>> todosConsumer);

	boolean saveTodo(Todo newTodo);

	Optional<Todo> getTodo(int id);

	boolean deleteTodo(int id);

	void printTodos();
}