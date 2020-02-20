package hirain.itd.hmi.demo.service;

import hirain.itd.hmi.demo.bean.vo.PageBean;

public interface ICityService {

    int insert(String name);

    int updateCityById(int id,String name) throws Exception;

    int deleteCityById(int id) throws Exception;

    PageBean selectAllByPage( String name,int pageCode, int pageSize) throws Exception;

    int ifExistCityName(String name) throws Exception;
}
