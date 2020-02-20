package hirain.itd.hmi.demo.controller;

import hirain.itd.hmi.demo.bean.vo.PageBean;
import hirain.itd.hmi.demo.bean.Project;
import hirain.itd.hmi.demo.service.IProjectService;
import hirain.itd.hmi.demo.utils.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("project")
public class ProjectController {
    @Autowired
    private IProjectService projectService;

    @PostMapping("")
    @ApiOperation(value = "insert project" ,notes = "insert project")
    public Object insert(Project project)
    {
        Response response;
        try {
            projectService.insert(project.getName());
            response=new Response("success", HttpStatus.OK);
        }catch (Exception e)
        {
            e.printStackTrace();
            response=new Response("fail",HttpStatus.FOUND);
        }
        return response.getResponse();
    }

    @PutMapping("")
    @ApiOperation(value = "update project",notes = "update project")
    public Object update(Project project)
    {
        Response response;
        try {
            projectService.updateProjectById(project.getProject_id(),project.getName());
            response=new Response("success", HttpStatus.OK);
        }catch (Exception e)
        {
            e.printStackTrace();
            response=new Response("fail",HttpStatus.FOUND);
        }
        return response.getResponse();
    }

    @DeleteMapping("{projectId}")
    @ApiOperation(value = "delete project by id", notes = "delete project by id")
    public Object delete(@PathVariable int projectId)
    {
        Response response;
        try {
            projectService.deleteProjectById(projectId);
            response = new Response(null, HttpStatus.OK);
        } catch (Exception e) {
            response = new Response(null, HttpStatus.NOT_FOUND);
        }
        return response.getResponse();
    }

    @GetMapping("")
    @ApiOperation(value = "get all project by paging", notes = "get all project by paging")
    public Object getAllCity(@RequestParam(value = "name", required = false, defaultValue = "") String  name,
                             @RequestParam(value = "pageCode", required = false, defaultValue = "1") int pageCode,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize) {
        Response response;
        PageBean pageBean;
        try {
            pageBean = projectService.selectAllByPage(name,pageCode, pageSize);
            response = new Response(pageBean, HttpStatus.OK);
        } catch (Exception e) {
            response = new Response(null, HttpStatus.NOT_FOUND);
        }
        return response.getResponse();
    }

    @GetMapping("ifExistCityName")
    @ApiOperation(value = "check if exist city name", notes = "0:not exist  1:existed")
    public Object ifExistCityName(@RequestParam String  name )
    {
        Response response;
        int result;
        try {
            result= projectService.ifExistProjectName(name);
            response = new Response(result, HttpStatus.OK);
        } catch (Exception e) {
            response = new Response(null, HttpStatus.NOT_FOUND);
        }
        return response.getResponse();
    }
}
