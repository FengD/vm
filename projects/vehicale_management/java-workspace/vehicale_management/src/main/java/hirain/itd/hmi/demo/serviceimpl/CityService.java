package hirain.itd.hmi.demo.serviceimpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import hirain.itd.hmi.demo.bean.City;
import hirain.itd.hmi.demo.bean.vo.PageBean;
import hirain.itd.hmi.demo.dao.CarMapper;
import hirain.itd.hmi.demo.dao.CityMapper;
import hirain.itd.hmi.demo.service.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService implements ICityService {
    @Autowired
    private CityMapper cityMapper;
    @Autowired
    private PageService pageService;
    @Autowired
    private CarMapper carMapper;

    @Override
    public int insert(String name)
    {
        return cityMapper.insert(name);
    }

    @Override
    public int updateCityById(int id,String name) throws Exception
    {
        return cityMapper.updateCityById(name,id);
    }

    @Override
    public int deleteCityById(int id) throws Exception
    {
        carMapper.deleteCarByCityId(id);
        return cityMapper.deleteCityById(id);
    }

    @Override
    public PageBean selectAllByPage(String name,int pageCode,int pageSize) throws Exception
    {
        System.out.println(name+pageCode+pageSize);
        PageHelper.startPage(pageCode,pageSize);
        List<City> all=cityMapper.selectAll(name);
        Page<City> p=(Page<City>)all;
        return pageService.getBeanByPage(p,pageCode,pageSize);
    }

    @Override
    public int ifExistCityName(String name) throws  Exception
    {
        int result=cityMapper.ifExistCityName(name);
        return result;
    }

}
