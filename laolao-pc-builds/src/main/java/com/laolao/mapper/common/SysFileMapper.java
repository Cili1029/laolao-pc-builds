package com.laolao.mapper.common;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface SysFileMapper {
    void insert(List<String> urls);

    void update(List<String> urls);

    @Select("select url from sys_file where status = 0 and created_at < #{expire}")
    List<String> selectExpireFiles(LocalDateTime expire);

    @Delete("delete from sys_file where status = 0 and created_at < #{expire}")
    void delete(LocalDateTime expire);
}
