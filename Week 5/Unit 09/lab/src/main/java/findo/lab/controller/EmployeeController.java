package findo.lab.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import findo.lab.model.Employee;
import findo.lab.service.EmployeeService;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/list")
    public String listEmployees(Model theModel, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 10;
        List<Employee> theEmployees = employeeService.findPaginated(page, pageSize);
        long totalEmployees = employeeService.countEmployees();

        theModel.addAttribute("employees", theEmployees);
        theModel.addAttribute("currentPage", page);
        theModel.addAttribute("totalEmployees", totalEmployees);
        theModel.addAttribute("totalPages", (int) Math.ceil((double) totalEmployees / pageSize));

        return "employees/list-employees";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {

        // create model attribute to bind form data
        Employee theEmployee = new Employee();

        theModel.addAttribute("employee", theEmployee);

        return "employees/employee-form";
    }

    @PostMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("employeeId") String id,
                                    Model theModel) {

        // get the employee from the service
        Employee theEmployee = employeeService.findById(id);

        // set employee as a model attribute to pre-populate the form
        theModel.addAttribute("employee", theEmployee);

        // send over to our form
        return "employees/employee-form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee theEmployee) {

        // save the employee
        employeeService.save(theEmployee);

        // use a redirect to prevent duplicate submissions
        return "redirect:/employees/list";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("employeeId") String id) {

        // delete the employee
        employeeService.deleteById(id);

        // redirect to /employees/list
        return "redirect:/employees/list";

    }

    @PostMapping("/upload")
    public String uploadCSVFile(@RequestParam("file") MultipartFile file) {
        employeeService.uploadCSVFile(file);
        return "redirect:/employees/list";
    }
    
}
