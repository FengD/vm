package hirain.itd.hmi.demo.dao;

import hirain.itd.hmi.demo.bean.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProjectMapper {
    int insert(@Param("name") String name);

    int updateProjectById(@Param("name") String name, @Param("id") int id);

    int deleteProjectById(int id);

    List<Project> selectAll(@Param("name") String name);

    int ifExistProjectName(@Param("name") String name);
}
