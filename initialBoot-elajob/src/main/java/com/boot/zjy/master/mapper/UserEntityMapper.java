package com.boot.zjy.master.mapper;

import com.boot.zjy.user.entity.UserEntity;
import com.boot.zjy.user.entity.UserEntityKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserEntityMapper {
    int deleteByPrimaryKey(UserEntityKey key);

     int insert(UserEntity record);

    int insertSelective(UserEntity record);

    UserEntity selectByPrimaryKey(UserEntityKey key);

    /**
     * 查询list运用分片
     * @param UserEntityKey key
     * @return
     */
    List<UserEntity> selectListWithzone(UserEntityKey key);

    int updateByPrimaryKeySelective(UserEntity record);

    int updateByPrimaryKey(UserEntity record);
}