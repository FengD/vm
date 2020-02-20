package hirain.itd.hmi.demo.service;

import hirain.itd.hmi.demo.bean.vo.PageBean;

public interface IProjectService {
    int insert(String name);

    int updateProjectById(int id,String name) throws Exception;

    int deleteProjectById(int id) throws Exception;

    PageBean selectAllByPage(String name,int pageCode, int pageSize) throws Exception;

    int ifExistProjectName(String name) throws  Exception;
}
