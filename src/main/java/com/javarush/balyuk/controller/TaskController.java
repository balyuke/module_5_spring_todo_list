package com.javarush.balyuk.controller;

import com.javarush.balyuk.domain.Task;
import com.javarush.balyuk.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;

@Controller
@RequestMapping("/")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    //@GetMapping("/")
    public String tasks(Model model,
                            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit
                            ) {

        // формула расчета offset:
        // offset = (page - 1) * limit
        List<Task> tasks = taskService.getAll((page - 1) * limit, limit);
        model.addAttribute("tasks", tasks);
        model.addAttribute("current_page", page);
        int totalPages = (int) Math.ceil(1.0 * taskService.getAllCount() / limit);
        // добавляем список кнопок, если кол-во задач, больше чес на одну страницу (>10)
        if (totalPages > 1) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("page_numbers", pageNumbers);
        }
        return "tasks";
    }

    @PostMapping("/{id}")
    public String edit(Model model,
                     @PathVariable Integer id,
                     @RequestBody TaskInfo info){
        if(isNull(id) || id <=0){
            throw new RuntimeException("Invalid task id");
        }

        Task task = taskService.edit(id, info.getDescription(), info.getStatus());

        return tasks(model, 1, 10);
    }

    @PostMapping("/")
    public String add(Model model,
                     @RequestBody TaskInfo info){
        Task task = taskService.create(info.getDescription(), info.getStatus());

        return tasks(model, 1, 10);
    }

    @DeleteMapping("/{id}")
    public String delete(Model model,
                         @PathVariable Integer id){
        if(isNull(id) || id <=0){
            throw new RuntimeException("Invalid task id");
        }

        taskService.delete(id);

        //return "tasks";
        return tasks(model, 1, 10);
    }

}
