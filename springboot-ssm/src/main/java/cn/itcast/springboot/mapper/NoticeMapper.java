package cn.itcast.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cn.itcast.springboot.pojo.Notice;

@Mapper
public interface NoticeMapper {
	//查询所有公告
	@Select("select * from notice")
	public List<Notice> findAll();
	
	//统计查询
	public Long count();
	
	//分页查询公告
	public List<Notice> findByPage(@Param("page") Integer page,@Param("rows") Integer rows);
}
