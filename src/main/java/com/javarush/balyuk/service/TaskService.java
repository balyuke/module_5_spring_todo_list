package com.javarush.balyuk.service;

import com.javarush.balyuk.dao.TaskDAO;
import com.javarush.balyuk.domain.Status;
import com.javarush.balyuk.domain.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class TaskService {

    private final TaskDAO taskDAO;

    public TaskService(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    // Аннотация @Transactional не ставится, так как содержимое метода включает только одну инструкцию sql/транзакцию
    // которая и так помечена @Transactional
    public List<Task> getAll(int offset, int limit) {
        return taskDAO.getAll(offset, limit);
    }

    // Аннотация @Transactional не ставится, так как содержимое метода включает только одну инструкцию sql/транзакцию
    // которая и так помечена @Transactional
    public int getAllCount() {
        return taskDAO.getAllCount();
    }

    // Этот метод отмечен аннотацией @Transactional, так как редактирование состоит из двух транзакций: Поиска Task и непосредственно ее Редактирование
    @Transactional
    public Task edit(int id, String description, Status status){
        Task task = taskDAO.getById(id);

        if(isNull(task)) {
            throw new RuntimeException("Task not found");
        }

        task.setDescription(description);
        task.setStatus(status);
        taskDAO.saveOrUpdate(task);

        return task;

    }

    // Аннотация @Transactional не ставится, так как содержимое метода включает только одну инструкцию sql/транзакцию
    // которая и так помечена @Transactional
    public Task create(String description, Status status){
        Task task = new Task();

        task.setDescription(description);
        task.setStatus(status);
        taskDAO.saveOrUpdate(task);

        return task;
    }


    // Этот метод отмечен аннотацией @Transactional, так как редактирование состоит из двух транзакций: Поиска Task и непосредственно ее Удаление
    @Transactional
    public void delete(int id){
        Task task = taskDAO.getById(id);

        if(isNull(task)) {
            throw new RuntimeException("Task not found");
        }

        taskDAO.delete(task);
    }


}
