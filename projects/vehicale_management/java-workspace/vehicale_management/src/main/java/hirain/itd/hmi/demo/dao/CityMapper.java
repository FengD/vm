package hirain.itd.hmi.demo.dao;


import hirain.itd.hmi.demo.bean.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CityMapper {
    int insert(@Param("name") String name);

    int updateCityById(@Param("name") String name, @Param("id") int id);

    int deleteCityById(int id);

    List<City> selectAll(@Param("name") String name);

    int ifExistCityName(@Param("name") String name);
}
