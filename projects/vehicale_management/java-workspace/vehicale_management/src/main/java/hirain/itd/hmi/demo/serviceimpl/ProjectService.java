package hirain.itd.hmi.demo.serviceimpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import hirain.itd.hmi.demo.bean.Project;
import hirain.itd.hmi.demo.bean.vo.PageBean;
import hirain.itd.hmi.demo.dao.CarMapper;
import hirain.itd.hmi.demo.dao.ProjectMapper;
import hirain.itd.hmi.demo.dao.RouteMapper;
import hirain.itd.hmi.demo.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService implements IProjectService {
    @Autowired
    private ProjectMapper ProjectMapper;
    @Autowired
    private PageService pageService;
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private RouteMapper routeMapper;

    @Override
    public int insert(String name)
    {
        return ProjectMapper.insert(name);
    }

    @Override
    public int updateProjectById(int id,String name) throws Exception
    {
        return ProjectMapper.updateProjectById(name,id);
    }

    @Override
    public int deleteProjectById(int id) throws Exception
    {
        carMapper.deleteCarByProjectId(id);
        routeMapper.deleteRouteByProjectId(id);
        return ProjectMapper.deleteProjectById(id);
    }

    @Override
    public PageBean selectAllByPage(String  name,int pageCode,int pageSize) throws Exception
    {
        PageHelper.startPage(pageCode,pageSize);
        List<Project> all=ProjectMapper.selectAll(name);
        Page<Project> p=(Page<Project>)all;
        return pageService.getBeanByPage(p,pageCode,pageSize);
    }

    @Override
    public int ifExistProjectName(String name) throws  Exception
    {
        int result=ProjectMapper.ifExistProjectName(name);
        return result;
    }

}
